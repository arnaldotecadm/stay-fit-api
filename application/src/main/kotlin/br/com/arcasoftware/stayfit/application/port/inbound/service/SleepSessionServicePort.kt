package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.DailySleep
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import java.time.LocalDate

interface SleepSessionServicePort {
    fun enqueue(healthDataPoint: HealthDataPoint)
    fun getDailySleepStagesSummary(localDate: LocalDate): DailySleep
}
