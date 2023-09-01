/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author rafael
 */
public class PagarCtaCteDTO {

	private BigDecimal montoNeto;
	private BigDecimal montoTotal;
	private BigDecimal interes;
	private BigDecimal totalPaga;
	private BigDecimal pagoCon;
	private BigDecimal pago;
	private BigDecimal saldo;
	private BigDecimal vuelto;
	private String descripcion;
	private List<MovimientosCtaCteDTO> movimientosCtaCte;
	private List<PagoPagoCtaCteDTO> pagos;
	private ClienteListDTO cliente;

	public BigDecimal getMontoNeto() {
		return montoNeto;
	}

	public void setMontoNeto(BigDecimal monto) {
		this.montoNeto = monto;
	}

	public BigDecimal getPagoCon() {
		return pagoCon;
	}

	public void setPagoCon(BigDecimal pagoCon) {
		this.pagoCon = pagoCon;
	}

	public List<MovimientosCtaCteDTO> getMovimientosCtaCte() {
		return movimientosCtaCte;
	}

	public void setMovimientosCtaCte(List<MovimientosCtaCteDTO> movimientosCtaCte) {
		this.movimientosCtaCte = movimientosCtaCte;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getTotalPaga() {
		return totalPaga;
	}

	public void setTotalPaga(BigDecimal totalPaga) {
		this.totalPaga = totalPaga;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoPaga) {
		this.montoTotal = montoPaga;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public ClienteListDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteListDTO cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getVuelto() {
		return vuelto;
	}

	public void setVuelto(BigDecimal vuelto) {
		this.vuelto = vuelto;
	}

	public BigDecimal getPago() {
		return pago;
	}

	public void setPago(BigDecimal pago) {
		this.pago = pago;
	}

	public List<PagoPagoCtaCteDTO> getPagos() {
		return pagos;
	}

	public void setPagos(List<PagoPagoCtaCteDTO> pagos) {
		this.pagos = pagos;
	}

}
