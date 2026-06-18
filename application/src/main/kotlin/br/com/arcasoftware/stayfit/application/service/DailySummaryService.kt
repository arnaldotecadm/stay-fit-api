package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.DailySummaryServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.DailySummaryPersistencePort
import br.com.arcasoftware.stayfit.domain.DailyActivitySummary
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DailySummaryService(private val dailySummaryPersistence: DailySummaryPersistencePort) :
    DailySummaryServicePort {
    override fun getDailySummaryEntityByDate(dateTime: LocalDate): DailyActivitySummary? {
        return this.dailySummaryPersistence.getDailySummaryEntityByDate(dateTime)
    }
}
