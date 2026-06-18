package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.HealthDataPointEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Repository
interface HealthDataPointRepository : JpaRepository<HealthDataPointEntity, Long> {
    fun findByDataPointUid(dataPointUid: UUID): HealthDataPointEntity?

    @Modifying
    @Query("DELETE FROM HealthDataPointEntity h WHERE h.dataPointUid IN :uids")
    fun deleteByDataPointUidIn(@Param("uids") uids: Collection<UUID>)
}
