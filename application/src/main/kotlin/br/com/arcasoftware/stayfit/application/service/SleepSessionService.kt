package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.SleepSessionServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepSessionPersistencePort
import br.com.arcasoftware.stayfit.domain.DailySleep
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

@Service
class SleepSessionService(
    private val sleepSessionPersistencePort: SleepSessionPersistencePort,
    private val sleepSessionChunkPersistenceService: SleepSessionChunkPersistenceService,
) : SleepSessionServicePort {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val queue = ConcurrentLinkedQueue<HealthDataPoint>()
    private var printedEmpty = false

    override fun enqueue(healthDataPoint: List<HealthDataPoint>) {
        this.queue.addAll(healthDataPoint)
    }

    @Scheduled(fixedDelay = 100)
    fun processQueue() {
        if (this.queue.isNotEmpty()) {
            val dataPoint = this.queue.poll() ?: return
            this.sleepSessionChunkPersistenceService.persistDataPoint(dataPoint)
        }
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 10)
    fun printCount() {
        if (this.queue.isNotEmpty()) {
            logger.info("Sleep Session Queue Size : ${queue.size}")
        } else {
            if (!this.printedEmpty) {
                logger.info("Sleep Session Queue Empty")
                this.printedEmpty = true
            }
        }
    }

    override fun getDailySleepStagesSummary(localDate: LocalDate): DailySleep {
        return this.sleepSessionPersistencePort.getDailySleepStagesSummary(localDate)
    }
}
