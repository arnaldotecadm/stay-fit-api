package br.com.arcasoftware.stayfit.outbound.queue

import br.com.arcasoftware.stayfit.application.port.outbound.queue.DailySummaryQueuePort
import br.com.arcasoftware.stayfit.domain.DailySummary
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sqs.SqsAsyncClient

@Service
class DailySummarySqsAdapter(
    sqsAsyncClient: SqsAsyncClient,
    objectMapper: ObjectMapper,
    @Value("\${cloud.aws.sqs.daily-summary-queue.url}") queueUrl: String,
) : AbstractSqsAdapter(sqsAsyncClient, objectMapper, queueUrl), DailySummaryQueuePort {

    override fun sendBatch(dailySummaries: List<DailySummary>) = doSendBatch(dailySummaries)
}
