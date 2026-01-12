package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.DailySummaryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface DailySummaryRepository : JpaRepository<DailySummaryEntity, Long> {
    fun findByDate(date: LocalDate): DailySummaryEntity?
}
