package br.com.arcasoftware.stayfit.application.port.outbound.queue

import br.com.arcasoftware.stayfit.domain.HealthDataPoint

interface HeartRateSeriesQueuePort {
    fun sendBatch(healthDataPoints: List<HealthDataPoint>)
}
