CREATE TABLE IF NOT EXISTS history (
    id_history INT AUTO_INCREMENT PRIMARY KEY,
    id_relation_mp INT NOT NULL,
    taked TINYINT(1) NOT NULL DEFAULT 0,
    taked_at TIMESTAMP NULL DEFAULT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by INT,
    updated_by INT,
    deleted TINYINT(1) NOT NULL DEFAULT 0,

    CONSTRAINT fk_history_created_by FOREIGN KEY (created_by) REFERENCES user(id_user),
    CONSTRAINT fk_history_updated_by FOREIGN KEY (updated_by) REFERENCES user(id_user),
    CONSTRAINT fk_history_relation_mp FOREIGN KEY (id_relation_mp) REFERENCES relations_mp(id_relation_mp)
);
