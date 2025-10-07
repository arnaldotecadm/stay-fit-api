package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.SleepStageServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepStagePersistencePort
import br.com.arcasoftware.stayfit.domain.SleepStage
import org.springframework.stereotype.Service

@Service
class SleepStageService(private val sleepStagePersistencePort: SleepStagePersistencePort) :
    SleepStageServicePort {
    override fun persist(sleepStage: SleepStage): SleepStage {
        return this.sleepStagePersistencePort.persist(sleepStage)
    }
}
