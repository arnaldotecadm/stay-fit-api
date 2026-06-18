package br.com.arcasoftware.stayfit.outbound.queue

import br.com.arcasoftware.stayfit.application.port.outbound.queue.ExerciseSessionQueuePort
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sqs.SqsAsyncClient

@Service
class ExerciseSessionSqsAdapter(
    sqsAsyncClient: SqsAsyncClient,
    objectMapper: ObjectMapper,
    @Value("\${cloud.aws.sqs.exercise-session-queue.url}") queueUrl: String,
) : AbstractSqsAdapter(sqsAsyncClient, objectMapper, queueUrl), ExerciseSessionQueuePort {

    override fun sendBatch(healthDataPoints: List<HealthDataPoint>) = doSendBatch(healthDataPoints)
}
