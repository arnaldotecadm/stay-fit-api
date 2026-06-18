package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.ExerciseSessionServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseSessionPersistencePort
import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSummary
import org.springframework.stereotype.Service

@Service
class ExerciseSessionService(
    private val exerciseSessionPersistence: ExerciseSessionPersistencePort
) : ExerciseSessionServicePort {

    override fun getBasicExerciseSessionList(): List<BasicExerciseSession> {
        return this.exerciseSessionPersistence.getBasicExerciseSessionList()
    }

    override fun getExerciseSessionSummary(): List<ExerciseSummary> {
        return this.exerciseSessionPersistence.getExerciseSessionSummary()
    }
}
