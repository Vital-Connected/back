CREATE TABLE medication (
    id_medication INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    medication_function VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    created_by INT,
    updated_by INT,
    deleted TINYINT(1) NOT NULL DEFAULT 0,

    CONSTRAINT fk_created_by_medication_user_id FOREIGN KEY (created_by) REFERENCES user(id_user),
    CONSTRAINT fk_updated_by_medication_user_id FOREIGN KEY (updated_by) REFERENCES user(id_user)
);
