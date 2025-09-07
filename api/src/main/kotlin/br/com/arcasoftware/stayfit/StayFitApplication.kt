package br.com.arcasoftware.stayfit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StayFitApplication

fun main(args: Array<String>) {
    runApplication<StayFitApplication>(*args)
}
