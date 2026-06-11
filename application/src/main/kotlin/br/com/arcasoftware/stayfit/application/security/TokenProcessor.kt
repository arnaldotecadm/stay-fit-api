package br.com.arcasoftware.stayfit.application.security

import br.com.arcasoftware.stayfit.domain.JwtAuthentication
import br.com.arcasoftware.stayfit.domain.UserModel
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.proc.BadJWTException
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.text.ParseException

@Component
class TokenProcessor(
    private val configurableJWTProcessor: ConfigurableJWTProcessor<*>,
    private val validation: AuthenticationValidation
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun authenticate(request: HttpServletRequest): Authentication? {
        val authHeader: String? = request.getHeader(AUTHORIZATION_HEADER)
        if (request.requestURI.contains("auth")) {
            return null
        }
        if (authHeader != null) {
            return if (authHeader.startsWith("Bearer")) {
                validateJWTToken(request)
            } else {
                logger.info(authHeader)
                null
            }
        }
        return null
    }

    fun validateJWTToken(request: HttpServletRequest): JwtAuthentication? {
        val authHeader: String = request.getHeader(AUTHORIZATION_HEADER)
        val claims = try {
            configurableJWTProcessor.process(getBearerToken(authHeader), null)
        } catch (ex: BadJWTException) {
            SecurityContextHolder.clearContext()
            logger.error("Error while trying to validate user: {}", authHeader)
            throw ex
        }
        if (null != claims) {
            validation.validateIssuer(claims)
            validation.verifyIfIdToken(claims)
            return JwtAuthentication(
                getUserModel(claims),
                claims
            )
        }
        return null
    }

    private fun getBearerToken(token: String): String {
        return token.substring("Bearer ".length)
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"

        @Throws(ParseException::class)
        fun getUserModel(claimsSet: JWTClaimsSet?): UserModel {
            if (claimsSet != null) {
                return UserModel(
                    claimsSet.getStringClaim("sub"),
                    "",
                    claimsSet.getStringClaim("email"),
                    claimsSet.getBooleanClaim("email_verified"),
                    "",
                    "",
                    true
                )
            } else {
                throw RuntimeException("Fail to generate UserModel")
            }
        }
    }
}
