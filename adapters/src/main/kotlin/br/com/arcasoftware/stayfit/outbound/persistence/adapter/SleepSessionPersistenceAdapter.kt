package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepSessionPersistencePort
import br.com.arcasoftware.stayfit.domain.SleepSession
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepSessionMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.SleepSessionMapper.toEntity
import br.com.arcasoftware.stayfit.outbound.persistence.repository.SleepSessionRepository
import org.springframework.stereotype.Service

@Service
class SleepSessionPersistenceAdapter(
    private val sleepSessionRepository: SleepSessionRepository
) : SleepSessionPersistencePort {
    override fun persist(sleepSession: SleepSession): SleepSession {
        return if (sleepSessionRepository.findByDataPointUid(sleepSession.dataPointUid) == null)
            sleepSessionRepository
                .save(sleepSession.toEntity())
                .toDomain()
        else
            sleepSession
    }
}
