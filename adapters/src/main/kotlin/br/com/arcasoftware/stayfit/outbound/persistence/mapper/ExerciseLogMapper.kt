package br.com.arcasoftware.stayfit.outbound.persistence.mapper

import br.com.arcasoftware.stayfit.domain.ExerciseLog
import br.com.arcasoftware.stayfit.model.ExerciseLogDTO
import br.com.arcasoftware.stayfit.outbound.persistence.model.ExerciseLogEntity
import java.util.*

object ExerciseLogMapper {

    fun ExerciseLogDTO.toDomain(dataPointUID: UUID): ExerciseLog {
        return ExerciseLog(
            dataPointUid = dataPointUID,
            cadence = this.cadence,
            count = this.count,
            heartRate = this.heartRate,
            power = this.power,
            speed = this.speed,
            timestamp = this.timestamp
        )
    }

    fun ExerciseLog.toEntity(): ExerciseLogEntity =
        ExerciseLogEntity(
            userId = this.userId,
            dataPointUid = this.dataPointUid,
            cadence = this.cadence,
            count = this.count,
            heartRate = this.heartRate,
            power = this.power,
            speed = this.speed,
            timestamp = this.timestamp
        )

    fun ExerciseLogEntity.toDomain(): ExerciseLog =
        ExerciseLog(
            userId = this.userId,
            dataPointUid = this.dataPointUid,
            cadence = this.cadence,
            count = this.count,
            heartRate = this.heartRate,
            power = this.power,
            speed = this.speed,
            timestamp = this.timestamp
        )

}
