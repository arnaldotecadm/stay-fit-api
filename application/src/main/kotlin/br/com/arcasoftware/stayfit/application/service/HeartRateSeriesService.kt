package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.inbound.service.HeartRateSeriesServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HeartRateSeriesPersistencePort
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.domain.HeartDailySession
import br.com.arcasoftware.stayfit.domain.HeartRateSeries
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

@Service
class HeartRateSeriesService(
    private val heartRateSeriesPersistencePort: HeartRateSeriesPersistencePort,
    private val dataPointService: HealthDataPointServicePort
) : HeartRateSeriesServicePort {
    companion object {
        private const val PROCESSING_BATCH_SIZE = 5_000
    }

    private val queue = ConcurrentLinkedQueue<HealthDataPoint>()
    private var printedEmpty = false
    private val logger = LoggerFactory.getLogger(HeartRateSeriesService::class.java)

    override fun enqueue(batch: List<HealthDataPoint>) {
        if (batch.isEmpty()) {
            return
        }

        this.queue.addAll(batch)
        this.printedEmpty = false
    }

    @Scheduled(fixedDelay = 1)
    fun processQueue() {
        while (true) {
            val chunk = this.dequeueChunk(PROCESSING_BATCH_SIZE)
            if (chunk.isEmpty()) {
                return
            }

            this.dataPointService.persistHeartRateBatch(chunk)
            val heartRateSeriesBatch = chunk
                .asSequence()
                .flatMap { it.sessions.orEmpty().asSequence() }
                .filterIsInstance<HeartRateSeries>()
                .toList()

            this.heartRateSeriesPersistencePort.persistBatch(heartRateSeriesBatch)
        }
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 10)
    fun printCount() {
        if (this.queue.isNotEmpty()) {
            logger.info("Heart Rate Queue Size : {}", queue.size)
            return
        }

        if (!this.printedEmpty) {
            logger.info("Heart Rate Queue Empty")
            this.printedEmpty = true
        }
    }

    override fun getHeartDailySession(localDate: LocalDate): List<HeartDailySession> {
        return this.heartRateSeriesPersistencePort.getHeartDailySession(localDate)
    }

    private fun dequeueChunk(maxSize: Int): List<HealthDataPoint> {
        val chunk = ArrayList<HealthDataPoint>(maxSize)
        while (chunk.size < maxSize) {
            val dataPoint = this.queue.poll() ?: break
            chunk.add(dataPoint)
        }
        return chunk
    }
}
