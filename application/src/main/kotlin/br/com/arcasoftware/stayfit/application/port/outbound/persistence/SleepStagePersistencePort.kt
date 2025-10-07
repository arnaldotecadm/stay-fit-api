package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.SleepStage

interface SleepStagePersistencePort {
    fun persist(sleepStage: SleepStage): SleepStage
}
