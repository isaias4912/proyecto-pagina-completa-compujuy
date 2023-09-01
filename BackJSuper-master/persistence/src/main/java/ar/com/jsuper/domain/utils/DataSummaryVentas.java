/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.domain.TransaccionCaja;
import java.math.BigDecimal;

/**
 *
 * @author rafael
 */
public class DataSummaryVentas {

    private BigDecimal totalActivas;
    private BigDecimal totalAnuladas;
    private BigDecimal total;
    private BigDecimal cantidadActivas;
    private BigDecimal cantidadAnuladas;
    private BigDecimal totalTarjetaCredito;
    private BigDecimal totalTarjetaDebito;
    private BigDecimal totalTarjeta;
    private BigDecimal totalEfectivo;
    private BigDecimal totalDineroEfectivo;
    private BigDecimal totalCtaCte;
    private BigDecimal cantidad;
    private TransaccionCaja transaccionCaja;

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

    public TransaccionCaja getTransaccionCaja() {
        return transaccionCaja;
    }

    public void setTransaccionCaja(TransaccionCaja transaccionCaja) {
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
