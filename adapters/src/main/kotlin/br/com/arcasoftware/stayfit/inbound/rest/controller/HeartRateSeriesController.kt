package br.com.arcasoftware.stayfit.inbound.rest.controller

import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.inbound.service.HeartRateSeriesServicePort
import br.com.arcasoftware.stayfit.controller.HeartRateApi
import br.com.arcasoftware.stayfit.model.HealthHeartRateSeriesDataPointDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class HeartRateSeriesController(
    private val dataPointService: HealthDataPointServicePort,
    private val heartRateSeriesServicePort: HeartRateSeriesServicePort
) : HeartRateApi {
    override fun postHearRateSeries(healthHeartRateSeriesDataPointDTO: List<HealthHeartRateSeriesDataPointDTO>): ResponseEntity<String> {
        println("Posting hear rate series: ${healthHeartRateSeriesDataPointDTO.size}")
        val healthDataPoints = healthHeartRateSeriesDataPointDTO.map { dataPoint -> dataPoint.toDomain() }
        heartRateSeriesServicePort.enqueue(healthDataPoints)
        return ResponseEntity.ok().build()
    }

}
