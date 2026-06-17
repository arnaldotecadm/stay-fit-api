package br.com.arcasoftware.stayfit.outbound.persistence.model.projection

import java.time.Instant

interface HeartDailySessionProjection {
    val minuteBucket: Instant
    val avgHr: Float
    val maxHr: Float
    val minHr: Float
}
