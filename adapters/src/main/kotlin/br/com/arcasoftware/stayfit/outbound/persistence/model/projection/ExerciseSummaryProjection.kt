package br.com.arcasoftware.stayfit.outbound.persistence.model.projection

import java.util.UUID

interface ExerciseSummaryProjection {
    val dataPointUid: UUID
    val healthDataType: String
    val exerciseType: String
    val date: String
    val startTime: String
    val endTime: String
    val distance: String?
    val count: Int?
    val duration: String
    val calories: Float
}