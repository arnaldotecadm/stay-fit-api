package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLogPersistencePort
import br.com.arcasoftware.stayfit.domain.ExerciseLog
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLogMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLogMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.ExerciseLogRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ExerciseLogPersistenceAdapter(
    private val exerciseLogRepository: ExerciseLogRepository
) : ExerciseLogPersistencePort {
    override fun persist(exerciseLog: ExerciseLog): ExerciseLog {
        return this.exerciseLogRepository
            .save(exerciseLog.toEntity())
            .toDomain()
    }

    override fun deleteByDataPointUid(dataPointUid: UUID) {
        exerciseLogRepository.deleteByDataPointUid(dataPointUid)
    }
}
