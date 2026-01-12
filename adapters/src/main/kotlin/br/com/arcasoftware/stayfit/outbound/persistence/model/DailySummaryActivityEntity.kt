package br.com.arcasoftware.stayfit.outbound.persistence.model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.Duration

@Entity
@Table(name = "daily_summary_activity")
data class DailySummaryActivityEntity(
    val calories: Float,
    val dataSource :String,
    val duration: Duration,
    val exerciseType: String
) : BaseEntity()
