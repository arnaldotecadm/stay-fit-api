package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepStagePersistencePort
import br.com.arcasoftware.stayfit.domain.SleepStage
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepStageMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepStageMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.SleepStageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class SleepStagePersistenceAdapter(
    private val sleepStageRepository: SleepStageRepository,
) : SleepStagePersistencePort {
    override fun persist(sleepStage: SleepStage): SleepStage =
        sleepStageRepository
            .save(sleepStage.toEntity())
            .toDomain()

    @Transactional
    override fun deleteByDataPointUid(dataPointUid: UUID) {
        sleepStageRepository.deleteByDataPointUid(dataPointUid)
    }

    @Transactional
    override fun deleteByDataPointUidIn(dataPointUids: Collection<UUID>) {
        if (dataPointUids.isNotEmpty()) sleepStageRepository.deleteByDataPointUidIn(dataPointUids)
    }

    @Transactional
    override fun persistBatch(stages: List<SleepStage>) {
        if (stages.isEmpty()) return
        sleepStageRepository.saveAll(stages.map { it.toEntity() })
    }
}
