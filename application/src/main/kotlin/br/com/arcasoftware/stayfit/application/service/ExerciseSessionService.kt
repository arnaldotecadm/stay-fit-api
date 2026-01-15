package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.ExerciseSessionServicePort
import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLocationPersistencePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseLogPersistencePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.ExerciseSessionPersistencePort
import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.ExerciseSession
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.domain.Session
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

@Service
class ExerciseSessionService(
    private val exerciseSessionPersistence: ExerciseSessionPersistencePort,
    private val exerciseLogPersistence: ExerciseLogPersistencePort,
    private val exerciseLocationPersistence: ExerciseLocationPersistencePort,
    private val dataPointService: HealthDataPointServicePort
) : ExerciseSessionServicePort {

    private val queue = ConcurrentLinkedQueue<HealthDataPoint>()

    override fun enqueue(healthDataPoint: HealthDataPoint) {
        this.queue.add(healthDataPoint)
    }

    @Scheduled(fixedDelay = 100)
    fun processQueue() {
        if (this.queue.isNotEmpty()) {
            val dataPoint = queue.poll()
            dataPointService.persist(dataPoint)
            dataPoint.sessions?.forEach { exerciseSession -> persist(exerciseSession) }
        }
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 10)
    fun printCount(){
        if (this.queue.isNotEmpty()) {
            println("Exercise Queue Size : ${queue.size}")
        }
    }

    private fun persist(exerciseSession: Session): ExerciseSession {
        // persist all the logs of the session
        exerciseSession as ExerciseSession
        exerciseLogPersistence.deleteByDataPointUid(exerciseSession.dataPointUid)
        exerciseSession.log?.forEach { exerciseLogPersistence.persist(it) }

        // persist all the locations of the session
        exerciseLocationPersistence.deleteByDataPointUid(exerciseSession.dataPointUid)
        exerciseSession.route?.forEach { exerciseLocationPersistence.persist(it) }

        return this.exerciseSessionPersistence.persist(exerciseSession)
    }

    override fun getBasicExerciseSessionList(): List<BasicExerciseSession> {
        return this.exerciseSessionPersistence.getBasicExerciseSessionList()
    }
}
