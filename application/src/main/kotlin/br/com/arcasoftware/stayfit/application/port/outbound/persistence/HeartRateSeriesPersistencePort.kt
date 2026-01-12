package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.HeartRateSeries

interface HeartRateSeriesPersistencePort {
    fun persist(heartRateSeries: HeartRateSeries): HeartRateSeries
}
