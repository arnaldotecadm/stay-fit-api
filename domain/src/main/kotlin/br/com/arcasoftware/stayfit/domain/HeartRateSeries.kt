package br.com.arcasoftware.stayfit.domain

import java.time.Duration
import java.time.Instant
import java.util.*

data class HeartRateSeries(
    val dataPointUid: UUID,
    val startTime: Instant,
    val endTime: Instant,
    val duration: Duration,
    val heartRate: Float,
    val max: Float?,
    val min: Float?
)
