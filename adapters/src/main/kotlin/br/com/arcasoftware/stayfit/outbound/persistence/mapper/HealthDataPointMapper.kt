package br.com.arcasoftware.stayfit.outbound.persistence.mapper

import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.model.DataSourceDTO
import br.com.arcasoftware.stayfit.model.HealthExerciseDataPointDTO
import br.com.arcasoftware.stayfit.model.HealthSleepDataPointDTO
import br.com.arcasoftware.stayfit.outbound.persistence.model.HealthDataPointEntity
import java.util.*

object HealthDataPointMapper {

    fun HealthExerciseDataPointDTO.toDomain(): HealthDataPoint {
        return HealthDataPoint(
            id = null,
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
