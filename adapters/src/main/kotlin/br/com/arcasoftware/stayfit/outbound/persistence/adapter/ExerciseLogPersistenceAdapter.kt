package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLogPersistencePort
import br.com.arcasoftware.stayfit.domain.ExerciseLog
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLogMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLogMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.ExerciseLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ExerciseLogPersistenceAdapter(
    private val exerciseLogRepository: ExerciseLogRepository,
) : ExerciseLogPersistencePort {
    override fun persist(exerciseLog: ExerciseLog): ExerciseLog =
        this.exerciseLogRepository
            .save(exerciseLog.toEntity())
            .toDomain()

    @Transactional
    override fun deleteByDataPointUid(dataPointUid: UUID) {
        exerciseLogRepository.deleteByDataPointUid(dataPointUid)
    }

    @Transactional
    override fun deleteByDataPointUidIn(dataPointUids: Collection<UUID>) {
        exerciseLogRepository.deleteByDataPointUidIn(dataPointUids)
    }

    @Transactional
    override fun persistBatch(exerciseLogs: List<ExerciseLog>) {
        if (exerciseLogs.isEmpty()) {
            return
        }
        this.exerciseLogRepository.saveAll(exerciseLogs.map { it.toEntity() })
    }
}
