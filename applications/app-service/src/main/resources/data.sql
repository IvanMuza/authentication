INSERT INTO role (name, description)
VALUES ('Admin', 'Admin role');
INSERT INTO role (name, description)
VALUES ('Consultant', 'Consultant role');
INSERT INTO role (name, description)
VALUES ('Customer', 'Customer role');

INSERT INTO users (document_number, name, last_name, birthdate, address, phone_number, email, base_salary, password,
                   role_id)
VALUES ('10001', 'Carlos', 'Ramírez', '1990-03-15', 'Calle 10 #5-20', '3001112233', 'carlos.ramirez@demo.com', 2500000,
        'test', 1),
       ('10002', 'María', 'Gómez', '1985-07-22', 'Carrera 15 #45-67', '3002223344', 'maria.gomez@demo.com', 3200000,
        'test', 2),
       ('10003', 'Andrés', 'López', '1992-11-05', 'Av. Siempre Viva 123', '3003334455', 'andres.lopez@demo.com',
        2800000, 'test', 3),
       ('10004', 'Laura', 'Fernández', '1995-01-30', 'Calle 50 #20-15', '3004445566', 'laura.fernandez@demo.com',
        3000000, 'test', 1),
       ('10005', 'Sofía', 'Martínez', '1998-09-18', 'Carrera 8 #12-34', '3005556677', 'sofia.martinez@demo.com',
        2700000, 'test', 2);
