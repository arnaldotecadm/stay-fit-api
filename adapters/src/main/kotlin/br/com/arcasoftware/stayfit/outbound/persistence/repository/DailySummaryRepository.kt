package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.DailySummaryEntity
import br.com.arcasoftware.stayfit.outbound.persistence.model.projection.DailyActivitySummaryProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface DailySummaryRepository : JpaRepository<DailySummaryEntity, Long> {
    fun findByDate(date: LocalDate): DailySummaryEntity?

    @Query(
        nativeQuery = true,
        value = """
            SELECT
            ds."date",
            ds.sleep_score,
            ds.total_steps,
            ds.active_time_in_minutes,
            ds.total_burned_calories,
            ds.distance_while_active / 1000.0 AS distance_km,
            ds.exercise_calories,
            /* JSON array of all activities for the day */
            cast(jsonb_agg(
                jsonb_build_object(
                    'id', dsa.id,
                    'data_point_uid', dsa.data_point_uid,
                    'calories', coalesce(dsa.calories, 0),
                    'data_source', dsa.data_source,
                    'duration', coalesce(dsa.duration, 0),
                    'exerciseType', coalesce(dsa.exercise_type, '')
                )
                ORDER BY dsa.id
            ) as TEXT) AS activities,
            COUNT(dsa.id) AS activity_count,
            SUM(COALESCE(dsa.calories, 0)) AS activity_calories_sum,
            ws.week_steps
        FROM stay_fit.daily_summary ds
        LEFT JOIN stay_fit.daily_summary_exercise_list dsel
            ON dsel.daily_summary_entity_id = ds.id
        LEFT JOIN stay_fit.daily_summary_activity dsa
            ON dsa.id = dsel.exercise_list_id
           AND dsa.data_source LIKE '%shealth%'
        CROSS JOIN (
            SELECT CAST(
                jsonb_agg(
                    jsonb_build_object(
                        'date', "date",
                        'steps', total_steps
                    )
                    ORDER BY "date"
                ) AS TEXT
            ) AS week_steps
            FROM stay_fit.daily_summary
            WHERE "date" >= DATE_TRUNC('week', cast(:dateTime as date))
              AND "date" < DATE_TRUNC('week', cast(:dateTime as date)) + INTERVAL '7 days'
        ) ws
        WHERE ds."date" = :dateTime
        GROUP BY
            ds."date",
            ds.sleep_score,
            ds.total_steps,
            ds.active_time_in_minutes,
            ds.total_burned_calories,
            ds.distance_while_active,
            ds.exercise_calories,
            ws.week_steps
        ORDER BY ds."date" DESC;
        """
    )
    fun getDailySummaryEntityByDate(dateTime: LocalDate): DailyActivitySummaryProjection?
}
