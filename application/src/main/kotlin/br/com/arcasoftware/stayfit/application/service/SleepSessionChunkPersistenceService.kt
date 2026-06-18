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
        dataPointService.persist(dataPoint)
        dataPoint.sessions
            .orEmpty()
            .filterIsInstance<SleepSession>()
            .forEach { sleepSession ->
                sleepStagePersistencePort.deleteByDataPointUid(sleepSession.dataPointUid)
                sleepSession.stages?.forEach { sleepStagePersistencePort.persist(it) }
                sleepSessionPersistencePort.persist(sleepSession)
            }
    }

    @Transactional
    fun persistBatch(dataPoints: List<HealthDataPoint>) {
        dataPointService.persistBatch(dataPoints)

        val sessions = dataPoints
            .flatMap { it.sessions.orEmpty() }
            .filterIsInstance<SleepSession>()

        if (sessions.isEmpty()) return

        val uids = sessions.map { it.dataPointUid }

        sleepStagePersistencePort.deleteByDataPointUidIn(uids)
        sleepSessionPersistencePort.deleteByDataPointUidIn(uids)
        sleepSessionPersistencePort.persistBatch(sessions)

        val allStages = sessions.flatMap { it.stages.orEmpty() }
        sleepStagePersistencePort.persistBatch(allStages)
    }
}
