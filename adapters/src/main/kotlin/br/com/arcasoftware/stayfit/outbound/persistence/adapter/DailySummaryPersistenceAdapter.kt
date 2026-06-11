package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.DailySummaryPersistencePort
import br.com.arcasoftware.stayfit.domain.DailyActivitySummary
import br.com.arcasoftware.stayfit.domain.DailySummary
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.DailySummaryMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.DailySummaryMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.DailySummaryRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DailySummaryPersistenceAdapter(
    private val dailySummaryRepository: DailySummaryRepository,
) : DailySummaryPersistencePort {

    override fun persist(dailySummary: DailySummary): DailySummary {
        dailySummaryRepository.findByDate(dailySummary.date)?.let {
            dailySummaryRepository.deleteById(it.id)
        }

        return dailySummaryRepository
            .save(dailySummary.toEntity())
            .toDomain()
    }

    override fun getDailySummaryEntityByDate(localDate: LocalDate): DailyActivitySummary? {
        return this.dailySummaryRepository.getDailySummaryEntityByDate(localDate)?.toDomain()
    }
}
