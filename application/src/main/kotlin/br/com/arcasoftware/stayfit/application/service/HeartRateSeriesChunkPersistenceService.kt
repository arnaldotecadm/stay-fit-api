package br.com.arcasoftware.stayfit.application.service

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HealthDataPointPersistencePort
import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HeartRateSeriesPersistencePort
import br.com.arcasoftware.stayfit.domain.HealthDataPoint
import br.com.arcasoftware.stayfit.domain.HeartRateSeries
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.util.UUID

@Service
class HeartRateSeriesChunkPersistenceService(
    private val heartRateSeriesPersistencePort: HeartRateSeriesPersistencePort,
    private val healthDataPointPersistencePort: HealthDataPointPersistencePort
) {
    private val seenUids: Cache<UUID, Unit> = Caffeine.newBuilder()
        .maximumSize(100_000)
        .expireAfterWrite(Duration.ofMinutes(10))
        .build()

    @Transactional
    fun persistChunk(chunk: List<HealthDataPoint>) {
        val newDataPoints = chunk.filter { seenUids.getIfPresent(it.dataPointUid) == null }
        if (newDataPoints.isEmpty()) return

        healthDataPointPersistencePort.persistHeartRateBatch(newDataPoints)

        val heartRateSeries = newDataPoints
            .asSequence()
            .flatMap { it.sessions.orEmpty().asSequence() }
            .filterIsInstance<HeartRateSeries>()
            .toList()

        heartRateSeriesPersistencePort.persistBatch(heartRateSeries)

        newDataPoints.forEach { seenUids.put(it.dataPointUid, Unit) }
    }
}
