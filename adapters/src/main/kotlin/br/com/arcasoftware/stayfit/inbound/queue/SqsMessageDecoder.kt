package br.com.arcasoftware.stayfit.inbound.queue

import br.com.arcasoftware.stayfit.application.service.HeartRateSeriesService
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.util.Base64
import java.util.zip.GZIPInputStream

object SqsMessageDecoder {
    private val logger = LoggerFactory.getLogger(this::class.java)
    fun <T> decode(body: String, objectMapper: ObjectMapper, type: Class<T>): T {
        val compressed = Base64.getDecoder().decode(body)
        val json = GZIPInputStream(ByteArrayInputStream(compressed)).use { it.readBytes() }
        return try {
            return objectMapper.readValue(json, type)
        } catch (e: StackOverflowError) {
            logger.error(
                "Stack overflow while deserializing {} bytes into {}",
                json.size,
                type
            )
            throw e
        }
    }
}
