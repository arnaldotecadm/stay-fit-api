package br.com.arcasoftware.stayfit.domain

import java.time.Instant
import java.util.UUID

data class SleepStage(
    val dataPointUid: UUID,
    val startTime: Instant,
    val endTime: Instant,
    val stage: SleepStageType
)
