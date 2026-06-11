package br.com.arcasoftware.stayfit.domain

import java.time.Duration
import java.time.Instant
import java.util.UUID

data class HeartRateSeries(
    override val dataPointUid: UUID,
    override val startTime: Instant,
    override val endTime: Instant,
    override val userId: String? = null,
    val duration: Duration,
    val heartRate: Float,
    val max: Float?,
    val min: Float?
) : Session
