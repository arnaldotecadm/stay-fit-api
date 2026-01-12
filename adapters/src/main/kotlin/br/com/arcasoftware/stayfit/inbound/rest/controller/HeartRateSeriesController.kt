package br.com.arcasoftware.stayfit.inbound.rest.controller

import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.inbound.service.HeartRateSeriesServicePort
import br.com.arcasoftware.stayfit.controller.HeartRateApi
import br.com.arcasoftware.stayfit.model.HealthHeartRateSeriesDataPointDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HeartRateSeriesMapper.toDomain
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class HeartRateSeriesController(
    private val dataPointService: HealthDataPointServicePort,
    private val heartRateSeriesServicePort: HeartRateSeriesServicePort
) : HeartRateApi {
    override fun postHearRateSeries(healthHeartRateSeriesDataPointDTO: List<HealthHeartRateSeriesDataPointDTO>): ResponseEntity<String> {
        healthHeartRateSeriesDataPointDTO.forEach {
            println(it.toDomain())
            this.dataPointService.persist(it.toDomain())

            // persist the exercise sessions related to the activity
            it.sessions?.map { dataPoint ->
                dataPoint.toDomain(
                    UUID.fromString(
                        it.uid
                    )
                )
            }
                ?.forEach { heartRateSeriesServicePort.persist(it) }
        }
        return ResponseEntity.ok().build()
    }

}
