package br.com.arcasoftware.stayfit.inbound.rest.controller

import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.inbound.service.SleepSessionServicePort
import br.com.arcasoftware.stayfit.controller.SleepsApi
import br.com.arcasoftware.stayfit.model.HealthSleepDataPointDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepSessionMapper.toDomain
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class SleepSessionController(
    private val dataPointService: HealthDataPointServicePort,
    private val sleepSessionServicePort: SleepSessionServicePort
) : SleepsApi {
    override fun postSleepSession(healthSleepDataPointDTO: HealthSleepDataPointDTO): ResponseEntity<String> {
        println("Posting sleep session")
        this.dataPointService.persist(healthSleepDataPointDTO.toDomain())

        // persist the exercise sessions related to the activity
        healthSleepDataPointDTO.sessions?.map { dataPoint -> dataPoint.toDomain(UUID.fromString(healthSleepDataPointDTO.uid)) }
            ?.forEach { sleepSessionServicePort.persist(it) }
        return ResponseEntity.ok().build()
    }

}
