/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Table(name = "transaccion_caja")
public class TransaccionCaja implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "fecha_apertura")
    private Date fechaApertura;
    @Column(name = "fecha_cierre")
    private Date fechaCierre;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caja_id")
    private Caja caja;
    @OneToMany(mappedBy = "transaccionCaja", fetch = FetchType.LAZY)
    private List<DetalleTransaccionCaja> detallesTransaccionCaja;

    public TransaccionCaja() {
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

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public List<DetalleTransaccionCaja> getDetallesTransaccionCaja() {
        return detallesTransaccionCaja;
    }

    public void setDetallesTransaccionCaja(List<DetalleTransaccionCaja> detallesTransaccionCaja) {
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
