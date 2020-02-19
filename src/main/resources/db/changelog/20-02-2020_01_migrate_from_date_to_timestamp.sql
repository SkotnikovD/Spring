--liquibase formatted sql

--changeset DmitriySk:5

ALTER TABLE posts ALTER COLUMN created_at TYPE TIMESTAMP without time zone;

--rollback  ALTER TABLE posts ALTER COLUMN created_at TYPE DATE;