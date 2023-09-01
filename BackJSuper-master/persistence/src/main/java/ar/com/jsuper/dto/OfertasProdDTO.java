/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author rafael
 */
public class OfertasProdDTO {

    private int id;
    private String nombre;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date fechaDesde;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date fechaHasta;
    private String categoriaOferta;
    private String tipoOferta;
    private String tipoOfertaTipo;
    private String tipoDescuento;
    private String detalle;
    private BigDecimal valor;
    private Integer prioridad;
    private BigDecimal cantidad;
    private Boolean activo;
    private Set<OfertasProductoSinOfertaDTO> ofertaProductos;

    public OfertasProdDTO() {
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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
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

    public Set<OfertasProductoSinOfertaDTO> getOfertaProductos() {
        return ofertaProductos;
    }

    public void setOfertaProductos(Set<OfertasProductoSinOfertaDTO> ofertaProductos) {
        this.ofertaProductos = ofertaProductos;
    }

}
