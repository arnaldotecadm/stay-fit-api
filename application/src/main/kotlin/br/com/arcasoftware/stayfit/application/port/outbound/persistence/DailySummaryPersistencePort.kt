package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.DailySummary

interface DailySummaryPersistencePort {
    fun persist(dailySummary: DailySummary): DailySummary
}
