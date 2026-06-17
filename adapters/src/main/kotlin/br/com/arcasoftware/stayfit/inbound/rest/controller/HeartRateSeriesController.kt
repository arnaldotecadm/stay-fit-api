package br.com.arcasoftware.stayfit.inbound.rest.controller

import br.com.arcasoftware.stayfit.application.port.inbound.service.HeartRateSeriesServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.queue.HeartRateSeriesQueuePort
import br.com.arcasoftware.stayfit.controller.HeartRateApi
import br.com.arcasoftware.stayfit.model.HealthHeartRateSeriesDataPointDTO
import br.com.arcasoftware.stayfit.model.HeartDailySessionDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HeartRateSeriesMapper.toDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@CrossOrigin("*")
class HeartRateSeriesController(
    private val heartRateSeriesServicePort: HeartRateSeriesServicePort,
    private val heartRateSeriesQueuePort: HeartRateSeriesQueuePort,
) : HeartRateApi {
    override fun postHearRateSeries(healthHeartRateSeriesDataPointDTO: List<HealthHeartRateSeriesDataPointDTO>): ResponseEntity<String> {
        heartRateSeriesQueuePort.sendBatch(healthHeartRateSeriesDataPointDTO.map { it.toDomain() })
        return ResponseEntity.accepted().build()
    }

    override fun getHearRateSeries(date: LocalDate): ResponseEntity<List<HeartDailySessionDTO>> {
        val heartDailySession =
            this.heartRateSeriesServicePort.getHeartDailySession(localDate = date).map { session -> session.toDTO() }
        return ResponseEntity.ok(heartDailySession)
    }
}
