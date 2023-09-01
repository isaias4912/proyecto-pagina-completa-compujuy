package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "afip_pto_venta")
@IdClass(AfipPtoVentaAppPK.class)
public class AfipPtoVenta implements Serializable {

    @Id
    @Column(name = "nro")
    private int nro;
    @Id
    @Column(name = "app_id")
    private int app;
    @Column(name = "emision_tipo")
    private String emisionTipo;
    @Column(name = "bloqueado")
    private String bloqueado;
    @Column(name = "fecha_baja")
    private Date fechaBaja;

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public String getEmisionTipo() {
        return emisionTipo;
    }

    public void setEmisionTipo(String emisionTipo) {
        this.emisionTipo = emisionTipo;
    }

    public String getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(String bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public int getApp() {
        return app;
    }

    public void setApp(int app) {
        this.app = app;
    }

}
