package br.com.arcasoftware.stayfit.outbound.persistence.model

import br.com.arcasoftware.stayfit.domain.SleepStageType
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.Instant
import java.util.*

@Entity
@Table(name = "sleep_stage", indexes = [Index(name = "ix_sleep_stage_datapoint_uid", columnList = "dataPointUid")])
data class SleepStageEntity(
    override val dataPointUid: UUID,
    val startTime: Instant,
    val endTime: Instant,
    val stage: SleepStageType
) : BaseEntity(dataPointUid = dataPointUid)
