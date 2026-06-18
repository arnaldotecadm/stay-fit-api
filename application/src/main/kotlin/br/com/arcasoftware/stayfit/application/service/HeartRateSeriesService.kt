package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.HeartRateSeriesServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HeartRateSeriesPersistencePort
import br.com.arcasoftware.stayfit.domain.HeartDailySession
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class HeartRateSeriesService(
    private val heartRateSeriesPersistencePort: HeartRateSeriesPersistencePort
) : HeartRateSeriesServicePort {
    override fun getHeartDailySession(localDate: LocalDate): List<HeartDailySession> {
        return this.heartRateSeriesPersistencePort.getHeartDailySession(localDate)
    }
}
