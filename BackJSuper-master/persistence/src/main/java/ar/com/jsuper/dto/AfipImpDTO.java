package ar.com.jsuper.dto;

import java.io.Serializable;

public class AfipImpDTO implements Serializable {

    private Integer id;
    private String descripcion;
    private Double alicuota;
    private Double importe;
    private Double baseImponible;

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

    public Double getAlicuota() {
        return alicuota;
    }

    public void setAlicuota(Double alicuota) {
        this.alicuota = alicuota;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(Double baseImponible) {
        this.baseImponible = baseImponible;
    }

}
