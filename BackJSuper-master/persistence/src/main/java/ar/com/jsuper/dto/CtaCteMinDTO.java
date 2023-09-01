package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CtaCteMinDTO implements Serializable {

    private Integer id;
    private BigDecimal limite;
    private BigDecimal saldo;
    private BigDecimal margen;
    private String descripcion;
    private Boolean activo;
    private Date fechaAlta;

    public CtaCteMinDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getMargen() {
        return margen;
    }

    public void setMargen(BigDecimal margen) {
        this.margen = margen;
    }

}
