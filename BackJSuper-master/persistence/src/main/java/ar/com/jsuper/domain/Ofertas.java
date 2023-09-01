/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "ofertas")
public class Ofertas {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha_desde")
    private Date fechaDesde;
    @Column(name = "fecha_hasta")
    private Date fechaHasta;
    @Column(name = "categoria_oferta")
    private String categoriaOferta;
    @Column(name = "tipo_oferta")
    private String tipoOferta;
    @Column(name = "tipo_oferta_tipo")
    private String tipoOfertaTipo;
    @Column(name = "tipo_descuento")
    private String tipoDescuento;
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "prioridad")
    private Integer prioridad;
    @Column(name = "cantidad")
    private BigDecimal cantidad;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(mappedBy = "oferta", fetch = FetchType.LAZY)
    private Set<OfertasProducto> ofertaProductos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id")
    private App app;

    public Ofertas() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getCategoriaOferta() {
        return categoriaOferta;
    }

    public void setCategoriaOferta(String categoriaOferta) {
        this.categoriaOferta = categoriaOferta;
    }

    public String getTipoOferta() {
        return tipoOferta;
    }

    public void setTipoOferta(String tipoOferta) {
        this.tipoOferta = tipoOferta;
    }

    public String getTipoOfertaTipo() {
        return tipoOfertaTipo;
    }

    public void setTipoOfertaTipo(String tipoOfertaTipo) {
        this.tipoOfertaTipo = tipoOfertaTipo;
    }

    public String getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(String tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Set<OfertasProducto> getOfertaProductos() {
        return ofertaProductos;
    }

    public void setOfertaProductos(Set<OfertasProducto> ofertaProductos) {
        this.ofertaProductos = ofertaProductos;
    }

}
