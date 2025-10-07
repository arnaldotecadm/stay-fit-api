package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.SleepStage

interface SleepStageServicePort {
    fun persist(sleepStage: SleepStage): SleepStage
}
