package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.domain.HeartDailySession
import java.time.LocalDate

interface HeartRateSeriesServicePort {
    fun enqueue(batch: List<HealthDataPoint>)
    fun getHeartDailySession(localDate: LocalDate): List<HeartDailySession>
}
