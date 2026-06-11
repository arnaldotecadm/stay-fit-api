package br.com.arcasoftware.stayfit.application.security

import br.com.arcasoftware.stayfit.application.enums.ExceptionEnum
import br.com.arcasoftware.stayfit.application.exception.ValidationException
import com.nimbusds.jwt.JWTClaimsSet
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AuthenticationValidation {

    private val logger = LoggerFactory.getLogger(AuthenticationValidation::class.java)
    val issuerUrl = "https://accounts.google.com"

    fun verifyIfIdToken(claims: JWTClaimsSet) {
        if (claims.issuer != issuerUrl) {
            logger.error("Not an ID Token")
            throw ValidationException(ExceptionEnum.AUTHENTICATION_ERROR)
        }
    }

    fun validateIssuer(claims: JWTClaimsSet) {
        if (claims.issuer != issuerUrl) {
            logger.error("Issuer {} does not match cognito idp {}", claims.issuer, issuerUrl)
            throw ValidationException(ExceptionEnum.AUTHENTICATION_ERROR)
        }
    }
}
