-- DROP SCHEMA stay_fit;

CREATE SCHEMA stay_fit AUTHORIZATION postgres;

-- DROP SEQUENCE stay_fit.daily_summary_activity_seq;

CREATE SEQUENCE stay_fit.daily_summary_activity_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE stay_fit.daily_summary_activity_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE stay_fit.daily_summary_activity_seq TO postgres;

-- DROP SEQUENCE stay_fit.daily_summary_seq;

CREATE SEQUENCE stay_fit.daily_summary_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE stay_fit.daily_summary_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE stay_fit.daily_summary_seq TO postgres;

-- DROP SEQUENCE stay_fit.exercise_location_seq;

CREATE SEQUENCE stay_fit.exercise_location_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE stay_fit.exercise_location_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE stay_fit.exercise_location_seq TO postgres;

-- DROP SEQUENCE stay_fit.exercise_log_seq;

CREATE SEQUENCE stay_fit.exercise_log_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE stay_fit.exercise_log_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE stay_fit.exercise_log_seq TO postgres;

-- DROP SEQUENCE stay_fit.exercise_session_seq;

CREATE SEQUENCE stay_fit.exercise_session_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE stay_fit.exercise_session_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE stay_fit.exercise_session_seq TO postgres;

-- DROP SEQUENCE stay_fit.health_datapoint_heart_rate_seq;

CREATE SEQUENCE stay_fit.health_datapoint_heart_rate_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE stay_fit.health_datapoint_heart_rate_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE stay_fit.health_datapoint_heart_rate_seq TO postgres;

-- DROP SEQUENCE stay_fit.health_datapoint_seq;

CREATE SEQUENCE stay_fit.health_datapoint_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE stay_fit.health_datapoint_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE stay_fit.health_datapoint_seq TO postgres;

-- DROP SEQUENCE stay_fit.heart_rate_series_seq;

CREATE SEQUENCE stay_fit.heart_rate_series_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE stay_fit.heart_rate_series_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE stay_fit.heart_rate_series_seq TO postgres;

-- DROP SEQUENCE stay_fit.sleep_session_seq;

CREATE SEQUENCE stay_fit.sleep_session_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE stay_fit.sleep_session_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE stay_fit.sleep_session_seq TO postgres;

-- DROP SEQUENCE stay_fit.sleep_stage_seq;

CREATE SEQUENCE stay_fit.sleep_stage_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE stay_fit.sleep_stage_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE stay_fit.sleep_stage_seq TO postgres;
-- stay_fit.daily_summary definition

-- Drop table

-- DROP TABLE stay_fit.daily_summary;

CREATE TABLE stay_fit.daily_summary ( id int8 NOT NULL, data_point_uid uuid NULL, user_id varchar(255) NULL, active_time_in_minutes int8 NOT NULL, "date" timestamptz(6) NOT NULL, distance_while_active int8 NOT NULL, exercise_calories int8 NOT NULL, sleep_score int8 NOT NULL, total_burned_calories int8 NOT NULL, total_steps int8 NOT NULL, CONSTRAINT daily_summary_pkey PRIMARY KEY (id));
CREATE INDEX ix_daily_summary_date ON stay_fit.daily_summary USING btree (date);

-- Permissions

ALTER TABLE stay_fit.daily_summary OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.daily_summary TO postgres;


-- stay_fit.daily_summary_activity definition

-- Drop table

-- DROP TABLE stay_fit.daily_summary_activity;

CREATE TABLE stay_fit.daily_summary_activity ( id int8 NOT NULL, data_point_uid uuid NULL, user_id varchar(255) NULL, calories float4 NOT NULL, data_source varchar(255) NULL, duration numeric(21) NULL, exercise_type varchar(255) NULL, start_time timestamp(6), CONSTRAINT daily_summary_activity_pkey PRIMARY KEY (id));

-- Permissions

ALTER TABLE stay_fit.daily_summary_activity OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.daily_summary_activity TO postgres;


-- stay_fit.exercise_location definition

-- Drop table

-- DROP TABLE stay_fit.exercise_location;

CREATE TABLE stay_fit.exercise_location ( id int8 NOT NULL, data_point_uid uuid NULL, user_id varchar(255) NULL, accuracy float4 NULL, altitude float4 NULL, latitude float4 NOT NULL, longitude float4 NOT NULL, "timestamp" timestamptz(6) NULL, CONSTRAINT exercise_location_pkey PRIMARY KEY (id));
CREATE INDEX ix_exercise_location_datapoint_uid ON stay_fit.exercise_location USING btree (data_point_uid);

