package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cilo22
 */
public class UbicacionStockDTO {

    private int id;
    private BigDecimal cantidad;
//    @JsonBackReference
    @JsonIgnoreProperties("ubicacionStocks")
    private UbicacionDTO ubicacion;
    @JsonIgnoreProperties("ubicacionStocks")
    private StockSucursalDTO stockSucursal;

    public UbicacionStockDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UbicacionDTO getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionDTO ubicacion) {
        this.ubicacion = ubicacion;
    }

    public StockSucursalDTO getStockSucursal() {
        return stockSucursal;
    }

    public void setStockSucursal(StockSucursalDTO stockSucursal) {
        this.stockSucursal = stockSucursal;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }



}
