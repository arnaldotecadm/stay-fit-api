package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.ExerciseLocation

fun interface ExerciseLocationServicePort {
    fun persist(exerciseLocation: ExerciseLocation): ExerciseLocation
}
