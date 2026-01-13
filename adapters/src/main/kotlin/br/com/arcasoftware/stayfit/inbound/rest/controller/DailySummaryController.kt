package br.com.arcasoftware.stayfit.inbound.rest.controller

import br.com.arcasoftware.stayfit.application.port.inbound.service.DailySummaryServicePort
import br.com.arcasoftware.stayfit.controller.DailySummaryApi
import br.com.arcasoftware.stayfit.model.DailySummaryDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.DailySummaryMapper.toDomain
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class DailySummaryController(
    private val dailySummaryService: DailySummaryServicePort
) : DailySummaryApi {
    override fun postDailySummary(dailySummaryDTO: DailySummaryDTO): ResponseEntity<String> {
        println("Posting daily summary")
        this.dailySummaryService.persist(dailySummaryDTO.toDomain())
        return ResponseEntity.ok().build()
    }

}
