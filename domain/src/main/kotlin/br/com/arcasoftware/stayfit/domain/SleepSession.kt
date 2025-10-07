package br.com.arcasoftware.stayfit.domain

import java.time.Duration
import java.time.Instant
import java.util.*

data class SleepSession(
    val dataPointUid: UUID,
    val startTime: Instant,
    val endTime: Instant,
    val duration: Duration,
    val stages: List<SleepStage>?
)
