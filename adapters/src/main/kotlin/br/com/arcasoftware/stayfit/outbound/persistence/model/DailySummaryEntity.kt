package br.com.arcasoftware.stayfit.outbound.persistence.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "daily_summary", indexes = [Index(name = "ix_daily_summary_date", columnList = "date")])
data class DailySummaryEntity(
    val date: LocalDate,
    val totalSteps: Long,
    val activeTimeInMinutes: Long,
    val exerciseCalories: Long,
    val totalBurnedCalories: Long,
    val distanceWhileActive: Long,
    val sleepScore: Long,
    @OneToMany(cascade = [CascadeType.ALL]) val exerciseList: List<DailySummaryActivityEntity>
) : BaseEntity()
