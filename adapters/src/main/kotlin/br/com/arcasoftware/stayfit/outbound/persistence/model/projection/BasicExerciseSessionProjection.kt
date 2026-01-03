package br.com.arcasoftware.stayfit.outbound.persistence.model.projection

import java.time.Duration
import java.time.Instant

interface BasicExerciseSessionProjection {
    val startTime: Instant
    val endTime: Instant
    val type: String
    val duration: Duration
    val distance: Double
    val calories: Double
    val meanHeartRate: Double
    val maxHeartRate: Double
    val meanCadence: Double
    val meanSpeed: Double
    val maxSpeed: Double
}