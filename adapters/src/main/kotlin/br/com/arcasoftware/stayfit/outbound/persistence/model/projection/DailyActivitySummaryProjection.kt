package br.com.arcasoftware.stayfit.outbound.persistence.model.projection

import java.time.LocalDate

interface DailyActivitySummaryProjection {
    val date: LocalDate
    val sleepScore: Long
    val totalSteps: Long
    val activeTimeInMinutes: Long
    val totalBurnedCalories: Long
    val distanceKm: Long
    val exerciseCalories: Long
    val activityCount: Long
    val activityCaloriesSum: Long
    val weekSteps: String
    val activities: String
}