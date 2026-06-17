package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLocationPersistencePort
import br.com.arcasoftware.stayfit.domain.ExerciseLocation
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLocationMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLocationMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.ExerciseLocationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ExerciseLocationPersistenceAdapter(
    private val exerciseLocationRepository: ExerciseLocationRepository,
) : ExerciseLocationPersistencePort {
    override fun persist(exerciseLocation: ExerciseLocation): ExerciseLocation =
        this.exerciseLocationRepository
            .save(exerciseLocation.toEntity())
            .toDomain()

    @Transactional
    override fun deleteByDataPointUid(dataPointUid: UUID) {
        this.exerciseLocationRepository.deleteByDataPointUid(dataPointUid)
    }

    @Transactional
    override fun deleteByDataPointUidIn(dataPointUids: Collection<UUID>) {
        this.exerciseLocationRepository.deleteByDataPointUidIn(dataPointUids)
    }

    @Transactional
    override fun persistBatch(exerciseLocations: List<ExerciseLocation>) {
        if (exerciseLocations.isEmpty()) {
            return
        }
        this.exerciseLocationRepository.saveAll(exerciseLocations.map { it.toEntity() })
    }
}
