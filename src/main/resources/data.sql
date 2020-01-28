DROP TABLE IF EXISTS users_to_user_roles;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  user_id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  birthday_date DATE DEFAULT NULL,
  password VARCHAR(250) NOT NULL,
  login VARCHAR(250) UNIQUE NOT NULL
);


CREATE TABLE user_roles (
  user_role_id INT AUTO_INCREMENT  PRIMARY KEY,
  role VARCHAR(250) NOT NULL
);


CREATE TABLE users_to_user_roles (
  users_to_user_roles_id INT AUTO_INCREMENT PRIMARY KEY,
  user_role_id_fk INT, FOREIGN KEY(user_role_id) REFERENCES user_roles(user_role_id),
  user_id_fk INT, FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

INSERT INTO user_roles (role) VALUES
  ('USER'),
  ('ADMIN'),
  ('ROOT_ADMIN');