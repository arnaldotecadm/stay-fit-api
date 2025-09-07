package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.ExerciseLocation

interface ExerciseLocationPersistencePort {
    fun persist(exerciseLocation: ExerciseLocation): ExerciseLocation
}
