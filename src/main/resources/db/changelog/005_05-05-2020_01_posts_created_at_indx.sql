--liquibase formatted sql

--changeset DmitriySk:7

CREATE INDEX posts_created_at_index ON posts (created_at);

--rollback DROP INDEX posts_created_at_index

--changeset DmitriySk:8

ALTER TABLE posts
ALTER COLUMN fk_user_id SET NOT NULL

--rollback ALTER TABLE posts
--           ALTER COLUMN fk_user_id INT
