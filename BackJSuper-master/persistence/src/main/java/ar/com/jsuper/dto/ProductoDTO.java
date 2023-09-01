package ar.com.jsuper.dto;

import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 *
 * @author RAFAEL
 */
public class ProductoDTO extends ObjectDTO implements Serializable, IProductoPrecio {

	private int id;
	private String nombre;
	private String nombreFinal;
	private BigDecimal contenidoNeto;
	private String detalle;
	private String codigoBarra;
	private String codigoEspecial;
	private BigDecimal iva;
	private Integer idIva;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal precioCosto;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal precioVenta;
	private boolean activo;
	private String tags;
	private Boolean pesable;
	private Boolean ingresoCantidadManual;
	private UnidadDTO unidad;
	private Integer tipo;
	private Set<ImagenProductoDTO> imagenProducto;
	private Set<CodigoBarrasDTO> codigoBarras;
	private Set<ProveedoresMinDTO> proveedores;
	private List<ListaPreciosDTO> precios;
	private Set<StockSucursalNanoDTO> stockSucursales;
	@JsonIgnoreProperties("productoDTO")
	private ProductoPadreSinProductoDTO productoPadre;
	private Set<ProductosCompuestosDTO> productosCompuestos;

	public ProductoDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public UnidadDTO getUnidad() {
		return unidad;
	}

	public void setUnidad(UnidadDTO unidad) {
		this.unidad = unidad;
	}

	public Set<CodigoBarrasDTO> getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(Set<CodigoBarrasDTO> codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public List<ListaPreciosDTO> getPrecios() {
		return precios;
	}

	public void setPrecios(List<ListaPreciosDTO> precios) {
		this.precios = precios;
	}

	public Set<StockSucursalNanoDTO> getStockSucursales() {
		return stockSucursales;
	}

	public void setStockSucursales(Set<StockSucursalNanoDTO> stockSucursales) {
		this.stockSucursales = stockSucursales;
	}

	public Set<ImagenProductoDTO> getImagenProducto() {
		return imagenProducto;
	}

	public void setImagenProducto(Set<ImagenProductoDTO> imagenProducto) {
		this.imagenProducto = imagenProducto;
	}

	public ProductoPadreSinProductoDTO getProductoPadre() {
		return productoPadre;
	}

	public void setProductoPadre(ProductoPadreSinProductoDTO productoPadre) {
		this.productoPadre = productoPadre;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Set<ProductosCompuestosDTO> getProductosCompuestos() {
		return productosCompuestos;
	}

	public void setProductosCompuestos(Set<ProductosCompuestosDTO> productosCompuestos) {
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

	public Set<ProveedoresMinDTO> getProveedores() {
		return proveedores;
	}

	public void setProveedores(Set<ProveedoresMinDTO> proveedores) {
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

	public Integer getIdIva() {
		return idIva;
	}

	public void setIdIva(Integer idIva) {
		this.idIva = idIva;
	}

}
