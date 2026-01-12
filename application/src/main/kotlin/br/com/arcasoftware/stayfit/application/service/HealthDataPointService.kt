package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HealthDataPointPersistencePort
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import org.springframework.stereotype.Service

@Service
class HealthDataPointService(private val healthDataPointPersistence: HealthDataPointPersistencePort) :
    HealthDataPointServicePort {
    override fun persist(healthDataPoint: HealthDataPoint): HealthDataPoint {
        return this.healthDataPointPersistence.persist(healthDataPoint)
    }

    override fun persistHeartRate(healthDataPoint: HealthDataPoint): HealthDataPoint {
        return this.healthDataPointPersistence.persistHeartRate(healthDataPoint)
    }
}
