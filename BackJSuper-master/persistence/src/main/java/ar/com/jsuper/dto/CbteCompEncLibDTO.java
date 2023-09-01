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
import ar.com.jsuper.utils.TipoEmpresa;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author rafa
 */
public class CbteCompEncLibDTO {

    private Integer id;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaCarga;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaCbte;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date fechaVencCbte;
    private String numero;
    private Boolean activo;
    private String motivoAnulacion;
    private Integer tipo;
    private Comprobante tipoCbte;
    private Factura tipoFactura;
    private Concepto concepto;
    private Integer cargada;
    private Boolean pagada;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
    private Date caeVenc;
    private String cae;
    private String observacion;
    private BigDecimal totalTribu;
    private BigDecimal totalIVAs;
    private BigDecimal totalBaseImp;
    private BigDecimal saldo;
    private BigDecimal total;
    private Integer idProveedor;
    private Integer idSucursal;
    private SingleObjectDTO proveedor;
    private String nombreProveedor;
    private String direccionProveedor;
    private Doc tipoDocProveedor;
    private String nroDocProveedor;
    private TipoEmpresa tipoProveedor;
    private SingleObjectDTO sucursal;
    private Set<AfipIVADTO> ivas;
    private Set<AfipTributoDTO> tributos;

    public CbteCompEncLibDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Date getFechaCbte() {
        return fechaCbte;
    }

    public void setFechaCbte(Date fechaCbte) {
        this.fechaCbte = fechaCbte;
    }

    public Date getFechaVencCbte() {
        return fechaVencCbte;
    }

    public void setFechaVencCbte(Date fechaVencCbte) {
        this.fechaVencCbte = fechaVencCbte;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getMotivoAnulacion() {
        return motivoAnulacion;
    }

    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Comprobante getTipoCbte() {
        return tipoCbte;
    }

    public void setTipoCbte(Comprobante tipoCbte) {
        this.tipoCbte = tipoCbte;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public Integer getCargada() {
        return cargada;
    }

    public void setCargada(Integer cargada) {
        this.cargada = cargada;
    }

    public Boolean getPagada() {
        return pagada;
    }

    public void setPagada(Boolean pagada) {
        this.pagada = pagada;
    }

    public Date getCaeVenc() {
        return caeVenc;
    }

    public void setCaeVenc(Date caeVenc) {
        this.caeVenc = caeVenc;
    }

    public String getCae() {
        return cae;
    }

    public void setCae(String cae) {
        this.cae = cae;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public BigDecimal getTotalTribu() {
        return totalTribu;
    }

    public void setTotalTribu(BigDecimal totalTribu) {
        this.totalTribu = totalTribu;
    }

    public BigDecimal getTotalIVAs() {
        return totalIVAs;
    }

    public void setTotalIVAs(BigDecimal totalIVAs) {
        this.totalIVAs = totalIVAs;
    }

    public BigDecimal getTotalBaseImp() {
        return totalBaseImp;
    }

    public void setTotalBaseImp(BigDecimal totalBaseImp) {
        this.totalBaseImp = totalBaseImp;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public SingleObjectDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(SingleObjectDTO proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public Doc getTipoDocProveedor() {
        return tipoDocProveedor;
    }

    public void setTipoDocProveedor(Doc tipoDocProveedor) {
        this.tipoDocProveedor = tipoDocProveedor;
    }

    public String getNroDocProveedor() {
        return nroDocProveedor;
    }

    public void setNroDocProveedor(String nroDocProveedor) {
        this.nroDocProveedor = nroDocProveedor;
    }

    public TipoEmpresa getTipoProveedor() {
        return tipoProveedor;
    }

    public void setTipoProveedor(TipoEmpresa tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }

    public SingleObjectDTO getSucursal() {
        return sucursal;
    }

    public void setSucursal(SingleObjectDTO sucursal) {
        this.sucursal = sucursal;
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

    public Factura getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(Factura tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

}
