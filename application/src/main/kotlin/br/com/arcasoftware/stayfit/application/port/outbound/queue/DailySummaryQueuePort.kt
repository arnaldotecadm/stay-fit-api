package br.com.arcasoftware.stayfit.application.port.outbound.queue

import br.com.arcasoftware.stayfit.domain.DailySummary

interface DailySummaryQueuePort {
    fun sendBatch(dailySummaries: List<DailySummary>)
}
