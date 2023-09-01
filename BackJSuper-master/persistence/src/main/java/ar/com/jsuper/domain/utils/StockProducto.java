/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.StockSucursal;
import java.util.Set;

/**
 *
 * @author rafael
 */
public class StockProducto {

    private Producto producto;
    private Set<StockSucursal> sotcksSucursal;
    private Set<StockProducto> stockProductos;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Set<StockSucursal> getSotcksSucursal() {
        return sotcksSucursal;
    }

    public void setSotcksSucursal(Set<StockSucursal> sotcksSucursal) {
        this.sotcksSucursal = sotcksSucursal;
    }

    public Set<StockProducto> getStockProductos() {
        return stockProductos;
    }

    public void setStockProductos(Set<StockProducto> stockProductos) {
        this.stockProductos = stockProductos;
    }

}
