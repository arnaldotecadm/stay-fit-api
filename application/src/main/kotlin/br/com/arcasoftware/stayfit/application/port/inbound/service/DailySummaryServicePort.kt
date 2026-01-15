package br.com.arcasoftware.stayfit.application.port.inbound.service

import br.com.arcasoftware.stayfit.domain.DailySummary

interface DailySummaryServicePort {
    fun enqueue(dailySummary: DailySummary)
}
