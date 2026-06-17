package br.com.arcasoftware.stayfit.domain

import java.time.Duration
import java.time.Instant

data class DailyActivity(
    val duration: Duration,
    val exerciseType: String?,
    val calories: Long,
    val localdate: Instant
)
