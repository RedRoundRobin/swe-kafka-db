-- noinspection SqlNoDataSourceInspectionForFile

-- PostgreSQL 9.6
-- '\\' is a delimiter

-- select version() as postgresql_version

CREATE TABLE IF NOT EXISTS sensors (
    time timestamptz NOT NULL,
    sensor_id integer NOT NULL,
    device_id integer NOT NULL,
    value double precision NULL,
    PRIMARY KEY (time, sensor_id)
);

CREATE TABLE IF NOT EXISTS alerts (
    time timestamptz NOT NULL,
    sensor_id integer NOT NULL,
    device_id integer NOT NULL,
    value double precision NOT NULL,
    PRIMARY KEY (time, sensor_id)
);

CREATE TABLE IF NOT EXISTS logs (
    time timestamptz NOT NULL,
    user_id integer NOT NULL,
    operation text NOT NULL,
    data text NULL,
    PRIMARY KEY (time, user_id)
);

SELECT create_hypertable('sensors', 'time');

SELECT create_hypertable('alerts', 'time');

SELECT create_hypertable('logs', 'time');
