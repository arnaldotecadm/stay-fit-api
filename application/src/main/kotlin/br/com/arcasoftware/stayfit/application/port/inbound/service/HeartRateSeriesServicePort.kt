package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.HeartRateSeries

interface HeartRateSeriesServicePort {
    fun persist(heartRateSeries: HeartRateSeries): HeartRateSeries
}
