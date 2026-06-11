package br.com.arcasoftware.stayfit.application.exception

import br.com.arcasoftware.stayfit.application.enums.ExceptionEnum
import org.springframework.http.HttpStatus

class ValidationException(message: String) : RuntimeException(message) {
    private lateinit var httpStatus: HttpStatus

    constructor(exceptionEnum: ExceptionEnum) : this(exceptionEnum.message) {
        this.httpStatus = exceptionEnum.status
    }
}
