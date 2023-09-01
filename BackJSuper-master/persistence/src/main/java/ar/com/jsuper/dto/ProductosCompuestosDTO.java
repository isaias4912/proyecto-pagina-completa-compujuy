package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entity implementation class for Entity: Rubros
 *
 */
public class ProductosCompuestosDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private BigDecimal cantidad;

    private String descripcion;

    private ProductoMinDTO producto;

    public ProductosCompuestosDTO() {
        super();
    }

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

    public ProductoMinDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMinDTO producto) {
        this.producto = producto;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }


}
