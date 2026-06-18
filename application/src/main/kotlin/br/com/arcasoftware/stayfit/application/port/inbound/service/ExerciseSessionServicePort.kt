package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSummary

interface ExerciseSessionServicePort {
    fun getBasicExerciseSessionList(): List<BasicExerciseSession>
    fun getExerciseSessionSummary(): List<ExerciseSummary>
}
