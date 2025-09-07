package br.com.arcasoftware.stayfit.domain

import java.time.Instant
import java.util.UUID

data class ExerciseLog(
    val dataPointUid: UUID,
    val cadence: Float?,
    val count: Int?,
    val heartRate: Float?,
    val power: Float?,
    val speed: Float?,
    val timestamp: Instant
)
