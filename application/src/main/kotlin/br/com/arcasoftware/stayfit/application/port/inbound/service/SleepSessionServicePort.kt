package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.DailySleep
import java.time.LocalDate

fun interface SleepSessionServicePort {
    fun getDailySleepStagesSummary(localDate: LocalDate): DailySleep
}
