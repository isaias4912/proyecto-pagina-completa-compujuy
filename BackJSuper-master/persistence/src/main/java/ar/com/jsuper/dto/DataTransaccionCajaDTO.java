/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author rafael
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataTransaccionCajaDTO {
    private CajaDTO caja;
    private TransaccionCajaDTO transaccionCaja;
    private DetalleTransaccionCajaDTO detalleTransaccionCaja;

    public DataTransaccionCajaDTO() {
    }

    public CajaDTO getCaja() {
        return caja;
    }

    public void setCaja(CajaDTO caja) {
        this.caja = caja;
    }

    public TransaccionCajaDTO getTransaccionCaja() {
        return transaccionCaja;
    }

    public void setTransaccionCaja(TransaccionCajaDTO transaccionCaja) {
        this.transaccionCaja = transaccionCaja;
    }

    public DetalleTransaccionCajaDTO getDetalleTransaccionCaja() {
        return detalleTransaccionCaja;
    }

    public void setDetalleTransaccionCaja(DetalleTransaccionCajaDTO detalleTransaccionCaja) {
        this.detalleTransaccionCaja = detalleTransaccionCaja;
    }

   
}
