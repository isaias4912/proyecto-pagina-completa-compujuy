ALTER TABLE `encabezadoventas` 
ADD COLUMN `afip_valida` BIT(1) NULL AFTER `afip_modo`;

ALTER TABLE `encabezadoventas` 
ADD COLUMN `afip_total_neto_gravado` DECIMAL(10,4) NULL AFTER `afip_valida`,
ADD COLUMN `afip_total_neto_no_gravado` DECIMAL(10,4) NULL AFTER `afip_total_neto_gravado`,
ADD COLUMN `afip_total_exento` DECIMAL(10,4) NULL AFTER `afip_total_neto_no_gravado`,
ADD COLUMN `afip_importe_total` DECIMAL(10,4) NULL AFTER `afip_total_exento`;

ALTER TABLE `producto` 
ADD COLUMN `iva_id` TINYINT NULL AFTER `fecha_creacion`;

ALTER TABLE `caja` 
ADD COLUMN `punto_venta_id` TINYINT(4) NULL DEFAULT NULL AFTER `limite_consumidor_final`;

ALTER TABLE `caja` 
ADD COLUMN `comprobantes_hab` TINYTEXT NULL AFTER `punto_venta_id`;

update producto set iva_id= 5;

-- CREATE TABLE IF NOT EXISTS `afip_iva` (
--   `id` MEDIUMINT(9) NOT NULL AUTO_INCREMENT,
--   `descripcion` VARCHAR(100) NOT NULL,
--   `fecha_desde` DATETIME NULL,
--   `fecha_hasta` DATETIME NULL,
--   `valor` DECIMAL(4,2) NOT NULL,
--   `id_afip` MEDIUMINT(9) NOT NULL,
--   PRIMARY KEY (`id`));
-- 
-- INSERT INTO jsuperfinal.afip_iva (descripcion, fecha_desde, fecha_hasta, valor, id_afip) 
-- 	VALUES ('0%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 3);
-- INSERT INTO jsuperfinal.afip_iva (descripcion, fecha_desde, fecha_hasta, valor, id_afip) 
-- 	VALUES ('10.5%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 10.5, 4);
-- INSERT INTO jsuperfinal.afip_iva (descripcion, fecha_desde, fecha_hasta, valor, id_afip) 
-- 	VALUES ('21%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 21, 5);
-- INSERT INTO jsuperfinal.afip_iva (descripcion, fecha_desde, fecha_hasta, valor, id_afip) 
-- 	VALUES ('27%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 27, 6);
-- INSERT INTO jsuperfinal.afip_iva (descripcion, fecha_desde, fecha_hasta, valor, id_afip) 
-- 	VALUES ('5%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 8);
-- INSERT INTO jsuperfinal.afip_iva (descripcion, fecha_desde, fecha_hasta, valor, id_afip) 
-- 	VALUES ('2.5%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2.5, 9);
