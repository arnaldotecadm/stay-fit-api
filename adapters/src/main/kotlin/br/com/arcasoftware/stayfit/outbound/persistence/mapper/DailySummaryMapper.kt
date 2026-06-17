package br.com.arcasoftware.stayfit.outbound.persistence.mapper

import br.com.arcasoftware.stayfit.configs.JacksonConfig
import br.com.arcasoftware.stayfit.domain.DailyActivity
import br.com.arcasoftware.stayfit.domain.DailyActivitySummary
import br.com.arcasoftware.stayfit.domain.DailySummary
import br.com.arcasoftware.stayfit.domain.JwtAuthentication
import br.com.arcasoftware.stayfit.domain.UserModel
import br.com.arcasoftware.stayfit.domain.WeekSteps
import br.com.arcasoftware.stayfit.model.DailyActivityDTO
import br.com.arcasoftware.stayfit.model.DailySummaryDTO
import br.com.arcasoftware.stayfit.model.DailySummaryResponseDTO
import br.com.arcasoftware.stayfit.model.WeekStepDTO
import br.com.arcasoftware.stayfit.outbound.persistence.model.DailySummaryEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.projection.DailyActivitySummaryProjection
import org.springframework.security.core.context.SecurityContextHolder

object DailySummaryMapper {
    fun DailySummaryDTO.toDomain(): DailySummary {
        val userSub = (SecurityContextHolder.getContext().authentication as JwtAuthentication).principal as UserModel
        return DailySummary(
            userId = userSub.sub,
            date = this.date,
            totalSteps = this.totalSteps,
            activeTimeInMinutes = this.activeTimeInMinutes,
            exerciseCalories = this.exerciseCalories,
            totalBurnedCalories = this.totalBurnedCalories,
            distanceWhileActive = this.distanceWhileActive,
            sleepScore = this.sleepScore
        )
    }

    fun DailySummary.toEntity(): DailySummaryEntity =
        DailySummaryEntity(
            userId = userId,
            date = this.date,
            totalSteps = this.totalSteps,
            activeTimeInMinutes = this.activeTimeInMinutes,
            exerciseCalories = this.exerciseCalories,
            totalBurnedCalories = this.totalBurnedCalories,
            distanceWhileActive = this.distanceWhileActive,
            sleepScore = this.sleepScore
        )

    fun DailySummaryEntity.toDomain(): DailySummary =
        DailySummary(
            userId = userId,
            date = this.date,
            totalSteps = this.totalSteps,
            activeTimeInMinutes = this.activeTimeInMinutes,
            exerciseCalories = this.exerciseCalories,
            totalBurnedCalories = this.totalBurnedCalories,
            distanceWhileActive = this.distanceWhileActive,
            sleepScore = this.sleepScore
        )

    fun DailyActivitySummaryProjection.toDomain(): DailyActivitySummary =
        DailyActivitySummary(
            date = this.date,
            sleepScore = this.sleepScore,
            totalSteps = this.totalSteps,
            activeTimeInMinutes = this.activeTimeInMinutes,
            totalBurnedCalories = this.totalBurnedCalories,
            distanceKm = this.distanceKm,
            exerciseCalories = this.exerciseCalories,
            activityCount = this.activityCount,
            activityCaloriesSum = this.activityCaloriesSum,
            weekSteps = JacksonConfig().objectMapper().readValue(this.weekSteps, Array<WeekSteps>::class.java).toList(),
            activityList =
                JacksonConfig()
                    .objectMapper()
                    .readValue(this.activities, Array<DailyActivity>::class.java)
                    .toList(),
        )

    fun DailyActivitySummary.toDomain(): DailySummaryResponseDTO =
        DailySummaryResponseDTO(
            date = this.date,
            sleepScore = this.sleepScore,
            totalSteps = this.totalSteps,
            activeTimeInMinutes = this.activeTimeInMinutes,
            totalBurnedCalories = this.totalBurnedCalories,
            distanceWhileActive = this.distanceKm,
            exerciseCalories = this.exerciseCalories,
            activityCount = this.activityCount,
            activityCaloriesSum = this.activityCaloriesSum,
            weekSteps = this.weekSteps.map { WeekStepDTO(date = it.date, steps = it.steps) },
            activityList =
                this.activityList.map {
                    DailyActivityDTO(
                        exerciseType = it.exerciseType,
                        duration = it.duration,
                        calories = it.calories,
                        localdate = it.localdate,
                    )
                },
        )
}
