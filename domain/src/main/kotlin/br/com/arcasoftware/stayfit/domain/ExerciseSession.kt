package br.com.arcasoftware.stayfit.domain

import java.time.Duration
import java.time.Instant
import java.util.UUID

data class ExerciseSession(
    val dataPointUid: UUID,
    val altitudeGain: Float?,
    val altitudeLoss: Float?,
    val calories: Float,
    val comment: String?,
    val count: Int?,
    val countType: CountType?,
    val customTitle: String?,
    val declineDistance: Float?,
    val distance: Float?,
    val duration: Duration,
    val endTime: Instant,
    val exerciseType: String,
    val inclineDistance: Float?,
    val log: List<ExerciseLog>?,
    val maxAltitude: Float?,
    val maxCadence: Float?,
    val maxCaloriesBurnRate: Float?,
    val maxHeartRate: Float?,
    val maxPower: Float?,
    val maxRpm: Float?,
    val maxSpeed: Float?,
    val meanCadence: Float?,
    val meanCalorieBurnRate: Float?,
    val meanHeartRate: Float?,
    val meanPower: Float?,
    val meanRpm: Float?,
    val meanSpeed: Float?,
    val minAltitude: Float?,
    val minHeartRate: Float?,
    val route: List<ExerciseLocation>?,
    val startTime: Instant,
    val swimmingLog: Any?,
    val vo2Max: Float? = null
)
