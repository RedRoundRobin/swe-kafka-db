-- GATEWAYS
INSERT INTO "public"."gateways" ("gateway_id", "name") VALUES ('1', 'gw_US-GATEWAY-1');

-- US GATEWAY-1 Devices
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('1', '1', 'WATER-MACHINE', '5', '1');
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('2', '2', 'COFFEE-MACHINE-1', '5', '1');
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('3', '4', 'SNACK-MACHINE', '5', '1');
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('4', '5', 'COFFEE-MACHINE-2', '5', '1');


-- Water Machine 1 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('1', '1', 'temperatura_interna', '1');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('2', '3', 'acqua', '1');

-- Coffee Machine 1 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('3', '1', 'temperatura', '2');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('4', '2', 'pressione', '2');

-- Snack machine 1 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('5', '1', 'temperatura_interna', '3');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('6', '2', 'temperatura_esterna', '3');

-- Coffee Machine 2 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('7', '1', 'espresso', '4');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('8', '2', 'capuccino', '4');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id", "cmd_enabled") VALUES ('9', '3', 'temperatura_interna', '4', 'true');


-- Companies / Entities
INSERT INTO "public"."entities" ("entity_id", "name", "location", "deleted") VALUES ('1', 'Pulp Company', 'Italia', 'false');
INSERT INTO "public"."entities" ("entity_id", "name", "location", "deleted") VALUES ('2', 'Coffee Company', 'Germania', 'false');

-- Administrator Users
insert into users (user_id, name, surname, email, password, type) values (1, 'Andrea', 'Simion', 'admin@admin.it', 'admin', 2);
insert into users (user_id, name, surname, email, password, type, telegram_name) values (10, 'Mariano', 'Simion', 'admin2@admin2.it', 'admin2', 2, 'Maxelweb');
insert into users (user_id, name, surname, email, password, type, telegram_name) values (11, 'Giovanni', 'Simion', 'admin3@admin3.it', 'admin3', 2, 'giovannividotto');

-- Pulp Company users
insert into users (user_id, name, surname, email, password, type, entity_id) values (2, 'Jules', 'Winnfield', 'mario@rossi.it', 'mariorossi', 0, 1);
insert into users (user_id, name, surname, email, password, type, entity_id) values (3, 'Marsellus', 'Wallace', 'acambell1@wisc.edu', 'password3', 0, 1);
insert into users (user_id, name, surname, email, password, type, entity_id) values (4, 'Bucth', 'Coolidge', 'esims2@wordpress.org', 'password4', 0, 1);
insert into users (user_id, name, surname, email, password, type, entity_id, telegram_name, two_factor_authentication) values (5, 'Vincent', 'Vega', 'ale@tomm.it', 'aletomm', 1, 1, 'Alet0m', true);
insert into users (user_id, name, surname, email, password, type, entity_id) values (6, 'Mia', 'Wallace', 'mod@mod.it', 'mod', 1, 1);

-- Coffee Company users
insert into users (user_id, name, surname, email, password, type, entity_id) values (7, 'Donald', 'Trump', 'mod2@mod2.it', 'mod2', 1, 2);
insert into users (user_id, name, surname, email, password, type, entity_id) values (8, 'Shane', 'Harrhy', 'sharrhy6@acquirethisname.com', 'password8', 0, 2);
insert into users (user_id, name, surname, email, password, type, entity_id) values (9, 'Drucie', 'Stronach', 'dstronach7@berkeley.edu', 'password9', 0, 2);


-- FOREIGN KEYS Pulp Company - sensors
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '1');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '3');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '5');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '4');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '6');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '9');

-- FOREIGN KEYS Coffee Company - sensors
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '2');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '7');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '8');


-- Alerts
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (1, 5, 1, 1, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (2, 120, 1, 4, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (3, 25, 0, 5, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (4, 25, 0, 6, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (5, 30, 1, 7, 2);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (6, 30, 1, 8, 2);



