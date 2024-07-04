CREATE DATABASE IF NOT EXISTS gameAE_DB;

USE gameAE_DB;

CREATE TABLE IF NOT EXISTS jugadores (
    usuario VARCHAR(50) PRIMARY KEY,
    contrasena VARCHAR(50) NOT NULL,
    scoreMaximo INT DEFAULT 0
);

INSERT INTO jugadores (usuario, contrasena, scoreMaximo) VALUES
('atormentador', '911', 4300),
('destructor99', '111', 500),
('Maquinita20', '555', 1000),
('michaeljackson', 'hehe', 1400),
('terminator10', 'rrr', 700),
('xenomorph9', '777', 8000);
