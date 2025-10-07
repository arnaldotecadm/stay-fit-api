package br.com.arcasoftware.stayfit.outbound.persistence.mapper

import br.com.arcasoftware.stayfit.domain.SleepStage
import br.com.arcasoftware.stayfit.domain.SleepStageType
import br.com.arcasoftware.stayfit.model.SleepStageDTO
import br.com.arcasoftware.stayfit.outbound.persistence.model.SleepStageEntity
import java.util.*

object SleepStageMapper {

    fun SleepStageDTO.toDomain(dataPointUID: UUID): SleepStage {
        return SleepStage(
            dataPointUid = dataPointUID,
            startTime = this.startTime,
            endTime = this.endTime,
            stage = SleepStageType.valueOf(this.stage.name)
        )
    }


    fun SleepStage.toEntity(): SleepStageEntity =
        SleepStageEntity(
            dataPointUid = this.dataPointUid,
            startTime = this.startTime,
            endTime = this.endTime,
            stage = this.stage
        )

    fun SleepStageEntity.toDomain(): SleepStage =
        SleepStage(
            dataPointUid = this.dataPointUid,
            startTime = this.startTime,
            endTime = this.endTime,
            stage = this.stage
        )

}
