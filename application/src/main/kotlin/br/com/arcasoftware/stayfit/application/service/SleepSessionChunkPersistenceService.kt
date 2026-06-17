package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepSessionPersistencePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepStagePersistencePort
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.domain.SleepSession
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SleepSessionChunkPersistenceService(
    private val sleepSessionPersistencePort: SleepSessionPersistencePort,
    private val sleepStagePersistencePort: SleepStagePersistencePort,
    private val dataPointService: HealthDataPointServicePort
) {
    @Transactional
    fun persistDataPoint(dataPoint: HealthDataPoint) {
        this.dataPointService.persist(dataPoint)
        dataPoint.sessions
            .orEmpty()
            .filterIsInstance<SleepSession>()
            .forEach { sleepSession ->
                this.sleepStagePersistencePort.deleteByDataPointUid(sleepSession.dataPointUid)
                sleepSession.stages?.forEach { this.sleepStagePersistencePort.persist(it) }
                this.sleepSessionPersistencePort.persist(sleepSession)
            }
    }
}
