package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.ExerciseLogServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLogPersistencePort
import br.com.arcasoftware.stayfit.domain.ExerciseLog
import org.springframework.stereotype.Service

@Service
class ExerciseLogService(private val exerciseLogPersistence: ExerciseLogPersistencePort) :
    ExerciseLogServicePort {
    override fun persist(exerciseLog: ExerciseLog): ExerciseLog {
        return this.exerciseLogPersistence.persist(exerciseLog)
    }
}
