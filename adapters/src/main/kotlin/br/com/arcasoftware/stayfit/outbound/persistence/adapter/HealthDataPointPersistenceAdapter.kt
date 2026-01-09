package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HealthDataPointPersistencePort
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.HealthDataPointRepository
import org.springframework.stereotype.Service

@Service
class HealthDataPointPersistenceAdapter(
    private val healthDataPointRepository: HealthDataPointRepository
) : HealthDataPointPersistencePort {
    override fun persist(healthDataPoint: HealthDataPoint): HealthDataPoint {
        return if (this.healthDataPointRepository.findByDataPointUid(healthDataPoint.dataPointUid) == null)
            this.healthDataPointRepository
                .save(healthDataPoint.toEntity())
                .toDomain()
        else
            healthDataPoint
    }
}
