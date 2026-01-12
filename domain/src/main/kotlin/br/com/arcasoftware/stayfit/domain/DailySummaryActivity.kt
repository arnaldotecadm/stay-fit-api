package br.com.arcasoftware.stayfit.domain

import java.time.Duration

data class DailySummaryActivity(
    val calories: Float,
    val dataSource: String,
    val duration: Duration,
    val exerciseType: String
)
