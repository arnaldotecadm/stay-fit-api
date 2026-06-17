package br.com.arcasoftware.stayfit.domain

import java.time.Instant

data class HeartDailySession(
    val minuteBucket: Instant,
    val avgHr: Float,
    val maxHr: Float,
    val minHr: Float
)
