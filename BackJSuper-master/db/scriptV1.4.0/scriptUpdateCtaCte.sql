ALTER TABLE `jsuperfinal`.`encabezadoventas` 
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);

ALTER TABLE `jsuperfinal`.`pagoventas` 
ADD COLUMN `movimientos_cta_cte_id` BIGINT(20) NULL DEFAULT NULL AFTER `pago_con`,
ADD INDEX `fk_pagoventas_movimientos_cta_cte1_idx` (`movimientos_cta_cte_id` ASC);

CREATE TABLE IF NOT EXISTS `jsuperfinal`.`cuentas_corrientes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `limite` DECIMAL(9,4) NOT NULL,
  `fecha_alta` DATETIME NOT NULL,
  `activo` BIT(1) NOT NULL,
  `clientes_id` BIGINT(20) NOT NULL,
  `descripcion` TINYTEXT NULL DEFAULT NULL AFTER,
  PRIMARY KEY (`id`),
  INDEX `fk_cuentas_corrientes_clientes1_idx` (`clientes_id` ASC),
  CONSTRAINT `fk_cuentas_corrientes_clientes1`
    FOREIGN KEY (`clientes_id`)
    REFERENCES `jsuperfinal`.`clientes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `jsuperfinal`.`movimientos_cta_cte` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `tipo` INT(11) NOT NULL COMMENT '1: cta cte clientes\n2: cta cte proveedores',
  `haber` DECIMAL(9,4) NOT NULL,
  `debe` DECIMAL(9,4) NOT NULL,
  `fecha` DATETIME NOT NULL,
  `saldo` DECIMAL(9,4) NOT NULL,
  `cuentas_corrientes_id` INT(11) NOT NULL,
  `descripcion` TINYTEXT NULL DEFAULT NULL AFTER,
  PRIMARY KEY (`id`),
  INDEX `fk_movimientos_cta_cte_cuentas_corrientes1_idx` (`cuentas_corrientes_id` ASC),
  CONSTRAINT `fk_movimientos_cta_cte_cuentas_corrientes1`
    FOREIGN KEY (`cuentas_corrientes_id`)
    REFERENCES `jsuperfinal`.`cuentas_corrientes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `jsuperfinal`.`pagos_cta_cte` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `monto` DECIMAL(9,4) NOT NULL,
  `numero` VARCHAR(100) NULL DEFAULT NULL,
  `tarjeta` VARCHAR(45) NULL DEFAULT NULL,
  `tipo` VARCHAR(45) NULL DEFAULT NULL,
  `pago_con` DECIMAL(9,4) NULL DEFAULT NULL,
  `descripcion` TINYTEXT NULL DEFAULT NULL,
  `formapagos_id` BIGINT(20) NOT NULL,
  `movimientos_cta_cte_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_pagos_cta_cte_formapagos1_idx` (`formapagos_id` ASC),
  INDEX `fk_pagos_cta_cte_movimientos_cta_cte1_idx` (`movimientos_cta_cte_id` ASC),
  CONSTRAINT `fk_pagos_cta_cte_formapagos1`
    FOREIGN KEY (`formapagos_id`)
    REFERENCES `jsuperfinal`.`formapagos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pagos_cta_cte_movimientos_cta_cte1`
    FOREIGN KEY (`movimientos_cta_cte_id`)
    REFERENCES `jsuperfinal`.`movimientos_cta_cte` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

ALTER TABLE `jsuperfinal`.`pagoventas` 
ADD CONSTRAINT `fk_pagoventas_movimientos_cta_cte1`
  FOREIGN KEY (`movimientos_cta_cte_id`)
  REFERENCES `jsuperfinal`.`movimientos_cta_cte` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;