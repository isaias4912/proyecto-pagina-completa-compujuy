package ar.com.jsuper.domain.utils;

import ar.com.jsuper.domain.Familias;
import java.util.Set;
import java.io.Serializable;
import java.math.BigDecimal;

public class PrecioFamiliasProducto implements Serializable {

    private Set<Familias> familias;
    private Integer tipoModificacion;
    private BigDecimal cantidad;

    public PrecioFamiliasProducto() {
    }

    public Set<Familias> getFamilias() {
        return familias;
    }

    public void setFamilias(Set<Familias> familias) {
        this.familias = familias;
    }

    public Integer getTipoModificacion() {
        return tipoModificacion;
    }

    public void setTipoModificacion(Integer tipoModificacion) {
        this.tipoModificacion = tipoModificacion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

}
