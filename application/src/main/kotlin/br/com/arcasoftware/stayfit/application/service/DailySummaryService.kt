package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.DailySummaryServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.DailySummaryPersistencePort
import br.com.arcasoftware.stayfit.domain.DailySummary
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

@Service
class DailySummaryService(private val dailySummaryPersistence: DailySummaryPersistencePort) :
    DailySummaryServicePort {

    private val queue = ConcurrentLinkedQueue<DailySummary>()

    override fun enqueue(dailySummary: DailySummary) {
        queue.add(dailySummary)
    }

    @Scheduled(fixedDelay = 100)
    fun processQueue() {
        if (this.queue.isNotEmpty()) {
            this.dailySummaryPersistence.persist(this.queue.poll())
        }
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 10)
    fun printCount(){
        if (this.queue.isNotEmpty()) {
            println("Daily Summary Queue Size : ${queue.size}")
        }
    }
}
