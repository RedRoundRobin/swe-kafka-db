-- noinspection SqlNoDataSourceInspectionForFile

-- PostgreSQL 9.6
-- '\\' is a delimiter

-- select version() as postgresql_version

CREATE TABLE IF NOT EXISTS sensors (
  sensor_id integer NOT NULL,
  device_id integer NOT NULL,
  value double precision,
  time timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS alerts (
  sensor_id integer NOT NULL,
  device_id integer NOT NULL,
  value double precision,
  time timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS logs (
    log_id integer NOT NULL,
    time timestamp NOT NULL
);

SELECT create_hypertable('sensors', 'time');

SELECT create_hypertable('alerts', 'time');

SELECT create_hypertable('logs', 'time');
