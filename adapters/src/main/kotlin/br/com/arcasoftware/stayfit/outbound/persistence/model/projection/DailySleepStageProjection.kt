package br.com.arcasoftware.stayfit.outbound.persistence.model.projection

import java.time.Instant

interface DailySleepStageProjection {
    val stage: Int
    val durationMinutes: Float
    val startTime: Instant
    val endTime: Instant
    val duration: Long
}
