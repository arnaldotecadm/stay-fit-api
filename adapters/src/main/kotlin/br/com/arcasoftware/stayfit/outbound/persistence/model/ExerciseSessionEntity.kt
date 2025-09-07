package br.com.arcasoftware.stayfit.outbound.persistence.model

import br.com.arcasoftware.stayfit.domain.CountType
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.Duration
import java.time.Instant
import java.util.*

@Entity
@Table(name = "exercise_session", indexes = [Index(name = "ix_exercise_session_datapoint_uid", columnList = "dataPointUid")])
data class ExerciseSessionEntity(
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
    @Transient
    val log: List<ExerciseLogEntity>?,
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
    @Transient
    val route: List<ExerciseLocationEntity>?,
    val startTime: Instant,
    //val swimmingLog: Any?,
    val vo2Max: Float? = null,
    override val dataPointUid: UUID
) : BaseEntity(dataPointUid = dataPointUid)
