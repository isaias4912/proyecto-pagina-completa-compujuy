/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author rafa22
 */
public class EventosVentasDTO implements Serializable {

    private Integer id;
    private String nombre;
    private Integer tipo;
    private String nombreUsuarioAuth;
    private String idUsuarioAuth;
    private String detalle;
    private Date fecha;
    private BigDecimal monto;
    
    private SimpleObjectDTO cbteVenEnc;

    public EventosVentasDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getNombreUsuarioAuth() {
        return nombreUsuarioAuth;
    }

    public void setNombreUsuarioAuth(String nombreUsuarioAuth) {
        this.nombreUsuarioAuth = nombreUsuarioAuth;
    }

    public String getIdUsuarioAuth() {
        return idUsuarioAuth;
    }

    public void setIdUsuarioAuth(String idUsuarioAuth) {
        this.idUsuarioAuth = idUsuarioAuth;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public SimpleObjectDTO getCbteVenEnc() {
        return cbteVenEnc;
    }

    public void setCbteVenEnc(SimpleObjectDTO cbteVenEnc) {
        this.cbteVenEnc = cbteVenEnc;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
}
