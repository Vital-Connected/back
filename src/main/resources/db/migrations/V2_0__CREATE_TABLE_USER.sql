CREATE TABLE IF NOT EXISTS user (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by INT,
    updated_by INT,
    deleted TINYINT(1) NOT NULL DEFAULT 0,

    CONSTRAINT fk_user_created_by FOREIGN KEY (created_by) REFERENCES user(id_user),
    CONSTRAINT fk_user_updated_by FOREIGN KEY (updated_by) REFERENCES user(id_user),
    CONSTRAINT fk_user_role_id FOREIGN KEY (role_id) REFERENCES role(id_role)
);