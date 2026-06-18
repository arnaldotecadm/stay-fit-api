package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.SleepStageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SleepStageRepository : JpaRepository<SleepStageEntity, Long> {
    @Modifying
    @Query("DELETE FROM SleepStageEntity s WHERE s.dataPointUid = :uid")
    fun deleteByDataPointUid(@Param("uid") dataPointUid: UUID)

    @Modifying
    @Query("DELETE FROM SleepStageEntity s WHERE s.dataPointUid IN :uids")
    fun deleteByDataPointUidIn(@Param("uids") uids: Collection<UUID>)
}
