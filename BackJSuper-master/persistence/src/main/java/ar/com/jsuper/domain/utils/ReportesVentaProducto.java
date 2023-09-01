/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.dto.ProductoMinDTO;
import java.math.BigDecimal;

/**
 *
 * @author rafael
 */
public class ReportesVentaProducto {

    private BigDecimal valor;
    private Producto producto;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
