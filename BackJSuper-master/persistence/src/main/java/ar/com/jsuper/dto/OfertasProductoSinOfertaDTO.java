package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OfertasProductoSinOfertaDTO implements Serializable {

    private Integer id;
    private BigDecimal precio;
    private BigDecimal descuento;
    private Boolean estado;
    private ProductoMinDTO producto;

    public OfertasProductoSinOfertaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public ProductoMinDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMinDTO producto) {
        this.producto = producto;
    }

}
