package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.ExerciseLocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExerciseLocationRepository : JpaRepository<ExerciseLocationEntity, Long> {
    fun findByDataPointUid(dataPointUid: UUID): ExerciseLocationEntity?
    fun deleteByDataPointUid(dataPointUid: UUID)
}
