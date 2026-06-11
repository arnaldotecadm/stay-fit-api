package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.SleepSessionEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.projection.DailySleepStageProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

@Repository
interface SleepSessionRepository : JpaRepository<SleepSessionEntity, Long> {
    fun findByDataPointUid(dataPointUid: UUID): SleepSessionEntity?

    @Query(
        nativeQuery = true,
        value = """
            with sleeps as (
                select 
                    ss.data_point_uid,
                    ss.duration,
                    ss.start_time,
                    ss.end_time
                from stay_fit.sleep_session ss 
            )
            select
                stage.stage,
                SUM(EXTRACT(EPOCH FROM (stage.end_time - stage.start_time))) / 60 AS duration_minutes,
                s.start_time,
                s.end_time,
                s.duration
            FROM stay_fit.sleep_stage stage
            right join sleeps s on s.data_point_uid = stage.data_point_uid
            WHERE cast(s.end_time as date) = :localDate
            GROUP BY stage, s.start_time, s.end_time, s.duration
            ORDER BY stage;
        """,
    )
    fun getDailySleepStagesSummary(localDate: LocalDate): List<DailySleepStageProjection>
}
