/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.domain.App;
import ar.com.jsuper.utils.*;
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
public class CbtePresupuestoEncVenDTO {
    private Integer id;
    private Comprobante tipoCbte;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaCarga;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal total;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal efectivo; //TODO  revisar este campo, ya no se lo usa al parecer
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal vuelto; //TODO  revisar este campo, ya no se lo usa al parecer
    private Integer idCliente;
    private Boolean estado;
    private App app;
    private UsuariosNanoDTO usuario;
    private List<CbtePresupuestoDetVenSinEncabDTO> detalleVentas;
    private Set<PagoVentasSinEncabDTO> pagosCbte;
    private SimpleObjectDTO sucursal;
    private String nombreCliente;
    private String direccionCliente;
    private Doc tipoDocCliente;
    private String nroDocCliente;
    private TipoCliente tipoCliente;
    private Origen origen;
    private Concepto concepto;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaDesde;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaHasta;
    private String razonSocialEmpresa;
    private String condicionEmpresa;
    private String fechaIniActEmpresa;
    private String ingBrutosEmpresa;
    private String cuitEmpresa;
    private String domComercialEmpresa;
    private String cbteNro;
    private String uuid;
    private Boolean listaPrecio;
    private String listaPrecioData;
    private Boolean pagada;
    /*------------------------*/
    private EstadoCbte estadoCbte;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaVigencia;
    private ClienteMinDTO cliente;
    private String observacion;

    public EstadoCbte getEstadoCbte() {
        return estadoCbte;
    }

    public void setEstadoCbte(EstadoCbte estadoCbte) {
        this.estadoCbte = estadoCbte;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public ClienteMinDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteMinDTO cliente) {
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Comprobante getTipoCbte() {
        return tipoCbte;
    }

    public void setTipoCbte(Comprobante tipoCbte) {
        this.tipoCbte = tipoCbte;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public BigDecimal getVuelto() {
        return vuelto;
    }

    public void setVuelto(BigDecimal vuelto) {
        this.vuelto = vuelto;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public UsuariosNanoDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosNanoDTO usuario) {
        this.usuario = usuario;
    }

    public List<CbtePresupuestoDetVenSinEncabDTO> getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(List<CbtePresupuestoDetVenSinEncabDTO> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }

    public Set<PagoVentasSinEncabDTO> getPagosCbte() {
        return pagosCbte;
    }

    public void setPagosCbte(Set<PagoVentasSinEncabDTO> pagosCbte) {
        this.pagosCbte = pagosCbte;
    }

    public SimpleObjectDTO getSucursal() {
        return sucursal;
    }

    public void setSucursal(SimpleObjectDTO sucursal) {
        this.sucursal = sucursal;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public Doc getTipoDocCliente() {
        return tipoDocCliente;
    }

    public void setTipoDocCliente(Doc tipoDocCliente) {
        this.tipoDocCliente = tipoDocCliente;
    }

    public String getNroDocCliente() {
        return nroDocCliente;
    }

    public void setNroDocCliente(String nroDocCliente) {
        this.nroDocCliente = nroDocCliente;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Origen getOrigen() {
        return origen;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

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

    public String getRazonSocialEmpresa() {
        return razonSocialEmpresa;
    }

    public void setRazonSocialEmpresa(String razonSocialEmpresa) {
        this.razonSocialEmpresa = razonSocialEmpresa;
    }

    public String getCondicionEmpresa() {
        return condicionEmpresa;
    }

    public void setCondicionEmpresa(String condicionEmpresa) {
        this.condicionEmpresa = condicionEmpresa;
    }

    public String getFechaIniActEmpresa() {
        return fechaIniActEmpresa;
    }

    public void setFechaIniActEmpresa(String fechaIniActEmpresa) {
        this.fechaIniActEmpresa = fechaIniActEmpresa;
    }

    public String getIngBrutosEmpresa() {
        return ingBrutosEmpresa;
    }

    public void setIngBrutosEmpresa(String ingBrutosEmpresa) {
        this.ingBrutosEmpresa = ingBrutosEmpresa;
    }

    public String getCuitEmpresa() {
        return cuitEmpresa;
    }

    public void setCuitEmpresa(String cuitEmpresa) {
        this.cuitEmpresa = cuitEmpresa;
    }

    public String getDomComercialEmpresa() {
        return domComercialEmpresa;
    }

    public void setDomComercialEmpresa(String domComercialEmpresa) {
        this.domComercialEmpresa = domComercialEmpresa;
    }

    public String getCbteNro() {
        return cbteNro;
    }

    public void setCbteNro(String cbteNro) {
        this.cbteNro = cbteNro;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getListaPrecio() {
        return listaPrecio;
    }

    public void setListaPrecio(Boolean listaPrecio) {
        this.listaPrecio = listaPrecio;
    }

    public String getListaPrecioData() {
        return listaPrecioData;
    }

    public void setListaPrecioData(String listaPrecioData) {
        this.listaPrecioData = listaPrecioData;
    }

    public Boolean getPagada() {
        return pagada;
    }

    public void setPagada(Boolean pagada) {
        this.pagada = pagada;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
