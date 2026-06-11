package br.com.arcasoftware.stayfit.application.enums

import org.springframework.http.HttpStatus

enum class ExceptionEnum(val status: HttpStatus, val message: String) {
    AUTHENTICATION_ERROR(HttpStatus.UNAUTHORIZED, "An error has happened while trying to authenticate the user")
}
