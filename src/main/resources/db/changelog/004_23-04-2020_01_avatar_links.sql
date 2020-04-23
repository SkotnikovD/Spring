--liquibase formatted sql

--changeset DmitriySk:6

ALTER TABLE users
  ADD COLUMN avatar_fullsize_url VARCHAR,
  ADD COLUMN avatar_thumbnail_url VARCHAR;

--rollback ALTER TABLE users (
--            DROP COLUMN avatar_fullsize_url VARCHAR,
--            DROP COLUMN avatar_thumbnail_url VARCHAR);