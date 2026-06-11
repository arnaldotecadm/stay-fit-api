package br.com.arcasoftware.stayfit.application.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.ServletException

@Component
class JwtAuthFilter(val tokenProcessor: TokenProcessor) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authentication: Authentication? =
            try {
                tokenProcessor.authenticate(request)
            } catch (ex: Exception) {
                log.error("Cognito ID Token processing error", ex)
                SecurityContextHolder.clearContext()
                throw ex
            }
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}
