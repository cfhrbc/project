
INSERT INTO roles (name)
SELECT 'ROLE_ADMIN'
    WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN'
);

INSERT INTO roles (name)
SELECT 'ROLE_USER'
    WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE name = 'ROLE_USER'
);

INSERT INTO users (name, surname, email, password, age)
SELECT 'admin', 'adminov', 'serarmynin@bk.ru', '$2b$12$jqc3Xs7rB0.PNVcmd67T9uUzPDjBbAVpHTxj57yzxkUKOUxl7JdjW', 29
    WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'serarmynin@bk.ru'
);

INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'serarmynin@bk.ru'
  AND r.name = 'ROLE_ADMIN'
  AND NOT EXISTS (
    SELECT 1 FROM users_roles ur WHERE ur.user_id = u.id AND ur.role_id = r.id
);