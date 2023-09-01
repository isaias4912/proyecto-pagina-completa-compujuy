package ar.com.jsuper.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.math.BigDecimal;
import java.io.Serializable;

/**
 *
 * @author cilo22
 */
public class PrecioProductosDTO  extends  ObjectDTO  implements Serializable {

    private Integer id;
    private boolean activo;
    private BigDecimal precio;
    private TipoPreciosDTO tipoPrecio;

    public PrecioProductosDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

   
    public TipoPreciosDTO getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(TipoPreciosDTO tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
    }

}
