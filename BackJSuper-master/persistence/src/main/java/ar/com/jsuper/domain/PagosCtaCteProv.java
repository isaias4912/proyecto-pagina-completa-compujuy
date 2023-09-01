/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pagos_cta_cte_prov")
public class PagosCtaCteProv implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "monto_neto")
	private BigDecimal montoNeto;
	@Column(name = "monto_total")
	private BigDecimal montoTotal;
	@Column(name = "fecha")
	private Date fecha;
	@Column(name = "pago_con")
	private BigDecimal pagoCon;
	@Column(name = "pago")
	private BigDecimal pago;
	@Column(name = "numero")
	private String numero;
	@Column(name = "tarjeta")
	private String tarjeta;
	@Column(name = "tipo")
	private String tipo;
	@Column(name = "interes")
	private BigDecimal interes;
	@OneToMany(mappedBy = "pagoCtaCte", fetch = FetchType.LAZY)
	private List<MovimientosCtaCteProv> movimientos;
	@OneToMany(mappedBy = "pagoCtaCte", fetch = FetchType.LAZY)
	private List<PagoPagoCtaCteProv> pagos;

	public PagosCtaCteProv() {
	}

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

	public BigDecimal getMontoNeto() {
		return montoNeto;
	}

	public void setMontoNeto(BigDecimal montoNeto) {
		this.montoNeto = montoNeto;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getPagoCon() {
		return pagoCon;
	}

	public void setPagoCon(BigDecimal pagoCon) {
		this.pagoCon = pagoCon;
	}

	public BigDecimal getPago() {
		return pago;
	}

	public void setPago(BigDecimal pago) {
		this.pago = pago;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public List<MovimientosCtaCteProv> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<MovimientosCtaCteProv> movimientos) {
		this.movimientos = movimientos;
	}

	public List<PagoPagoCtaCteProv> getPagos() {
		return pagos;
	}

	public void setPagos(List<PagoPagoCtaCteProv> pagos) {
		this.pagos = pagos;
	}

}
