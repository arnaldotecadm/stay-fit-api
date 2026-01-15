package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.HealthDataPoint

interface SleepSessionServicePort {
    fun enqueue(healthDataPoint: HealthDataPoint)
}
