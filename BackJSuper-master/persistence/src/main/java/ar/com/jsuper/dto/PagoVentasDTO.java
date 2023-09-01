/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.math.BigDecimal;

public class PagoVentasDTO implements Serializable {

    private int id;
    private String descripcion;
    private BigDecimal monto;
    private BigDecimal pagoCon;
    private BigDecimal interes;
    private BigDecimal montoConInteres;
    private String numero;
    private String tipo;
    private String tarjeta;
    private FormaPagosDTO formaPago;
    @JsonBackReference
    private CbteVenEncDTO cbteVenEnc;

    public PagoVentasDTO() {
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

    public FormaPagosDTO getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPagosDTO formaPago) {
        this.formaPago = formaPago;
    }

    public CbteVenEncDTO getCbteVenEnc() {
        return cbteVenEnc;
    }

    public void setCbteVenEnc(CbteVenEncDTO cbteVenEnc) {
        this.cbteVenEnc = cbteVenEnc;
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

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getMontoConInteres() {
        return montoConInteres;
    }

    public void setMontoConInteres(BigDecimal montoConInteres) {
        this.montoConInteres = montoConInteres;
    }

}
