package br.com.arcasoftware.stayfit.application.config

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.jwk.source.JWKSourceBuilder
import com.nimbusds.jose.proc.JWSKeySelector
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jose.util.DefaultResourceRetriever
import com.nimbusds.jose.util.ResourceRetriever
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.net.MalformedURLException
import java.net.URL

@Component
class Configuration {
    @Bean
    @Throws(MalformedURLException::class)
    fun configurableJWTProcessor(): ConfigurableJWTProcessor<SecurityContext> {
        val resourceRetriever: ResourceRetriever = DefaultResourceRetriever(
            2000,
            2000
        )
        val jwkURL = URL("https://www.googleapis.com/oauth2/v3/certs")

        val keySource: JWKSource<SecurityContext> =
            JWKSourceBuilder.create<SecurityContext>(jwkURL, resourceRetriever).build()
        val jwtProcessor: ConfigurableJWTProcessor<SecurityContext> = DefaultJWTProcessor()
        val keySelector: JWSKeySelector<SecurityContext> = JWSVerificationKeySelector(JWSAlgorithm.RS256, keySource)
        jwtProcessor.jwsKeySelector = keySelector
        return jwtProcessor
    }
}
