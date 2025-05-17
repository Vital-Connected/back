create table have(
	id_have INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	id_patient INT NOT NULL,
	id_caregiver INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,

	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by INT,
    updated_by INT,
    deleted TINYINT(1) NOT NULL DEFAULT 0,

    CONSTRAINT fk_created_by_have_user_id FOREIGN KEY (created_by) REFERENCES user(id_user),
    CONSTRAINT fk_updated_by_have_user_id FOREIGN KEY (updated_by) REFERENCES user(id_user),
    CONSTRAINT fk_id_patient_have_patient_id FOREIGN KEY (id_patient) REFERENCES patient(id_patient),
    CONSTRAINT fk_id_caregiver_have_patient_id FOREIGN KEY (id_caregiver) REFERENCES caregiver(id_caregiver)
)