CREATE TABLE  IF NOT EXISTS users
(
    id_user BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birthdate DATE,
    address VARCHAR(200),
    phone_number VARCHAR(20),
    email VARCHAR(150) NOT NULL UNIQUE,
    base_salary NUMERIC(15,2) NOT NULL CHECK (base_salary >= 0 AND base_salary <= 15000000)
);