package br.com.arcasoftware.stayfit.inbound.rest.controller

import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.inbound.service.SleepSessionServicePort
import br.com.arcasoftware.stayfit.controller.SleepsApi
import br.com.arcasoftware.stayfit.model.HealthSleepDataPointDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class SleepSessionController(
    private val dataPointService: HealthDataPointServicePort,
    private val sleepSessionServicePort: SleepSessionServicePort
) : SleepsApi {
    override fun postSleepSession(healthSleepDataPointDTO: HealthSleepDataPointDTO): ResponseEntity<String> {
        println("Posting sleep session")
        this.sleepSessionServicePort.enqueue(healthSleepDataPointDTO.toDomain())
        return ResponseEntity.ok().build()
    }

}
