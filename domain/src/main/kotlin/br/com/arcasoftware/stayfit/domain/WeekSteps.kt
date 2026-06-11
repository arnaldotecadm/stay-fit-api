package br.com.arcasoftware.stayfit.domain

import java.time.LocalDate

data class WeekSteps(
    val date: LocalDate,
    val steps: Long
)
