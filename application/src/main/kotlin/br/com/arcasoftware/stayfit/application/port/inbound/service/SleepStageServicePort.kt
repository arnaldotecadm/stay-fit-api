package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.SleepStage

fun interface SleepStageServicePort {
    fun persist(sleepStage: SleepStage): SleepStage
}
