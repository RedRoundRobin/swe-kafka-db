-- noinspection SqlNoDataSourceInspectionForFile

-- PostgreSQL 9.6
-- '\\' is a delimiter

-- select version() as postgresql_version

CREATE TABLE IF NOT EXISTS sensors (
  id_sensor integer NOT NULL,
  id_device integer NOT NULL,
  value double precision,
  time timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS alerts (
  id_sensor integer NOT NULL,
  id_device integer NOT NULL,
  value double precision,
  time timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS logs (
    id_log integer NOT NULL,
    time timestamp NOT NULL
);

SELECT create_hypertable('sensors', 'time');

SELECT create_hypertable('alerts', 'time');

SELECT create_hypertable('logs', 'time');
