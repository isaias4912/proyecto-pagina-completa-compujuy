ALTER TABLE `detalleventas` 
ADD COLUMN `info_prod_adic` MEDIUMTEXT NULL COMMENT 'informacion del producto adicional, como ser un numero de serie' AFTER `base_imponible`,
ADD COLUMN `numero_serie` VARCHAR(250) NULL AFTER `info_prod_adic`;
