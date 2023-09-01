CREATE TABLE IF NOT EXISTS `lista_precios` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(250) NOT NULL,
  `fecha_creacion` DATETIME NOT NULL,
  `tipo` TINYINT(4) NOT NULL,
  `detalle` TINYTEXT NULL DEFAULT NULL,
  `valor` DECIMAL(12,4) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

ALTER TABLE `lista_precios` 
ADD COLUMN `app_id` INT(11) NOT NULL AFTER `valor`,
ADD INDEX `fk_lista_precios_app1_idx` (`app_id` ASC);

ALTER TABLE `lista_precios` 
ADD COLUMN `activo` BIT(1) NOT NULL AFTER `app_id`;

INSERT INTO menu_item_data (nombre, tipo_href, href, class_css, title, orden) 
	VALUES ('Lista de precios', 1, 'admin.utils.listaprecioslist', 'fa fa-caret_right', NULL, NULL);
INSERT INTO menu_item (nombre, descripcion, menu_item_id, menu_item_data_id, principal, tipo_href, href, class_css, title, orden) 
	VALUES ('Lista de precios', NULL, 7, 56, false, 1, 'admin.utils.listaprecioslist', 'fa fa-caret-right', NULL, 2);

update menu_item set orden=1 where id=8;

drop TABLE if exists menu_data;

ALTER TABLE `detalleventas` 
CHANGE COLUMN `cantidad` `cantidad` DECIMAL(9,4) NOT NULL ,
CHANGE COLUMN `precio` `precio` DECIMAL(10,4) NOT NULL ,
CHANGE COLUMN `descripcion` `descripcion` VARCHAR(150) NOT NULL ,
CHANGE COLUMN `subtotal` `subtotal` DECIMAL(10,4) NOT NULL ;

ALTER TABLE `encabezadoventas` 
ADD COLUMN `lista_precio` BIT(1) NULL DEFAULT NULL AFTER `afip_importe_total`,
ADD COLUMN `lista_precio_data` MEDIUMTEXT NULL DEFAULT NULL AFTER `lista_precio`;

ALTER TABLE `detalleventas` 
DROP FOREIGN KEY `fk_detalleventas_ofertas1`;

ALTER TABLE `detalleventas` 
DROP COLUMN `ofertas_id`,
DROP COLUMN `nombre_oferta`,
DROP COLUMN `descuento_oferta`,
ADD COLUMN `oferta` BIT(1) NULL DEFAULT NULL AFTER `numero_serie`,
ADD COLUMN `oferta_data` MEDIUMTEXT NULL DEFAULT NULL AFTER `oferta`,
DROP INDEX `fk_detalleventas_ofertas1_idx` ;

ALTER TABLE `detalleventas` 
ADD COLUMN `oferta_descuento` DECIMAL(10,4) NULL DEFAULT NULL AFTER `oferta_data`;
