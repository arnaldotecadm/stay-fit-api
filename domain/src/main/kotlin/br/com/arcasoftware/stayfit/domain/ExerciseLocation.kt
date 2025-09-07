package br.com.arcasoftware.stayfit.domain

import java.time.Instant
import java.util.UUID

data class ExerciseLocation(
    val dataPointUid: UUID,
    val accuracy: Float?,
    val altitude: Float?,
    val latitude: Float,
    val longitude: Float,
    val timestamp: Instant
)
