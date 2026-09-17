-- 1. Crear la tabla Ciudadano
CREATE TABLE Ciudadano (
    id_ciudadano SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    curp VARCHAR(18) UNIQUE NOT NULL,
    telefono VARCHAR(15)
);

-- 2. Crear la tabla SolicitudLicencia
CREATE TABLE SolicitudLicencia (
    folio SERIAL PRIMARY KEY,
    id_ciudadano INT NOT NULL,
    tipo_licencia VARCHAR(50) NOT NULL,
    esta_aprobada BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_ciudadano FOREIGN KEY (id_ciudadano) REFERENCES Ciudadano(id_ciudadano)
);

-- 3. Crear la tabla Requisito
CREATE TABLE Requisito (
    id_requisito SERIAL PRIMARY KEY,
    folio_solicitud INT NOT NULL,
    nombre_documento VARCHAR(150) NOT NULL,
    fue_entregado BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_solicitud FOREIGN KEY (folio_solicitud) REFERENCES SolicitudLicencia(folio)
);