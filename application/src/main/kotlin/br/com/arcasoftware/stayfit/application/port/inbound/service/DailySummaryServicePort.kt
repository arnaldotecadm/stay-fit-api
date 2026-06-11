package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.DailyActivitySummary
import br.com.arcasoftware.stayfit.domain.DailySummary
import java.time.LocalDate

interface DailySummaryServicePort {
    fun enqueue(dailySummary: DailySummary)
    fun getDailySummaryEntityByDate(dateTime: LocalDate): DailyActivitySummary?
}
