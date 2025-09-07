package br.com.arcasoftware.stayfit.outbound.persistence.model

import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "exercise_location", indexes = [Index(name = "ix_exercise_location_datapoint_uid", columnList = "dataPointUid")])
data class ExerciseLocationEntity(
    val accuracy: Float?,
    val altitude: Float?,
    val latitude: Float,
    val longitude: Float,
    val timestamp: Instant,
    override val dataPointUid: UUID
): BaseEntity(dataPointUid = dataPointUid)
