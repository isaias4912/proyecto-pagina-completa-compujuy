package ar.com.jsuper.domain;

import ar.com.jsuper.utils.Comprobante;
import ar.com.jsuper.utils.ComprobanteConverter;
import ar.com.jsuper.utils.Concepto;
import ar.com.jsuper.utils.ConceptoConverter;
import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.DocConverter;
import ar.com.jsuper.utils.Factura;
import ar.com.jsuper.utils.FacturaConverter;
import ar.com.jsuper.utils.TipoEmpresa;
import ar.com.jsuper.utils.TipoEmpresaConverter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Where;

/**
 * Entity implementation class for Entity: mov
 *
 */
@Entity
@Table(name = "cbte_comp_enc")
public class CbteCompEnc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "fecha_carga")
	private Date fechaCarga;
	@Column(name = "fecha_cbte")
	private Date fechaCbte;
	@Column(name = "fecha_venc_cbte")
	private Date fechaVencCbte;
	@Column(name = "numero")
	private String numero;
	@Column(name = "activo")
	private Boolean activo;
	@Column(name = "motivo_anulacion")
	private String motivoAnulacion;
	@Column(name = "tipo")
	private Integer tipo;
	@Convert(converter = ComprobanteConverter.class)
	@Column(name = "tipo_cbte")
	private Comprobante tipoCbte;
	@Convert(converter = FacturaConverter.class)
	@Column(name = "tipo_factura")
	private Factura tipoFactura;
	@Convert(converter = ConceptoConverter.class)
	@Column(name = "concepto")
	private Concepto concepto;
	@Column(name = "cargada")
	private Integer cargada;
	@Column(name = "pagada")
	private Boolean pagada;
	@Column(name = "cae_venc")
	private Date caeVenc;
	@Column(name = "cae")
	private String cae;
	@Column(name = "observacion")
	private String observacion;
	@Column(name = "total_trib")
	private BigDecimal totalTrib;
	@Column(name = "total_iva")
	private BigDecimal totalIVAs;
	@Column(name = "total_base_imp")
	private BigDecimal totalBaseImp;
	@Column(name = "saldo")
	private BigDecimal saldo;
	@Column(name = "total")
	private BigDecimal total;
	@Column(name = "id_proveedor")
	private Integer idProveedor;
	@Column(name = "id_sucursal")
	private Integer idSucursal;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proveedores_id")
	private Proveedores proveedor;
	@Column(name = "nombre_proveedor")
	private String nombreProveedor;
	@Column(name = "direccion_proveedor")
	private String direccionProveedor;
	@Convert(converter = DocConverter.class)
	@Column(name = "tipo_doc_proveedor")
	private Doc tipoDocProveedor;
	@Column(name = "nro_doc_proveedor")
	private String nroDocProveedor;
	@Convert(converter = TipoEmpresaConverter.class)
	@Column(name = "tipo_proveedor")
	private TipoEmpresa tipoProveedor;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursales_id")
	private Sucursales sucursal;
	@OneToMany(mappedBy = "cbte", fetch = FetchType.LAZY)
	@Where(clause = "tipo_cbte = 2")
	private Set<IvasAfip> ivas;
	@OneToMany(mappedBy = "cbte", fetch = FetchType.LAZY)
	@Where(clause = "tipo_cbte = 2")
	private Set<TributosAfip> tributos;
	@OneToMany(mappedBy = "cbteCompEnc", fetch = FetchType.LAZY)
	private Set<CbteCompDet> items;
	@OneToMany(mappedBy = "cbteCompEnc", fetch = FetchType.LAZY)
	private Set<PagoCbteComp> pagos;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id")
	private App app;

	public CbteCompEnc() {
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
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

	public Set<CbteCompDet> getItems() {
		return items;
	}

	public void setItems(Set<CbteCompDet> items) {
		this.items = items;
	}

	public Proveedores getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedores proveedor) {
		this.proveedor = proveedor;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
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

	public Comprobante getTipoCbte() {
		return tipoCbte;
	}

	public void setTipoCbte(Comprobante tipoCbte) {
		this.tipoCbte = tipoCbte;
	}

	public Set<PagoCbteComp> getPagos() {
		return pagos;
	}

	public void setPagos(Set<PagoCbteComp> pagos) {
		this.pagos = pagos;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Sucursales getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursales sucursal) {
		this.sucursal = sucursal;
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

	public BigDecimal getTotalTrib() {
		return totalTrib;
	}

	public void setTotalTrib(BigDecimal totalTrib) {
		this.totalTrib = totalTrib;
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

	public Date getCaeVenc() {
		return caeVenc;
	}

	public void setCaeVenc(Date caeVenc) {
		this.caeVenc = caeVenc;
	}

	public Concepto getConcepto() {
		return concepto;
	}

	public void setConcepto(Concepto concepto) {
		this.concepto = concepto;
	}

	public Factura getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(Factura tipoFactura) {
		this.tipoFactura = tipoFactura;
	}

}
