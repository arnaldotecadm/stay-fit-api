package br.com.arcasoftware.stayfit.domain

import java.time.Instant

data class DailySummary(
    val userId: String? = null,
    val date: Instant,
    val totalSteps: Long,
    val activeTimeInMinutes: Long,
    val exerciseCalories: Long,
    val totalBurnedCalories: Long,
    val distanceWhileActive: Long,
    val sleepScore: Long
)
