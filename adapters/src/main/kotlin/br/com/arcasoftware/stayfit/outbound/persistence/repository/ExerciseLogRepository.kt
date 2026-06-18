package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.ExerciseLogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ExerciseLogRepository : JpaRepository<ExerciseLogEntity, Long> {
    @Modifying
    @Query("DELETE FROM ExerciseLogEntity e WHERE e.dataPointUid = :uid")
    fun deleteByDataPointUid(@Param("uid") dataPointUid: UUID)

    @Modifying
    @Query("DELETE FROM ExerciseLogEntity e WHERE e.dataPointUid IN :uids")
    fun deleteByDataPointUidIn(@Param("uids") uids: Collection<UUID>)
}
