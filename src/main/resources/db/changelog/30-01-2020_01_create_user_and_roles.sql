--liquibase formatted sql

--changeset DmitriySk:1

CREATE TABLE users (
  user_id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  birthday_date DATE DEFAULT NULL,
  password VARCHAR(250) NOT NULL,
  login VARCHAR(250) UNIQUE NOT NULL
);
--rollback DROP TABLE users;




--changeset DmitriySk:2

CREATE TABLE user_roles (
  user_role_id INT AUTO_INCREMENT  PRIMARY KEY,
  role VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE users_to_user_roles (
  users_to_user_roles_id INT AUTO_INCREMENT PRIMARY KEY,
  user_role_id_fk INT, FOREIGN KEY(user_role_id_fk) REFERENCES user_roles(user_role_id),
  user_id_fk INT, FOREIGN KEY(user_id_fk) REFERENCES users(user_id) ON DELETE CASCADE
);
--rollback DROP TABLE users_to_user_roles;
--rollback DROP TABLE user_roles;