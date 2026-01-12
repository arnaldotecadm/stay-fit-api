package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.HeartRateSeriesEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface HeartRateSeriesRepository : JpaRepository<HeartRateSeriesEntity, Long> {
    fun findByDataPointUid(dataPointUid: UUID): HeartRateSeriesEntity?
}
