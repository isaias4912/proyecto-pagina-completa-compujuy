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
 * @author rafa22
 */
public class CbteVenDetMinDTO {

    private Integer id;
    private String descripcion;
    private BigDecimal cantidad;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal precio;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal descuento;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal adicional;
    private ProductoMinDTO producto;
    private CbteVenEncNanoDTO cbteEnc;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal total;
    private String tipoPrecioVenta;
    private String infoProdAdic;
    private String numeroSerie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getAdicional() {
        return adicional;
    }

    public void setAdicional(BigDecimal adicional) {
        this.adicional = adicional;
    }

    public ProductoMinDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMinDTO producto) {
        this.producto = producto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getTipoPrecioVenta() {
        return tipoPrecioVenta;
    }

    public void setTipoPrecioVenta(String tipoPrecioVenta) {
        this.tipoPrecioVenta = tipoPrecioVenta;
    }

    public String getInfoProdAdic() {
        return infoProdAdic;
    }

    public void setInfoProdAdic(String infoProdAdic) {
        this.infoProdAdic = infoProdAdic;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public CbteVenEncNanoDTO getCbteEnc() {
        return cbteEnc;
    }

    public void setCbteEnc(CbteVenEncNanoDTO cbteEnc) {
        this.cbteEnc = cbteEnc;
    }
}
