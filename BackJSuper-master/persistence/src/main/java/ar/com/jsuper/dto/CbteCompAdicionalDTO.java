package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
public class CbteCompAdicionalDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private BigDecimal importe;

    private String nombre;

    public CbteCompAdicionalDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
