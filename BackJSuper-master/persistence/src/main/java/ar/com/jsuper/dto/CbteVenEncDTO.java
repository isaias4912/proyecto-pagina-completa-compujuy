/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.Comprobante;
import ar.com.jsuper.utils.Concepto;
import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.Factura;
import ar.com.jsuper.utils.MoneySerializer;
import ar.com.jsuper.utils.Origen;
import ar.com.jsuper.utils.TipoCliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author rafa22
 */
public class CbteVenEncDTO extends CbteEncDTO {


    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaVenta;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaCbte;
    private Concepto concepto;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaDesde;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaHasta;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaVenPag;
    private String ptoVenta;
    private String uuid;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal totalIva;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal totalBaseImp;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal totalTrib;
    private Factura tipoFactura;
    private String cae;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date caeVenc;
    private String afipModo;
    private Boolean afipValida;
    private String afipError;
    private String afipCondicionCli;
    private SimpleObjectDTO transaccionCaja;
    private Set<EventosVentasDTO> eventosVenta;
    private Set<AfipIVADTO> ivas;
    private Set<AfipTributoDTO> tributos;
    private String observacion;

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

    @Override
    public Concepto getConcepto() {
        return concepto;
    }

    @Override
    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    @Override
    public Date getFechaDesde() {
        return fechaDesde;
    }

    @Override
    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    @Override
    public Date getFechaHasta() {
        return fechaHasta;
    }

    @Override
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

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
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

    public Boolean getAfipValida() {
        return afipValida;
    }

    public void setAfipValida(Boolean afipValida) {
        this.afipValida = afipValida;
    }

    public String getAfipError() {
        return afipError;
    }

    public void setAfipError(String afipError) {
        this.afipError = afipError;
    }

    public SimpleObjectDTO getTransaccionCaja() {
        return transaccionCaja;
    }

    public void setTransaccionCaja(SimpleObjectDTO transaccionCaja) {
        this.transaccionCaja = transaccionCaja;
    }

    public Set<EventosVentasDTO> getEventosVenta() {
        return eventosVenta;
    }

    public void setEventosVenta(Set<EventosVentasDTO> eventosVenta) {
        this.eventosVenta = eventosVenta;
    }

    public Set<AfipIVADTO> getIvas() {
        return ivas;
    }

    public void setIvas(Set<AfipIVADTO> ivas) {
        this.ivas = ivas;
    }

    public Set<AfipTributoDTO> getTributos() {
        return tributos;
    }

    public void setTributos(Set<AfipTributoDTO> tributos) {
        this.tributos = tributos;
    }

    public String getAfipCondicionCli() {
        return afipCondicionCli;
    }

    public void setAfipCondicionCli(String afipCondicionCli) {
        this.afipCondicionCli = afipCondicionCli;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
