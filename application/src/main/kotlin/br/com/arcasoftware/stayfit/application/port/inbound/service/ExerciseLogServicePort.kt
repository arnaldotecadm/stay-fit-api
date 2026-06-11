package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.ExerciseLog

fun interface ExerciseLogServicePort {
    fun persist(exerciseLog: ExerciseLog): ExerciseLog
}
