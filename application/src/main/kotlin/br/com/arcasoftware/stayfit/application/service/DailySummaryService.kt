package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.DailySummaryServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.DailySummaryPersistencePort
import br.com.arcasoftware.stayfit.domain.DailySummary
import org.springframework.stereotype.Service

@Service
class DailySummaryService(private val dailySummaryPersistence: DailySummaryPersistencePort) :
    DailySummaryServicePort {
    override fun persist(dailySummary: DailySummary): DailySummary {
        return this.dailySummaryPersistence.persist(dailySummary)
    }
}
