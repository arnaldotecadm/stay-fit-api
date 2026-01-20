package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.ExerciseSessionEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.projection.BasicExerciseSessionProjection
import br.com.arcasoftware.stayfit.outbound.persistence.model.projection.ExerciseSummaryProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExerciseSessionRepository : JpaRepository<ExerciseSessionEntity, Long> {
    @Query(
        nativeQuery = true,
        value = """
            select 
                TO_CHAR(dp.start_time, 'YYYY-MM-DD HH24:MI:SS') as startTime,
                TO_CHAR(dp.end_time, 'YYYY-MM-DD HH24:MI:SS') as endTime,
                es.exercise_type as type,
                make_interval(secs => es.duration / 1000000000) as duration,
                es.distance::numeric  AS distance,
                es.calories as calories,
                es.mean_heart_rate as meanHeartRate,
                es.max_heart_rate as maxHeartRate,
                es.mean_cadence as meanCadence,
                (es.mean_speed * 3.6) as meanSpeed,
                (es.max_speed * 3.6) as maxSpeed
            from stay_fit.health_datapoint_view dp
            left join stay_fit.exercise_session es on es.data_point_uid = dp.data_point_uid
            order by TO_CHAR(dp.start_time, 'YYYY-MM-DD HH24:MI:SS') desc
        """
    )
    fun getBasicExerciseSessionList(): List<BasicExerciseSessionProjection>

    fun findByDataPointUid(dataPointUid: UUID): ExerciseSessionEntity?

    @Query(
        nativeQuery = true, value = """
        select
            hd.data_point_uid,
            hd.health_data_type,
            es.exercise_type,
            cast(es.start_time as date) as date,
            to_char(es.start_time, 'HH24:MI:SS') as start_time,
            to_char(es.end_time, 'HH24:MI:SS') as end_time,
            to_char(coalesce(es.distance, 0.0) / 1000.0, 'FM9999990.000') as distance,
            coalesce(es.count, 0) as count,
            cast(make_interval(secs => es.duration / 1000000000) as varchar) as duration,
            es.calories 
        from exercise_session es
        join health_datapoint_latest_view hd 
            on hd.data_point_uid = es.data_point_uid
        order by es.start_time desc
    """
    )
    fun getExerciseSessionSummary(): List<ExerciseSummaryProjection>
}
