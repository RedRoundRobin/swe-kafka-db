--PostgreSQL 9.6
--'\\' is a delimiter

--select version() as postgresql_version

CREATE TABLE IF NOT EXISTS entity(
  id_entity integer not null,
  name varchar(30), 
  location varchar(30) not null,
  deleted boolean not null,
  primary key(id_entity)
);

CREATE TABLE IF NOT EXISTS _user(
  id_user integer,
  name varchar(30),
  surname varchar(30), 
  email varchar(30) not null,
  password varchar(12) not null, --credo non vada salvata in chiaro(hash)
  type smallint not null,
  telegram_name varchar(30),
  telegram_chat integer,
  two_factor_authentication Boolean default false, --trigger: se true => i campi telegram != null (?)
  deleted boolean not null,
  id_entity integer not null,
  primary key(id_user),
  foreign key(id_user) references entity(id_entity) 
);


CREATE TABLE IF NOT EXISTS gateway(
  id_gateway integer,
  name varchar(30), 
  primary key (id_gateway)
);


CREATE TABLE IF NOT EXISTS device(
  id_device integer,
  id_gateway integer,
  name varchar(30), 
  frequency integer not null,
  primary key(id_device),
  foreign key(id_gateway) references gateway(id_gateway)
);


CREATE TABLE IF NOT EXISTS sensor(
  id_sensor integer,
  id_device integer, 
  type varchar(30),
  primary key(id_sensor, id_device),
  foreign key(id_device) references device(id_device)
);


CREATE TABLE IF NOT EXISTS alert(
  id_alert integer,
  threshold float,
  type smallint,
  deleted Boolean not null default false,
  id_device integer,
  id_sensor integer,
  id_entity integer, 
  primary key(id_alert),
  foreign key(id_sensor, id_device) references sensor(id_sensor, id_device),
  foreign key(id_entity) references entity(id_entity)
);


CREATE TABLE IF NOT EXISTS view(
  id_view integer,
  name varchar(30), 
  primary key(id_view)
);


CREATE TABLE IF NOT EXISTS view_graph(
  id_graph integer,
  correlation smallint, 
  id_view integer,
  id_sensor_1 integer not null,
  id_device_1 integer not null,
  id_sensor_2 integer not null,
  id_device_2 integer not null,
  primary key(id_graph),
  foreign key(id_view) references view(id_view),
  foreign key(id_sensor_1, id_device_1) references sensor(id_sensor, id_device),
  foreign key(id_sensor_2, id_device_2) references sensor(id_sensor, id_device)
);


CREATE TABLE IF NOT EXISTS user_alert(
  id_user integer,
  id_alert integer,
  primary key(id_user, id_alert),
  foreign key(id_user) references _user(id_user),
  foreign key(id_alert) references alert(id_alert) 
);
