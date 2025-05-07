CREATE TABLE IF NOT EXISTS houses
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    address          VARCHAR(100) NOT NULL,
    area             DOUBLE       NOT NULL,
    constructionYear INTEGER      NOT NULL,
    user_id          BIGINT       NOT NULL,
    CONSTRAINT fk_houses_user FOREIGN KEY (user_id) REFERENCES users (id)
);