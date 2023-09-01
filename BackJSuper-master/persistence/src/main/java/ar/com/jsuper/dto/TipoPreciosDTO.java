package ar.com.jsuper.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael
 */

public class TipoPreciosDTO {

    private int id;
    private String nombre;
    private Date desde;
    private Date hasta;
    private boolean activo;
    private Integer orden;
    private BigDecimal margenContado;
    private BigDecimal margenTarjetaCred;
    private BigDecimal margenTarjetaDeb;
    private BigDecimal margenCtaCorriente;

    public TipoPreciosDTO() {
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

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public BigDecimal getMargenContado() {
        return margenContado;
    }

    public void setMargenContado(BigDecimal margenContado) {
        this.margenContado = margenContado;
    }

    public BigDecimal getMargenTarjetaCred() {
        return margenTarjetaCred;
    }

    public void setMargenTarjetaCred(BigDecimal margenTarjetaCred) {
        this.margenTarjetaCred = margenTarjetaCred;
    }

    public BigDecimal getMargenTarjetaDeb() {
        return margenTarjetaDeb;
    }

    public void setMargenTarjetaDeb(BigDecimal margenTarjetaDeb) {
        this.margenTarjetaDeb = margenTarjetaDeb;
    }

    public BigDecimal getMargenCtaCorriente() {
        return margenCtaCorriente;
    }

    public void setMargenCtaCorriente(BigDecimal margenCtaCorriente) {
        this.margenCtaCorriente = margenCtaCorriente;
    }

}
