package br.com.arcasoftware.stayfit.inbound.rest.controller

import br.com.arcasoftware.stayfit.application.port.inbound.service.ExerciseSessionServicePort
import br.com.arcasoftware.stayfit.controller.ExercisesApi
import br.com.arcasoftware.stayfit.model.ExerciseSummaryDTO
import br.com.arcasoftware.stayfit.model.HealthExerciseDataPointDTO
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.ExerciseSessionMapper.toDto
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HealthDataPointMapper.toDomain
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
class ExerciseSessionController(
    private val exerciseSessionService: ExerciseSessionServicePort
) : ExercisesApi {
    override fun postExerciseSession(healthExerciseDataPointDTO: HealthExerciseDataPointDTO): ResponseEntity<String> {
        println("Posting exercise")
        this.exerciseSessionService.enqueue(healthExerciseDataPointDTO.toDomain())
        return ResponseEntity.ok().build()
    }

    override fun getExerciseSummary(): ResponseEntity<List<ExerciseSummaryDTO>> {
        val exerciseSessionSummary = this.exerciseSessionService.getExerciseSessionSummary()
        return ResponseEntity.ok(exerciseSessionSummary.map { it.toDto() })
    }
}
