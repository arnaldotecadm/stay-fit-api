package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.ExerciseLocationServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLocationPersistencePort
import br.com.arcasoftware.stayfit.domain.ExerciseLocation
import org.springframework.stereotype.Service

@Service
class ExerciseLocationService(private val exerciseLocationPersistence: ExerciseLocationPersistencePort) :
    ExerciseLocationServicePort {
    override fun persist(exerciseLocation: ExerciseLocation): ExerciseLocation {
        return this.exerciseLocationPersistence.persist(exerciseLocation)
    }
}
