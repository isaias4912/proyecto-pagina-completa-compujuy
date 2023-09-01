package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Producto para el punto de venta cuando se busca
 *
 * @author RAFAEL
 */
public class ProductoPVDTO extends ObjectDTO implements Serializable, IProductoPrecio {

	private int id;
	private String nombre;
	private String nombreFinal;
	private String codigoEspecial;
	private BigDecimal contenidoNeto;
	private String detalle;
	private boolean activo;
	private BigDecimal precioCosto;
	private BigDecimal precioVenta;
	private BigDecimal iva;
	private Integer idIva;
	private Boolean pesable;
	private Boolean ingresoCantidadManual;
	private UnidadDTO unidad;
	private Integer tipo;
	private BigDecimal stock;
	private Set<CodigoBarrasMinDTO> codigoBarras;
	private Set<StockSucursalNanoDTO> stockSucursales;
	private List<ListaPreciosDTO> precios;

	public ProductoPVDTO() {
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
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

	public BigDecimal getPrecioCosto() {
		return precioCosto;
	}

	public void setPrecioCosto(BigDecimal precioCosto) {
		this.precioCosto = precioCosto;
	}

	@Override
	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	@Override
	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
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

	public Set<CodigoBarrasMinDTO> getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(Set<CodigoBarrasMinDTO> codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public Set<StockSucursalNanoDTO> getStockSucursales() {
		return stockSucursales;
	}

	public void setStockSucursales(Set<StockSucursalNanoDTO> stockSucursales) {
		this.stockSucursales = stockSucursales;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public String getCodigoEspecial() {
		return codigoEspecial;
	}

	public void setCodigoEspecial(String codigoEspecial) {
		this.codigoEspecial = codigoEspecial;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public Integer getIdIva() {
		return idIva;
	}

	public void setIdIva(Integer idIva) {
		this.idIva = idIva;
	}

	@Override
	public List<ListaPreciosDTO> getPrecios() {
		return precios;
	}

	@Override
	public void setPrecios(List<ListaPreciosDTO> precios) {
		this.precios = precios;
	}

}
