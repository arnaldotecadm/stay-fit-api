package br.com.arcasoftware.stayfit.outbound.persistence.mapper

import br.com.arcasoftware.stayfit.domain.DailySummary
import br.com.arcasoftware.stayfit.domain.DailySummaryActivity
import br.com.arcasoftware.stayfit.model.DailySummaryActivityDTO
import br.com.arcasoftware.stayfit.model.DailySummaryDTO
import br.com.arcasoftware.stayfit.outbound.persistence.model.DailySummaryActivityEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.DailySummaryEntity

object DailySummaryMapper {

    fun DailySummaryDTO.toDomain(): DailySummary {
        return DailySummary(
            date = this.date,
            totalSteps = this.totalSteps,
            activeTimeInMinutes = this.activeTimeInMinutes,
            exerciseCalories = this.exerciseCalories,
            totalBurnedCalories = this.totalBurnedCalories,
            distanceWhileActive = this.distanceWhileActive,
            sleepScore = this.sleepScore,
            exerciseList = this.exerciseList.map { it.toDomain() }
        )
    }

    fun DailySummary.toEntity(): DailySummaryEntity =
        DailySummaryEntity(
            date = this.date,
            totalSteps = this.totalSteps,
            activeTimeInMinutes = this.activeTimeInMinutes,
            exerciseCalories = this.exerciseCalories,
            totalBurnedCalories = this.totalBurnedCalories,
            distanceWhileActive = this.distanceWhileActive,
            sleepScore = this.sleepScore,
            exerciseList = this.exerciseList.map { it.toEntity() }
        )

    fun DailySummaryEntity.toDomain(): DailySummary =
        DailySummary(
            date = this.date,
            totalSteps = this.totalSteps,
            activeTimeInMinutes = this.activeTimeInMinutes,
            exerciseCalories = this.exerciseCalories,
            totalBurnedCalories = this.totalBurnedCalories,
            distanceWhileActive = this.distanceWhileActive,
            sleepScore = this.sleepScore,
            exerciseList = this.exerciseList.map { it.toDomain() }
        )

    fun DailySummaryActivityDTO.toDomain(): DailySummaryActivity {
        return DailySummaryActivity(
            calories = this.calories,
            dataSource = this.dataSource,
            duration = this.duration,
            exerciseType = this.exerciseType
        )
    }

    fun DailySummaryActivity.toEntity(): DailySummaryActivityEntity {
        return DailySummaryActivityEntity(
            calories = this.calories,
            duration = this.duration,
            dataSource = this.dataSource,
            exerciseType = this.exerciseType
        )
    }

    fun DailySummaryActivityEntity.toDomain(): DailySummaryActivity {
        return DailySummaryActivity(
            calories = this.calories,
            dataSource = this.dataSource,
            duration = this.duration,
            exerciseType = this.exerciseType
        )
    }
}
