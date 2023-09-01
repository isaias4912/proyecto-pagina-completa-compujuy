ALTER TABLE `caja` 
DROP INDEX `nombre_UNIQUE`;

ALTER TABLE `imagen_producto` 
ADD COLUMN `orden` INT(11) NULL DEFAULT NULL AFTER `alt`;

ALTER TABLE `stock_sucursal` 
CHANGE COLUMN `stock` `stock` DECIMAL(15,4) NULL DEFAULT NULL ;