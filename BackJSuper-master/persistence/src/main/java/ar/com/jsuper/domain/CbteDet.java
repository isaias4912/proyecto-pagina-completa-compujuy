/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author rafa22
 */
@Entity
@Table(name = "cbte_det")
public class CbteDet implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	@Column(name = "precio")
	private BigDecimal precio;
	@Column(name = "precio_costo")
	private BigDecimal precioCosto;
	@Column(name = "descuento")
	private BigDecimal descuento;
	@Column(name = "porcentaje_descuento")
	private BigDecimal porcentajeDescuento;
	@Column(name = "adicional")
	private BigDecimal adicional;
	@Column(name = "porcentaje_adicional")
	private BigDecimal porcentajeAdicional;
	@Column(name = "codigoBarra")
	private String codigoBarra;
	@Column(name = "oferta")
	private Boolean oferta;
	@Column(name = "oferta_data")
	private String ofertaData;
	@Column(name = "oferta_descuento")
	private BigDecimal ofertaDescuento;
	@Column(name = "precio_sin_iva")
	private BigDecimal precioSinIva;
	@Column(name = "precio_con_iva")
	private BigDecimal precioConIva;
	@Column(name = "iva_id")
	private Integer ivaId;
	@Column(name = "iva_des")
	private String ivaDes;
	@Column(name = "iva_unidad")
	private BigDecimal ivaUnidad;
	@Column(name = "iva_total")
	private BigDecimal ivaTotal;
	@Column(name = "base_imponible")
	private BigDecimal baseImponible;
	@Column(name = "base_imponible_total")
	private BigDecimal baseImponibleTotal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productos_id")
	private Producto producto;
	@ManyToOne
	@JoinColumn(name = "cbte_enc_id", nullable = false)
	private CbteEnc cbteEnc;
	@Column(name = "total")
	private BigDecimal total;
	@Column(name = "tipoPrecioVenta")
	private String tipoPrecioVenta;
	@Column(name = "info_prod_adic")
	private String infoProdAdic;
	@Column(name = "numero_serie")
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public CbteEnc getCbteEnc() {
		return cbteEnc;
	}

	public void setCbteEnc(CbteEnc cbteEnc) {
		this.cbteEnc = cbteEnc;
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

	public BigDecimal getIvaUnidad() {
		return ivaUnidad;
	}

	public void setIvaUnidad(BigDecimal ivaUnidad) {
		this.ivaUnidad = ivaUnidad;
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
