package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.ExerciseLog

interface ExerciseLogPersistencePort {
    fun persist(exerciseLog: ExerciseLog): ExerciseLog
}
