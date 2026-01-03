package br.com.arcasoftware.stayfit.outbound.persistence.mapper

import br.com.arcasoftware.stayfit.domain.BasicExerciseSession
import br.com.arcasoftware.stayfit.domain.CountType
import br.com.arcasoftware.stayfit.domain.ExerciseSession
import br.com.arcasoftware.stayfit.model.ExerciseSessionDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLocationMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLocationMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLogMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseLogMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.ExerciseSessionEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.projection.BasicExerciseSessionProjection
import java.util.*

object ExerciseSessionMapper {

    fun ExerciseSessionDTO.toDomain(dataPointUID: UUID): ExerciseSession {
        return ExerciseSession(
            dataPointUid = dataPointUID,
            altitudeGain = this.altitudeGain,
            altitudeLoss = this.altitudeLoss,
            calories = this.calories,
            comment = this.comment,
            count = this.count,
            countType = this.countType?.let { CountType.valueOf(this.countType.name) },
            customTitle = this.customTitle,
            declineDistance = this.declineDistance,
            distance = this.distance,
            duration = this.duration,
            endTime = this.endTime,
            exerciseType = this.exerciseType,
            inclineDistance = this.inclineDistance,
            log = this.log?.map { it.toDomain(dataPointUID) },
            maxAltitude = this.maxAltitude,
            maxCadence = this.maxCadence,
            maxCaloriesBurnRate = this.maxCaloriesBurnRate,
            maxHeartRate = this.maxHeartRate,
            maxPower = this.maxPower,
            maxRpm = this.maxRpm,
            maxSpeed = this.maxSpeed,
            meanCadence = this.meanCadence,
            meanCalorieBurnRate = this.meanCalorieBurnRate,
            meanHeartRate = this.meanHeartRate,
            meanPower = this.meanPower,
            meanRpm = this.meanRpm,
            meanSpeed = this.meanSpeed,
            minAltitude = this.minAltitude,
            minHeartRate = this.minHeartRate,
            route = this.route?.map { it.toDomain(dataPointUID) },
            startTime = this.startTime,
            swimmingLog = null,
            vo2Max = this.vo2Max
        )
    }

    fun ExerciseSession.toEntity(): ExerciseSessionEntity {
        return ExerciseSessionEntity(
            dataPointUid = this.dataPointUid,
            altitudeGain = this.altitudeGain,
            altitudeLoss = this.altitudeLoss,
            calories = this.calories,
            comment = this.comment,
            count = this.count,
            countType = this.countType?.let { CountType.valueOf(this.countType!!.name) },
            customTitle = this.customTitle,
            declineDistance = this.declineDistance,
            distance = this.distance,
            duration = this.duration,
            endTime = this.endTime,
            exerciseType = this.exerciseType,
            inclineDistance = this.inclineDistance,
            log = this.log?.map { it.toEntity() },
            maxAltitude = this.maxAltitude,
            maxCadence = this.maxCadence,
            maxCaloriesBurnRate = this.maxCaloriesBurnRate,
            maxHeartRate = this.maxHeartRate,
            maxPower = this.maxPower,
            maxRpm = this.maxRpm,
            maxSpeed = this.maxSpeed,
            meanCadence = this.meanCadence,
            meanCalorieBurnRate = this.meanCalorieBurnRate,
            meanHeartRate = this.meanHeartRate,
            meanPower = this.meanPower,
            meanRpm = this.meanRpm,
            meanSpeed = this.meanSpeed,
            minAltitude = this.minAltitude,
            minHeartRate = this.minHeartRate,
            route = this.route?.map { it.toEntity() },
            startTime = this.startTime,
            //swimmingLog = null,
            vo2Max = this.vo2Max
        )
    }

    fun ExerciseSessionEntity.toDomain(): ExerciseSession {
        return ExerciseSession(
            dataPointUid = this.dataPointUid,
            altitudeGain = this.altitudeGain,
            altitudeLoss = this.altitudeLoss,
            calories = this.calories,
            comment = this.comment,
            count = this.count,
            countType = this.countType?.let { CountType.valueOf(this.countType.name) },
            customTitle = this.customTitle,
            declineDistance = this.declineDistance,
            distance = this.distance,
            duration = this.duration,
            endTime = this.endTime,
            exerciseType = this.exerciseType,
            inclineDistance = this.inclineDistance,
            log = this.log?.map { it.toDomain() },
            maxAltitude = this.maxAltitude,
            maxCadence = this.maxCadence,
            maxCaloriesBurnRate = this.maxCaloriesBurnRate,
            maxHeartRate = this.maxHeartRate,
            maxPower = this.maxPower,
            maxRpm = this.maxRpm,
            maxSpeed = this.maxSpeed,
            meanCadence = this.meanCadence,
            meanCalorieBurnRate = this.meanCalorieBurnRate,
            meanHeartRate = this.meanHeartRate,
            meanPower = this.meanPower,
            meanRpm = this.meanRpm,
            meanSpeed = this.meanSpeed,
            minAltitude = this.minAltitude,
            minHeartRate = this.minHeartRate,
            route = this.route?.map { it.toDomain() },
            startTime = this.startTime,
            swimmingLog = null,
            vo2Max = this.vo2Max
        )
    }

    fun BasicExerciseSessionProjection.toDomain(): BasicExerciseSession {
        return BasicExerciseSession(
            startTime = this.startTime,
            endTime = this.endTime,
            type = this.type,
            duration = this.duration,
            distance = this.distance,
            calories = this.calories,
            meanHeartRate = this.meanHeartRate,
            maxHeartRate = this.maxHeartRate,
            meanCadence = this.meanCadence,
            meanSpeed = this.meanSpeed,
            maxSpeed = this.maxSpeed
        )
    }
}