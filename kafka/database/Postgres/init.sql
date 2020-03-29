-- noinspection SqlNoDataSourceInspectionForFile

-- PostgreSQL 9.6
-- '\\' is a delimiter

-- select version() as postgresql_version

CREATE TABLE IF NOT EXISTS entities (
  entity_id serial PRIMARY KEY NOT NULL,
  name varchar(32) NOT NULL, 
  location varchar(32) NOT NULL,
  deleted boolean NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS users (
  user_id serial PRIMARY KEY NOT NULL,
  name varchar(32) NOT NULL,
  surname varchar(32) NOT NULL,
  email varchar(32) NOT NULL,
  password varchar(256) NOT NULL,
  type smallint NOT NULL,
  telegram_name varchar(32),
  telegram_chat varchar(32),
  two_factor_authentication boolean DEFAULT false,
  deleted boolean NOT NULL DEFAULT false,
  entity_id integer,
  CONSTRAINT fk_entity FOREIGN KEY (entity_id) REFERENCES entities (entity_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS gateways (
  gateway_id serial PRIMARY KEY NOT NULL,
  name varchar(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS devices (
  device_id serial PRIMARY KEY NOT NULL,
  real_device_id integer NOT NULL,
  name varchar(32) NOT NULL,
  frequency integer NOT NULL,
  gateway_id integer,
  UNIQUE (gateway_id, real_device_id),
  CONSTRAINT fk_gateway FOREIGN KEY (gateway_id) REFERENCES gateways (gateway_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS sensors (
  sensor_id serial PRIMARY KEY NOT NULL,
  real_sensor_id integer NOT NULL,
  type varchar(32) NOT NULL,
  device_id integer,
  UNIQUE (device_id, real_sensor_id),
  CONSTRAINT fk_device FOREIGN KEY (device_id) REFERENCES devices (device_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS entity_sensors (
  entity_id integer NOT NULL,
  sensor_id integer NOT NULL,
  PRIMARY KEY (entity_id, sensor_id),
  CONSTRAINT fk_sensor FOREIGN KEY (sensor_id) REFERENCES sensors (sensor_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_entity FOREIGN KEY (entity_id) REFERENCES entities (entity_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS alerts (
  alert_id serial PRIMARY KEY NOT NULL,
  threshold real NOT NULL,
  type smallint NOT NULL,
  deleted boolean NOT NULL DEFAULT false,
  sensor_id integer NOT NULL,
  entity_id integer NOT NULL,
  last_sent timestamptz DEFAULT NULL,
  CONSTRAINT fk_sensor FOREIGN KEY (sensor_id) REFERENCES sensors (sensor_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_entity FOREIGN KEY (entity_id) REFERENCES entities (entity_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS views (
  view_id serial PRIMARY KEY NOT NULL,
  name varchar(32) NOT NULL,
  user_id integer NOT NULL,
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS views_graphs (
  graph_id serial PRIMARY KEY NOT NULL,
  correlation smallint NOT NULL,
  view_id integer,
  sensor_1_id integer,
  sensor_2_id integer,
  CONSTRAINT fk_view FOREIGN KEY (view_id) REFERENCES views (view_id) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_sensor1 FOREIGN KEY (sensor_1_id) REFERENCES sensors (sensor_id) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_sensor2 FOREIGN KEY (sensor_2_id) REFERENCES sensors (sensor_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS disabled_users_alerts (
  user_id integer NOT NULL,
  alert_id integer NOT NULL,
  PRIMARY KEY (user_id, alert_id),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_alert FOREIGN KEY (alert_id) REFERENCES alerts (alert_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE VIEW sensors_devices_view AS 
SELECT s.device_id, real_device_id, sensor_id, real_sensor_id, type
FROM sensors s, devices d
WHERE s.device_id=d.device_id;

