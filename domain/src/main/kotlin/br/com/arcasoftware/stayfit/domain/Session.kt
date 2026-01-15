package br.com.arcasoftware.stayfit.domain

import java.time.Instant
import java.util.UUID

interface Session {
    val dataPointUid: UUID
    val startTime: Instant
    val endTime: Instant
}
