CREATE TABLE patient (
    id_patient INT PRIMARY KEY,
    birthday DATE NOT NULL,
    patient_condition VARCHAR(20),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by INT,
    updated_by INT,
    deleted TINYINT(1) NOT NULL DEFAULT 0,

    CONSTRAINT fk_patient_user_id FOREIGN KEY (id_patient) REFERENCES user(id_user),
    CONSTRAINT fk_created_by_patient_user_id FOREIGN KEY (created_by) REFERENCES user(id_user),
    CONSTRAINT fk_updated_by_patient_user_id FOREIGN KEY (updated_by) REFERENCES user(id_user)
);
