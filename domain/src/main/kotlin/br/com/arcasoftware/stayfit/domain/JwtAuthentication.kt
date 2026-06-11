package br.com.arcasoftware.stayfit.domain

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable

class JwtAuthentication(authoritiesList: Collection<GrantedAuthority>?) : AbstractAuthenticationToken(authoritiesList), Serializable {
    private lateinit var principal: Any
    private lateinit var credentials: Any

    constructor(principal: Any, credentials: Any) : this(null) {
        this.principal = principal
        this.credentials = credentials
        isAuthenticated = true
    }

    override fun getCredentials(): Any {
        return this.credentials
    }

    override fun getPrincipal(): Any {
        return this.principal
    }
}
