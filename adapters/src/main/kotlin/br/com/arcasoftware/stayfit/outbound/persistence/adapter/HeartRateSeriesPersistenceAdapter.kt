package br.com.arcasoftware.stayfit.outbound.persistence.adapter

import br.com.arcasoftware.stayfit.application.port.outbound.persistence.HeartRateSeriesPersistencePort
import br.com.arcasoftware.stayfit.domain.HeartDailySession
import br.com.arcasoftware.stayfit.domain.HeartRateSeries
import br.com.arcasoftware.stayfit.outbound.persistence.mapper.HeartRateSeriesMapper.toDomain
import br.com.arcasoftware.stayfit.outbound.persistence.repository.HeartRateSeriesRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.sql.Types
import java.time.LocalDate
import java.util.UUID

private const val INSERT_SQL = """
    INSERT INTO stay_fit.heart_rate_series
        (id, data_point_uid, user_id, duration, end_time, heart_rate, max, min, start_time)
    VALUES (nextval('stay_fit.heart_rate_series_seq'), ?, ?, ?, ?, ?, ?, ?, ?)
"""

@Service
class HeartRateSeriesPersistenceAdapter(
    private val heartRateSeriesRepository: HeartRateSeriesRepository,
    private val jdbcTemplate: JdbcTemplate,
) : HeartRateSeriesPersistencePort {

    @Transactional
    override fun persistBatch(heartRateSeriesBatch: List<HeartRateSeries>) {
        if (heartRateSeriesBatch.isEmpty()) return

        val uids = heartRateSeriesBatch.asSequence().map { it.dataPointUid }.distinct().toList()
        heartRateSeriesRepository.deleteByDataPointUidIn(uids)

        jdbcTemplate.batchUpdate(INSERT_SQL.trimIndent(), heartRateSeriesBatch, 1_000) { ps, item ->
            val max = item.max
            val min = item.min
            ps.setObject(1, item.dataPointUid)
            ps.setString(2, item.userId)
            ps.setLong(3, item.duration.toNanos())
            ps.setTimestamp(4, Timestamp.from(item.endTime))
            ps.setFloat(5, item.heartRate)
            if (max != null) ps.setFloat(6, max) else ps.setNull(6, Types.FLOAT)
            if (min != null) ps.setFloat(7, min) else ps.setNull(7, Types.FLOAT)
            ps.setTimestamp(8, Timestamp.from(item.startTime))
        }
    }

    override fun getHeartDailySession(localDate: LocalDate): List<HeartDailySession> =
        heartRateSeriesRepository.getHeartDailySession(localDate).map { it.toDomain() }
}
