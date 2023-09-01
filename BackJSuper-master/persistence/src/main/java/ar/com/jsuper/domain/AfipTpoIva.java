package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "afip_tpo_iva")
public class AfipTpoIva implements Serializable {

    @Id
    @Column(name = "id")
    private int id;
    @Id
    @Column(name = "app_id")
    private int app;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_desde")
    private Date fechaDesde;
    @Column(name = "fecha_hasta")
    private Date fechaHasta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public int getApp() {
        return app;
    }

    public void setApp(int app) {
        this.app = app;
    }

}
