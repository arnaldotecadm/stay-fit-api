package br.com.arcasoftware.stayfit.domain

import java.time.Duration
import java.time.Instant
import java.util.*

data class SleepSession(
    override val type: HealthDataPointType = HealthDataPointType.SLEEP,
    override val dataPointUid: UUID,
    override val startTime: Instant,
    override val endTime: Instant,
    override val userId: String? = null,
    val duration: Duration,
    val stages: List<SleepStage>?
) : Session
