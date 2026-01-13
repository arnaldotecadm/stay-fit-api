package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.ExerciseLog
import java.util.UUID

interface ExerciseLogPersistencePort {
    fun persist(exerciseLog: ExerciseLog): ExerciseLog
    fun deleteByDataPointUid(dataPointUid: UUID)
}
