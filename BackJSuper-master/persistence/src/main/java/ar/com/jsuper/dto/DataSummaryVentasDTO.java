/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;

/**
 *
 * @author rafael
 */
public class DataSummaryVentasDTO {

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal totalActivas;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal totalAnuladas;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal total;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal cantidadActivas;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal cantidadAnuladas;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal totalTarjetaCredito;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal totalTarjetaDebito;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal totalTarjeta;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal totalEfectivo;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal totalDineroEfectivo;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal totalCtaCte;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal cantidad;
    private TransaccionCajaMinDTO transaccionCaja;

    public BigDecimal getTotalActivas() {
        return totalActivas;
    }

    public void setTotalActivas(BigDecimal totalActivas) {
        this.totalActivas = totalActivas;
    }

    public BigDecimal getTotalAnuladas() {
        return totalAnuladas;
    }

    public void setTotalAnuladas(BigDecimal totalAnuladas) {
        this.totalAnuladas = totalAnuladas;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getCantidadActivas() {
        return cantidadActivas;
    }

    public void setCantidadActivas(BigDecimal cantidadActivas) {
        this.cantidadActivas = cantidadActivas;
    }

    public BigDecimal getCantidadAnuladas() {
        return cantidadAnuladas;
    }

    public void setCantidadAnuladas(BigDecimal cantidadAnuladas) {
        this.cantidadAnuladas = cantidadAnuladas;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotalTarjeta() {
        return totalTarjeta;
    }

    public void setTotalTarjeta(BigDecimal totalTarjeta) {
        this.totalTarjeta = totalTarjeta;
    }

    public BigDecimal getTotalEfectivo() {
        return totalEfectivo;
    }

    public void setTotalEfectivo(BigDecimal totalEfectivo) {
        this.totalEfectivo = totalEfectivo;
    }

    public BigDecimal getTotalTarjetaCredito() {
        return totalTarjetaCredito;
    }

    public void setTotalTarjetaCredito(BigDecimal totalTarjetaCredito) {
        this.totalTarjetaCredito = totalTarjetaCredito;
    }

    public BigDecimal getTotalTarjetaDebito() {
        return totalTarjetaDebito;
    }

    public void setTotalTarjetaDebito(BigDecimal totalTarjetaDebito) {
        this.totalTarjetaDebito = totalTarjetaDebito;
    }

    public TransaccionCajaMinDTO getTransaccionCaja() {
        return transaccionCaja;
    }

    public void setTransaccionCaja(TransaccionCajaMinDTO transaccionCaja) {
        this.transaccionCaja = transaccionCaja;
    }

    public BigDecimal getTotalCtaCte() {
        return totalCtaCte;
    }

    public void setTotalCtaCte(BigDecimal totalCtaCte) {
        this.totalCtaCte = totalCtaCte;
    }

    public BigDecimal getTotalDineroEfectivo() {
        return totalDineroEfectivo;
    }

    public void setTotalDineroEfectivo(BigDecimal totalDineroEfectivo) {
        this.totalDineroEfectivo = totalDineroEfectivo;
    }

}
