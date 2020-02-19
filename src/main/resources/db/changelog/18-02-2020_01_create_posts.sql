--liquibase formatted sql

--changeset DmitriySk:4

CREATE TABLE posts (
  post_id INT AUTO_INCREMENT PRIMARY KEY,
  post_text VARCHAR NOT NULL,
  created_at DATE NOT NULL DEFAULT NOW(),
  fk_user_id INT, FOREIGN KEY(fk_user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
--rollback DROP TABLE posts;