/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PagosCtaCteNanoDTO implements Serializable {

	private int id;
	private String descripcion;
	private BigDecimal montoNeto;
	private BigDecimal montoTotal;
	private BigDecimal interes;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fecha;
	private BigDecimal pagoCon;
	private String numero;
	private String tarjeta;
	private String tipo;

	public PagosCtaCteNanoDTO() {
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

	public BigDecimal getPagoCon() {
		return pagoCon;
	}

	public void setPagoCon(BigDecimal pagoCon) {
		this.pagoCon = pagoCon;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
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

}
