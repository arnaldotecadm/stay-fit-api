package br.com.arcasoftware.stayfit.configs

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Component
class GzipRequestFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val encoding = request.getHeader("Content-Encoding")

        if (encoding != null && encoding.equals("gzip", ignoreCase = true)) {
            val gzipRequest = GzipHttpServletRequestWrapper(request)
            filterChain.doFilter(gzipRequest, response)
        } else {
            filterChain.doFilter(request, response)
        }
    }
}
