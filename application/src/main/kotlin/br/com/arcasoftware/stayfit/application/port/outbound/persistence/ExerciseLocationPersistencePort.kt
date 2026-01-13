package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.ExerciseLocation
import java.util.UUID

interface ExerciseLocationPersistencePort {
    fun persist(exerciseLocation: ExerciseLocation): ExerciseLocation
    fun deleteByDataPointUid(dataPointUid: UUID)
}
