CREATE TABLE IF NOT EXISTS education (
                              id  BIGINT PRIMARY KEY AUTO_INCREMENT,
                              institution VARCHAR(100) NOT NULL,
                              degree VARCHAR(100) NOT NULL,
                              start_Year INTEGER NOT NULL ,
                              end_Year INTEGER NOT NULL ,
                              user_id BIGINT NOT NULL,

                              CONSTRAINT fk_education_user
                                  FOREIGN KEY (user_id) REFERENCES users(id)
                                      ON DELETE CASCADE
);