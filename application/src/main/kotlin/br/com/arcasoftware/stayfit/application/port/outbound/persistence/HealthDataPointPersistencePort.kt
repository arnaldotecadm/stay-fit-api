package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.HealthDataPoint

interface HealthDataPointPersistencePort {
    fun persist(healthDataPoint: HealthDataPoint): HealthDataPoint
    fun persistHeartRate(healthDataPoint: HealthDataPoint): HealthDataPoint
}
