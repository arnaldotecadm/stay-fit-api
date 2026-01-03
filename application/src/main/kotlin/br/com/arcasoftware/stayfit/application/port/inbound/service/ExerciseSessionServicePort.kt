package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSession

interface ExerciseSessionServicePort {
    fun persist(exerciseSession: ExerciseSession): ExerciseSession
    fun getBasicExerciseSessionList(): List<BasicExerciseSession>
}
