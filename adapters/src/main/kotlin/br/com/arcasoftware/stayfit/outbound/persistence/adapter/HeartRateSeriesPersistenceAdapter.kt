package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HeartRateSeriesPersistencePort
import br.com.arcasoftware.stayfit.domain.HeartRateSeries
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HeartRateSeriesMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HeartRateSeriesMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.HeartRateSeriesRepository
import org.springframework.stereotype.Service

@Service
class HeartRateSeriesPersistenceAdapter(
    private val heartRateSeriesRepository: HeartRateSeriesRepository,
) : HeartRateSeriesPersistencePort {
    override fun persist(heartRateSeries: HeartRateSeries): HeartRateSeries {
        return if (heartRateSeriesRepository.findByDataPointUid(heartRateSeries.dataPointUid) == null)
            heartRateSeriesRepository
                .save(heartRateSeries.toEntity())
                .toDomain()
        else
            heartRateSeries
    }
}
