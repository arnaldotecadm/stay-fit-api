package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.DailySleep
import br.com.arcasoftware.stayfit.domain.SleepSession
import java.time.LocalDate

interface SleepSessionPersistencePort {
    fun persist(sleepSession: SleepSession): SleepSession
    fun getDailySleepStagesSummary(localDate: LocalDate): DailySleep
}
