package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.HeartRateSeriesServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HeartRateSeriesPersistencePort
import br.com.arcasoftware.stayfit.domain.HeartRateSeries
import org.springframework.stereotype.Service

@Service
class HeartRateSeriesService(
    private val heartRateSeriesPersistencePort: HeartRateSeriesPersistencePort
) : HeartRateSeriesServicePort {
    override fun persist(heartRateSeries: HeartRateSeries): HeartRateSeries {
        return this.heartRateSeriesPersistencePort.persist(heartRateSeries)
    }
}
