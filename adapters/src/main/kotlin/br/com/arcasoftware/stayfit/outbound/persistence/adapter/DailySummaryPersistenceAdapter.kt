package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.DailySummaryPersistencePort
import br.com.arcasoftware.stayfit.domain.DailyActivitySummary
import br.com.arcasoftware.stayfit.domain.DailySummary
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.DailySummaryMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.DailySummaryMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.DailySummaryRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId

import org.springframework.transaction.annotation.Transactional

@Service
class DailySummaryPersistenceAdapter(
    private val dailySummaryRepository: DailySummaryRepository,
) : DailySummaryPersistencePort {
    override fun persist(dailySummary: DailySummary): DailySummary {
        dailySummaryRepository.findByDate(dailySummary.date.atZone(ZoneId.systemDefault()).toLocalDate())?.let {
            dailySummaryRepository.deleteById(it.id!!)
        }

        return dailySummaryRepository
            .save(dailySummary.toEntity())
            .toDomain()
    }

    override fun getDailySummaryEntityByDate(localDate: LocalDate): DailyActivitySummary? =
        this.dailySummaryRepository.getDailySummaryEntityByDate(localDate)?.toDomain()

    @Transactional
    override fun persistBatch(dailySummaries: List<DailySummary>) {
        dailySummaries.forEach { persist(it) }
    }
}
