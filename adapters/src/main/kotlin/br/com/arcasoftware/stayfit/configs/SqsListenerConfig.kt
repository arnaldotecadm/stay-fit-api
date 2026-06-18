package br.com.arcasoftware.stayfit.configs

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import java.time.Duration

@Configuration
class SqsListenerConfig(
    @Value("\${cloud.aws.sqs.max-concurrent-messages}") private val maxConcurrentMessages: Int,
    @Value("\${cloud.aws.sqs.max-messages-per-poll}") private val maxMessagesPerPoll: Int,
) {
    @Bean
    fun defaultSqsListenerContainerFactory(sqsAsyncClient: SqsAsyncClient): SqsMessageListenerContainerFactory<Any> =
        SqsMessageListenerContainerFactory.builder<Any>()
            .configure { options ->
                options
                    .maxConcurrentMessages(maxConcurrentMessages)
                    .maxMessagesPerPoll(maxMessagesPerPoll)
                    .pollTimeout(Duration.ofSeconds(20))
            }
            .sqsAsyncClient(sqsAsyncClient)
            .build()
}
