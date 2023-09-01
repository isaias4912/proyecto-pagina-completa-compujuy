package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author RAFAEL
 */
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "nombre_final")
	private String nombreFinal;
	@Column(name = "contenido_neto")
	private BigDecimal contenidoNeto;
	@Column(name = "detalle")
	private String detalle;
	@Column(name = "codigo_barra")
	private String codigoBarra;
	@Column(name = "codigo_especial")
	private String codigoEspecial;
	@Column(name = "iva")
	private BigDecimal iva;
	@Column(name = "iva_id")
	private Integer idIva;
	@Column(name = "precio_costo")
	private BigDecimal precioCosto;
	@Column(name = "precio_venta")
	private BigDecimal precioVenta;
	@Column(name = "activo")
	private boolean activo;
	@Column(name = "tags")
	private String tags;
	@Column(name = "tipo")
	private Integer tipo;
	@Column(name = "pesable")
	private Boolean pesable;
	@Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	@Column(name = "ingreso_cantidad_manual")
	private Boolean ingresoCantidadManual;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidad_id", insertable = true)
	private Unidad unidad;
	@OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
	private Set<ImagenProducto> imagenProducto;
	@OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
	private Set<CodigoBarras> codigoBarras;
//	@OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
//	private Set<PrecioProductos> precios;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "producto_proveedores", joinColumns = {
		@JoinColumn(name = "producto_id", nullable = false, updatable = false, insertable = false)},
			inverseJoinColumns = {
				@JoinColumn(name = "proveedores_id",
						nullable = false, updatable = false, insertable = false)})
	private Set<Proveedores> proveedores;

	@OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
	private Set<StockSucursal> stockSucursales;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_padre_id")
	private ProductoPadre productoPadre;
	@OneToMany(mappedBy = "productoPrincipal", fetch = FetchType.LAZY)
	private Set<ProductosCompuestos> productosCompuestos;

	public Producto() {
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

	public String getNombreFinal() {
		return nombreFinal;
	}

	public void setNombreFinal(String nombreFinal) {
		this.nombreFinal = nombreFinal;
	}

	public BigDecimal getContenidoNeto() {
		return contenidoNeto;
	}

	public void setContenidoNeto(BigDecimal contenidoNeto) {
		this.contenidoNeto = contenidoNeto;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Set<CodigoBarras> getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(Set<CodigoBarras> codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

//	public Set<PrecioProductos> getPrecios() {
//		return precios;
//	}
//
//	public void setPrecios(Set<PrecioProductos> precios) {
//		this.precios = precios;
//	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public Set<StockSucursal> getStockSucursales() {
		return stockSucursales;
	}

	public void setStockSucursales(Set<StockSucursal> stockSucursales) {
		this.stockSucursales = stockSucursales;
	}

	public Set<ImagenProducto> getImagenProducto() {
		return imagenProducto;
	}

	public void setImagenProducto(Set<ImagenProducto> imagenProducto) {
		this.imagenProducto = imagenProducto;
	}

	public ProductoPadre getProductoPadre() {
		return productoPadre;
	}

	public void setProductoPadre(ProductoPadre productoPadre) {
		this.productoPadre = productoPadre;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return nombre;
	}

	public Set<ProductosCompuestos> getProductosCompuestos() {
		return productosCompuestos;
	}

	public void setProductosCompuestos(Set<ProductosCompuestos> productosCompuestos) {
		this.productosCompuestos = productosCompuestos;
	}

	public BigDecimal getPrecioCosto() {
		return precioCosto;
	}

	public void setPrecioCosto(BigDecimal precioCosto) {
		this.precioCosto = precioCosto;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Set<Proveedores> getProveedores() {
		return proveedores;
	}

	public void setProveedores(Set<Proveedores> proveedores) {
		this.proveedores = proveedores;
	}

	public Boolean getPesable() {
		return pesable;
	}

	public void setPesable(Boolean pesable) {
		this.pesable = pesable;
	}

	public Boolean getIngresoCantidadManual() {
		return ingresoCantidadManual;
	}

	public void setIngresoCantidadManual(Boolean ingresoCantidadManual) {
		this.ingresoCantidadManual = ingresoCantidadManual;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getCodigoEspecial() {
		return codigoEspecial;
	}

	public void setCodigoEspecial(String codigoEspecial) {
		this.codigoEspecial = codigoEspecial;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getIdIva() {
		return idIva;
	}

	public void setIdIva(Integer idIva) {
		this.idIva = idIva;
	}

}
