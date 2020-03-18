-- noinspection SqlNoDataSourceInspectionForFile

-- PostgreSQL 9.6
-- '\\' is a delimiter

-- select version() as postgresql_version

INSERT INTO entities (name, location, deleted) VALUES ('Test', 'Test', false);

INSERT INTO users (name, surname, email, password, type, telegram_name, telegram_chat, two_factor_authentication, deleted, entity_id) VALUES ('Utente', 'Utente', 'utente@utente.it', 'utente', 0, NULL, NULL, false, false, 1);

INSERT INTO users (name, surname, email, password, type, telegram_name, telegram_chat, two_factor_authentication, deleted, entity_id) VALUES ('Moderatore', 'Moderatore', 'moderatore@moderatore.it', 'moderatore', 1, NULL, NULL, false, false, 1);

INSERT INTO users (name, surname, email, password, type, telegram_name, telegram_chat, two_factor_authentication, deleted, entity_id) VALUES ('Amministratore', 'Amministratore', 'amministratore@amministratore.it', 'amministratore', 2, NULL, NULL, false, false, NULL);
