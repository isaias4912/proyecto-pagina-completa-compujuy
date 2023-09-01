/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

/**
 *
 * @author rafa22
 */
public class CbtePresupuestoDetVenSinEncabDTO {

	private Integer id;
	private String descripcion;
	private BigDecimal cantidad;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal precio;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal precioCosto;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal descuento;
	private BigDecimal porcentajeDescuento;
	private BigDecimal porcentajeAdicional;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal ofertaDescuento;
	private Boolean oferta;
	private String ofertaData;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal adicional;
	private String codigoBarra;
	private ProductoListDTO producto;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal total;
	private String tipoPrecioVenta;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal precioSinIva;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal precioConIva;
	private Integer ivaId;
	private String ivaDes;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal importeIva;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal baseImponible;
	private String infoProdAdic;
	private String numeroSerie;
	private BigDecimal ivaUnidad;
	private BigDecimal ivaTotal;
	private BigDecimal baseImponibleTotal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getAdicional() {
		return adicional;
	}

	public void setAdicional(BigDecimal adicional) {
		this.adicional = adicional;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public ProductoListDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoListDTO producto) {
		this.producto = producto;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getTipoPrecioVenta() {
		return tipoPrecioVenta;
	}

	public void setTipoPrecioVenta(String tipoPrecioVenta) {
		this.tipoPrecioVenta = tipoPrecioVenta;
	}

	public Boolean getOferta() {
		return oferta;
	}

	public void setOferta(Boolean oferta) {
		this.oferta = oferta;
	}

	public String getOfertaData() {
		return ofertaData;
	}

	public void setOfertaData(String ofertaData) {
		this.ofertaData = ofertaData;
	}

	public BigDecimal getPrecioCosto() {
		return precioCosto;
	}

	public void setPrecioCosto(BigDecimal precioCosto) {
		this.precioCosto = precioCosto;
	}

	public BigDecimal getPrecioSinIva() {
		return precioSinIva;
	}

	public void setPrecioSinIva(BigDecimal precioSinIva) {
		this.precioSinIva = precioSinIva;
	}

	public BigDecimal getPrecioConIva() {
		return precioConIva;
	}

	public void setPrecioConIva(BigDecimal precioConIva) {
		this.precioConIva = precioConIva;
	}

	public Integer getIvaId() {
		return ivaId;
	}

	public void setIvaId(Integer ivaId) {
		this.ivaId = ivaId;
	}

	public String getIvaDes() {
		return ivaDes;
	}

	public void setIvaDes(String ivaDes) {
		this.ivaDes = ivaDes;
	}

	public BigDecimal getImporteIva() {
		return importeIva;
	}

	public void setImporteIva(BigDecimal importeIva) {
		this.importeIva = importeIva;
	}

	public BigDecimal getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public String getInfoProdAdic() {
		return infoProdAdic;
	}

	public void setInfoProdAdic(String infoProdAdic) {
		this.infoProdAdic = infoProdAdic;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public BigDecimal getOfertaDescuento() {
		return ofertaDescuento;
	}

	public void setOfertaDescuento(BigDecimal ofertaDescuento) {
		this.ofertaDescuento = ofertaDescuento;
	}

	public BigDecimal getIvaUnidad() {
		return ivaUnidad;
	}

	public void setIvaUnidad(BigDecimal ivaUnidad) {
		this.ivaUnidad = ivaUnidad;
	}

	public BigDecimal getIvaTotal() {
		return ivaTotal;
	}

	public void setIvaTotal(BigDecimal ivaTotal) {
		this.ivaTotal = ivaTotal;
	}

	public BigDecimal getBaseImponibleTotal() {
		return baseImponibleTotal;
	}

	public void setBaseImponibleTotal(BigDecimal baseImponibleTotal) {
		this.baseImponibleTotal = baseImponibleTotal;
	}

	public BigDecimal getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public BigDecimal getPorcentajeAdicional() {
		return porcentajeAdicional;
	}

	public void setPorcentajeAdicional(BigDecimal porcentajeAdicional) {
		this.porcentajeAdicional = porcentajeAdicional;
	}

}
