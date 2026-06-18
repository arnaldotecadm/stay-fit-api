package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.DailySleep
import br.com.arcasoftware.stayfit.domain.SleepSession
import java.time.LocalDate
import java.util.UUID

interface SleepSessionPersistencePort {
    fun persist(sleepSession: SleepSession): SleepSession
    fun getDailySleepStagesSummary(localDate: LocalDate): DailySleep
    fun deleteByDataPointUidIn(dataPointUids: Collection<UUID>)
    fun persistBatch(sessions: List<SleepSession>)
}
