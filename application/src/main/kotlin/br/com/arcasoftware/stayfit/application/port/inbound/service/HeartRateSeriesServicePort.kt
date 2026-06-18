package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.HeartDailySession
import java.time.LocalDate

fun interface HeartRateSeriesServicePort {
    fun getHeartDailySession(localDate: LocalDate): List<HeartDailySession>
}
