CREATE TABLE cliente
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre            VARCHAR(50)  NOT NULL,
    apellido          VARCHAR(50)  NOT NULL,
    email             VARCHAR(150) NOT NULL UNIQUE,
    telefono          VARCHAR(50),
    tipo_de_documento VARCHAR(20)  NOT NULL,
    documento         VARCHAR(20)  NOT NULL,
    UNIQUE (tipo_de_documento, documento)
);

CREATE TABLE evento
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    descripcion    TEXT,
    tipo_de_evento VARCHAR(20)  NOT NULL
);

CREATE TABLE funcion
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha     DATE        NOT NULL,
    hora      TIME        NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    altura    VARCHAR(20) NOT NULL,
    localidad VARCHAR(50) NOT NULL,
    provincia VARCHAR(50) NOT NULL,
    evento_id BIGINT,
    CONSTRAINT fk_funcion_evento FOREIGN KEY (evento_id) REFERENCES evento (id) ON DELETE CASCADE
);

CREATE TABLE disponibilidad
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_de_entrada VARCHAR(20)    NOT NULL,
    precio          DECIMAL(10, 2) NOT NULL CHECK (precio >= 0),
    cupos_totales   INT            NOT NULL CHECK (cupos_totales >= 0),
    cupos_ocupados  INT            NOT NULL CHECK (cupos_ocupados >= 0),
    funcion_id      BIGINT,
    CONSTRAINT fk_disponibilidad_funcion FOREIGN KEY (funcion_id) REFERENCES funcion (id) ON DELETE CASCADE,
    CONSTRAINT ck_cupos CHECK (cupos_ocupados <= cupos_totales)
);

CREATE TABLE reserva
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_creacion  DATE           NOT NULL DEFAULT CURRENT_DATE,
    tipo_de_entrada VARCHAR(20)    NOT NULL,
    funcion_id      BIGINT,
    estado_reserva  VARCHAR(20)    NOT NULL DEFAULT 'RESERVADO',
    monto_total     DECIMAL(10, 2) NOT NULL CHECK (monto_total >= 0),
    bonificado      TINYINT(1)     NOT NULL DEFAULT 0,
    cliente_id      BIGINT,
    CONSTRAINT fk_reserva_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE CASCADE,
    CONSTRAINT fk_reserva_funcion FOREIGN KEY (funcion_id) REFERENCES funcion (id) ON DELETE CASCADE
);

CREATE TABLE pase_gratis
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_de_otorgamiento DATE       NOT NULL DEFAULT CURRENT_DATE,
    usado                 TINYINT(1) NOT NULL DEFAULT 0,
    cliente_id            BIGINT,
    CONSTRAINT fk_pase_gratis_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE CASCADE
);

