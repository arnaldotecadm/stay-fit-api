package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.SleepSessionServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepSessionPersistencePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepStagePersistencePort
import br.com.arcasoftware.stayfit.domain.SleepSession
import org.springframework.stereotype.Service

@Service
class SleepSessionService(
    private val sleepSessionPersistencePort: SleepSessionPersistencePort,
    private val sleepStagePersistencePort: SleepStagePersistencePort
) : SleepSessionServicePort {
    override fun persist(sleepSession: SleepSession): SleepSession {
        // persist all the stages of the session
        sleepSession.stages?.forEach { sleepStagePersistencePort.persist(it) }

        return this.sleepSessionPersistencePort.persist(sleepSession)
    }
}
