--liquibase formatted sql


--changeset DmitriySk:10

ALTER TABLE users_to_user_roles DROP COLUMN users_to_user_roles_id;

--rollback ALTER TABLE users_to_user_roles ADD COLUMN users_to_user_roles_id;
--rollback ALTER TABLE users_to_user_roles ALTER COLUMN users_to_user_roles_id SET NOT NULL;

--changeset DmitriySk:11

ALTER TABLE users_to_user_roles
ADD CONSTRAINT users_to_user_roles_unique_constraint UNIQUE (user_role_id_fk, user_id_fk);

--rollback ALTER TABLE users_to_user_roles DROP CONSTRAINT users_to_user_roles_unique_constraint;




