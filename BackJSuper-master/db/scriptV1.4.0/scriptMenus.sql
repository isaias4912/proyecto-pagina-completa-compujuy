CREATE TABLE IF NOT EXISTS `jsuperfinal`.`menu` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `descripcion` TINYTEXT NULL DEFAULT NULL,
  `roles_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_menu_roles1_idx` (`roles_id` ASC),
  CONSTRAINT `fk_menu_roles1`
    FOREIGN KEY (`roles_id`)
    REFERENCES `jsuperfinal`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `jsuperfinal`.`menu_item` (
  `id` INT(11) ZEROFILL NOT NULL,
  `nombre` VARCHAR(100) NULL DEFAULT NULL,
  `descripcion` TINYTEXT NULL DEFAULT NULL,
  `menu_id` INT(11) NULL DEFAULT NULL,
  `menu_item_id` INT(11) ZEROFILL NULL DEFAULT NULL,
  `menu_item_data_id` INT(11) NULL DEFAULT NULL,
  `principal` BIT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_menu_item_menu1_idx` (`menu_id` ASC),
  INDEX `fk_menu_item_menu_item1_idx` (`menu_item_id` ASC),
  INDEX `fk_menu_item_menu_item_data1_idx` (`menu_item_data_id` ASC),
  CONSTRAINT `fk_menu_item_menu1`
    FOREIGN KEY (`menu_id`)
    REFERENCES `jsuperfinal`.`menu` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_menu_item_menu_item1`
    FOREIGN KEY (`menu_item_id`)
    REFERENCES `jsuperfinal`.`menu_item` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_menu_item_menu_item_data1`
    FOREIGN KEY (`menu_item_data_id`)
    REFERENCES `jsuperfinal`.`menu_item_data` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `jsuperfinal`.`menu_item_data` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(150) NOT NULL,
  `tipo_href` INT(11) NOT NULL COMMENT '1 : enlace\n2: estado',
  `href` TINYTEXT NULL DEFAULT NULL,
  `class` VARCHAR(250) NULL DEFAULT NULL,
  `title` TINYTEXT NULL DEFAULT NULL,
  `orden` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;