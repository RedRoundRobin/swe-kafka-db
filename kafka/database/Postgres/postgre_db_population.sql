-- GATEWAYS
INSERT INTO "public"."gateways" ("gateway_id", "name") VALUES ('1', 'US-GATEWAY-1');
INSERT INTO "public"."gateways" ("gateway_id", "name") VALUES ('2', 'DE-GATEWAY-1');
INSERT INTO "public"."gateways" ("gateway_id", "name") VALUES ('3', 'SG-GATEWAY-1');


-- US GATEWAY-1 Devices
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('1', '1', 'US-COFFEE-MACHINE-1', '60', '1');
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('2', '2', 'US-COFFEE-MACHINE-2', '60', '1');

-- DE GATEWAY-1 Devices
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('3', '1', 'DE-COFFEE-MACHINE-1', '60', '2');
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('4', '2', 'DE-COFFEE-MACHINE-2', '60', '2');

-- SG GATEWAY-1 Devices
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('5', '1', 'SG-COFFEE-MACHINE-1', '60', '3');



-- US Coffee Machine 1 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('1', '1', 'stick', '1');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('2', '2', 'plastic cups', '1');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('3', '3', 'cappuccino', '1');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('4', '4', 'espresso', '1');

-- US Coffee Machine 2 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('5', '1', 'stick', '2');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('6', '2', 'plastic cups', '2');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('7', '4', 'espresso', '2');

-- DE Coffee Machine 1 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('8', '1', 'stick', '3');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('9', '2', 'plastic cups', '3');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('10', '3', 'cappuccino', '3');

-- DE Coffee Machine 2 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('11', '1', 'stick', '4');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('12', '2', 'plastic cups', '4');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('13', '3', 'cappuccino', '4');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('14', '4', 'espresso', '4');

-- SG Coffee Machine 1 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('15', '1', 'stick', '5');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('16', '2', 'plastic cups', '5');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('17', '3', 'cappuccino', '5');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('18', '4', 'espresso', '5');


-- Companies / Entities
INSERT INTO "public"."entities" ("entity_id", "name", "location", "deleted") VALUES ('1', 'Coffee Company', 'Italy', 'false');
INSERT INTO "public"."entities" ("entity_id", "name", "location", "deleted") VALUES ('2', 'Stick Company', 'Brasil', 'false');
INSERT INTO "public"."entities" ("entity_id", "name", "location", "deleted") VALUES ('3', 'Plastic Cups Company', 'Cina', 'false');


-- Coffee Company Users
insert into Users (user_id, name, surname, email, password, type, entity_id) values (2, 'Kimbra', 'Timmins', 'ktimmins0@purevolume.com', 'password', 0, 1);
insert into Users (user_id, name, surname, email, password, type, entity_id) values (3, 'Adelind', 'Cambell', 'acambell1@wisc.edu', 'password', 0, 1);
insert into Users (user_id, name, surname, email, password, type, entity_id) values (4, 'Essy', 'Sims', 'esims2@wordpress.org', 'password', 0, 1);
insert into Users (user_id, name, surname, email, password, type, entity_id) values (5, 'Bernadette', 'Dufore', 'bdufore3@answers.com', 'password', 0, 1);
insert into Users (user_id, name, surname, email, password, type, entity_id) values (6, 'Terry', 'Doulton', 'tdoulton4@twitpic.com', 'password', 1, 1);

-- Stick Company Users
insert into Users (user_id, name, surname, email, password, type, entity_id) values (7, 'Romola', 'Spoward', 'rspoward5@netlog.com', 'password', 1, 2);
insert into Users (user_id, name, surname, email, password, type, entity_id) values (8, 'Shane', 'Harrhy', 'sharrhy6@acquirethisname.com', 'password', 0, 2);
insert into Users (user_id, name, surname, email, password, type, entity_id) values (9, 'Drucie', 'Stronach', 'dstronach7@berkeley.edu', 'password', 0, 2);

-- Plastic Cups Company Users
insert into Users (user_id, name, surname, email, password, type, entity_id) values (10, 'Rosalinda', 'Glencross', 'rglencross8@bloglovin.com', 'password', 1, 3);
insert into Users (user_id, name, surname, email, password, type, entity_id) values (11, 'Goddart', 'Ellwell', 'gellwell9@xing.com', 'password', 0, 3);
insert into Users (user_id, name, surname, email, password, type, entity_id) values (12, 'Ortensia', 'Kording', 'okordinga@toplist.cz', 'password', 0, 3);



-- FOREIGN KEYS Coffee Company - sensors
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '3');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '4');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '7');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '10');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '13');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '14');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '17');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('1', '18');

-- FOREIGN KEYS Stick Company - sensors
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '1');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '5');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '8');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '11');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('2', '15');

-- FOREIGN KEYS Plastic Cups Company - sensors
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('3', '2');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('3', '6');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('3', '9');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('3', '12');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('3', '16');




