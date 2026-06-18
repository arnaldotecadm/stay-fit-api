package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepSessionPersistencePort
import br.com.arcasoftware.stayfit.domain.DailySleep
import br.com.arcasoftware.stayfit.domain.SleepSession
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepSessionMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepSessionMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.SleepSessionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
class SleepSessionPersistenceAdapter(
    private val sleepSessionRepository: SleepSessionRepository,
) : SleepSessionPersistencePort {
    override fun persist(sleepSession: SleepSession): SleepSession =
        if (sleepSessionRepository.findByDataPointUid(sleepSession.dataPointUid) == null) {
            sleepSessionRepository
                .save(sleepSession.toEntity())
                .toDomain()
        } else {
            sleepSession
        }

    override fun getDailySleepStagesSummary(localDate: LocalDate): DailySleep =
        sleepSessionRepository.getDailySleepStagesSummary(localDate = localDate).toDomain()

    @Transactional
    override fun deleteByDataPointUidIn(dataPointUids: Collection<UUID>) {
        if (dataPointUids.isNotEmpty()) sleepSessionRepository.deleteByDataPointUidIn(dataPointUids)
    }

    @Transactional
    override fun persistBatch(sessions: List<SleepSession>) {
        if (sessions.isEmpty()) return
        sleepSessionRepository.saveAll(sessions.map { it.toEntity() })
    }
}
