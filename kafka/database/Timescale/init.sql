-- noinspection SqlNoDataSourceInspectionForFile

-- PostgreSQL 9.6
-- '\\' is a delimiter

-- select version() as postgresql_version

CREATE TABLE IF NOT EXISTS sensors (
    time timestamptz PRIMARY KEY DEFAULT CURRENT_TIMESTAMP,
    real_sensor_id integer NOT NULL,
    real_device_id integer NOT NULL,
    gateway_name text NOT NULL,
    value double precision NULL
);

CREATE TABLE IF NOT EXISTS alerts (
    time timestamptz PRIMARY KEY DEFAULT CURRENT_TIMESTAMP,
    real_sensor_id integer NOT NULL,
    real_device_id integer NOT NULL,
    gateway_name text NOT NULL,
    value double precision NOT NULL
);

CREATE TABLE IF NOT EXISTS logs (
    time timestamptz PRIMARY KEY DEFAULT CURRENT_TIMESTAMP,
    user_id integer NOT NULL,
    ip_addr varchar(36) NOT NULL,
    operation text NOT NULL,
    data text NULL
);

SELECT create_hypertable('sensors', 'time');

SELECT create_hypertable('alerts', 'time');

SELECT create_hypertable('logs', 'time');