-- GATEWAYS
INSERT INTO "public"."gateways" ("gateway_id", "name") VALUES ('1', 'US-GATEWAY-1');
INSERT INTO "public"."gateways" ("gateway_id", "name") VALUES ('2', 'DE-GATEWAY-2');
INSERT INTO "public"."gateways" ("gateway_id", "name") VALUES ('3', 'SG-GATEWAY-3');


-- US GATEWAY-1 Devices
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('1', '1', 'COFFEE-MACHINE-1', '5', '1');
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('2', '2', 'COFFEE-MACHINE-2', '7', '1');

INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('6', '3', 'SNACK-MACHINE-1', '10', '1');

-- DE GATEWAY-1 Devices
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('3', '1', 'DE-COFFEE-MACHINE-1', '5', '2');
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('4', '2', 'DE-COFFEE-MACHINE-2', '5', '2');

-- SG GATEWAY-1 Devices
INSERT INTO "public"."devices" ("device_id", "real_device_id", "name", "frequency", "gateway_id") VALUES ('5', '1', 'SG-COFFEE-MACHINE-1', '5', '3');



-- US Coffee Machine 1 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('1', '1', 'sticks', '1');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('2', '2', 'plastic cups', '1');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('3', '3', 'cappuccino', '1');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('4', '4', 'espresso', '1');

-- US Coffee Machine 2 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('5', '1', 'stick', '2');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('6', '2', 'plastic cups', '2');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('7', '4', 'espresso', '2');

-- US Snack Machine 1 Sensors
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('19', '1', 'snack-1', '6');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('20', '2', 'snack-2', '6');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('21', '3', 'snack-3', '6');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('22', '4', 'drink-1', '6');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('23', '5', 'drink-2', '6');
INSERT INTO "public"."sensors" ("sensor_id", "real_sensor_id", "type", "device_id") VALUES ('24', '6', 'drink-3', '6');


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
INSERT INTO "public"."entities" ("entity_id", "name", "location", "deleted") VALUES ('4', 'Snack Company', 'Usa', 'false');
INSERT INTO "public"."entities" ("entity_id", "name", "location", "deleted") VALUES ('5', 'Drinks Company', 'Usa', 'false');

-- Administrator Users
insert into users (user_id, name, surname, email, password, type) values (1, 'Andrea', 'Simion', 'admin@admin.it', 'admin', 2);

-- Coffee Company users
insert into users (user_id, name, surname, email, password, type, entity_id) values (2, 'Mario', 'Rossi', 'mario@rossi.it', 'mariorossi', 0, 1);
insert into users (user_id, name, surname, email, password, type, entity_id) values (3, 'Adelind', 'Cambell', 'acambell1@wisc.edu', 'password3', 0, 1);
insert into users (user_id, name, surname, email, password, type, entity_id) values (4, 'Essy', 'Sims', 'esims2@wordpress.org', 'password4', 0, 1);
insert into users (user_id, name, surname, email, password, type, entity_id) values (5, 'Gloria', 'Brown', 'mod@mod.it', 'mod', 0, 1);
insert into users (user_id, name, surname, email, password, type, entity_id) values (6, 'Terry', 'Doulton', 'tdoulton4@twitpic.com', 'password6', 1, 1);

-- Stick Company users
insert into users (user_id, name, surname, email, password, type, entity_id) values (7, 'Donald', 'Trump', 'mod2@mod2.it', 'mod2', 1, 2);
insert into users (user_id, name, surname, email, password, type, entity_id) values (8, 'Shane', 'Harrhy', 'sharrhy6@acquirethisname.com', 'password8', 0, 2);
insert into users (user_id, name, surname, email, password, type, entity_id) values (9, 'Drucie', 'Stronach', 'dstronach7@berkeley.edu', 'password9', 0, 2);

-- Plastic Cups Company users
insert into users (user_id, name, surname, email, password, type, entity_id) values (10, 'Rosalinda', 'Glencross', 'rglencross8@bloglovin.com', 'password10', 1, 3);
insert into users (user_id, name, surname, email, password, type, entity_id) values (11, 'Goddart', 'Ellwell', 'gellwell9@xing.com', 'password11', 0, 3);
insert into users (user_id, name, surname, email, password, type, entity_id) values (12, 'Ortensia', 'Kording', 'okordinga@toplist.cz', 'password12', 0, 3);

