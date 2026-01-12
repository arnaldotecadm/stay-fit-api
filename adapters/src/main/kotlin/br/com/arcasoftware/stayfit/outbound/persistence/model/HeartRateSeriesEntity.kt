package br.com.arcasoftware.stayfit.outbound.persistence.model

import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.Duration
import java.time.Instant
import java.util.*

@Entity
@Table(name = "heart_rate_series", indexes = [Index(name = "ix_heart_rate_series_datapoint_uid", columnList = "dataPointUid")])
data class HeartRateSeriesEntity(
    override val dataPointUid: UUID,
    val startTime: Instant,
    val endTime: Instant,
    val duration: Duration,
    val heartRate: Float,
    val max: Float?,
    val min: Float?
) : BaseEntity(dataPointUid = dataPointUid)
