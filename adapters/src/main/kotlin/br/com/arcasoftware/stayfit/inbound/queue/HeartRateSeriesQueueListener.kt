package br.com.arcasoftware.stayfit.inbound.queue

import br.com.arcasoftware.stayfit.application.service.HeartRateSeriesChunkPersistenceService
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

private val logger = LoggerFactory.getLogger(HeartRateSeriesQueueListener::class.java)

@Service
@ConditionalOnProperty(
    value = ["cloud.aws.sqs.enabled"],
    havingValue = "true",
    matchIfMissing = false
)
class HeartRateSeriesQueueListener(
    private val chunkPersistenceService: HeartRateSeriesChunkPersistenceService,
    private val objectMapper: ObjectMapper,
) {
    @SqsListener("\${cloud.aws.sqs.heart-rate-series-queue.url}")
    fun onMessages(messages: List<Message<String>>) {
        val dataPoints = messages.map { SqsMessageDecoder.decode(it.payload, objectMapper, HealthDataPoint::class.java) }
        chunkPersistenceService.persistChunk(dataPoints)
        logger.info("Persisted batch of {} heart-rate data points", dataPoints.size)
    }
}
