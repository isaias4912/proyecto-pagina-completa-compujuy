ALTER TABLE `enc_movimientos` 
ADD COLUMN `pase_movimientos_id` INT(11) NULL DEFAULT NULL AFTER `cont_factura_enc_id`,
CHANGE COLUMN `tipo` `tipo` TINYINT(2) NULL DEFAULT NULL COMMENT '2 - ingreso\n3- egreso\n4- pase' ,
CHANGE COLUMN `subtipo` `subtipo` TINYINT(2) NULL DEFAULT NULL COMMENT 'tipo2: - 1: otros, 2: factura\\ntipo3:- 1: uso personal, 2: robo' ,
ADD INDEX `fk_enc_movimientos_pase_movimientos1_idx` (`pase_movimientos_id` ASC);

CREATE TABLE IF NOT EXISTS `pase_movimientos` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cantidad` DECIMAL(10,4) NOT NULL,
  `fecha` DATETIME NOT NULL,
  `sucursales_origen_id` BIGINT(20) NOT NULL,
  `sucursales_destino_id` BIGINT(20) NOT NULL,
  `producto_id` BIGINT(20) NOT NULL,
  `referencia` VARCHAR(254) NULL DEFAULT NULL,
  `descripcion` MEDIUMTEXT NULL DEFAULT NULL,
  `estado` BIT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_pase_movimientos_sucursales1_idx` (`sucursales_origen_id` ASC),
  INDEX `fk_pase_movimientos_sucursales2_idx` (`sucursales_destino_id` ASC),
  INDEX `fk_pase_movimientos_producto1_idx` (`producto_id` ASC),
  CONSTRAINT `fk_pase_movimientos_sucursales1`
    FOREIGN KEY (`sucursales_origen_id`)
    REFERENCES `sucursales` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pase_movimientos_sucursales2`
    FOREIGN KEY (`sucursales_destino_id`)
    REFERENCES `sucursales` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pase_movimientos_producto1`
    FOREIGN KEY (`producto_id`)
    REFERENCES `producto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

ALTER TABLE `enc_movimientos` 
ADD CONSTRAINT `fk_enc_movimientos_pase_movimientos1`
  FOREIGN KEY (`pase_movimientos_id`)
  REFERENCES `pase_movimientos` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `pase_movimientos` 
ADD COLUMN `app_id` INT(11) NOT NULL AFTER `estado`,
ADD INDEX `fk_pase_movimientos_app1_idx` (`app_id` ASC);

ALTER TABLE `pase_movimientos` 
ADD CONSTRAINT `fk_pase_movimientos_app1`
  FOREIGN KEY (`app_id`)
  REFERENCES `app` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


INSERT INTO menu_item_data (nombre, tipo_href, href, class_css, title, orden) 
	VALUES ('Pases', 1, 'admin.productos.listpase', 'fa fa-caret-right', NULL, NULL);

INSERT INTO menu_item (nombre, descripcion, menu_item_id, menu_item_data_id, principal, tipo_href, href, class_css, title, orden) 
	VALUES ('Pases', NULL, 6, 54, false, 1, 'admin.productos.listpase', 'fa fa-caret-right', NULL, 3);


