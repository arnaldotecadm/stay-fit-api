package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSummary

interface ExerciseSessionPersistencePort {
    fun persist(exerciseSession: ExerciseSession): ExerciseSession
    fun getBasicExerciseSessionList(): List<BasicExerciseSession>
    fun getExerciseSessionSummary(): List<ExerciseSummary>
}
