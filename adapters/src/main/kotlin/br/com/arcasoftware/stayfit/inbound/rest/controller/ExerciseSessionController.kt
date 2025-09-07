package br.com.arcasoftware.stayfit.inbound.rest.controller

import br.com.arcasoftware.stayfit.application.port.inbound.service.ExerciseSessionServicePort
import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.controller.ExercisesApi
import br.com.arcasoftware.stayfit.model.HealthDataPointDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseSessionMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ExerciseSessionController(
    private val dataPointService: HealthDataPointServicePort,
    private val exerciseSessionService: ExerciseSessionServicePort
) : ExercisesApi {
    override fun postExerciseSession(healthDataPointDTO: HealthDataPointDTO): ResponseEntity<String> {
        println(healthDataPointDTO.toDomain())
        this.dataPointService.persist(healthDataPointDTO.toDomain())

        // persist the exercise sessions related to the activity
        healthDataPointDTO.value.map { dataPoint -> dataPoint.toDomain(UUID.fromString(healthDataPointDTO.uid)) }
            .forEach { exerciseSessionService.persist(it) }

        return ResponseEntity.ok().build()
    }
}
