/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.math.BigDecimal;
import java.util.Set;

/**
 *
 * @author rafa
 */
public class ProductoRecursiveDTO {

    private Set<ProductoRecursiveDTO> productos;

    private Integer id;
    private String nombreFinal;
    private BigDecimal cantidad;

    public ProductoRecursiveDTO() {
    }

    public Set<ProductoRecursiveDTO> getProductos() {
        return productos;
    }

    public void setProductos(Set<ProductoRecursiveDTO> productos) {
        this.productos = productos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreFinal() {
        return nombreFinal;
    }

    public void setNombreFinal(String nombreFinal) {
        this.nombreFinal = nombreFinal;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

}