-- Permissions

ALTER TABLE stay_fit.exercise_location OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.exercise_location TO postgres;


-- stay_fit.exercise_log definition

-- Drop table

-- DROP TABLE stay_fit.exercise_log;

CREATE TABLE stay_fit.exercise_log ( id int8 NOT NULL, data_point_uid uuid NULL, user_id varchar(255) NULL, cadence float4 NULL, count int4 NULL, heart_rate float4 NULL, power float4 NULL, speed float4 NULL, "timestamp" timestamptz(6) NULL, CONSTRAINT exercise_log_pkey PRIMARY KEY (id));
CREATE INDEX ix_exercise_log_datapoint_uid ON stay_fit.exercise_log USING btree (data_point_uid);

-- Permissions

ALTER TABLE stay_fit.exercise_log OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.exercise_log TO postgres;


-- stay_fit.exercise_session definition

-- Drop table

-- DROP TABLE stay_fit.exercise_session;

CREATE TABLE stay_fit.exercise_session ( id int8 NOT NULL, data_point_uid uuid NULL, user_id varchar(255) NULL, altitude_gain float4 NULL, altitude_loss float4 NULL, calories float4 NOT NULL, "comment" varchar(255) NULL, count int4 NULL, count_type int2 NULL, custom_title varchar(255) NULL, decline_distance float4 NULL, distance float4 NULL, duration numeric(21) NULL, end_time timestamptz(6) NULL, exercise_type varchar(255) NULL, incline_distance float4 NULL, max_altitude float4 NULL, max_cadence float4 NULL, max_calories_burn_rate float4 NULL, max_heart_rate float4 NULL, max_power float4 NULL, max_rpm float4 NULL, max_speed float4 NULL, mean_cadence float4 NULL, mean_calorie_burn_rate float4 NULL, mean_heart_rate float4 NULL, mean_power float4 NULL, mean_rpm float4 NULL, mean_speed float4 NULL, min_altitude float4 NULL, min_heart_rate float4 NULL, start_time timestamptz(6) NULL, vo2max float4 NULL, CONSTRAINT exercise_session_count_type_check CHECK (((count_type >= 0) AND (count_type <= 4))), CONSTRAINT exercise_session_pkey PRIMARY KEY (id));
CREATE INDEX ix_exercise_session_datapoint_uid ON stay_fit.exercise_session USING btree (data_point_uid);

-- Permissions

ALTER TABLE stay_fit.exercise_session OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.exercise_session TO postgres;


-- stay_fit.flyway_schema_history definition

-- Drop table

-- DROP TABLE stay_fit.flyway_schema_history;

CREATE TABLE stay_fit.flyway_schema_history ( installed_rank int4 NOT NULL, "version" varchar(50) NULL, description varchar(200) NOT NULL, "type" varchar(20) NOT NULL, script varchar(1000) NOT NULL, checksum int4 NULL, installed_by varchar(100) NOT NULL, installed_on timestamp DEFAULT now() NOT NULL, execution_time int4 NOT NULL, success bool NOT NULL, CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank));
CREATE INDEX flyway_schema_history_s_idx ON stay_fit.flyway_schema_history USING btree (success);

-- Permissions

ALTER TABLE stay_fit.flyway_schema_history OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.flyway_schema_history TO postgres;


-- stay_fit.health_datapoint definition

-- Drop table

-- DROP TABLE stay_fit.health_datapoint;

CREATE TABLE stay_fit.health_datapoint ( id int8 NOT NULL, data_point_uid uuid NULL, user_id varchar(255) NULL, client_data_id varchar(255) NULL, client_version int4 NULL, data_source_entity varchar(255) NULL, end_time timestamptz(6) NULL, health_data_type varchar(255) NULL, start_time timestamptz(6) NULL, update_time timestamptz(6) NULL, zone_offset varchar(6) NULL, CONSTRAINT health_datapoint_health_data_type_check CHECK (((health_data_type)::text = ANY ((ARRAY['EXERCISE'::character varying, 'SLEEP'::character varying, 'HEART_RATE'::character varying])::text[]))), CONSTRAINT health_datapoint_pkey PRIMARY KEY (id));
CREATE INDEX ix_health_datapoint_datapoint_uid ON stay_fit.health_datapoint USING btree (data_point_uid);

-- Permissions

ALTER TABLE stay_fit.health_datapoint OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.health_datapoint TO postgres;


-- stay_fit.health_datapoint_heart_rate definition

