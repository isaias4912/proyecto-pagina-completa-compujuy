DROP PROCEDURE IF EXISTS UPDATE_TRANSACCION;
DELIMITER ;;
CREATE PROCEDURE UPDATE_TRANSACCION()
BEGIN
  DECLARE cursor_TIPO TINYINT ;
  DECLARE cursor_FECHA DATETIME;
  DECLARE cursor_ID BIGINT;
  DECLARE done INT DEFAULT FALSE;
   DECLARE cursor_i CURSOR FOR SELECT tipo, fecha, transaccion_caja_id  FROM detalle_transaccion_caja where tipo=1 OR tipo=20;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
   OPEN cursor_i;
   read_loop: LOOP
     FETCH cursor_i INTO cursor_TIPO, cursor_FECHA, cursor_ID ;
     IF done THEN
       LEAVE read_loop;
     END IF;
       if (cursor_TIPO=1)THEN
          UPDATE transaccion_caja SET fecha_apertura=cursor_FECHA where id=cursor_ID;
       END IF;
      if (cursor_TIPO=20)THEN
          UPDATE transaccion_caja SET fecha_cierre =cursor_FECHA where id=cursor_ID;
       END IF;
   END LOOP;
   CLOSE cursor_i;
END
;;
DELIMITER ;
call UPDATE_TRANSACCION();
DROP PROCEDURE IF EXISTS UPDATE_TRANSACCION;