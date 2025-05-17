CREATE TABLE IF NOT EXISTS role (
    id_role INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT, 

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    created_by INT,
    updated_by INT,
    deleted TINYINT(1) NOT NULL DEFAULT 0

);

INSERT INTO role (name, description) VALUES
('admin', 'Full access to the system, including user and data management'),
('caregiver', 'Responsible for monitoring and managing patient medications'),
('patient', 'Can view prescriptions and confirm medication intake'),
('self-care-patient', 'Patient + Caregiver functions');
