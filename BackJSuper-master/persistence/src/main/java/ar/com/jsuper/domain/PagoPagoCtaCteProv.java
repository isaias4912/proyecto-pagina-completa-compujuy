/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import ar.com.jsuper.utils.Asociada;
import ar.com.jsuper.utils.AsociadaConverter;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "pago_pago_cta_cte_prov")
public class PagoPagoCtaCteProv implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "monto")
	private BigDecimal monto;
	@Column(name = "pago_con")
	private BigDecimal pagoCon;
	@Column(name = "asociada_id")
	private Integer asociadaId;
	@Convert(converter = AsociadaConverter.class)
	@Column(name = "asociada_tipo")
	private Asociada asociadaTipo;
	@Transient
	private BigDecimal interes;
	@Transient
	private BigDecimal montoConInteres;
	@Column(name = "numero")
	private String numero;
	@Column(name = "tarjeta")
	private String tarjeta;
	@Column(name = "tipo")
	private String tipo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "formapagos_id")
	private FormaPagos formaPago;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pagos_cta_cte_prov_id")
	private PagosCtaCteProv pagoCtaCte;

	public PagoPagoCtaCteProv() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
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

	public FormaPagos getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPagos formaPago) {
		this.formaPago = formaPago;
	}

	public String getTipo() {
		return tipo;
	}

	public PagosCtaCteProv getPagoCtaCte() {
		return pagoCtaCte;
	}

	public void setPagoCtaCte(PagosCtaCteProv pagoCtaCte) {
		this.pagoCtaCte = pagoCtaCte;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getPagoCon() {
		return pagoCon;
	}

	public void setPagoCon(BigDecimal pagoCon) {
		this.pagoCon = pagoCon;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getMontoConInteres() {
		return montoConInteres;
	}

	public void setMontoConInteres(BigDecimal montoConInteres) {
		this.montoConInteres = montoConInteres;
	}

	public Integer getAsociadaId() {
		return asociadaId;
	}

	public void setAsociadaId(Integer asociadaId) {
		this.asociadaId = asociadaId;
	}

	public Asociada getAsociadaTipo() {
		return asociadaTipo;
	}

	public void setAsociadaTipo(Asociada asociadaTipo) {
		this.asociadaTipo = asociadaTipo;
	}

}
