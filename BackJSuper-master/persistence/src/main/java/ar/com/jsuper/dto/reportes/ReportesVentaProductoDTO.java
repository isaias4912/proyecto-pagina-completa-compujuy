/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.reportes;

import ar.com.jsuper.dto.ProductoMinDTO;
import java.math.BigDecimal;

/**
 *
 * @author rafael
 */
public class ReportesVentaProductoDTO {

    private BigDecimal valor;
    private ProductoMinDTO producto;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public ProductoMinDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMinDTO producto) {
        this.producto = producto;
    }

}
