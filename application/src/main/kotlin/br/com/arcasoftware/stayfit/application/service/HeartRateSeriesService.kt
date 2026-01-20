package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.inbound.service.HealthDataPointServicePort
import br.com.arcasoftware.stayfit.application.port.inbound.service.HeartRateSeriesServicePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HeartRateSeriesPersistencePort
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.domain.HeartRateSeries
import br.com.arcasoftware.stayfit.domain.Session
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

@Service
class HeartRateSeriesService(
    private val heartRateSeriesPersistencePort: HeartRateSeriesPersistencePort,
    private val dataPointService: HealthDataPointServicePort
) : HeartRateSeriesServicePort {

    private val queue = ConcurrentLinkedQueue<HealthDataPoint>()

    override fun enqueue(batch: List<HealthDataPoint>) {
        queue.addAll(batch)
    }

    @Scheduled(fixedDelay = 100)
    fun processQueue() {
        val chunk = mutableListOf<HealthDataPoint>()
        while (chunk.size < 500 && queue.isNotEmpty()) {
            chunk.add(queue.poll())
        }
        if (chunk.isNotEmpty()) {
            chunk.forEach {
                dataPointService.persistHeartRate(it)
                it.sessions?.forEach { session -> persist(session) }
            }
        }
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 10)
    fun printCount() {
        if (this.queue.isNotEmpty()) {
            println("Heart Rate Queue Size : ${queue.size}")
        }
    }

    private fun persist(heartRateSeries: Session): HeartRateSeries {
        heartRateSeries as HeartRateSeries
        return this.heartRateSeriesPersistencePort.persist(heartRateSeries)
    }
}
