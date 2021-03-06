--liquibase formatted sql

--changeset DmitriySk:5

CREATE TABLE posts (
  post_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  post_text VARCHAR NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  fk_user_id INT, FOREIGN KEY(fk_user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
--rollback DROP TABLE posts;