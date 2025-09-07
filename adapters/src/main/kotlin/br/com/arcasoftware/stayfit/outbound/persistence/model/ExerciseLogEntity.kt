package br.com.arcasoftware.stayfit.outbound.persistence.model

import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.Instant
import java.util.*

@Entity
@Table(name = "exercise_log", indexes = [Index(name = "ix_exercise_log_datapoint_uid", columnList = "dataPointUid")])
data class ExerciseLogEntity(
    val cadence: Float?,
    val count: Int?,
    val heartRate: Float?,
    val power: Float?,
    val speed: Float?,
    val timestamp: Instant,
    override val dataPointUid: UUID
) : BaseEntity(dataPointUid = dataPointUid)
