package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.ExerciseLogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ExerciseLogRepository : JpaRepository<ExerciseLogEntity, Long>{
    fun findByDataPointUid(dataPointUid: UUID): ExerciseLogEntity?
}
