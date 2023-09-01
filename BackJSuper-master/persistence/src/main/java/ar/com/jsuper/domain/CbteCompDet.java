package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "cbte_comp_det")
public class CbteCompDet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	@Column(name = "precio_sin_iva")
	private BigDecimal precioSinIva;
	@Column(name = "precio_con_iva")
	private BigDecimal precioConIva;
	@Column(name = "total")
	private BigDecimal total;
	@Column(name = "iva_des")
	private String ivaDes;
	@Column(name = "iva_id")
	private Integer ivaId;
	@Column(name = "iva_unidad")
	private BigDecimal ivaUnidad;
	@Column(name = "iva_total")
	private BigDecimal ivaTotal;
	@Column(name = "descuento")
	private BigDecimal descuento;
	@Column(name = "porcentaje_descuento")
	private BigDecimal porcentajeDescuento;
	@Column(name = "base_imponible")
	private BigDecimal baseImponible;
	@Column(name = "base_imponible_total")
	private BigDecimal baseImponibleTotal;
	@Column(name = "id_producto")
	private Integer idProducto;
	@Column(name = "nombre_producto")
	private String nombreProducto;
	@Column(name = "cant_agregada_stock")
	private Boolean cantAgregadaStock;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cbte_comp_enc_id")
	private CbteCompEnc cbteCompEnc;

	public CbteCompDet() {
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

	public CbteCompEnc getCbteCompEnc() {
		return cbteCompEnc;
	}

	public void setCbteCompEnc(CbteCompEnc cbteCompEnc) {
		this.cbteCompEnc = cbteCompEnc;
	}

	public Boolean getCantAgregadaStock() {
		return cantAgregadaStock;
	}

	public void setCantAgregadaStock(Boolean cantAgregadaStock) {
		this.cantAgregadaStock = cantAgregadaStock;
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

}
