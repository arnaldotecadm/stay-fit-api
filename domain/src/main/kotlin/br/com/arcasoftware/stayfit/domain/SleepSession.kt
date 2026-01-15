package br.com.arcasoftware.stayfit.domain

import java.time.Duration
import java.time.Instant
import java.util.*

data class SleepSession(
    override val dataPointUid: UUID,
    override val startTime: Instant,
    override val endTime: Instant,
    val duration: Duration,
    val stages: List<SleepStage>?
) : Session
