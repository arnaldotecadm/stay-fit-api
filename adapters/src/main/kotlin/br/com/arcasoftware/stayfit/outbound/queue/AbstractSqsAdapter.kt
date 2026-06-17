package br.com.arcasoftware.stayfit.outbound.queue

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequest
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import java.io.ByteArrayOutputStream
import java.util.Base64
import java.util.zip.GZIPOutputStream

private val logger = LoggerFactory.getLogger(AbstractSqsAdapter::class.java)

private const val SQS_MAX_BATCH_SIZE = 10

abstract class AbstractSqsAdapter(
    private val sqsClient: SqsClient,
    private val objectMapper: ObjectMapper,
    private val queueUrl: String,
) {
    protected fun <T : Any> doSend(item: T) {
        sqsClient.sendMessage(
            SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(compressAndEncode(item))
                .build(),
        )
    }

    protected fun <T : Any> doSendBatch(items: List<T>) {
        if (items.isEmpty()) return

        items.chunked(SQS_MAX_BATCH_SIZE).forEach { chunk ->
            val entries = chunk.mapIndexed { index, item ->
                SendMessageBatchRequestEntry.builder()
                    .id(index.toString())
                    .messageBody(compressAndEncode(item))
                    .build()
            }
            sqsClient.sendMessageBatch(
                SendMessageBatchRequest.builder()
                    .queueUrl(queueUrl)
                    .entries(entries)
                    .build(),
            )
        }
        logger.info("Sent batch of {} messages to {}", items.size, queueUrl)
    }

    private fun <T : Any> compressAndEncode(item: T): String {
        val json = objectMapper.writeValueAsBytes(item)
        val compressed = ByteArrayOutputStream().also { out ->
            GZIPOutputStream(out).use { gzip -> gzip.write(json) }
        }.toByteArray()
        return Base64.getEncoder().encodeToString(compressed)
    }
}
