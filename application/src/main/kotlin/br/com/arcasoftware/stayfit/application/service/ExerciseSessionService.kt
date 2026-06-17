package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.ExerciseSessionServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseSessionPersistencePort
import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSummary
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.ArrayList
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

@Service
class ExerciseSessionService(
    private val exerciseSessionPersistence: ExerciseSessionPersistencePort,
    private val chunkPersistenceService: ExerciseSessionChunkPersistenceService
) : ExerciseSessionServicePort {
    companion object {
        private const val PROCESSING_BATCH_SIZE = 5_000
        private const val MAX_SINGLE_ITEM_RETRY = 3
    }

    private val queue = ConcurrentLinkedQueue<HealthDataPoint>()
    private val failedAttempts = ConcurrentHashMap<UUID, Int>()
    private var printedEmpty = false
    private val logger = LoggerFactory.getLogger(ExerciseSessionService::class.java)

    override fun enqueue(healthDataPoint: List<HealthDataPoint>) {
        this.queue.addAll(healthDataPoint)
        this.printedEmpty = false
    }

    @Scheduled(fixedDelay = 1)
    fun processQueue() {
        while (true) {
            val chunk = this.dequeueChunk(PROCESSING_BATCH_SIZE)
            if (chunk.isEmpty()) {
                return
            }

            this.persistChunkWithIsolation(chunk)
        }
    }

    private fun persistChunkWithIsolation(chunk: List<HealthDataPoint>) {
        try {
            this.chunkPersistenceService.persistChunk(chunk)
            chunk.forEach { this.failedAttempts.remove(it.dataPointUid) }
            return
        } catch (exception: RuntimeException) {
            if (chunk.size == 1) {
                this.handleSingleItemFailure(chunk.first(), exception)
                return
            }
        }

        val middleIndex = chunk.size / 2
        this.persistChunkWithIsolation(ArrayList(chunk.subList(0, middleIndex)))
        this.persistChunkWithIsolation(ArrayList(chunk.subList(middleIndex, chunk.size)))
    }

    private fun handleSingleItemFailure(dataPoint: HealthDataPoint, exception: RuntimeException) {
        val attempts = this.failedAttempts.merge(dataPoint.dataPointUid, 1, Int::plus) ?: 1
        if (attempts < MAX_SINGLE_ITEM_RETRY) {
            this.queue.add(dataPoint)
            logger.warn(
                "Retrying failed exercise datapoint {} (attempt {}/{}).",
                dataPoint.dataPointUid,
                attempts,
                MAX_SINGLE_ITEM_RETRY,
                exception
            )
            return
        }

        this.failedAttempts.remove(dataPoint.dataPointUid)
        logger.info(
            "Discarding exercise datapoint {} after {} failed attempts.",
            dataPoint.dataPointUid,
            attempts,
            exception
        )
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 10)
    fun printCount() {
        if (this.queue.isNotEmpty()) {
            logger.info("Exercise Queue Size : {}", queue.size)
            return
        }

        if (!this.printedEmpty) {
            logger.info("Exercise Queue Empty")
            this.printedEmpty = true
        }
    }

    private fun dequeueChunk(maxSize: Int): List<HealthDataPoint> {
        val chunk = ArrayList<HealthDataPoint>(maxSize)
        while (chunk.size < maxSize) {
            val dataPoint = this.queue.poll() ?: break
            chunk.add(dataPoint)
        }
        return chunk
    }

    override fun getBasicExerciseSessionList(): List<BasicExerciseSession> {
        return this.exerciseSessionPersistence.getBasicExerciseSessionList()
    }

    override fun getExerciseSessionSummary(): List<ExerciseSummary> {
        return this.exerciseSessionPersistence.getExerciseSessionSummary()
    }
}
