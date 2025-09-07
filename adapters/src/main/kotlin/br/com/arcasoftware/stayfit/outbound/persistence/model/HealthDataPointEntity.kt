package br.com.arcasoftware.stayfit.outbound.persistence.model

import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

@Entity
@Table(name = "health_datapoint", indexes = [Index(name = "ix_health_datapoint_datapoint_uid", columnList = "dataPointUid")])
data class HealthDataPointEntity(
    val clientDataId: String?,
    val clientVersion: Int?,
    val dataSourceEntity: String?,
    val endTime: Instant?,
    val startTime: Instant,
    val updateTime: Instant?,
    val zoneOffset: ZoneOffset?,
    override val dataPointUid: UUID
) : BaseEntity(dataPointUid = dataPointUid)
