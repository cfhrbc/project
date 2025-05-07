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