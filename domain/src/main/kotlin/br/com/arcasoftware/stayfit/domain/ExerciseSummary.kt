package br.com.arcasoftware.stayfit.domain

import java.util.UUID

data class ExerciseSummary(
    val dataPointUid: UUID,
    val healthDataType: String,
    val exerciseType: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val distance: String? = null,
    val count: Int? = null,
    val duration: String,
    val calories: Float
)
