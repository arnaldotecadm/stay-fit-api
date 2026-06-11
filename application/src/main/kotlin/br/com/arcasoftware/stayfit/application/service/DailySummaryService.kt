package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.DailySummaryServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.DailySummaryPersistencePort
import br.com.arcasoftware.stayfit.domain.DailyActivitySummary
import br.com.arcasoftware.stayfit.domain.DailySummary
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

@Service
class DailySummaryService(private val dailySummaryPersistence: DailySummaryPersistencePort) :
    DailySummaryServicePort {

    private val queue = ConcurrentLinkedQueue<DailySummary>()
    private var printedEmpty = false

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
    fun printCount() {
        if (this.queue.isNotEmpty()) {
            println("Daily Summary Queue Size : ${queue.size}")
        } else {
            if (!this.printedEmpty) {
                println("Daily Summary Queue Empty")
                this.printedEmpty = true
            }
        }
    }

    override fun getDailySummaryEntityByDate(dateTime: LocalDate): DailyActivitySummary? {
        return this.dailySummaryPersistence.getDailySummaryEntityByDate(dateTime)
    }
}
