CREATE TABLE IF NOT EXISTS families (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        relation VARCHAR(50) NOT NULL,
                                        name VARCHAR(100) NOT NULL,
                                        age INT NOT NULL,
                                        phone_number VARCHAR(20),
                                        user_id BIGINT NOT NULL,
                                        CONSTRAINT fk_family_user FOREIGN KEY (user_id) REFERENCES users(id)
);