package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.HealthDataPoint

fun interface HeartRateSeriesServicePort {
    fun enqueue(batch: List<HealthDataPoint>)
}
