package br.com.arcasoftware.stayfit.domain

import java.time.Instant

data class WeekSteps(
    val date: Instant,
    val steps: Long
)
