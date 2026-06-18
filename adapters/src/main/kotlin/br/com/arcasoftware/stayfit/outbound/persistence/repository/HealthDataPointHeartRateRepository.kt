package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.HearRateSeriesHealthDataPointEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface HealthDataPointHeartRateRepository : JpaRepository<HearRateSeriesHealthDataPointEntity, Long> {
    fun findByDataPointUid(dataPointUid: UUID): HearRateSeriesHealthDataPointEntity?

    @Modifying
    @Query("DELETE FROM HearRateSeriesHealthDataPointEntity h WHERE h.dataPointUid IN :uids")
    fun deleteByDataPointUidIn(@Param("uids") uids: Collection<UUID>)
}
