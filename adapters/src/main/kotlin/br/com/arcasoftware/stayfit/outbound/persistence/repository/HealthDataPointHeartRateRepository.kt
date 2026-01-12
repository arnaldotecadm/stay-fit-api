package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.HearRateSeriesHealthDataPointEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface HealthDataPointHeartRateRepository : JpaRepository<HearRateSeriesHealthDataPointEntity, Long> {
    fun findByDataPointUid(dataPointUid: UUID): HearRateSeriesHealthDataPointEntity?
}
