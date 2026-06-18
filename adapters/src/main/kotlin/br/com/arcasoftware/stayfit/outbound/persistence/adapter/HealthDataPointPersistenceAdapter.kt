package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HealthDataPointPersistencePort
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toEntityHeartRate
import br.com.arcasoftware.stayfit.outbound.persistence.repository.HealthDataPointHeartRateRepository
import br.com.arcasoftware.stayfit.outbound.persistence.repository.HealthDataPointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class HealthDataPointPersistenceAdapter(
    private val healthDataPointRepository: HealthDataPointRepository,
    private val healthDataPointHeartRateRepository: HealthDataPointHeartRateRepository,
) : HealthDataPointPersistencePort {
    override fun persist(healthDataPoint: HealthDataPoint): HealthDataPoint =
        if (healthDataPointRepository.findByDataPointUid(healthDataPoint.dataPointUid) == null) {
            healthDataPointRepository.save(healthDataPoint.toEntity()).toDomain()
        } else {
            healthDataPoint
        }

    @Transactional
    override fun persistBatch(healthDataPoints: List<HealthDataPoint>) {
        if (healthDataPoints.isEmpty()) return
        val deduped = healthDataPoints.associateByTo(LinkedHashMap(healthDataPoints.size)) { it.dataPointUid }
        healthDataPointRepository.deleteByDataPointUidIn(deduped.keys)
        healthDataPointRepository.saveAll(deduped.values.map { it.toEntity() })
    }

    @Transactional
    override fun persistHeartRateBatch(healthDataPoints: List<HealthDataPoint>) {
        if (healthDataPoints.isEmpty()) return
        val deduped = healthDataPoints.associateByTo(LinkedHashMap(healthDataPoints.size)) { it.dataPointUid }
        healthDataPointHeartRateRepository.deleteByDataPointUidIn(deduped.keys)
        healthDataPointHeartRateRepository.saveAll(deduped.values.map { it.toEntityHeartRate() })
    }
}
