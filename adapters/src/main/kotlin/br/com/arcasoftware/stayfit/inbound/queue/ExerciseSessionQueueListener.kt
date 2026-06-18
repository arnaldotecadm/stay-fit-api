package br.com.arcasoftware.stayfit.inbound.queue

import br.com.arcasoftware.stayfit.application.service.ExerciseSessionChunkPersistenceService
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

private val logger = LoggerFactory.getLogger(ExerciseSessionQueueListener::class.java)

@Service
@ConditionalOnProperty(
    value = ["cloud.aws.sqs.enabled"],
    havingValue = "true",
    matchIfMissing = false
)
class ExerciseSessionQueueListener(
    private val chunkPersistenceService: ExerciseSessionChunkPersistenceService,
    private val objectMapper: ObjectMapper,
) {
    @SqsListener("\${cloud.aws.sqs.exercise-session-queue.url}")
    fun onMessages(messages: List<Message<String>>) {
        val dataPoints = messages.map { SqsMessageDecoder.decode(it.payload, objectMapper, HealthDataPoint::class.java) }
        chunkPersistenceService.persistChunk(dataPoints)
        logger.info("Persisted batch of {} exercise data points", dataPoints.size)
    }
}
