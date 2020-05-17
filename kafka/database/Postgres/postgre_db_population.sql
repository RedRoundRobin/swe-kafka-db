-- GATEWAYS
INSERT INTO "public"."gateways" ("gateway_id", "name") VALUES ('1', 'gw_US-GATEWAY-1');
INSERT INTO "public"."gateways" ("gateway_id", "name") VALUES ('2', 'gw_arduino');

-- US GATEWAY-1 Devices
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('1', '1', 'WATER-MACHINE', '5', '1');
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('2', '2', 'COFFEE-MACHINE-1', '5', '1');
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('3', '4', 'SNACK-MACHINE', '5', '1');
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('4', '5', 'COFFEE-MACHINE-2', '5', '1');

-- Arduino Devices
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('5', '1', 'ARDUINO-UNO', '2', '2');


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


-- Arduino Uno Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id", "cmd_enabled") VALUES ('10', '1', 'led_blu', '5', 'true');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('11', '2', 'potenziometro_A', '5');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('12', '3', 'potenziometro_B', '5');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id", "cmd_enabled") VALUES ('13', '4', 'led_rosso', '5', 'true');



-- Companies / Entities
INSERT INTO "public"."entities" ("entity_id", "name", "location", "deleted") VALUES ('1', 'Pulp Company', 'America', 'false');
INSERT INTO "public"."entities" ("entity_id", "name", "location", "deleted") VALUES ('2', 'Coffee Company', 'Germania', 'false');

-- Administrator Users
insert into users (user_id, name, surname, email, password, type, telegram_name) values (1, 'Nicolo', 'Rossi', 'admin@admin.it', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 2, 'Fritz20'); -- psw: admin
insert into users (user_id, name, surname, email, password, type, telegram_name, two_factor_authentication) values (10, 'Mariano', 'Bianchi', 'admin2@admin2.it', '661bb43140229ad4dc3e762e7bdd68cc14bb9093c158c386bd989fea807acd9bd7f805ca4736b870b6571594d0d8fcfc57b98431143dfb770e083fa9be89bc72', 2, 'Maxelweb', 'true'); -- psw: admin2
insert into users (user_id, name, surname, email, password, type, telegram_name) values (11, 'Fouad', 'Brown', 'admin3@admin3.it', '448d8ca07486257065add370c87e61a3c778c70d4fcb5db89f011ade315e7a942fb3352e6bded66c4f9adef6f3314588ace81aa12096111ee306fa5ed4294182', 2, 'giovannividotto'); -- psw: admin3

-- Pulp Company users
insert into users (user_id, name, surname, email, password, type, entity_id, telegram_name) values (2, 'Giuseppe', 'Rosa', 'mario@rossi.it', '7856873f653b390afec6c83a6fc8a47de054a6c19effd4e81bc4c1383ed2eed576f272e90517da5c563e8ba79f6470a305788fc091af6db822f239850ee97db8', 0, 1, 'Bit1011111');  -- psw: mariorossi
insert into users (user_id, name, surname, email, password, type, entity_id) values (3, 'Marsellus', 'Wallace', 'acambell1@wisc.edu', '2a64d6563d9729493f91bf5b143365c0a7bec4025e1fb0ae67e307a0c3bed1c28cfb259fc6be768ab0a962b1e2c9527c5f21a1090a9b9b2d956487eb97ad4209', 0, 1); -- psw: password3
insert into users (user_id, name, surname, email, password, type, entity_id) values (4, 'Bucth', 'Coolidge', 'esims2@wordpress.org', '11961811bd4e11f23648afbd2d5c251d2784827147f1731be010adaf0ab38ae18e5567c6fd1bee50a4cd35fb544b3c594e7d677efa7ca01c2a2cb47f8fb12b17', 0, 1);  -- psw: password4
insert into users (user_id, name, surname, email, password, type, entity_id, telegram_name) values (5, 'Alessandro', 'Blue', 'ale@tomm.it', '3768616a7a236cf702c421e0887a4c29daa94c7bf42abf1f476a2e92b6f1ec7b9607d12a9979196b96d3d0c884a0f1e98512efcf417e048ee77c003ba03824e4', 1, 1, 'Alet0m');  -- psw: aletomm
insert into users (user_id, name, surname, email, password, type, entity_id) values (6, 'Mia', 'Wallace', 'mod@mod.it', 'fd2d1f62aab380a798f7a2daf73e8aca617ddbe13da858609939d7420769d3398000e5463a9ab995ff8d0c48c2a1b64bb71db0528cc5adea40abb5bf9cd1a62a', 1, 1);  -- psw: mod

-- Coffee Company users
insert into users (user_id, name, surname, email, password, type, entity_id) values (7, 'Donald', 'Trump', 'mod2@mod2.it', '3243878e639e400978908a6673112c3e7c9e6ea2d7e4b483a60025e90118d2be2939f047728995ba510b151d3c7e29f3636602ad039b570ea568553910578264', 1, 2); -- psw: mod2
insert into users (user_id, name, surname, email, password, type, entity_id) values (8, 'Shane', 'Harrhy', 'sharrhy6@acquirethisname.com', '219aab6b2cf738d9f370e197ce0151be42ae6095e0d72fa49592865c9d2dde5d2fa72e908ac374cba55426e6d0ed39324fd6de1d5da2641cc7f4491f5edd931f', 0, 2); -- psw: password8
insert into users (user_id, name, surname, email, password, type, entity_id) values (9, 'Drucie', 'Stronach', 'dstronach7@berkeley.edu', '219aab6b2cf738d9f370e197ce0151be42ae6095e0d72fa49592865c9d2dde5d2fa72e908ac374cba55426e6d0ed39324fd6de1d5da2641cc7f4491f5edd931f', 0, 2);  -- psw: password8


-- FOREIGN KEYS Pulp Company - sensors
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '1');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '3');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '5');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '4');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '6');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '9');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '10');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '11');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '12');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '13');

-- FOREIGN KEYS Coffee Company - sensors
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '2');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '7');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '8');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '11');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '12');


-- Alerts
-- INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (1, 5, 1, 1, 1);
-- INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (2, 120, 1, 4, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (1, 25, 0, 5, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (2, 25, 0, 6, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (3, 30, 1, 7, 2);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (4, 30, 1, 8, 2);

-- Alerts arduino 
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (5, 1, 2, 10, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (6, 60, 0, 11, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (7, 100, 0, 12, 1);



