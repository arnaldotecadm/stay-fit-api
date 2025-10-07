package br.com.arcasoftware.stayfit.outbound.persistence.mapper

import br.com.arcasoftware.stayfit.domain.SleepSession
import br.com.arcasoftware.stayfit.model.SleepSessionDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepStageMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepStageMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.SleepSessionEntity
import java.util.*

object SleepSessionMapper {

    fun SleepSessionDTO.toDomain(dataPointUID: UUID): SleepSession {
        return SleepSession(
            dataPointUid = dataPointUID,
            duration = this.duration,
            endTime = this.endTime,
            startTime = this.startTime,
            stages = this.stages?.map { stage -> stage.toDomain(dataPointUID) }
        )
    }

    fun SleepSession.toEntity(): SleepSessionEntity {
        return SleepSessionEntity(
            dataPointUid = this.dataPointUid,
            duration = this.duration,
            endTime = this.endTime,
            startTime = this.startTime,
            stages = this.stages?.map { it.toEntity() }
        )
    }

    fun SleepSessionEntity.toDomain(): SleepSession {
        return SleepSession(
            dataPointUid = this.dataPointUid,
            duration = this.duration,
            endTime = this.endTime,
            startTime = this.startTime,
            stages = this.stages?.map { it.toDomain() }
        )
    }
}