package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLogPersistencePort
import br.com.arcasoftware.stayfit.domain.ExerciseLog
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLogMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLogMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.ExerciseLogRepository
import org.springframework.stereotype.Service

@Service
class ExerciseLogPersistenceAdapter(
    private val exerciseLogRepository: ExerciseLogRepository
) : ExerciseLogPersistencePort {
    override fun persist(exerciseLog: ExerciseLog): ExerciseLog =
        this.exerciseLogRepository
            .save(exerciseLog.toEntity())
            .toDomain()
}
