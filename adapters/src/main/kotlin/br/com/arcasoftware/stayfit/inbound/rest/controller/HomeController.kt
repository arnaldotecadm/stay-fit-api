package br.com.arcasoftware.stayfit.inbound.rest.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
class HomeController {

    @GetMapping("ping")
    fun ping(): String {
        return "pong"
    }
}