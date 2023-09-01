ALTER TABLE `transaccion_caja` 
ADD COLUMN `fecha_apertura` DATETIME NULL DEFAULT NULL AFTER `caja_id`,
ADD COLUMN `fecha_cierre` DATETIME NULL DEFAULT NULL AFTER `fecha_apertura`;

ALTER TABLE `detalle_transaccion_caja` 
ADD COLUMN `subtipo` TINYINT(4) NULL DEFAULT NULL COMMENT 'Subtipo dentro de tipo\n';

CREATE TABLE IF NOT EXISTS `param_salidas_caja` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `activo` BIT(1) NOT NULL,
  `detalle` TINYTEXT NULL DEFAULT NULL,
  `orden` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `param_entradas_caja` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `activo` BIT(1) NOT NULL,
  `detalle` TINYTEXT NULL DEFAULT NULL,
  `orden` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

INSERT INTO `param_entradas_caja` (`nombre`, `activo`, `orden`) VALUES ('Solicitud de cambio', '1', '1');
INSERT INTO `param_entradas_caja` (`nombre`, `activo`, `orden`) VALUES ('Resguardo de dinero', '1', '2');
INSERT INTO `param_entradas_caja` (`nombre`, `activo`, `orden`) VALUES ('Recaudación', '1', '3');
INSERT INTO `param_entradas_caja` (`nombre`, `activo`, `orden`) VALUES ('Otro', '1', '4');

INSERT INTO `jsuperfinal`.`param_salidas_caja` (`nombre`, `activo`, `orden`) VALUES ('Pago a proveedores', '1', '1');
INSERT INTO `jsuperfinal`.`param_salidas_caja` (`nombre`, `activo`, `orden`) VALUES ('Recaudación', '1', '2');
INSERT INTO `jsuperfinal`.`param_salidas_caja` (`nombre`, `activo`, `orden`) VALUES ('Solicitud de cambio', '1', '3');
INSERT INTO `jsuperfinal`.`param_salidas_caja` (`nombre`, `activo`, `orden`) VALUES ('Arqueo parcial', '1', '4');
INSERT INTO `jsuperfinal`.`param_salidas_caja` (`nombre`, `activo`, `orden`) VALUES ('Arqueo', '1', '5');
INSERT INTO `jsuperfinal`.`param_salidas_caja` (`nombre`, `activo`, `orden`) VALUES ('Otros pagos', '1', '6');
INSERT INTO `jsuperfinal`.`param_salidas_caja` (`nombre`, `activo`, `orden`) VALUES ('Perdida de dinero', '1', '7');
INSERT INTO `jsuperfinal`.`param_salidas_caja` (`nombre`, `activo`, `orden`) VALUES ('Otro', '1', '8');


