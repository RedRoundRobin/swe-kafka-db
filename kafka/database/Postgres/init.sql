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
  email varchar(32) NOT NULL UNIQUE,
  password varchar(256) NOT NULL,
  type smallint NOT NULL,
  telegram_name varchar(32) UNIQUE,
  telegram_chat varchar(32) UNIQUE,
  two_factor_authentication boolean DEFAULT false,
  deleted boolean NOT NULL DEFAULT false,
  entity_id integer,
  CONSTRAINT fk_entity FOREIGN KEY (entity_id) REFERENCES entities (entity_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS gateways (
  gateway_id serial PRIMARY KEY NOT NULL,
  name varchar(32) NOT NULL UNIQUE,
  last_sent timestamptz DEFAULT NULL
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
  cmd_enabled boolean NOT NULL DEFAULT false,
  UNIQUE (device_id, real_sensor_id),
  CONSTRAINT fk_device FOREIGN KEY (device_id) REFERENCES devices (device_id) ON DELETE CASCADE ON UPDATE CASCADE
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
  CONSTRAINT fk_view FOREIGN KEY (view_id) REFERENCES views (view_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_sensor1 FOREIGN KEY (sensor_1_id) REFERENCES sensors (sensor_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_sensor2 FOREIGN KEY (sensor_2_id) REFERENCES sensors (sensor_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS disabled_users_alerts (
  user_id integer NOT NULL,
  alert_id integer NOT NULL,
  PRIMARY KEY (user_id, alert_id),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_alert FOREIGN KEY (alert_id) REFERENCES alerts (alert_id) ON DELETE CASCADE ON UPDATE CASCADE
);


ALTER SEQUENCE entities_entity_id_seq START WITH 100 RESTART WITH 100 MINVALUE 100 INCREMENT 25  OWNED BY entities.entity_id;
ALTER SEQUENCE users_user_id_seq START WITH 100 RESTART WITH 100 MINVALUE 100 INCREMENT 25 OWNED BY users.user_id;
ALTER SEQUENCE gateways_gateway_id_seq START WITH 100 RESTART WITH 100 MINVALUE 100 INCREMENT 25 OWNED BY gateways.gateway_id;
ALTER SEQUENCE devices_device_id_seq START WITH 100 RESTART WITH 100 MINVALUE 100 INCREMENT 25 OWNED BY devices.device_id;
ALTER SEQUENCE sensors_sensor_id_seq START WITH 100 RESTART WITH 100 MINVALUE 100 INCREMENT 25 OWNED BY sensors.sensor_id;
ALTER SEQUENCE alerts_alert_id_seq START WITH 100 RESTART WITH 100 MINVALUE 100 INCREMENT 25 OWNED BY alerts.alert_id;
ALTER SEQUENCE views_view_id_seq START WITH 100 RESTART WITH 100 MINVALUE 100 INCREMENT 25 OWNED BY views.view_id;
ALTER SEQUENCE views_graphs_graph_id_seq START WITH 100 RESTART WITH 100 MINVALUE 100 INCREMENT 25 OWNED BY views_graphs.view_id;


CREATE VIEW sensors_devices_view AS 
SELECT s.device_id, d.real_device_id, s.sensor_id, s.real_sensor_id, s.type, g.gateway_id, g.name
FROM sensors s, devices d, gateways g
WHERE s.device_id = d.device_id AND d.gateway_id = g.gateway_id;

