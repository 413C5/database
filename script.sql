CREATE DATABASE bdprueba;

USE bdprueba;

CREATE TABLE `usuarios` (
  `nombre` varchar(8) NOT NULL,
  `clave` varchar(8) NOT NULL,
  `intentos` int(8) NOT NULL,
  `bloqueado` int(8) NOT NULL,
  `admin` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `usuarios` (`nombre`, `clave`, `intentos`, `bloqueado`, `admin`) VALUES
('admin', 'admin', 0, 0, 1),
('gamma', 'gamma', 0, 0, 0),
('alfa', 'alfa', 0, 0, 0),
('beta', 'beta', 0, 0, 0),
('delta', 'delta', 0, 0, 0);

COMMIT;