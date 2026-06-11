package br.com.arcasoftware.stayfit.security

import br.com.arcasoftware.stayfit.application.security.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.security.config.Customizer

@Configuration
class SecurityConfig(private val jwtAuthFilter: JwtAuthFilter) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/actuator/**").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/api/v1/auth/**").permitAll()
            .anyRequest().permitAll()
            .and()
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .cors()

        return http.build()
    }

    /**
     * TODO
     * @Bean
     * Used for debugging purposes when the body request must be audited
     */
    fun requestLoggingFilter(): CommonsRequestLoggingFilter {
        return CommonsRequestLoggingFilter().apply {
            setIncludePayload(true)
            setIncludeHeaders(false)
            setIncludeQueryString(true)
            setMaxPayloadLength(10000)
            setAfterMessagePrefix("REQUEST DATA: ")
        }
    }
}
