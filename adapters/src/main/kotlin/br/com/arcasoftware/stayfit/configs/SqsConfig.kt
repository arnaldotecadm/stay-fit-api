package br.com.arcasoftware.stayfit.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import java.net.URI

@Configuration
class SqsConfig(
    @Value("\${cloud.aws.region.static}") private val region: String,
    @Value("\${cloud.aws.sqs.endpoint-override:}") private val endpointOverride: String,
) {
    @Bean
    fun sqsAsyncClient(): SqsAsyncClient =
        SqsAsyncClient.builder()
            .region(Region.of(region))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .applyEndpointOverride()
            .build()

    private fun <B : software.amazon.awssdk.awscore.client.builder.AwsClientBuilder<B, *>> B.applyEndpointOverride(): B =
        apply { if (endpointOverride.isNotBlank()) endpointOverride(URI.create(endpointOverride)) }
}
