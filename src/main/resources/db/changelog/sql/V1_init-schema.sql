CREATE TABLE IF NOT EXISTS roles (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(50) NOT NULL,
                       surname VARCHAR(50),
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255),
                       age INT
);

CREATE TABLE IF NOT EXISTS users_roles (
                             user_id BIGINT NOT NULL,
                             role_id BIGINT NOT NULL,
                             PRIMARY KEY (user_id, role_id),
                             CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
                             CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
);