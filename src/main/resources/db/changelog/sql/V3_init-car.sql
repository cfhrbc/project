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