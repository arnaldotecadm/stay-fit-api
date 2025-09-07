package br.com.arcasoftware.stayfit.configs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
    private val objectMapperKotlinModule =
        KotlinModule
            .Builder()
            .configure(KotlinFeature.NullIsSameAsDefault, true)
            .build()

    @Bean
    fun objectMapper(): ObjectMapper =
        ObjectMapper()
            .registerModule(objectMapperKotlinModule)
            .registerModule(JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
}
