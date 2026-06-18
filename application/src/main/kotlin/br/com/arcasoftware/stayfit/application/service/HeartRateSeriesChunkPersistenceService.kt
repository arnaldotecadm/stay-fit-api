package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HeartRateSeriesPersistencePort
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.domain.HeartRateSeries
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HeartRateSeriesChunkPersistenceService(
    private val heartRateSeriesPersistencePort: HeartRateSeriesPersistencePort,
    private val dataPointService: HealthDataPointServicePort
) {
    @Transactional
    fun persistChunk(chunk: List<HealthDataPoint>) {
        dataPointService.persistHeartRateBatch(chunk)
        val heartRateSeries = chunk
            .asSequence()
            .flatMap { it.sessions.orEmpty().asSequence() }
            .filterIsInstance<HeartRateSeries>()
            .toList()
        heartRateSeriesPersistencePort.persistBatch(heartRateSeries)
    }
}
