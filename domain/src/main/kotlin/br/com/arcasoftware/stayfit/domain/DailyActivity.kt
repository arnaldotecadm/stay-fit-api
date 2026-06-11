package br.com.arcasoftware.stayfit.domain

import java.time.Duration

data class DailyActivity(
    val duration: Duration,
    val exerciseType: String?,
    val calories: Long
)
