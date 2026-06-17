package br.com.arcasoftware.stayfit.inbound.rest.controller

import br.com.arcasoftware.stayfit.application.port.inbound.service.DailySummaryServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.queue.DailySummaryQueuePort
import br.com.arcasoftware.stayfit.controller.DailySummaryApi
import br.com.arcasoftware.stayfit.model.DailySummaryDTO
import br.com.arcasoftware.stayfit.model.DailySummaryResponseDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.DailySummaryMapper.toDomain
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@CrossOrigin("*")
class DailySummaryController(
    private val dailySummaryService: DailySummaryServicePort,
    private val dailySummaryQueuePort: DailySummaryQueuePort,
) : DailySummaryApi {
    override fun postDailySummary(dailySummaryDTO: List<DailySummaryDTO>): ResponseEntity<String> {
        dailySummaryQueuePort.sendBatch(dailySummaryDTO.map { it.toDomain() })
        return ResponseEntity.accepted().build()
    }

    override fun getDailySummary(date: LocalDate): ResponseEntity<DailySummaryResponseDTO> {
        this.dailySummaryService.getDailySummaryEntityByDate(date)?.let {
            return ResponseEntity.ok(it.toDomain())
        }
        return ResponseEntity.noContent().build()
    }
}
