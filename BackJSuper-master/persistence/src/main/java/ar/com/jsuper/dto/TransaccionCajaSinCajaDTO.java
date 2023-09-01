/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rafael
 */
public class TransaccionCajaSinCajaDTO implements Serializable {

    private int id;
    private Integer estado;
    private String detalle;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date fechaApertura;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date fechaCierre;

    @JsonManagedReference
    private List<DetalleTransaccionCajaDTO> detallesTransaccionCaja;

    public TransaccionCajaSinCajaDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public List<DetalleTransaccionCajaDTO> getDetallesTransaccionCaja() {
        return detallesTransaccionCaja;
    }

    public void setDetallesTransaccionCaja(List<DetalleTransaccionCajaDTO> detallesTransaccionCaja) {
        this.detallesTransaccionCaja = detallesTransaccionCaja;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

}
