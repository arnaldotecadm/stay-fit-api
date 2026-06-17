package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.HeartRateSeriesEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.projection.HeartDailySessionProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface HeartRateSeriesRepository : JpaRepository<HeartRateSeriesEntity, Long> {
    fun deleteByDataPointUidIn(dataPointUids: Collection<UUID>)

    @Query(
        nativeQuery = true,
        value = """
            SELECT
                date_trunc('minute', start_time) AS minuteBucket,
                AVG(heart_rate) AS avgHr,
                MAX(heart_rate) AS maxHr,
                MIN(heart_rate) AS minHr
            FROM stay_fit.heart_rate_series
            WHERE cast(start_time as date) = :localDate
            GROUP BY 1
            ORDER BY 1;
        """
    )
    fun getHeartDailySession(localDate: LocalDate): List<HeartDailySessionProjection>
}
