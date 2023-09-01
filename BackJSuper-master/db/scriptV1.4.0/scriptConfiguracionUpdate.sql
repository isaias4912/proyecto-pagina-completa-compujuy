ALTER TABLE `jsuperfinal`.`app` 
ADD COLUMN `configuracion_id` INT(11) NOT NULL AFTER `alt_logo`,
ADD INDEX `fk_app_configuracion1_idx` (`configuracion_id` ASC);

ALTER TABLE `jsuperfinal`.`configuracion` 
DROP COLUMN `codigoAutorizacion`,
DROP COLUMN `acceso`,
DROP COLUMN `logo`,
DROP COLUMN `telCelEmpresa`,
DROP COLUMN `telEmpresa`,
DROP COLUMN `direccionEmpresa`,
DROP COLUMN `nombreEmpresa`,
DROP COLUMN `codigosEspecialesProd`,
DROP COLUMN `tipoProveedor`,
DROP COLUMN `ciudades`,
DROP COLUMN `tipoCliente`,
ADD COLUMN `cli_ctacte_interes` DECIMAL(9,4) NOT NULL DEFAULT 0 AFTER `id`;

ALTER TABLE `jsuperfinal`.`app` 
ADD CONSTRAINT `fk_app_configuracion1`
  FOREIGN KEY (`configuracion_id`)
  REFERENCES `jsuperfinal`.`configuracion` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;