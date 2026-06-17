package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLocationPersistencePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLogPersistencePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseSessionPersistencePort
import br.com.arcasoftware.stayfit.domain.ExerciseSession
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExerciseSessionChunkPersistenceService(
    private val exerciseSessionPersistence: ExerciseSessionPersistencePort,
    private val exerciseLogPersistence: ExerciseLogPersistencePort,
    private val exerciseLocationPersistence: ExerciseLocationPersistencePort,
    private val dataPointService: HealthDataPointServicePort
) {
    @Transactional
    fun persistChunk(chunk: List<HealthDataPoint>) {
        this.dataPointService.persistBatch(chunk)
        val exerciseSessions = chunk
            .asSequence()
            .flatMap { it.sessions.orEmpty().asSequence() }
            .filterIsInstance<ExerciseSession>()
            .toList()

        val dataPointUids = exerciseSessions.asSequence().map { it.dataPointUid }.distinct().toList()
        val logs = exerciseSessions.asSequence().flatMap { it.log.orEmpty().asSequence() }.toList()
        val routes = exerciseSessions.asSequence().flatMap { it.route.orEmpty().asSequence() }.toList()

        if (dataPointUids.isNotEmpty()) {
            this.exerciseLogPersistence.deleteByDataPointUidIn(dataPointUids)
            this.exerciseLocationPersistence.deleteByDataPointUidIn(dataPointUids)
            this.exerciseSessionPersistence.persistBatch(exerciseSessions)
            this.exerciseLogPersistence.persistBatch(logs)
            this.exerciseLocationPersistence.persistBatch(routes)
        }
    }
}
