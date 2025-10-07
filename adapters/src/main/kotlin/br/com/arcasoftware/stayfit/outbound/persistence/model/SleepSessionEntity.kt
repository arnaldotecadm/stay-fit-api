package br.com.arcasoftware.stayfit.outbound.persistence.model

import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.Duration
import java.time.Instant
import java.util.*

@Entity
@Table(name = "sleep_session", indexes = [Index(name = "ix_sleep_session_datapoint_uid", columnList = "dataPointUid")])
data class SleepSessionEntity(
    override val dataPointUid: UUID,
    val startTime: Instant,
    val endTime: Instant,
    val duration: Duration,
    @Transient
    val stages: List<SleepStageEntity>?
) : BaseEntity(dataPointUid = dataPointUid)
