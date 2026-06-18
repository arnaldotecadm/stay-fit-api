package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.DailyActivitySummary
import java.time.LocalDate

fun interface DailySummaryServicePort {
    fun getDailySummaryEntityByDate(dateTime: LocalDate): DailyActivitySummary?
}
