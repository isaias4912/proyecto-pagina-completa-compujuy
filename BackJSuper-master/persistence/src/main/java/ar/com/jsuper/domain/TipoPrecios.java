package ar.com.jsuper.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author cilo22
 */
@Entity
@Table(name = "tipo_precios")
public class TipoPrecios {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "desde")
    private Date desde;
    @Column(name = "hasta")
    private Date hasta;
    @Column(name = "activo")
    private boolean activo;
    @Column(name = "orden")
    private Integer orden;
    @Column(name = "margenContado")
    private BigDecimal margenContado;
    @Column(name = "margenTarjetaCred")
    private BigDecimal margenTarjetaCred;
    @Column(name = "margenTarjetaDeb")
    private BigDecimal margenTarjetaDeb;
    @Column(name = "margenCtaCorriente")
    private BigDecimal margenCtaCorriente;

    public TipoPrecios() {
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
