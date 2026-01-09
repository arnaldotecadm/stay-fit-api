package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLocationPersistencePort
import br.com.arcasoftware.stayfit.domain.ExerciseLocation
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLocationMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLocationMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.ExerciseLocationRepository
import org.springframework.stereotype.Service

@Service
class ExerciseLocationPersistenceAdapter(
    private val exerciseLocationRepository: ExerciseLocationRepository
) : ExerciseLocationPersistencePort {
    override fun persist(exerciseLocation: ExerciseLocation): ExerciseLocation {
        return if (this.exerciseLocationRepository.findByDataPoint(exerciseLocation.dataPointUid) == null)
            this.exerciseLocationRepository
                .save(exerciseLocation.toEntity())
                .toDomain()
        else
            exerciseLocation
    }
}
