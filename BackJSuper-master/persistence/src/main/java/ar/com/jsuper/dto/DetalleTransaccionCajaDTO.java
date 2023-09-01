/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author rafael
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalleTransaccionCajaDTO implements Serializable {

    private int id;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date fecha;
    private Integer tipo;
    private Integer subtipo;
    private BigDecimal monto;
    private String observacion;
    private String motivo;
    private String nombreUsuarioAuth;
    private String idUsuarioAuth;
    private String dniUsuarioAuth;
    @JsonBackReference
    private TransaccionCajaDTO transaccionCaja;
    private SimpleObjectDTO cbteVenEnc;
    private Boolean asociada;
    private Integer asociadaTipo;
    private Integer asociadaId;
    private UsuariosMinDTO usuario;

    public DetalleTransaccionCajaDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public TransaccionCajaDTO getTransaccionCaja() {
        return transaccionCaja;
    }

    public void setTransaccionCaja(TransaccionCajaDTO transaccionCaja) {
        this.transaccionCaja = transaccionCaja;
    }

    public SimpleObjectDTO getCbteVenEnc() {
        return cbteVenEnc;
    }

    public void setCbteVenEnc(SimpleObjectDTO cbteVenEnc) {
        this.cbteVenEnc = cbteVenEnc;
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

    public String getDniUsuarioAuth() {
        return dniUsuarioAuth;
    }

    public void setDniUsuarioAuth(String dniUsuarioAuth) {
        this.dniUsuarioAuth = dniUsuarioAuth;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(Integer subtipo) {
        this.subtipo = subtipo;
    }

    public Boolean getAsociada() {
        return asociada;
    }

    public void setAsociada(Boolean asociada) {
        this.asociada = asociada;
    }

    public Integer getAsociadaTipo() {
        return asociadaTipo;
    }

    public void setAsociadaTipo(Integer asociadaTipo) {
        this.asociadaTipo = asociadaTipo;
    }

    public Integer getAsociadaId() {
        return asociadaId;
    }

    public void setAsociadaId(Integer asociadaId) {
        this.asociadaId = asociadaId;
    }

    public UsuariosMinDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosMinDTO usuario) {
        this.usuario = usuario;
    }

}
