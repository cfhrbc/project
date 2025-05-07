CREATE TABLE IF NOT EXISTS work (
                                         id  BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         company VARCHAR(255) NOT NULL,
                                         position VARCHAR(255) NOT NULL,
                                         start_date VARCHAR(255) NOT NULL,
                                         end_date VARCHAR(255),
                                         user_id BIGINT NOT NULL,

                                         CONSTRAINT fk_work_user
                                             FOREIGN KEY (user_id) REFERENCES users(id)
                                                 ON DELETE CASCADE
);