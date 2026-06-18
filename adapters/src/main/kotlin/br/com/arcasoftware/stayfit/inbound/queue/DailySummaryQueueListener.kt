package br.com.arcasoftware.stayfit.inbound.queue

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.DailySummaryPersistencePort
import br.com.arcasoftware.stayfit.domain.DailySummary
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

private val logger = LoggerFactory.getLogger(DailySummaryQueueListener::class.java)

@Service
class DailySummaryQueueListener(
    private val dailySummaryPersistencePort: DailySummaryPersistencePort,
    private val objectMapper: ObjectMapper,
) {
    @SqsListener("\${cloud.aws.sqs.daily-summary-queue.url}")
    fun onMessages(messages: List<Message<String>>) {
        val summaries = messages.map { SqsMessageDecoder.decode(it.payload, objectMapper, DailySummary::class.java) }
        dailySummaryPersistencePort.persistBatch(summaries)
        logger.debug("Persisted batch of {} daily summaries", summaries.size)
    }
}