-- Snack Company users
insert into users (user_id, name, surname, email, password, type, entity_id) values (13,'Roger','Darcy','Roger_Darcy1647@yahoo.com','password13',1,4);
insert into users (user_id, name, surname, email, password, type, entity_id) values (14,'Kieth','Lindop','Kieth_Lindop5965@cispeto.com','password14',0,4);
insert into users (user_id, name, surname, email, password, type, entity_id) values (15,'Mayleen','Turner','Mayleen_Turner4790@deons.tech','password15',0,4);
insert into users (user_id, name, surname, email, password, type, entity_id) values (16,'Chris','London','Chris_London8290@muall.tech','password16',0,4);
insert into users (user_id, name, surname, email, password, type, entity_id) values (17,'Moira','Eddison','Moira_Eddison6993@zorer.org','password17',0,4);
insert into users (user_id, name, surname, email, password, type, entity_id) values (18,'Nicholas','Cage','user2@user2.it','user2',0,4);

-- Drinks Company users
insert into users (user_id, name, surname, email, password, type, entity_id) values (19,'Chadwick','Wright','Chadwick_Wright7009@hourpy.biz','password19',0,5);
insert into users (user_id, name, surname, email, password, type, entity_id) values (20,'William','Stevens','William_Stevens2369@ubusive.com','password20',1,5);
insert into users (user_id, name, surname, email, password, type, entity_id) values (21,'Oliver','Rixon','Oliver_Rixon3216@bungar.biz','password21',0,5);
insert into users (user_id, name, surname, email, password, type, entity_id) values (22,'Jayden','Emmott','Jayden_Emmott282@bauros.biz','password22',0,5);
insert into users (user_id, name, surname, email, password, type, entity_id) values (23,'Johnathan','Duvall','Johnathan_Duvall3652@extex.org','password23',0,5);

-- views 
insert into views (view_id, name, user_id) values (1, 'Vista1', 1);
insert into views (view_id, name, user_id) values (2, 'Vista2', 1);
insert into views (view_id, name, user_id) values (3, 'Vista3', 1);
insert into views (view_id, name, user_id) values (4, 'Vista4', 1);

-- ViewGraphs

insert into views_graphs (graph_id, correlation, view_id, sensor_1_id, sensor_2_id) values (1, 0, 1, 1, 2);
insert into views_graphs (graph_id, correlation, view_id, sensor_1_id, sensor_2_id) values (2, 0, 1, 1, 2);	
insert into views_graphs (graph_id, correlation, view_id, sensor_1_id, sensor_2_id) values (3, 0, 1, 1, 2);	
insert into views_graphs (graph_id, correlation, view_id, sensor_1_id, sensor_2_id) values (4, 0, 1, 1, 2);	
insert into views_graphs (graph_id, correlation, view_id, sensor_1_id, sensor_2_id) values (5, 0, 1, 1, 2);	
	

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

-- FOREIGN KEYS Snack Company - sensors
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('4', '19');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('4', '20');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('4', '21');

-- FOREIGN KEYS Drinks Company - sensors
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('5', '22');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('5', '23');
INSERT INTO "public"."entity_sensors" ("entity_id", "sensor_id") VALUES ('5', '24');



-- Coffeee Company Alerts
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (1, 10, 1, 4, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (2, 10, 1, 7, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (3, 10, 1, 14, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (4, 10, 1, 18, 1);

INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (5, 5, 1, 3, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (6, 5, 1, 10, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (7, 5, 1, 13, 1);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (8, 5, 1, 17, 1);

-- Stick Company Alerts
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (9, 15, 1, 1, 2);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (10, 15, 1, 5, 2);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (11, 15, 1, 8, 2);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (12, 15, 1, 11, 2);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (13, 15, 1, 15, 2);

-- Plastic Cups Company Alerts
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (14, 15, 1, 2, 3);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (15, 15, 1, 6, 3);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (16, 15, 1, 9, 3);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (17, 15, 1, 12, 3);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (18, 15, 1, 16, 3);

-- Snack Company users
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (19, 3, 1, 19, 4);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (20, 3, 1, 20, 4);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (21, 3, 1, 21, 4);

-- Drink Company Alerts
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (22, 3, 1, 22, 5);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (23, 3, 1, 23, 5);
INSERT INTO "public"."alerts" ("alert_id","threshold","type","sensor_id","entity_id") VALUES (24, 3, 1, 24, 5);




