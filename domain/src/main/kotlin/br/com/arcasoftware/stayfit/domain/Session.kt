package br.com.arcasoftware.stayfit.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.Instant
import java.util.UUID

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = ExerciseSession::class, name = "EXERCISE"),
    JsonSubTypes.Type(value = SleepSession::class, name = "SLEEP"),
    JsonSubTypes.Type(value = HeartRateSeries::class, name = "HEART_RATE")
)
interface Session {
    val type: HealthDataPointType
    val dataPointUid: UUID
    val userId: String?
    val startTime: Instant
    val endTime: Instant
}
