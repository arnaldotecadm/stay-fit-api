package br.com.arcasoftware.stayfit.domain

data class UserModel(
    val sub: String,
    val name: String,
    val email: String? = null,
    val isEmailVerified: Boolean = false,
    val issuer: String? = null,
    val picture: String? = null,
    val active: Boolean = false
)
