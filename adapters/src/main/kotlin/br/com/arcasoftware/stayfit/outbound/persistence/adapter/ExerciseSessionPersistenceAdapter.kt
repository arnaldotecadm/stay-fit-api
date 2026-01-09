package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseSessionPersistencePort
import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSession
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseSessionMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseSessionMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.ExerciseSessionRepository
import org.springframework.stereotype.Service

@Service
class ExerciseSessionPersistenceAdapter(
    private val exerciseSessionRepository: ExerciseSessionRepository
) : ExerciseSessionPersistencePort {
    override fun persist(exerciseSession: ExerciseSession): ExerciseSession {
        return if (this.exerciseSessionRepository.findByDataPointUid(exerciseSession.dataPointUid) == null)
            this.exerciseSessionRepository
                .save(exerciseSession.toEntity())
                .toDomain()
        else
            exerciseSession

    }

    override fun getBasicExerciseSessionList(): List<BasicExerciseSession> {
        return this.exerciseSessionRepository.getBasicExerciseSessionList()
            .map { it.toDomain() }
    }
}
