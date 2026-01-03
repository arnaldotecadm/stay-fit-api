package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSession

interface ExerciseSessionPersistencePort {
    fun persist(exerciseSession: ExerciseSession): ExerciseSession
    fun getBasicExerciseSessionList(): List<BasicExerciseSession>
}
