package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseSessionPersistencePort
import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSummary
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseSessionMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseSessionMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.ExerciseSessionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExerciseSessionPersistenceAdapter(
    private val exerciseSessionRepository: ExerciseSessionRepository,
) : ExerciseSessionPersistencePort {
    override fun persist(exerciseSession: ExerciseSession): ExerciseSession {
        persistBatch(listOf(exerciseSession))
        return exerciseSession
    }

    @Transactional
    override fun persistBatch(exerciseSessionBatch: List<ExerciseSession>) {
        if (exerciseSessionBatch.isEmpty()) {
            return
        }

        val dataPointUids = exerciseSessionBatch.asSequence().map { it.dataPointUid }.distinct().toList()
        this.exerciseSessionRepository.deleteByDataPointUidIn(dataPointUids)
        this.exerciseSessionRepository.saveAll(exerciseSessionBatch.map { it.toEntity() })
    }

    override fun getBasicExerciseSessionList(): List<BasicExerciseSession> =
        this.exerciseSessionRepository
            .getBasicExerciseSessionList()
            .map { it.toDomain() }

    override fun getExerciseSessionSummary(): List<ExerciseSummary> =
        this.exerciseSessionRepository
            .getExerciseSessionSummary()
            .map { it.toDomain() }
}
