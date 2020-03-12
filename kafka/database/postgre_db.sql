-- noinspection SqlNoDataSourceInspectionForFile

-- PostgreSQL 9.6
-- '\\' is a delimiter

-- select version() as postgresql_version

CREATE TABLE IF NOT EXISTS entities (
  id_entity integer PRIMARY KEY,
  name varchar(32) NOT NULL, 
  location varchar(32) NOT NULL,
  deleted boolean NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS users (
  id_user integer PRIMARY KEY,
  name varchar(32) NOT NULL,
  surname varchar(32) NOT NULL,
  email varchar(32) NOT NULL,
  password varchar(256) NOT NULL,
  type smallint NOT NULL,
  telegram_name varchar(32),
  telegram_chat varchar(32),
  two_factor_authentication boolean DEFAULT false,
  deleted boolean NOT NULL DEFAULT false,
  id_entity integer,
  CONSTRAINT fk_entity FOREIGN KEY (id_entity) REFERENCES entities (id_entity) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS gateways (
  id_gateway integer PRIMARY KEY,
  name varchar(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS devices (
  id_device integer PRIMARY KEY,
  name varchar(32) NOT NULL,
  frequency integer NOT NULL,
  id_gateway integer,
  CONSTRAINT fk_gateway FOREIGN KEY (id_gateway) REFERENCES gateways (id_gateway) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS sensors (
  id_sensor integer PRIMARY KEY,
  type varchar(32) NOT NULL,
  id_device_sensor integer NOT NULL,
  id_device integer,
  UNIQUE (id_device, id_device_sensor),
  CONSTRAINT fk_device FOREIGN KEY (id_device) REFERENCES devices (id_device) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS alerts (
  id_alert integer PRIMARY KEY,
  threshold real,
  type smallint NOT NULL,
  deleted boolean NOT NULL DEFAULT false,
  id_sensor integer,
  id_entity integer,
  CONSTRAINT fk_sensor FOREIGN KEY (id_sensor) REFERENCES sensors (id_sensor) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_entity FOREIGN KEY (id_entity) REFERENCES entities (id_entity) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS views (
  id_view integer PRIMARY KEY,
  name varchar(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS views_graphs (
  id_graph integer PRIMARY KEY,
  correlation smallint NOT NULL,
  id_view integer,
  id_sensor_1 integer,
  id_sensor_2 integer,
  CONSTRAINT fk_view FOREIGN KEY (id_view) REFERENCES views (id_view) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_sensor1 FOREIGN KEY (id_sensor_1) REFERENCES sensors (id_sensor) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_sensor2 FOREIGN KEY (id_sensor_2) REFERENCES sensors (id_sensor) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS disabled_users_alerts (
  id_user integer,
  id_alert integer,
  PRIMARY KEY (id_user, id_alert),
  CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users (id_user) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_alert FOREIGN KEY (id_alert) REFERENCES alerts (id_alert) ON DELETE SET NULL ON UPDATE CASCADE
);
