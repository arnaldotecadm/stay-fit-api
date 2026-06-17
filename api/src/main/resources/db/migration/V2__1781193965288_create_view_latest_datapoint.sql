CREATE OR REPLACE VIEW stay_fit.health_datapoint_latest_view AS
SELECT
    hd.data_point_uid,
    hd.health_data_type
FROM (
         SELECT
             hd.*,
             ROW_NUMBER() OVER (
            PARTITION BY date_trunc('minute', hd.start_time)
            ORDER BY
                CASE
                    WHEN hd.data_source_entity ILIKE '%health%' THEN 0
                    ELSE 1
                END,
                hd.start_time DESC
        ) AS rn
         FROM stay_fit.health_datapoint hd
     ) hd
         LEFT JOIN stay_fit.exercise_session es
                   ON es.data_point_uid = hd.data_point_uid
WHERE hd.rn = 1;
