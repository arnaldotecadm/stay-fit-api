package br.com.arcasoftware.stayfit.application.port.outbound.queue

import br.com.arcasoftware.stayfit.domain.HealthDataPoint

interface ExerciseSessionQueuePort {
    fun sendBatch(healthDataPoints: List<HealthDataPoint>)
}
