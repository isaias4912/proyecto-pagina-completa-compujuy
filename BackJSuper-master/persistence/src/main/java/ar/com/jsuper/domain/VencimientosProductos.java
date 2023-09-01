/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "vencimientos_productos")
public class VencimientosProductos implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;
    @Column(name = "fecha_carga")
    private Date fechaCarga;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "alerta_vencimientos")
    private Boolean alertaVencimientos;
    @Column(name = "dias_alerta")
    private Integer diasAlerta;
    @Column(name = "tipo")
    private Integer tipo;
    @Column(name = "lote")
    private String lote;
    @Column(name = "cantidad_productos")
    private BigDecimal cantidadProductos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", updatable = false)
    private Producto producto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cbte_comp_det_id", updatable = false)
    private CbteCompDet facturaDet;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", updatable = false)
    private App app;

    public VencimientosProductos() {
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

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public CbteCompDet getFacturaDet() {
        return facturaDet;
    }

    public void setFacturaDet(CbteCompDet facturaDet) {
        this.facturaDet = facturaDet;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public BigDecimal getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(BigDecimal cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

}
