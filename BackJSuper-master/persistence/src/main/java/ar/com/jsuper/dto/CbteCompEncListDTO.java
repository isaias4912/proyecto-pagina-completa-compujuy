package ar.com.jsuper.dto;

import ar.com.jsuper.utils.Comprobante;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Entity implementation class for Entity: mov
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CbteCompEncListDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
	private Date fechaCarga;
	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Argentina/Buenos_Aires")
	private Date fechaCbte;
	private String numero;
	private Boolean activo;
	private String motivoAnulacion;
	private Integer tipo;
	private Integer cargada;
	private Comprobante tipoCbte;
	private BigDecimal subtotal;
	private BigDecimal total;
	private Integer idProveedor;
	private Integer idSucursal;
	private BigDecimal totalDescuentos;
	private BigDecimal totalAdicionales;
	private BigDecimal totalImpuestos;
	private ProveedoresMinDTO proveedor;
	private Boolean pagada;
	private String cae;
	private String observacion;
	private Set<PagoCbteCompNanoDTO> pagos;

	public CbteCompEncListDTO() {
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

	public Date getFechaCbte() {
		return fechaCbte;
	}

	public void setFechaCbte(Date fechaCbte) {
		this.fechaCbte = fechaCbte;
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

	public ProveedoresMinDTO getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedoresMinDTO proveedor) {
		this.proveedor = proveedor;
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

	public Set<PagoCbteCompNanoDTO> getPagos() {
		return pagos;
	}

	public void setPagos(Set<PagoCbteCompNanoDTO> pagos) {
		this.pagos = pagos;
	}

}
