ALTER TABLE `producto` 
CHANGE COLUMN `nombre` `nombre` VARCHAR(250) NOT NULL ,
CHANGE COLUMN `activo` `activo` BIT(1) NOT NULL ,
CHANGE COLUMN `nombre_final` `nombre_final` VARCHAR(45) NOT NULL ,
ADD COLUMN `pesable` BIT(1) NULL DEFAULT NULL COMMENT 'Indica si el produtco es pesaable' AFTER `nombre_final`,
ADD COLUMN `ingreso_cantidad_manual` BIT(1) NULL DEFAULT NULL COMMENT 'Campo para que si la cantidad es manual vaya el focus directamente para el ingreso de la cantidad\n' AFTER `pesable`;
