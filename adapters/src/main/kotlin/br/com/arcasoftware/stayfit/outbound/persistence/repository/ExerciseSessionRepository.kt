package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.ExerciseSessionEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.HealthDataPointEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.projection.BasicExerciseSessionProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ExerciseSessionRepository : JpaRepository<ExerciseSessionEntity, Long>{
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
}
