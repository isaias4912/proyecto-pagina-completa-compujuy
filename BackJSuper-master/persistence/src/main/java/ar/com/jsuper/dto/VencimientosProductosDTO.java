/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author rafael
 */
public class VencimientosProductosDTO implements Serializable {

    private int id;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaVencimiento;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaCarga;
    private String descripcion;
    private Boolean activo;
    private Boolean valido;
    private Boolean alertaVencimientos;
    private Integer diasAlerta;
    private Integer tipo;
    private String lote;
    private BigDecimal cantidadProductos;
    private ProductoMinDTO producto;
    private CbteCompDetDTO facturaDet;
    private Integer alerta;

    public VencimientosProductosDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
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

    public Boolean getAlertaVencimientos() {
        return alertaVencimientos;
    }

    public void setAlertaVencimientos(Boolean alertaVencimientos) {
        this.alertaVencimientos = alertaVencimientos;
    }

    public Integer getDiasAlerta() {
        return diasAlerta;
    }

    public void setDiasAlerta(Integer diasAlerta) {
        this.diasAlerta = diasAlerta;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public BigDecimal getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(BigDecimal cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public ProductoMinDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMinDTO producto) {
        this.producto = producto;
    }

    public CbteCompDetDTO getFacturaDet() {
        return facturaDet;
    }

    public void setFacturaDet(CbteCompDetDTO facturaDet) {
        this.facturaDet = facturaDet;
    }

    public Boolean getValido() {
        return valido;
    }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }

    public Integer getAlerta() {
        return alerta;
    }

    public void setAlerta(Integer alerta) {
        this.alerta = alerta;
    }

}
