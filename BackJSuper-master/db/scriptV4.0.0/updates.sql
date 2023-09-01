ALTER TABLE `configuracion` 
ADD COLUMN `print_host` VARCHAR(80) NULL AFTER `enabled_venta`,
ADD COLUMN `print_port` INT(8) NULL AFTER `impr_host`;

ALTER TABLE `cbte_ven_enc` 
ADD COLUMN `tipo` TINYINT(2) NOT NULL AFTER `uuid`;

update cbte_ven_enc set tipo=1;

ALTER TABLE `cbte_ven_enc` 
RENAME TO  `cbte_enc` ;

ALTER TABLE `cbte_ven_det` 
RENAME TO  `cbte_det` ;

ALTER TABLE `cbte_det` 
DROP FOREIGN KEY `fk_cbte_ven_det_cbte_ven_enc1`;
ALTER TABLE `cbte_det` 
CHANGE COLUMN `cbte_ven_enc_id` `cbte_enc_id` INT(11) UNSIGNED NULL DEFAULT NULL ;
ALTER TABLE `cbte_det` 
ADD CONSTRAINT `fk_cbte_ven_det_cbte_ven_enc1`
  FOREIGN KEY (`cbte_enc_id`)
  REFERENCES `cbte_enc` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `pago_cbte_ven` 
RENAME TO  `pago_cbte` ;

ALTER TABLE `cbte_enc` 
ADD COLUMN `estado_cbte` TINYINT(2) NULL;

ALTER TABLE `cbte_enc` ADD fecha_vigencia datetime NULL;

ALTER TABLE cbte_enc ADD observacionColumn1 MEDIUMTEXT NULL;

ALTER TABLE `cbte_enc` 
ADD COLUMN `source` TINYINT(2) NULL AFTER `fecha_vigencia`,
ADD COLUMN `source_id` INT(11) UNSIGNED NULL AFTER `source`;

-- agregamos el foreing key de clientes en cbte enc

ALTER TABLE `movimientos_producto` 
DROP FOREIGN KEY `fk_movimientos_producto_cbte_ven_det1`;
ALTER TABLE `movimientos_producto` 
CHANGE COLUMN `cbte_ven_det_id` `cbte_det_id` INT(11) UNSIGNED NULL DEFAULT NULL ;
ALTER TABLE `movimientos_producto` 
ADD CONSTRAINT `fk_movimientos_producto_cbte_ven_det1`
  FOREIGN KEY (`cbte_det_id`)
  REFERENCES `cbte_det` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `cbte_enc` 
ADD INDEX `fk_cbte_enc_clientes1_idx` (`clientes_id` ASC);

ALTER TABLE `cbte_enc` 
ADD CONSTRAINT `fk_cbte_enc_clientes1`
  FOREIGN KEY (`clientes_id`)
  REFERENCES `clientes` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;




