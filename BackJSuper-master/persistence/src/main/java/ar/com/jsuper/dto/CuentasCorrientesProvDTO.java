package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CuentasCorrientesProvDTO implements Serializable {

    private int id;
    private BigDecimal limite;
    private String descripcion;
    private Boolean activo;
    private Date fechaAlta;
    private ProveedoresMinDTO proveedor;

    public CuentasCorrientesProvDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public ProveedoresMinDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedoresMinDTO proveedor) {
        this.proveedor = proveedor;
    }

}
