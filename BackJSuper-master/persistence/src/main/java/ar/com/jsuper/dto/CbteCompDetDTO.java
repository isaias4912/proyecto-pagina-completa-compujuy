package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CbteCompDetDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private BigDecimal cantidad;
	private BigDecimal precioSinIva;
	private BigDecimal precioConIva;
	private BigDecimal total;
	private String ivaDes;
	private Integer ivaId;
	private BigDecimal ivaUnidad;
	private BigDecimal ivaTotal;
	private BigDecimal descuento;
	private BigDecimal porcentajeDescuento;
	private BigDecimal baseImponible;
	private BigDecimal baseImponibleTotal;
	private Integer idProducto;
	private String nombreProducto;

	public CbteCompDetDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getIvaDes() {
		return ivaDes;
	}

	public void setIvaDes(String ivaDes) {
		this.ivaDes = ivaDes;
	}

	public Integer getIvaId() {
		return ivaId;
	}

	public void setIvaId(Integer ivaId) {
		this.ivaId = ivaId;
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

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public BigDecimal getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public BigDecimal getBaseImponibleTotal() {
		return baseImponibleTotal;
	}

	public void setBaseImponibleTotal(BigDecimal baseImponibleTotal) {
		this.baseImponibleTotal = baseImponibleTotal;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

}
