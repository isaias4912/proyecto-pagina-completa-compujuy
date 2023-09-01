update detalle_transaccion_caja set subtipo=1 where tipo=2 AND motivo='Solicitud de cambio';
update detalle_transaccion_caja set subtipo=2 where tipo=2 AND motivo='Resguardo de dinero';
update detalle_transaccion_caja set subtipo=3 where tipo=2 AND motivo='Recaudacion';
update detalle_transaccion_caja set subtipo=4 where tipo=2 AND motivo='Otro';


update detalle_transaccion_caja set subtipo=1 where tipo=3 AND motivo='Pago a proveedores';
update detalle_transaccion_caja set subtipo=2 where tipo=3 AND motivo='Recaudacion';
update detalle_transaccion_caja set subtipo=3 where tipo=3 AND motivo='Solicitud de cambio';
update detalle_transaccion_caja set subtipo=4 where tipo=3 AND motivo='Arqueo parcial';
update detalle_transaccion_caja set subtipo=5 where tipo=3 AND motivo='Arqueo';
update detalle_transaccion_caja set subtipo=6 where tipo=3 AND motivo='Otros pagos';
update detalle_transaccion_caja set subtipo=7 where tipo=3 AND motivo='Perdida de dinero';
update detalle_transaccion_caja set subtipo=8 where tipo=3 AND motivo='Otro';