package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.SleepSession

interface SleepSessionServicePort {
    fun persist(sleepSession: SleepSession): SleepSession
}
