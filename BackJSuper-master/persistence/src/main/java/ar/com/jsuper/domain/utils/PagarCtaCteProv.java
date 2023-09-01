/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.domain.MovimientosCtaCteProv;
import ar.com.jsuper.domain.PagoPagoCtaCteProv;
import ar.com.jsuper.domain.Proveedores;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author rafael
 */
public class PagarCtaCteProv {

    private BigDecimal montoNeto;
    private BigDecimal montoTotal;
    private BigDecimal pagoCon;
    private BigDecimal saldo;
    private BigDecimal vuelto;
    private String descripcion;
    private BigDecimal interes;
    private List<MovimientosCtaCteProv> movimientosCtaCte;
    private List<PagoPagoCtaCteProv> pagos;
    private Proveedores proveedor;

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

	public BigDecimal getPagoCon() {
		return pagoCon;
	}

	public void setPagoCon(BigDecimal pagoCon) {
		this.pagoCon = pagoCon;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public List<MovimientosCtaCteProv> getMovimientosCtaCte() {
		return movimientosCtaCte;
	}

	public void setMovimientosCtaCte(List<MovimientosCtaCteProv> movimientosCtaCte) {
		this.movimientosCtaCte = movimientosCtaCte;
	}

	public List<PagoPagoCtaCteProv> getPagos() {
		return pagos;
	}

	public void setPagos(List<PagoPagoCtaCteProv> pagos) {
		this.pagos = pagos;
	}

	public Proveedores getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedores proveedor) {
		this.proveedor = proveedor;
	}


}
