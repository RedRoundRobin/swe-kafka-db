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
insert into users (user_id, name, surname, email, password, type) values (1, 'Andrea', 'Simion', 'admin@admin.it', 'C7AD44CBAD762A5DA0A452F9E854FDC1E0E7A52A38015F23F3EAB1D80B931DD472634DFAC71CD34EBC35D16AB7FB8A90C81F975113D6C7538DC69DD8DE9077EC', 2); -- psw: admin
insert into users (user_id, name, surname, email, password, type, telegram_name) values (10, 'Mariano', 'Simion', 'admin2@admin2.it', '661BB43140229AD4DC3E762E7BDD68CC14BB9093C158C386BD989FEA807ACD9BD7F805CA4736B870B6571594D0D8FCFC57B98431143DFB770E083FA9BE89BC72', 2, 'Maxelweb'); -- psw: admin2
insert into users (user_id, name, surname, email, password, type, telegram_name) values (11, 'Giovanni', 'Simion', 'admin3@admin3.it', '448D8CA07486257065ADD370C87E61A3C778C70D4FCB5DB89F011ADE315E7A942FB3352E6BDED66C4F9ADEF6F3314588ACE81AA12096111EE306FA5ED4294182', 2, 'giovannividotto'); -- psw: admin3

-- Pulp Company users
insert into users (user_id, name, surname, email, password, type, entity_id) values (2, 'Jules', 'Winnfield', 'mario@rossi.it', '7856873F653B390AFEC6C83A6FC8A47DE054A6C19EFFD4E81BC4C1383ED2EED576F272E90517DA5C563E8BA79F6470A305788FC091AF6DB822F239850EE97DB8', 0, 1);  -- psw: mariorossi
insert into users (user_id, name, surname, email, password, type, entity_id) values (3, 'Marsellus', 'Wallace', 'acambell1@wisc.edu', '2A64D6563D9729493F91BF5B143365C0A7BEC4025E1FB0AE67E307A0C3BED1C28CFB259FC6BE768AB0A962B1E2C9527C5F21A1090A9B9B2D956487EB97AD4209', 0, 1); -- psw: password3
insert into users (user_id, name, surname, email, password, type, entity_id) values (4, 'Bucth', 'Coolidge', 'esims2@wordpress.org', '11961811BD4E11F23648AFBD2D5C251D2784827147F1731BE010ADAF0AB38AE18E5567C6FD1BEE50A4CD35FB544B3C594E7D677EFA7CA01C2A2CB47F8FB12B17', 0, 1);  -- psw: password4
insert into users (user_id, name, surname, email, password, type, entity_id, telegram_name, two_factor_authentication) values (5, 'Vincent', 'Vega', 'ale@tomm.it', '3768616A7A236CF702C421E0887A4C29DAA94C7BF42ABF1F476A2E92B6F1EC7B9607D12A9979196B96D3D0C884A0F1E98512EFCF417E048EE77C003BA03824E4', 1, 1, 'Alet0m', true);  -- psw: aletomm
insert into users (user_id, name, surname, email, password, type, entity_id) values (6, 'Mia', 'Wallace', 'mod@mod.it', 'FD2D1F62AAB380A798F7A2DAF73E8ACA617DDBE13DA858609939D7420769D3398000E5463A9AB995FF8D0C48C2A1B64BB71DB0528CC5ADEA40ABB5BF9CD1A62A', 1, 1);  -- psw: mod

-- Coffee Company users
insert into users (user_id, name, surname, email, password, type, entity_id) values (7, 'Donald', 'Trump', 'mod2@mod2.it', '3243878E639E400978908A6673112C3E7C9E6EA2D7E4B483A60025E90118D2BE2939F047728995BA510B151D3C7E29F3636602AD039B570EA568553910578264', 1, 2); -- psw: mod2
insert into users (user_id, name, surname, email, password, type, entity_id) values (8, 'Shane', 'Harrhy', 'sharrhy6@acquirethisname.com', '219AAB6B2CF738D9F370E197CE0151BE42AE6095E0D72FA49592865C9D2DDE5D2FA72E908AC374CBA55426E6D0ED39324FD6DE1D5DA2641CC7F4491F5EDD931F', 0, 2); -- psw: password8
insert into users (user_id, name, surname, email, password, type, entity_id) values (9, 'Drucie', 'Stronach', 'dstronach7@berkeley.edu', '219AAB6B2CF738D9F370E197CE0151BE42AE6095E0D72FA49592865C9D2DDE5D2FA72E908AC374CBA55426E6D0ED39324FD6DE1D5DA2641CC7F4491F5EDD931F', 0, 2);  -- psw: password8


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



