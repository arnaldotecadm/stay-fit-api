package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.HealthDataPoint

interface ExerciseSessionServicePort {
    fun enqueue(healthDataPoint: HealthDataPoint)
    fun getBasicExerciseSessionList(): List<BasicExerciseSession>
}
