package br.com.arcasoftware.stayfit.inbound.queue

import br.com.arcasoftware.stayfit.application.service.SleepSessionChunkPersistenceService
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

private val logger = LoggerFactory.getLogger(SleepSessionQueueListener::class.java)

@Service
@ConditionalOnProperty(
    value = ["cloud.aws.sqs.enabled"],
    havingValue = "true",
    matchIfMissing = false
)
class SleepSessionQueueListener(
    private val chunkPersistenceService: SleepSessionChunkPersistenceService,
    private val objectMapper: ObjectMapper,
) {
    @SqsListener("\${cloud.aws.sqs.sleep-session-queue.url}")
    fun onMessages(messages: List<Message<String>>) {
        val dataPoints = messages.map { SqsMessageDecoder.decode(it.payload, objectMapper, HealthDataPoint::class.java) }
        chunkPersistenceService.persistBatch(dataPoints)
        logger.info("Persisted batch of {} sleep data points", dataPoints.size)
    }
}
