package br.com.arcasoftware.stayfit.outbound.queue

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequest
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry
import java.io.ByteArrayOutputStream
import java.util.Base64
import java.util.zip.GZIPOutputStream

import java.util.concurrent.CompletableFuture

private val logger = LoggerFactory.getLogger(AbstractSqsAdapter::class.java)

private const val SQS_MAX_BATCH_SIZE = 10

abstract class AbstractSqsAdapter(
    private val sqsAsyncClient: SqsAsyncClient,
    private val objectMapper: ObjectMapper,
    private val queueUrl: String,
) {
    protected fun <T : Any> doSendBatch(items: List<T>) {
        if (items.isEmpty()) return
        CompletableFuture.runAsync {
            items.chunked(SQS_MAX_BATCH_SIZE).forEach { chunk ->
                val entries = chunk.mapIndexed { index, item ->
                    SendMessageBatchRequestEntry.builder()
                        .id(index.toString())
                        .messageBody(compressAndEncode(item))
                        .build()
                }
                sqsAsyncClient.sendMessageBatch(
                    SendMessageBatchRequest.builder()
                        .queueUrl(queueUrl)
                        .entries(entries)
                        .build(),
                ).whenComplete { _, ex ->
                    if (ex != null) logger.error("Failed to send batch of {} to {}: {}", chunk.size, queueUrl, ex.message, ex)
                    else logger.info("Sent batch of {} messages to {}", chunk.size, queueUrl)
                }
            }
        }
    }

    private fun <T : Any> compressAndEncode(item: T): String {
        val json = objectMapper.writeValueAsBytes(item)
        val compressed = ByteArrayOutputStream().also { out ->
            GZIPOutputStream(out).use { gzip -> gzip.write(json) }
        }.toByteArray()
        return Base64.getEncoder().encodeToString(compressed)
    }
}
