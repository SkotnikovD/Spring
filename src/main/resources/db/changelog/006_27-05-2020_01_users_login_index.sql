--liquibase formatted sql

--changeset DmitriySk:9

CREATE INDEX users_login_index ON users (login);

--rollback DROP INDEX users_login_index
