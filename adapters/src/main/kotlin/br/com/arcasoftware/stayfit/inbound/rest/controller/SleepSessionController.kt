package br.com.arcasoftware.stayfit.inbound.rest.controller

import br.com.arcasoftware.stayfit.application.port.inbound.service.ExerciseSessionServicePort
import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.inbound.service.SleepSessionServicePort
import br.com.arcasoftware.stayfit.controller.ExercisesApi
import br.com.arcasoftware.stayfit.controller.SleepsApi
import br.com.arcasoftware.stayfit.model.HealthExerciseDataPointDTO
import br.com.arcasoftware.stayfit.model.HealthSleepDataPointDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseSessionMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepSessionMapper.toDomain
import java.util.*

@RestController
class SleepSessionController(
    private val dataPointService: HealthDataPointServicePort,
    private val sleepSessionServicePort: SleepSessionServicePort
) : SleepsApi {
    override fun postSleepSession(healthSleepDataPointDTO: HealthSleepDataPointDTO): ResponseEntity<String> {
        println(healthSleepDataPointDTO.toDomain())
        this.dataPointService.persist(healthSleepDataPointDTO.toDomain())

        // persist the exercise sessions related to the activity
        healthSleepDataPointDTO.sessions?.map { dataPoint -> dataPoint.toDomain(UUID.fromString(healthSleepDataPointDTO.uid)) }
            ?.forEach { sleepSessionServicePort.persist(it) }
        return ResponseEntity.ok().build()
    }

}
