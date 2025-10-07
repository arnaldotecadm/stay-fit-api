package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.SleepSession

interface SleepSessionPersistencePort {
    fun persist(sleepSession: SleepSession): SleepSession
}
