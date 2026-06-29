package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.DailySummaryPersistencePort
import br.com.arcasoftware.stayfit.domain.DailyActivitySummary
import br.com.arcasoftware.stayfit.domain.DailySummary
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.DailySummaryMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.DailySummaryMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.DailySummaryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class DailySummaryPersistenceAdapter(
    private val dailySummaryRepository: DailySummaryRepository,
) : DailySummaryPersistencePort {

    @Transactional
    override fun persist(dailySummary: DailySummary): DailySummary {
        dailySummaryRepository.deleteByDate(dailySummary.date)
        return dailySummaryRepository.save(dailySummary.toEntity()).toDomain()
    }

    override fun getDailySummaryEntityByDate(localDate: LocalDate): DailyActivitySummary? =
        dailySummaryRepository.getDailySummaryEntityByDate(localDate)?.toDomain()

    @Transactional
    override fun persistBatch(dailySummaries: List<DailySummary>) {
        if (dailySummaries.isEmpty()) return
        val deduped = dailySummaries.associateBy { it.date }
        dailySummaryRepository.deleteByDateIn(deduped.keys)
        dailySummaryRepository.saveAll(deduped.values.map { it.toEntity() })
    }
}
