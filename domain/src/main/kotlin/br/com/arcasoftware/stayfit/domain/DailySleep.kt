package br.com.arcasoftware.stayfit.domain

import java.time.Duration
import java.time.Instant

data class DailySleep(
    val startTime: Instant? = null,
    val endTime: Instant? = null,
    val duration: Duration? = null,
    val stages: List<DailySleepStage>? = null
)

data class DailySleepStage(
    val stage: SleepStageType? = null,
    val durationInMinutes: Float? = null
)
