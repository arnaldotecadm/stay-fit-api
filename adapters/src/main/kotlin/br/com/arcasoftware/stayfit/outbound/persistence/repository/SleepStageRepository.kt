package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.SleepStageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface SleepStageRepository : JpaRepository<SleepStageEntity, Long> {
    fun findByDataPointUidAndStartTime(
        dataPointUid: UUID,
        startTime: Instant
    ): SleepStageEntity?
}
