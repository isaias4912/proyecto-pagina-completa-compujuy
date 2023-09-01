/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import java.math.BigDecimal;

/**
 *
 * @author rafael
 */
public class DetalleVentasTicket {

	private String descripcion;
	private BigDecimal subtotal;
	private Integer puntoVenta;
	private Integer transaccion;
	private String infoProdAdic;
	private String numeroSerie;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(Integer puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Integer getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(Integer transaccion) {
		this.transaccion = transaccion;
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

}
