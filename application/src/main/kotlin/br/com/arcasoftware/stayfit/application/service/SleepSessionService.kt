package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.SleepSessionServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.SleepSessionPersistencePort
import br.com.arcasoftware.stayfit.domain.DailySleep
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SleepSessionService(
    private val sleepSessionPersistencePort: SleepSessionPersistencePort
) : SleepSessionServicePort {

    override fun getDailySleepStagesSummary(localDate: LocalDate): DailySleep {
        return this.sleepSessionPersistencePort.getDailySleepStagesSummary(localDate)
    }
}
