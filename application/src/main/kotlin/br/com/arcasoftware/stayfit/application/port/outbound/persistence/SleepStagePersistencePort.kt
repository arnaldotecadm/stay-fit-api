package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.SleepStage
import java.util.UUID

interface SleepStagePersistencePort {
    fun persist(sleepStage: SleepStage): SleepStage
    fun deleteByDataPointUid(dataPointUid: UUID)
}
