package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HealthDataPointPersistencePort
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toEntityHeartRate
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HeartRateSeriesMapper.toDomain
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
        if (this.healthDataPointRepository.findByDataPointUid(healthDataPoint.dataPointUid) == null) {
            this.healthDataPointRepository
                .save(healthDataPoint.toEntity())
                .toDomain()
        } else {
            healthDataPoint
        }

    override fun persistHeartRate(healthDataPoint: HealthDataPoint): HealthDataPoint =
        if (this.healthDataPointHeartRateRepository.findByDataPointUid(healthDataPoint.dataPointUid) == null) {
            this.healthDataPointHeartRateRepository
                .save(healthDataPoint.toEntityHeartRate())
                .toDomain()
        } else {
            healthDataPoint
        }

    @Transactional
    override fun persistBatch(healthDataPoints: List<HealthDataPoint>) {
        if (healthDataPoints.isEmpty()) {
            return
        }

        val deduplicatedByDataPointUid = LinkedHashMap<UUID, HealthDataPoint>(healthDataPoints.size)
        healthDataPoints.forEach { deduplicatedByDataPointUid[it.dataPointUid] = it }
        val uniqueDataPoints = deduplicatedByDataPointUid.values.toList()

        this.healthDataPointRepository.deleteByDataPointUidIn(deduplicatedByDataPointUid.keys)
        this.healthDataPointRepository.saveAll(uniqueDataPoints.map { it.toEntity() })
    }

    @Transactional
    override fun persistHeartRateBatch(healthDataPoints: List<HealthDataPoint>) {
        if (healthDataPoints.isEmpty()) {
            return
        }

        val deduplicatedByDataPointUid = LinkedHashMap<UUID, HealthDataPoint>(healthDataPoints.size)
        healthDataPoints.forEach { deduplicatedByDataPointUid[it.dataPointUid] = it }

        val uniqueDataPoints = deduplicatedByDataPointUid.values.toList()
        this.healthDataPointHeartRateRepository.deleteByDataPointUidIn(deduplicatedByDataPointUid.keys)
        this.healthDataPointHeartRateRepository.saveAll(uniqueDataPoints.map { it.toEntityHeartRate() })
    }
}
