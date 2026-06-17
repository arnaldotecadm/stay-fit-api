package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HeartRateSeriesPersistencePort
import br.com.arcasoftware.stayfit.domain.HeartDailySession
import br.com.arcasoftware.stayfit.domain.HeartRateSeries
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HeartRateSeriesMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HeartRateSeriesMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.HeartRateSeriesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class HeartRateSeriesPersistenceAdapter(
    private val heartRateSeriesRepository: HeartRateSeriesRepository,
) : HeartRateSeriesPersistencePort {

    @Transactional
    override fun persistBatch(heartRateSeriesBatch: List<HeartRateSeries>) {
        if (heartRateSeriesBatch.isEmpty()) {
            return
        }

        val dataPointUids = heartRateSeriesBatch.asSequence().map { it.dataPointUid }.distinct().toList()
        this.heartRateSeriesRepository.deleteByDataPointUidIn(dataPointUids)
        this.heartRateSeriesRepository.saveAll(heartRateSeriesBatch.map { it.toEntity() })
    }

    override fun getHeartDailySession(localDate: LocalDate): List<HeartDailySession> {
        return this.heartRateSeriesRepository.getHeartDailySession(localDate).map { it.toDomain() }
    }
}
