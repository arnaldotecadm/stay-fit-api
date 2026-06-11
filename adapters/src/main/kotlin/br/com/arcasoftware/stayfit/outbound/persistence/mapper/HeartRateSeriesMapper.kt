package br.com.arcasoftware.stayfit.outbound.persistence.mapper

import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.domain.HeartRateSeries
import br.com.arcasoftware.stayfit.model.HeartRateSeriesDTO
import br.com.arcasoftware.stayfit.outbound.persistence.model.HearRateSeriesHealthDataPointEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.HeartRateSeriesEntity
import java.util.UUID

object HeartRateSeriesMapper {

    fun HeartRateSeriesDTO.toDomain(dataPointUID: UUID): HeartRateSeries {
        return HeartRateSeries(
            dataPointUid = dataPointUID,
            duration = this.duration,
            endTime = this.endTime,
            startTime = this.startTime,
            heartRate = this.heartRate,
            min = this.min,
            max = this.max
        )
    }

    fun HeartRateSeries.toEntity(): HeartRateSeriesEntity {
        return HeartRateSeriesEntity(
            dataPointUid = this.dataPointUid,
            userId = this.userId,
            duration = this.duration,
            endTime = this.endTime,
            startTime = this.startTime,
            heartRate = this.heartRate,
            min = this.min,
            max = this.max
        )
    }

    fun HeartRateSeriesEntity.toDomain(): HeartRateSeries {
        return HeartRateSeries(
            dataPointUid = this.dataPointUid,
            userId = this.userId,
            duration = this.duration,
            endTime = this.endTime,
            startTime = this.startTime,
            heartRate = this.heartRate,
            min = this.min,
            max = this.max
        )
    }

    fun HearRateSeriesHealthDataPointEntity.toDomain(): HealthDataPoint =
        HealthDataPoint(
            id = this.id!!,
            userId = this.userId,
            healthDataType = this.healthDataType,
            clientDataId = this.clientDataId,
            clientVersion = this.clientVersion,
            dataSourceEntity = this.dataSourceEntity,
            endTime = this.endTime,
            startTime = this.startTime,
            updateTime = this.updateTime,
            zoneOffset = this.zoneOffset,
            dataPointUid = this.dataPointUid,
            sessions = emptyList()
        )
}