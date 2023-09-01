/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author rafael
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrecioProductoDTO implements Serializable {

    private ProductoMinDTO producto;
    private BigDecimal precioVenta;
    private BigDecimal precioCosto;
    private BigDecimal precioVentaInicial;
    private BigDecimal precioCostoInicial;
    private BigDecimal detalle;

    public PrecioProductoDTO() {
    }

    public ProductoMinDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMinDTO producto) {
        this.producto = producto;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(BigDecimal precioCosto) {
        this.precioCosto = precioCosto;
    }

    public BigDecimal getPrecioVentaInicial() {
        return precioVentaInicial;
    }

    public void setPrecioVentaInicial(BigDecimal precioVentaInicial) {
        this.precioVentaInicial = precioVentaInicial;
    }

    public BigDecimal getPrecioCostoInicial() {
        return precioCostoInicial;
    }

    public void setPrecioCostoInicial(BigDecimal precioCostoInicial) {
        this.precioCostoInicial = precioCostoInicial;
    }

    public BigDecimal getDetalle() {
        return detalle;
    }

    public void setDetalle(BigDecimal detalle) {
        this.detalle = detalle;
    }

}
