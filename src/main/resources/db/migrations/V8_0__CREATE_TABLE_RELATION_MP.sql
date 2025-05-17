CREATE TABLE relations_mp (
    id_relation_mp INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    dosage INT NOT NULL,
    frequency_value INT NOT NULL,
    frequency_unit ENUM('HOURS', 'DAYS', 'WEEKS') NOT NULL,
    total_dosage INT NOT NULL,
    id_medication INT NOT NULL,
    id_patient INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by INT,
    updated_by INT,
    deleted TINYINT(1) NOT NULL DEFAULT 0,

    CONSTRAINT fk_created_by_relations_mp_user_id FOREIGN KEY (created_by) REFERENCES user(id_user),
    CONSTRAINT fk_updated_by_relations_mp_user_id FOREIGN KEY (updated_by) REFERENCES user(id_user),
    CONSTRAINT fk_medication_relations_mp_medication_id FOREIGN KEY (id_medication) REFERENCES medication(id_medication),
    CONSTRAINT fk_patient_relations_mp_patient_id FOREIGN KEY (id_patient) REFERENCES patient (id_patient)
);