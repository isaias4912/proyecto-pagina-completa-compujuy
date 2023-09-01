package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
public class CbteCompImpuestoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private BigDecimal importe;

    private BigDecimal porcentaje;

    private String nombre;

    private ImpuestoDTO impuesto;

    public CbteCompImpuestoDTO() {
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

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ImpuestoDTO getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(ImpuestoDTO impuesto) {
        this.impuesto = impuesto;
    }

}
