package br.com.arcasoftware.stayfit.outbound.persistence.model

import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "daily_summary", indexes = [Index(name = "ix_daily_summary_date", columnList = "date")])
data class DailySummaryEntity(
    override val userId: String? = null,
    val date: Instant,
    val totalSteps: Long,
    val activeTimeInMinutes: Long,
    val exerciseCalories: Long,
    val totalBurnedCalories: Long,
    val distanceWhileActive: Long,
    val sleepScore: Long,
) : BaseEntity(userId = userId)
