package br.com.arcasoftware.stayfit.outbound.persistence.mapper

import br.com.arcasoftware.stayfit.domain.ExerciseLocation
import br.com.arcasoftware.stayfit.model.ExerciseLocationDTO
import br.com.arcasoftware.stayfit.outbound.persistence.model.ExerciseLocationEntity
import java.util.*

object ExerciseLocationMapper {

    fun ExerciseLocationDTO.toDomain(dataPointUID: UUID): ExerciseLocation {
        return ExerciseLocation(
            dataPointUid = dataPointUID,
            accuracy = this.accuracy,
            altitude = this.altitude,
            latitude = this.latitude,
            longitude = this.longitude,
            timestamp = this.timestamp,
        )
    }


    fun ExerciseLocation.toEntity(): ExerciseLocationEntity =
        ExerciseLocationEntity(
            dataPointUid = this.dataPointUid,
            accuracy = this.accuracy,
            altitude = this.altitude,
            latitude = this.latitude,
            longitude = this.longitude,
            timestamp = this.timestamp,
        )

    fun ExerciseLocationEntity.toDomain(): ExerciseLocation =
        ExerciseLocation(
            dataPointUid = this.dataPointUid,
            accuracy = this.accuracy,
            altitude = this.altitude,
            latitude = this.latitude,
            longitude = this.longitude,
            timestamp = this.timestamp,
        )

}