-- Drop table

-- DROP TABLE stay_fit.health_datapoint_heart_rate;

CREATE TABLE stay_fit.health_datapoint_heart_rate ( id int8 NOT NULL, data_point_uid uuid NULL, user_id varchar(255) NULL, client_data_id varchar(255) NULL, client_version int4 NULL, data_source_entity varchar(255) NULL, end_time timestamptz(6) NULL, health_data_type varchar(255) NULL, start_time timestamptz(6) NULL, update_time timestamptz(6) NULL, zone_offset varchar(6) NULL, CONSTRAINT health_datapoint_heart_rate_health_data_type_check CHECK (((health_data_type)::text = ANY ((ARRAY['EXERCISE'::character varying, 'SLEEP'::character varying, 'HEART_RATE'::character varying])::text[]))), CONSTRAINT health_datapoint_heart_rate_pkey PRIMARY KEY (id));
CREATE INDEX ix_health_datapoint_heart_rate_datapoint_uid ON stay_fit.health_datapoint_heart_rate USING btree (data_point_uid);

-- Permissions

ALTER TABLE stay_fit.health_datapoint_heart_rate OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.health_datapoint_heart_rate TO postgres;


-- stay_fit.heart_rate_series definition

-- Drop table

-- DROP TABLE stay_fit.heart_rate_series;

CREATE TABLE stay_fit.heart_rate_series ( id int8 NOT NULL, data_point_uid uuid NULL, user_id varchar(255) NULL, duration numeric(21) NULL, end_time timestamptz(6) NULL, heart_rate float4 NOT NULL, max float4 NULL, min float4 NULL, start_time timestamptz(6) NULL, CONSTRAINT heart_rate_series_pkey PRIMARY KEY (id));
CREATE INDEX ix_heart_rate_series_datapoint_uid ON stay_fit.heart_rate_series USING btree (data_point_uid);

-- Permissions

ALTER TABLE stay_fit.heart_rate_series OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.heart_rate_series TO postgres;


-- stay_fit.sleep_session definition

-- Drop table

-- DROP TABLE stay_fit.sleep_session;

CREATE TABLE stay_fit.sleep_session ( id int8 NOT NULL, data_point_uid uuid NULL, user_id varchar(255) NULL, duration numeric(21) NULL, end_time timestamptz(6) NULL, start_time timestamptz(6) NULL, CONSTRAINT sleep_session_pkey PRIMARY KEY (id));
CREATE INDEX ix_sleep_session_datapoint_uid ON stay_fit.sleep_session USING btree (data_point_uid);

-- Permissions

ALTER TABLE stay_fit.sleep_session OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.sleep_session TO postgres;


-- stay_fit.sleep_stage definition

-- Drop table

-- DROP TABLE stay_fit.sleep_stage;

CREATE TABLE stay_fit.sleep_stage ( id int8 NOT NULL, data_point_uid uuid NULL, user_id varchar(255) NULL, end_time timestamptz(6) NULL, stage int2 NULL, start_time timestamptz(6) NULL, CONSTRAINT sleep_stage_pkey PRIMARY KEY (id), CONSTRAINT sleep_stage_stage_check CHECK (((stage >= 0) AND (stage <= 4))));
CREATE INDEX ix_sleep_stage_datapoint_uid ON stay_fit.sleep_stage USING btree (data_point_uid);

-- Permissions

ALTER TABLE stay_fit.sleep_stage OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.sleep_stage TO postgres;


-- stay_fit.daily_summary_exercise_list definition

-- Drop table

-- DROP TABLE stay_fit.daily_summary_exercise_list;

CREATE TABLE stay_fit.daily_summary_exercise_list ( daily_summary_entity_id int8 NOT NULL, exercise_list_id int8 NOT NULL, CONSTRAINT uk1vnuianltmvsy7ybcybswv5h0 UNIQUE (exercise_list_id), CONSTRAINT fk1s2h7p5jh8bmjrxihe2q1tpmg FOREIGN KEY (daily_summary_entity_id) REFERENCES stay_fit.daily_summary(id), CONSTRAINT fk8i38aexn5s3meotw3gyj75ci8 FOREIGN KEY (exercise_list_id) REFERENCES stay_fit.daily_summary_activity(id));

-- Permissions

ALTER TABLE stay_fit.daily_summary_exercise_list OWNER TO postgres;
GRANT ALL ON TABLE stay_fit.daily_summary_exercise_list TO postgres;




-- Permissions

GRANT ALL ON SCHEMA stay_fit TO postgres;