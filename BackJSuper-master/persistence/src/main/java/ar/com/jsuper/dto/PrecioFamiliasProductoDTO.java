package ar.com.jsuper.dto;

import java.util.Set;
import java.io.Serializable;
import java.math.BigDecimal;

public class PrecioFamiliasProductoDTO implements Serializable {

    private Set<FamiliaDTO> familias;
    private Integer tipoModificacion;
    private BigDecimal cantidad;

    public PrecioFamiliasProductoDTO() {
    }

    public Set<FamiliaDTO> getFamilias() {
        return familias;
    }

    public void setFamilias(Set<FamiliaDTO> familias) {
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
