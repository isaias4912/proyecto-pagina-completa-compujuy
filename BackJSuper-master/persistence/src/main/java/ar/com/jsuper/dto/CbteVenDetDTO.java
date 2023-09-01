/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import javax.persistence.Column;

/**
 *
 * @author rafa22
 */
public class CbteVenDetDTO {

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
	private BigDecimal adicional;
	private Boolean oferta;
	private String ofertaData;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal ofertaDescuento;
	private String codigoBarra;
	private ProductoMinDTO producto;
	@JsonBackReference
	private CbteVenEncDTO cbteVenEnc;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal total;
	private String tipoPrecioVenta;
	private String infoProdAdic;
	private String numeroSerie;

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

	public ProductoMinDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoMinDTO producto) {
		this.producto = producto;
	}

	public CbteVenEncDTO getCbteVenEnc() {
		return cbteVenEnc;
	}

	public void setCbteVenEnc(CbteVenEncDTO cbteVenEnc) {
		this.cbteVenEnc = cbteVenEnc;
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
