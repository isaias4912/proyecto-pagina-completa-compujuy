/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.domain.Caja;
import ar.com.jsuper.domain.DetalleTransaccionCaja;
import ar.com.jsuper.domain.TransaccionCaja;

/**
 *
 * @author rafael
 */
public class DataTransaccionCaja {
    private Caja caja;
    private TransaccionCaja transaccionCaja;
    private DetalleTransaccionCaja detalleTransaccionCaja;

    public DataTransaccionCaja() {
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public TransaccionCaja getTransaccionCaja() {
        return transaccionCaja;
    }

    public void setTransaccionCaja(TransaccionCaja transaccionCaja) {
        this.transaccionCaja = transaccionCaja;
    }

    public DetalleTransaccionCaja getDetalleTransaccionCaja() {
        return detalleTransaccionCaja;
    }

    public void setDetalleTransaccionCaja(DetalleTransaccionCaja detalleTransaccionCaja) {
        this.detalleTransaccionCaja = detalleTransaccionCaja;
    }
    
}
