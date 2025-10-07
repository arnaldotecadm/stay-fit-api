package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepStagePersistencePort
import br.com.arcasoftware.stayfit.domain.SleepStage
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepStageMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepStageMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.SleepStageRepository
import org.springframework.stereotype.Service

@Service
class SleepStagePersistenceAdapter(
    private val sleepStageRepository: SleepStageRepository
) : SleepStagePersistencePort {
    override fun persist(sleepStage: SleepStage): SleepStage =
        sleepStageRepository
            .save(sleepStage.toEntity())
            .toDomain()
}
