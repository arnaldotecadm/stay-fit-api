package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.ExerciseSessionEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.HealthDataPointEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExerciseSessionRepository : JpaRepository<ExerciseSessionEntity, Long>
