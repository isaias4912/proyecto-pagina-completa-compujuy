/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PagosCtaCteProvDTO implements Serializable {

	private Integer id;
	private String descripcion;
	private BigDecimal montoNeto;
	private BigDecimal montoTotal;
	private BigDecimal interes;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fecha;
	private BigDecimal pagoCon;
	private BigDecimal pago;
	private String numero;
	private String tarjeta;
	private String tipo;
	private MovimientosCtaCteProvMinDTO movimiento;
	private List<MovimientosCtaCteProvNanoDTO> movimientos;
	private PagosCtaCteProvDTO pagoSaldo;

	public PagosCtaCteProvDTO() {
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

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
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

	public MovimientosCtaCteProvMinDTO getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(MovimientosCtaCteProvMinDTO movimiento) {
		this.movimiento = movimiento;
	}

	public List<MovimientosCtaCteProvNanoDTO> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<MovimientosCtaCteProvNanoDTO> movimientos) {
		this.movimientos = movimientos;
	}

	public PagosCtaCteProvDTO getPagoSaldo() {
		return pagoSaldo;
	}

	public void setPagoSaldo(PagosCtaCteProvDTO pagoSaldo) {
		this.pagoSaldo = pagoSaldo;
	}

}
