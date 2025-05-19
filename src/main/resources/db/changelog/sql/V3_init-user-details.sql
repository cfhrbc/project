CREATE TABLE IF NOT EXISTS cars (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    brand VARCHAR(100) NOT NULL,
                                    model VARCHAR(100) NOT NULL,
                                    color VARCHAR(50),
                                    year INT,
                                    owner_id BIGINT,
                                    CONSTRAINT fk_car_owner
                                        FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS families (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        relation VARCHAR(50) NOT NULL,
                                        name VARCHAR(100) NOT NULL,
                                        age INT NOT NULL,
                                        phone_number VARCHAR(20),
                                        user_id BIGINT NOT NULL,
                                        CONSTRAINT fk_family_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS houses
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    address          VARCHAR(100) NOT NULL,
    area             DOUBLE       NOT NULL,
    constructionYear INTEGER      NOT NULL,
    user_id          BIGINT       NOT NULL,
    CONSTRAINT fk_houses_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS social_media (
                                            id  BIGINT PRIMARY KEY AUTO_INCREMENT,
                                            platform VARCHAR(100) NOT NULL,
                                            username VARCHAR(100) NOT NULL,
                                            url VARCHAR(255) NOT NULL,
                                            user_id BIGINT NOT NULL,

                                            CONSTRAINT fk_social_media_user
                                                FOREIGN KEY (user_id) REFERENCES users(id)
                                                    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS educations (
                                         id  BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         institution VARCHAR(100) NOT NULL,
                                         degree VARCHAR(100) NOT NULL,
                                         start_Year INTEGER NOT NULL ,
                                         end_Year INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS user_educations (
                                 user_id BIGINT NOT NULL,
                                 education_id BIGINT NOT NULL,
                                 PRIMARY KEY (user_id, education_id),
                                 CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
                                 CONSTRAINT fk_education FOREIGN KEY (education_id) REFERENCES educations(id)
);