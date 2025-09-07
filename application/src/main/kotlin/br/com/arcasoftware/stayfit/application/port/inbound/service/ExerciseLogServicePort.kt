package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.ExerciseLog

interface ExerciseLogServicePort {
    fun persist(exerciseLog: ExerciseLog): ExerciseLog
}
