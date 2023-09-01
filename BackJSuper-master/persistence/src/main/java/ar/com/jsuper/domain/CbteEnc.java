/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import ar.com.jsuper.utils.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author rafa22
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table(name = "cbte_enc")
@DiscriminatorColumn(
        name = "tipo",
        discriminatorType = DiscriminatorType.INTEGER
)
public class CbteEnc implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Convert(converter = ComprobanteConverter.class)
    @Column(name = "tipo_cbte")
    private Comprobante tipoCbte;
    @Column(name = "fecha_carga")
    private Date fechaCarga;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "saldo")
    private BigDecimal saldo;
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "pagada")
    private Boolean pagada;
    @Convert(converter = OrigenConverter.class)
    @Column(name = "origen")
    private Origen origen;
    @Convert(converter = ConceptoConverter.class)
    @Column(name = "concepto")
    private Concepto concepto;
    @Column(name = "fecha_desde")
    private Date fechaDesde;
    @Column(name = "fecha_hasta")
    private Date fechaHasta;
//    @Column(name = "fecha_ven_pag")
//    private Date fechaVenPag;
//    @Column(name = "pto_venta")
//    private String ptoVenta;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "razon_social_empresa")
    private String razonSocialEmpresa;
    @Column(name = "fecha_ini_act_empresa")
    private String fechaIniActEmpresa;
    @Column(name = "ing_brutos_empresa")
    private String ingBrutosEmpresa;
    @Column(name = "cuit_empresa")
    private String cuitEmpresa;
    @Column(name = "dom_comercial_empresa")
    private String domComercialEmpresa;
    @Column(name = "condicion_empresa")
    private String condicionEmpresa;
    @Column(name = "cbte_nro")
    private String cbteNro;
    @Column(name = "lista_precio")
    private Boolean listaPrecio;
    @Column(name = "lista_precio_data")
    private String listaPrecioData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientes_id")
    private Cliente cliente;
    @OneToMany(mappedBy = "cbteEnc", fetch = FetchType.LAZY)
    private Set<CbteDet> detalleVentas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursales_id")
    private Sucursales sucursal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id")
    private Usuarios usuario;
    @Column(name = "nombre_cliente")
    private String nombreCliente;
    @Column(name = "direccion_cliente")
    private String direccionCliente;
    @Convert(converter = DocConverter.class)
    @Column(name = "tipo_doc_cliente")
    private Doc tipoDocCliente;
    @Column(name = "nro_doc_cliente")
    private String nroDocCliente;
    @Convert(converter = TipoClienteConverter.class)
    @Column(name = "tipo_cliente")
    private TipoCliente tipoCliente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id")
    private App app;
    @OneToMany(mappedBy = "cbteEnc", fetch = FetchType.LAZY)
    private Set<PagoCbteVen> pagosCbte;
    @Column(name = "observacion")
    private String observacion;

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

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
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

    public Boolean getPagada() {
        return pagada;
    }

    public void setPagada(Boolean pagada) {
        this.pagada = pagada;
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

//    public Date getFechaVenPag() {
//        return fechaVenPag;
//    }
//
//    public void setFechaVenPag(Date fechaVenPag) {
//        this.fechaVenPag = fechaVenPag;
//    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRazonSocialEmpresa() {
        return razonSocialEmpresa;
    }

    public void setRazonSocialEmpresa(String razonSocialEmpresa) {
        this.razonSocialEmpresa = razonSocialEmpresa;
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

    public String getCondicionEmpresa() {
        return condicionEmpresa;
    }

    public void setCondicionEmpresa(String condicionEmpresa) {
        this.condicionEmpresa = condicionEmpresa;
    }

    public String getCbteNro() {
        return cbteNro;
    }

    public void setCbteNro(String cbteNro) {
        this.cbteNro = cbteNro;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<CbteDet> getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(Set<CbteDet> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }

    public Sucursales getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursales sucursal) {
        this.sucursal = sucursal;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
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

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Set<PagoCbteVen> getPagosCbte() {
        return pagosCbte;
    }

    public void setPagosCbte(Set<PagoCbteVen> pagosCbte) {
        this.pagosCbte = pagosCbte;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
