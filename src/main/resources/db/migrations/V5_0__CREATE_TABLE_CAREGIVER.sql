create table caregiver(
	id_caregiver INT PRIMARY KEY,
    relation VARCHAR(100) NOT NULL,

	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by INT,
    updated_by INT,
    deleted TINYINT(1) NOT NULL DEFAULT 0,

    CONSTRAINT fk_caregiver_user_id FOREIGN KEY (id_caregiver) REFERENCES user(id_user),
    CONSTRAINT fk_created_by_caregiver_user_id FOREIGN KEY (created_by) REFERENCES user(id_user),
    CONSTRAINT fk_updated_by_caregiver_user_id FOREIGN KEY (updated_by) REFERENCES user(id_user)
)