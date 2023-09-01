/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.Comprobante;
import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.Factura;
import ar.com.jsuper.utils.MoneySerializer;
import ar.com.jsuper.utils.Origen;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author rafa22
 */
public class CbteVenEncMinDTO extends CbteEncMinDTO {
    private Comprobante tipoCbte;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal saldo;
    private Boolean estado;
    private Boolean pagada;
    private SimpleObjectDTO transaccionCaja;
    @JsonManagedReference
    private Set<PagoVentasSinEncabDTO> pagosCbte;
    private String cbteNro;
    private String ptoVenta;
    private Factura tipoFactura;
    private Boolean afipValida;

    public Comprobante getTipoCbte() {
        return tipoCbte;
    }

    public void setTipoCbte(Comprobante tipoCbte) {
        this.tipoCbte = tipoCbte;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Boolean getPagada() {
        return pagada;
    }

    public void setPagada(Boolean pagada) {
        this.pagada = pagada;
    }

    public SimpleObjectDTO getTransaccionCaja() {
        return transaccionCaja;
    }

    public void setTransaccionCaja(SimpleObjectDTO transaccionCaja) {
        this.transaccionCaja = transaccionCaja;
    }

    public Set<PagoVentasSinEncabDTO> getPagosCbte() {
        return pagosCbte;
    }

    public void setPagosCbte(Set<PagoVentasSinEncabDTO> pagosCbte) {
        this.pagosCbte = pagosCbte;
    }

    public String getCbteNro() {
        return cbteNro;
    }

    public void setCbteNro(String cbteNro) {
        this.cbteNro = cbteNro;
    }

    public String getPtoVenta() {
        return ptoVenta;
    }

    public void setPtoVenta(String ptoVenta) {
        this.ptoVenta = ptoVenta;
    }

    public Factura getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(Factura tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public Boolean getAfipValida() {
        return afipValida;
    }

    public void setAfipValida(Boolean afipValida) {
        this.afipValida = afipValida;
    }
}
