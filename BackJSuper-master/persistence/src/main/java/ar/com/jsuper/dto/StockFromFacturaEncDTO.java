package ar.com.jsuper.dto;

import ar.com.jsuper.utils.Comprobante;
import ar.com.jsuper.utils.MoneySerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Entity implementation class for Entity: mov
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockFromFacturaEncDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaFactura;

	private String numero;

	private Comprobante tipoCbte;

	private BigDecimal subtotal;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal total;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal totalDescuentos;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal totalAdicionales;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal totalImpuestos;

	private Integer completa;

	private Integer idProveedor;

	private Integer idSucursal;

	private List<StockFromFacturaDetDTO> items;

	private ProveedoresMinDTO proveedor;

	public StockFromFacturaEncDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Comprobante getTipoCbte() {
		return tipoCbte;
	}

	public void setTipoCbte(Comprobante tipoCbte) {
		this.tipoCbte = tipoCbte;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotalDescuentos() {
		return totalDescuentos;
	}

	public void setTotalDescuentos(BigDecimal totalDescuentos) {
		this.totalDescuentos = totalDescuentos;
	}

	public BigDecimal getTotalAdicionales() {
		return totalAdicionales;
	}

	public void setTotalAdicionales(BigDecimal totalAdicionales) {
		this.totalAdicionales = totalAdicionales;
	}

	public BigDecimal getTotalImpuestos() {
		return totalImpuestos;
	}

	public void setTotalImpuestos(BigDecimal totalImpuestos) {
		this.totalImpuestos = totalImpuestos;
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

	public List<StockFromFacturaDetDTO> getItems() {
		return items;
	}

	public void setItems(List<StockFromFacturaDetDTO> items) {
		this.items = items;
	}

	public ProveedoresMinDTO getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedoresMinDTO proveedor) {
		this.proveedor = proveedor;
	}

	public Integer getCompleta() {
		return completa;
	}

	public void setCompleta(Integer completa) {
		this.completa = completa;
	}

}
