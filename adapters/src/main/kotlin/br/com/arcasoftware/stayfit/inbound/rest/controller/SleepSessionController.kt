package br.com.arcasoftware.stayfit.inbound.rest.controller

import br.com.arcasoftware.stayfit.application.port.inbound.service.SleepSessionServicePort
import br.com.arcasoftware.stayfit.controller.SleepsApi
import br.com.arcasoftware.stayfit.model.DailySleepDTO
import br.com.arcasoftware.stayfit.model.HealthSleepDataPointDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepSessionMapper.toDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@CrossOrigin("*")
class SleepSessionController(
    private val sleepSessionServicePort: SleepSessionServicePort
) : SleepsApi {
    override fun postSleepSession(healthSleepDataPointDTO: HealthSleepDataPointDTO): ResponseEntity<String> {
        println("Posting sleep session")
        this.sleepSessionServicePort.enqueue(healthSleepDataPointDTO.toDomain())
        return ResponseEntity.ok().build()
    }

    override fun getSleepStage(localDate: LocalDate): ResponseEntity<DailySleepDTO> {
        return this.sleepSessionServicePort.getDailySleepStagesSummary(localDate).let {
            ResponseEntity.ok(it.toDTO())
        } ?: ResponseEntity.noContent().build()
    }
}
