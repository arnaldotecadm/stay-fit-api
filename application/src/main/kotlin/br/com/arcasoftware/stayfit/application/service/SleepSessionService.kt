package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.inbound.service.SleepSessionServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepSessionPersistencePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepStagePersistencePort
import br.com.arcasoftware.stayfit.domain.DailySleep
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.domain.Session
import br.com.arcasoftware.stayfit.domain.SleepSession
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

@Service
class SleepSessionService(
    private val sleepSessionPersistencePort: SleepSessionPersistencePort,
    private val sleepStagePersistencePort: SleepStagePersistencePort,
    private val dataPointService: HealthDataPointServicePort
) : SleepSessionServicePort {

    private val queue = ConcurrentLinkedQueue<HealthDataPoint>()
    private var printedEmpty = false

    override fun enqueue(healthDataPoint: HealthDataPoint) {
        this.queue.add(healthDataPoint)
    }

    @Scheduled(fixedDelay = 100)
    @Transactional
    fun processQueue() {
        if (this.queue.isNotEmpty()) {
            val dataPoint = queue.poll()
            dataPointService.persist(dataPoint)
            dataPoint.sessions?.forEach { sleepSession -> persist(sleepSession) }
        }
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 10)
    fun printCount() {
        if (this.queue.isNotEmpty()) {
            println("Sleep Session Queue Size : ${queue.size}")
        } else {
            if (!this.printedEmpty) {
                println("Sleep Session Queue Empty")
                this.printedEmpty = true
            }
        }
    }

    private fun persist(sleepSession: Session): SleepSession {
        // persist all the stages of the session
        sleepSession as SleepSession
        sleepStagePersistencePort.deleteByDataPointUid(sleepSession.dataPointUid)
        sleepSession.stages?.forEach { sleepStagePersistencePort.persist(it) }

        return this.sleepSessionPersistencePort.persist(sleepSession)
    }

    override fun getDailySleepStagesSummary(localDate: LocalDate): DailySleep {
        return this.sleepSessionPersistencePort.getDailySleepStagesSummary(localDate)
    }
}
