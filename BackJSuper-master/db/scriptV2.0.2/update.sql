CREATE TABLE IF NOT EXISTS `jsuperfinal`.`afip_tpo_cbte` (
  `id` INT(11) NOT NULL,
  `descripcion` VARCHAR(100) NOT NULL,
  `fecha_desde` DATETIME NULL DEFAULT NULL,
  `fecha_hasta` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `jsuperfinal`.`afip_tpo_concepto` (
  `id` INT(11) NOT NULL,
  `descripcion` VARCHAR(80) NOT NULL,
  `fecha_desde` DATETIME NULL DEFAULT NULL,
  `fecha_hasta` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `jsuperfinal`.`afip_pto_venta` (
  `nro` INT(11) NOT NULL,
  `emision_tipo` VARCHAR(100) NOT NULL,
  `bloqueado` VARCHAR(45) NULL DEFAULT NULL,
  `fecha_baja` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`nro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `jsuperfinal`.`afip_tpo_iva` (
  `id` INT(11) NOT NULL,
  `descripcion` VARCHAR(100) NOT NULL,
  `fecha_desde` DATETIME NULL DEFAULT NULL,
  `fecha_hasta` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


ALTER TABLE `jsuperfinal`.`afip_tpo_cbte` 
ADD COLUMN `app_id` INT(11) NOT NULL AFTER `fecha_hasta`,
ADD INDEX `fk_afip_tpo_cbte_app1_idx` (`app_id` ASC);
;

ALTER TABLE `jsuperfinal`.`afip_tpo_concepto` 
ADD COLUMN `app_id` INT(11) NOT NULL AFTER `fecha_hasta`,
ADD INDEX `fk_afip_tpo_concepto_app1_idx` (`app_id` ASC);
;

ALTER TABLE `jsuperfinal`.`afip_pto_venta` 
ADD COLUMN `app_id` INT(11) NOT NULL AFTER `fecha_baja`,
ADD INDEX `fk_afip_pto_venta_app1_idx` (`app_id` ASC);
;

ALTER TABLE `jsuperfinal`.`afip_tpo_iva` 
ADD COLUMN `app_id` INT(11) NOT NULL AFTER `fecha_hasta`,
ADD INDEX `fk_afip_tpo_iva_app1_idx` (`app_id` ASC);
;

ALTER TABLE `jsuperfinal`.`afip_tpo_cbte` 
ADD CONSTRAINT `fk_afip_tpo_cbte_app1`
  FOREIGN KEY (`app_id`)
  REFERENCES `jsuperfinal`.`app` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `jsuperfinal`.`afip_tpo_concepto` 
ADD CONSTRAINT `fk_afip_tpo_concepto_app1`
  FOREIGN KEY (`app_id`)
  REFERENCES `jsuperfinal`.`app` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `jsuperfinal`.`afip_pto_venta` 
ADD CONSTRAINT `fk_afip_pto_venta_app1`
  FOREIGN KEY (`app_id`)
  REFERENCES `jsuperfinal`.`app` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `jsuperfinal`.`afip_tpo_iva` 
ADD CONSTRAINT `fk_afip_tpo_iva_app1`
  FOREIGN KEY (`app_id`)
  REFERENCES `jsuperfinal`.`app` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `jsuperfinal`.`afip_pto_venta` 
DROP COLUMN `id`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`nro`, `app_id`);
;

ALTER TABLE `jsuperfinal`.`afip_tpo_iva` 
DROP COLUMN `id_afip`,
CHANGE COLUMN `id` `id` INT(11) NOT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`, `app_id`);
;

ALTER TABLE `jsuperfinal`.`afip_pto_venta` 
ADD CONSTRAINT `fk_afip_pto_venta_app1`
  FOREIGN KEY (`app_id`)
  REFERENCES `jsuperfinal`.`app` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE IF NOT EXISTS `jsuperfinal`.`afip_tpo_doc` (
  `id` INT(11) NOT NULL,
  `descripcion` VARCHAR(80) NOT NULL,
  `fecha_desde` DATETIME NULL DEFAULT NULL,
  `fecha_hasta` DATETIME NULL DEFAULT NULL,
  `app_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `app_id`),
  INDEX `fk_afip_tpo_doc_app1_idx` (`app_id` ASC),
  CONSTRAINT `fk_afip_tpo_doc_app1`
    FOREIGN KEY (`app_id`)
    REFERENCES `jsuperfinal`.`app` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


ALTER TABLE `jsuperfinal`.`configuracion` 
ADD COLUMN `tipo_empresa` TINYINT(3) NULL DEFAULT NULL AFTER `encabezado_reporte`;
ALTER TABLE `jsuperfinal`.`configuracion` 
ADD COLUMN `cuit_empresa` VARCHAR(25) NULL DEFAULT NULL AFTER `tipo_empresa`;

CREATE TABLE IF NOT EXISTS `jsuperfinal`.`afip_tpo_tributo` (
  `id` INT(11) NOT NULL,
  `descripcion` VARCHAR(180) NOT NULL,
  `fecha_desde` DATETIME NULL DEFAULT NULL,
  `fecha_hasta` VARCHAR(45) NULL DEFAULT NULL,
  `app_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `app_id`),
  INDEX `fk_afip_tpo_tributo_app1_idx` (`app_id` ASC),
  CONSTRAINT `fk_afip_tpo_tributo_app1`
    FOREIGN KEY (`app_id`)
    REFERENCES `jsuperfinal`.`app` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;