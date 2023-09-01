/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import ar.com.jsuper.utils.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.Where;

/**
 *
 * @author rafa22
 */
@Entity
@DiscriminatorValue(value="1")
public class CbteEncVenta extends CbteEnc implements Serializable {


    @Column(name = "fecha_venta")
    private Date fechaVenta;
    @Column(name = "fecha_cbte")
    private Date fechaCbte;
    @Convert(converter = ConceptoConverter.class)
    @Column(name = "concepto")
    private Concepto concepto;
    @Column(name = "fecha_desde")
    private Date fechaDesde;
    @Column(name = "fecha_hasta")
    private Date fechaHasta;
    @Column(name = "fecha_ven_pag")
    private Date fechaVenPag;
    @Column(name = "pto_venta")
    private String ptoVenta;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "total_iva")
    private BigDecimal totalIva;
    @Column(name = "total_base_imp")
    private BigDecimal totalBaseImp;
    @Column(name = "total_trib")
    private BigDecimal totalTrib;
    @Convert(converter = FacturaConverter.class)
    @Column(name = "tipo_factura")
    private Factura tipoFactura;
    @Column(name = "cae")
    private String cae;
    @Column(name = "cae_venc")
    private Date caeVenc;
    @Column(name = "afip_modo")
    private String afipModo;
    @Column(name = "afip_error")
    private String afipError;
    @Column(name = "afip_valida")
    private Boolean afipValida;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaccion_caja_id")
    private TransaccionCaja transaccionCaja;
    @OneToMany(mappedBy = "cbteEncVenta", fetch = FetchType.LAZY)
    private Set<EventosVentas> eventosVenta;
    @OneToMany(mappedBy = "cbte", fetch = FetchType.LAZY)
    @Where(clause = "tipo_cbte = 1")
    private Set<IvasAfip> ivas;
    @OneToMany(mappedBy = "cbte", fetch = FetchType.LAZY)
    @Where(clause = "tipo_cbte = 1")
    private Set<TributosAfip> tributos;

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
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

    public Date getFechaVenPag() {
        return fechaVenPag;
    }

    public void setFechaVenPag(Date fechaVenPag) {
        this.fechaVenPag = fechaVenPag;
    }

    public String getPtoVenta() {
        return ptoVenta;
    }

    public void setPtoVenta(String ptoVenta) {
        this.ptoVenta = ptoVenta;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(BigDecimal totalIva) {
        this.totalIva = totalIva;
    }

    public BigDecimal getTotalBaseImp() {
        return totalBaseImp;
    }

    public void setTotalBaseImp(BigDecimal totalBaseImp) {
        this.totalBaseImp = totalBaseImp;
    }

    public BigDecimal getTotalTrib() {
        return totalTrib;
    }

    public void setTotalTrib(BigDecimal totalTrib) {
        this.totalTrib = totalTrib;
    }

    public Factura getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(Factura tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public String getCae() {
        return cae;
    }

    public void setCae(String cae) {
        this.cae = cae;
    }

    public Date getCaeVenc() {
        return caeVenc;
    }

    public void setCaeVenc(Date caeVenc) {
        this.caeVenc = caeVenc;
    }

    public String getAfipModo() {
        return afipModo;
    }

    public void setAfipModo(String afipModo) {
        this.afipModo = afipModo;
    }

    public String getAfipError() {
        return afipError;
    }

    public void setAfipError(String afipError) {
        this.afipError = afipError;
    }

    public Boolean getAfipValida() {
        return afipValida;
    }

    public void setAfipValida(Boolean afipValida) {
        this.afipValida = afipValida;
    }

    public TransaccionCaja getTransaccionCaja() {
        return transaccionCaja;
    }

    public void setTransaccionCaja(TransaccionCaja transaccionCaja) {
        this.transaccionCaja = transaccionCaja;
    }

    public Set<EventosVentas> getEventosVenta() {
        return eventosVenta;
    }

    public void setEventosVenta(Set<EventosVentas> eventosVenta) {
        this.eventosVenta = eventosVenta;
    }

    public Set<IvasAfip> getIvas() {
        return ivas;
    }

    public void setIvas(Set<IvasAfip> ivas) {
        this.ivas = ivas;
    }

    public Set<TributosAfip> getTributos() {
        return tributos;
    }

    public void setTributos(Set<TributosAfip> tributos) {
        this.tributos = tributos;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getFechaCbte() {
        return fechaCbte;
    }

    public void setFechaCbte(Date fechaCbte) {
        this.fechaCbte = fechaCbte;
    }
}
