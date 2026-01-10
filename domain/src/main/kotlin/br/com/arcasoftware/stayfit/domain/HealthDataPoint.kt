package br.com.arcasoftware.stayfit.domain

import java.time.Instant
import java.time.ZoneOffset
import java.util.*

data class HealthDataPoint(
    val id: Long?,
    val clientDataId: String?,
    val clientVersion: Int?,
    val dataSourceEntity: String?,
    val endTime: Instant?,
    val startTime: Instant,
    val updateTime: Instant?,
    val zoneOffset: ZoneOffset?,
    val dataPointUid: UUID,
    val sleepScore: Int? = null
)
