package br.com.arcasoftware.stayfit.domain

import java.time.Instant

data class DailyActivitySummary(
    val date: Instant,
    val totalSteps: Long,
    val sleepScore: Long,
    val activeTimeInMinutes: Long,
    val totalBurnedCalories: Long,
    val distanceKm: Long,
    val exerciseCalories: Long,
    val activityCount: Long,
    val activityCaloriesSum: Long,
    val weekSteps: List<WeekSteps>,
    val activityList: List<DailyActivity>
)
