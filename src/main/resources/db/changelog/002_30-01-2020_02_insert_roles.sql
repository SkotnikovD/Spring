--liquibase formatted sql

--changeset DmitriySk:4

INSERT INTO user_roles (role) VALUES
  ('USER'),
  ('ADMIN'),
  ('ROOT_ADMIN');
--rollback delete from user_roles where role in ('USER', 'ADMIN', 'ROOT_ADMIN');