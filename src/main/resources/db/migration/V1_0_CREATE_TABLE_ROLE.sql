CREATE TABLE IF NOT EXISTS role (
    id_role INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO role (id_role, name, description) VALUES
(1, 'admin', 'Full access to the system, including user and data management'),
(2, 'caregiver', 'Responsible for monitoring and managing patient medications'),
(3, 'patient', 'Can view prescriptions and confirm medication intake'),
(4, 'self-care-patient', 'Patient + Caregiver functions');


