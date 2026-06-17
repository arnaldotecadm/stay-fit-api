package br.com.arcasoftware.stayfit.application.port.outbound.queue

import br.com.arcasoftware.stayfit.domain.HealthDataPoint

interface SleepSessionQueuePort {
    fun sendBatch(healthDataPoints: List<HealthDataPoint>)
}
