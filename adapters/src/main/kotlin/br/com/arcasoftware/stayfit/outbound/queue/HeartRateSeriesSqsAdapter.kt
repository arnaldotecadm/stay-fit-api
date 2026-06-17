package br.com.arcasoftware.stayfit.outbound.queue

import br.com.arcasoftware.stayfit.application.port.outbound.queue.HeartRateSeriesQueuePort
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sqs.SqsClient

@Service
class HeartRateSeriesSqsAdapter(
    sqsClient: SqsClient,
    objectMapper: ObjectMapper,
    @Value("\${cloud.aws.sqs.heart-rate-series-queue.url}") queueUrl: String,
) : AbstractSqsAdapter(sqsClient, objectMapper, queueUrl), HeartRateSeriesQueuePort {

    override fun sendBatch(healthDataPoints: List<HealthDataPoint>) = doSendBatch(healthDataPoints)
}
