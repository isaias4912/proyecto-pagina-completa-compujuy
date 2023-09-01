package ar.com.jsuper.dto;

import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.io.Serializable;

public class StockSucursalNanoDTO extends ObjectDTO implements Serializable {

    private int id;
	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal stock;
	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal stockMinimo;
	@JsonSerialize(using = MoneySerializer.class)
    private BigDecimal puntoReposicion;
    private String detalle;
    private String ubicacion;
    private SucursalesDTO sucursal;

    public StockSucursalNanoDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(BigDecimal stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public BigDecimal getPuntoReposicion() {
        return puntoReposicion;
    }

    public void setPuntoReposicion(BigDecimal puntoReposicion) {
        this.puntoReposicion = puntoReposicion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public SucursalesDTO getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalesDTO sucursal) {
        this.sucursal = sucursal;
    }

}
