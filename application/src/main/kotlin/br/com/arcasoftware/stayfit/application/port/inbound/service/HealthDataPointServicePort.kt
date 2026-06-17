package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.HealthDataPoint

interface HealthDataPointServicePort {
    fun persist(healthDataPoint: HealthDataPoint): HealthDataPoint
    fun persistBatch(healthDataPoints: List<HealthDataPoint>)
    fun persistHeartRate(healthDataPoint: HealthDataPoint): HealthDataPoint
    fun persistHeartRateBatch(healthDataPoints: List<HealthDataPoint>)
}
