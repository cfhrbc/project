-- Создаем роль ADMIN
INSERT INTO roles (role_id, name) VALUES (1, 'ROLE_ADMIN')
    ON DUPLICATE KEY UPDATE name='ROLE_ADMIN';
-- Создаем роль USER
INSERT INTO roles (role_id, name) VALUES (1, 'ROLE_USER')
ON DUPLICATE KEY UPDATE name='ROLE_USER';

-- Создаем пользователя admin с паролем admin и присваиваем ему роль ADMIN
INSERT INTO users (id, name, password, surname, email, age) VALUES (1, 'admin', '{bcrypt}$2a$10$9iwxFyH4mF0E0Zm.xeJpC.BFfnXKtcG5Jlf4C.MIzE0RzZZrSeTzG', 'admin', 'admin@example.com', 30)
    ON DUPLICATE KEY UPDATE name='admin', password='{bcrypt}$2a$10$9iwxFyH4mF0E0Zm.xeJpC.BFfnXKtcG5Jlf4C.MIzE0RzZZrSeTzG';

-- Связываем пользователя admin с ролью ADMIN
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1)
    ON DUPLICATE KEY UPDATE user_id=1, role_id=1;