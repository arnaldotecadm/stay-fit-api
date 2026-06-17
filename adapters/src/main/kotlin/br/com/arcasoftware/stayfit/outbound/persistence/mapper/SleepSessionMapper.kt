package br.com.arcasoftware.stayfit.outbound.persistence.mapper

import br.com.arcasoftware.stayfit.domain.DailySleep
import br.com.arcasoftware.stayfit.domain.DailySleepStage
import br.com.arcasoftware.stayfit.domain.SleepSession
import br.com.arcasoftware.stayfit.domain.SleepStageType
import br.com.arcasoftware.stayfit.model.DailySleepDTO
import br.com.arcasoftware.stayfit.model.DailySleepStageDTO
import br.com.arcasoftware.stayfit.model.SleepSessionDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepStageMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepStageMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.SleepSessionEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.projection.DailySleepStageProjection
import java.time.Duration
import java.util.UUID

object SleepSessionMapper {
    fun SleepSessionDTO.toDomain(dataPointUID: UUID): SleepSession =
        SleepSession(
            dataPointUid = dataPointUID,
            duration = this.duration,
            endTime = this.endTime,
            startTime = this.startTime,
            stages = this.stages?.map { stage -> stage.toDomain(dataPointUID) },
        )

    fun SleepSession.toEntity(): SleepSessionEntity =
        SleepSessionEntity(
            dataPointUid = this.dataPointUid,
            duration = this.duration,
            endTime = this.endTime,
            startTime = this.startTime,
            stages = this.stages?.map { it.toEntity() },
        )

    fun SleepSessionEntity.toDomain(): SleepSession =
        SleepSession(
            dataPointUid = this.dataPointUid,
            userId = this.userId,
            duration = this.duration,
            endTime = this.endTime,
            startTime = this.startTime,
            stages = this.stages?.map { it.toDomain() },
        )

    fun List<DailySleepStageProjection>.toDomain(): DailySleep {
        if (this.isEmpty()) {
            return DailySleep()
        }
        val dailyStages =
            this.map { stages ->
                DailySleepStage(
                    stage = SleepStageType.entries[stages.stage],
                    durationInMinutes = stages.durationMinutes,
                )
            }
        return DailySleep(
            startTime = this.first().startTime,
            endTime = this.first().endTime,
            duration = Duration.ofNanos(this.first().duration),
            stages = dailyStages,
        )
    }

    fun DailySleep.toDTO(): DailySleepDTO =
        DailySleepDTO(
            startTime = this.startTime,
            endTime = this.endTime,
            duration = this.duration,
            stages =
                this.stages?.map { stage ->
                    DailySleepStageDTO(
                        stage = stage.stage?.name,
                        durationInMinutes = stage.durationInMinutes,
                    )
                },
        )
}
