ALTER TABLE `jsuperfinal`.`clientes` 
DROP FOREIGN KEY `fk_clientes_personas1`;
-- 
ALTER TABLE `jsuperfinal`.`clientes` 
DROP INDEX `fk_clientes_personas1_idx` ;
ALTER TABLE `jsuperfinal`.`clientes` 
CHANGE COLUMN `personas_id` `personas_id1` INT(11) NOT NULL ;

ALTER TABLE `jsuperfinal`.`clientes` 
ADD COLUMN `personas_id` INT(11) NOT NULL;

update clientes set personas_id=personas_id1;
-- 
---------eliminamos el campo anterior
ALTER TABLE `jsuperfinal`.`clientes` 
DROP COLUMN `personas_id1`;

------------cambiamos  los encabezado de ventas
ALTER TABLE `jsuperfinal`.`encabezadoventas` 
DROP FOREIGN KEY `fk_encabezadoventas_clientes1`;

ALTER TABLE `jsuperfinal`.`encabezadoventas` 
DROP INDEX `fk_encabezadoventas_clientes1_idx` ;

----------- modificamos el id de cliente en encabezado ventas
update encabezadoventas set clientes_id=(select personas_id from clientes where id= encabezadoventas.clientes_id);

--------esto es solo para cuentas corrientes,  para la base cuando tiene esto
ALTER TABLE `jsuperfinal`.`cuentas_corrientes` 
DROP FOREIGN KEY `fk_cuentas_corrientes_clientes1`;

ALTER TABLE `jsuperfinal`.`cuentas_corrientes` 
DROP INDEX `fk_cuentas_corrientes_clientes1_idx` ;
------------------eliminamos el id de clientes y la pk
ALTER TABLE `jsuperfinal`.`clientes` 
DROP COLUMN `id`,
DROP PRIMARY KEY;

ALTER TABLE `jsuperfinal`.`clientes` 
ADD PRIMARY KEY (`personas_id`);

ALTER TABLE `jsuperfinal`.`encabezadoventas` 
CHANGE COLUMN `clientes_id` `clientes_id` INT(11) NULL DEFAULT NULL ;

-------------------agregamos  pk 
-- 
ALTER TABLE `jsuperfinal`.`encabezadoventas` 
ADD KEY `fk_encabezadoventas_clientes_personas1_idx` (`clientes_id`);

ALTER TABLE `jsuperfinal`.`encabezadoventas` 
ADD CONSTRAINT `fk_encabezadoventas_clientes_personas1`
  FOREIGN KEY (`clientes_id`)
  REFERENCES `jsuperfinal`.`clientes` (`personas_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

---------agregamos fk de personas a clientes
ALTER TABLE `jsuperfinal`.`clientes` 
ADD INDEX `fk_clientes_personas1_idx` (`personas_id`);

ALTER TABLE `jsuperfinal`.`clientes` 
ADD CONSTRAINT `fk_clientes_personas1`
  FOREIGN KEY (`personas_id`)
  REFERENCES `jsuperfinal`.`personas` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;