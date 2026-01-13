package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.ExerciseSessionServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLocationPersistencePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLogPersistencePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseSessionPersistencePort
import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSession
import org.springframework.stereotype.Service

@Service
class ExerciseSessionService(
    private val exerciseSessionPersistence: ExerciseSessionPersistencePort,
    private val exerciseLogPersistence: ExerciseLogPersistencePort,
    private val exerciseLocationPersistence: ExerciseLocationPersistencePort
) : ExerciseSessionServicePort {
    override fun persist(exerciseSession: ExerciseSession): ExerciseSession {
        // persist all the logs of the session
        exerciseLogPersistence.deleteByDataPointUid(exerciseSession.dataPointUid)
        exerciseSession.log?.forEach { exerciseLogPersistence.persist(it) }

        // persist all the locations of the session
        exerciseLocationPersistence.deleteByDataPointUid(exerciseSession.dataPointUid)
        exerciseSession.route?.forEach { exerciseLocationPersistence.persist(it) }

        return this.exerciseSessionPersistence.persist(exerciseSession)
    }

    override fun getBasicExerciseSessionList(): List<BasicExerciseSession> {
        return this.exerciseSessionPersistence.getBasicExerciseSessionList()
    }
}
