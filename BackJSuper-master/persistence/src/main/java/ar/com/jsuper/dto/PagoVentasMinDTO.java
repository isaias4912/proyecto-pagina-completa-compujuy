/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PagoVentasMinDTO implements Serializable {

    private int id;
    private String descripcion;
    private BigDecimal monto;
    private BigDecimal pagoCon;
    private String numero;
    private String tipo;
    private String tarjeta;
    private SimpleObjectDTO cbteEnc;

    public PagoVentasMinDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public SimpleObjectDTO getCbteEnc() {
        return cbteEnc;
    }

    public void setCbteEnc(SimpleObjectDTO cbteEnc) {
        this.cbteEnc = cbteEnc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPagoCon() {
        return pagoCon;
    }

    public void setPagoCon(BigDecimal pagoCon) {
        this.pagoCon = pagoCon;
    }

}
