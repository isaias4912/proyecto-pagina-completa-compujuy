package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cuentas_corrientes_prov")
public class CuentasCorrientesProv implements Serializable {

    @Id
    private int id;
    @Column(name = "limite")
    private BigDecimal limite;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "fecha_alta")
    private Date fechaAlta;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedores_id")
    @MapsId
    private Proveedores proveedor;

    public CuentasCorrientesProv() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
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

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Proveedores getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedores proveedor) {
        this.proveedor = proveedor;
    }

}
