package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.DailyActivitySummary
import br.com.arcasoftware.stayfit.domain.DailySummary
import java.time.LocalDate

interface DailySummaryPersistencePort {
    fun persist(dailySummary: DailySummary): DailySummary
    fun getDailySummaryEntityByDate(localDate: LocalDate): DailyActivitySummary?
}
