package br.com.arcasoftware.stayfit.application.port.outbound.persistence

import br.com.arcasoftware.stayfit.domain.HeartDailySession
import br.com.arcasoftware.stayfit.domain.HeartRateSeries
import java.time.LocalDate

interface HeartRateSeriesPersistencePort {
    fun persistBatch(heartRateSeriesBatch: List<HeartRateSeries>)
    fun getHeartDailySession(localDate: LocalDate): List<HeartDailySession>
}
