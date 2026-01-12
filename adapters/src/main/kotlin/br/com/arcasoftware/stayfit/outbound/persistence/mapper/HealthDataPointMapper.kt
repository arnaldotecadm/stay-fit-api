package br.com.arcasoftware.stayfit.outbound.persistence.mapper

import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.domain.HealthDataPointType
import br.com.arcasoftware.stayfit.model.DataSourceDTO
import br.com.arcasoftware.stayfit.model.HealthExerciseDataPointDTO
import br.com.arcasoftware.stayfit.model.HealthHeartRateSeriesDataPointDTO
import br.com.arcasoftware.stayfit.model.HealthSleepDataPointDTO
import br.com.arcasoftware.stayfit.outbound.persistence.model.HealthDataPointEntity
import java.util.*

object HealthDataPointMapper {

    fun HealthExerciseDataPointDTO.toDomain(): HealthDataPoint {
        return HealthDataPoint(
            id = null,
            healthDataType = HealthDataPointType.EXERCISE,
            clientDataId = this.clientDataId,
            clientVersion = this.clientVersion,
            dataSourceEntity = this.dataSource?.toDomain(),
            endTime = this.endTime,
            startTime = this.startTime,
            updateTime = this.updateTime,
            zoneOffset = this.zoneOffset,
            dataPointUid = UUID.fromString(this.uid)
        )
    }

    fun HealthSleepDataPointDTO.toDomain(): HealthDataPoint {
        return HealthDataPoint(
            id = null,
            healthDataType = HealthDataPointType.SLEEP,
            clientDataId = this.clientDataId,
            clientVersion = this.clientVersion,
            dataSourceEntity = this.dataSource?.toDomain(),
            endTime = this.endTime,
            startTime = this.startTime,
            updateTime = this.updateTime,
            zoneOffset = this.zoneOffset,
            dataPointUid = UUID.fromString(this.uid)
        )
    }

    fun HealthHeartRateSeriesDataPointDTO.toDomain(): HealthDataPoint {
        return HealthDataPoint(
            id = null,
            healthDataType = HealthDataPointType.HEART_RATE,
            clientDataId = this.clientDataId,
            clientVersion = this.clientVersion,
            dataSourceEntity = this.dataSource?.toDomain(),
            endTime = this.endTime,
            startTime = this.startTime,
            updateTime = this.updateTime,
            zoneOffset = this.zoneOffset,
            dataPointUid = UUID.fromString(this.uid)
        )
    }

    fun DataSourceDTO.toDomain(): String {
        return "${this.appId}:${this.deviceId}"
    }

    fun HealthDataPoint.toEntity(): HealthDataPointEntity =
        HealthDataPointEntity(
            clientDataId = this.clientDataId,
            healthDataType = this.healthDataType,
            clientVersion = this.clientVersion,
            dataSourceEntity = this.dataSourceEntity,
            endTime = this.endTime,
            startTime = this.startTime,
            updateTime = this.updateTime,
            zoneOffset = this.zoneOffset,
            dataPointUid = this.dataPointUid
        )

    fun HealthDataPointEntity.toDomain(): HealthDataPoint =
        HealthDataPoint(
            id = this.id!!,
            healthDataType = this.healthDataType,
            clientDataId = this.clientDataId,
            clientVersion = this.clientVersion,
            dataSourceEntity = this.dataSourceEntity,
            endTime = this.endTime,
            startTime = this.startTime,
            updateTime = this.updateTime,
            zoneOffset = this.zoneOffset,
            dataPointUid = this.dataPointUid
        )

}
