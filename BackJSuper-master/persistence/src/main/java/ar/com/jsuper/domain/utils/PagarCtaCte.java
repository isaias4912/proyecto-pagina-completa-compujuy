/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.MovimientosCtaCte;
import ar.com.jsuper.domain.PagoPagoCtaCte;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author rafael
 */
public class PagarCtaCte {

    private BigDecimal montoNeto;
    private BigDecimal montoTotal;
    private BigDecimal pagoCon;
    private BigDecimal saldo;
    private BigDecimal vuelto;
    private String descripcion;
    private BigDecimal interes;
    private List<MovimientosCtaCte> movimientosCtaCte;
    private List<PagoPagoCtaCte> pagos;
    private Cliente cliente;

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

    public List<MovimientosCtaCte> getMovimientosCtaCte() {
        return movimientosCtaCte;
    }

    public void setMovimientosCtaCte(List<MovimientosCtaCte> movimientosCtaCte) {
        this.movimientosCtaCte = movimientosCtaCte;
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

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoPaga) {
        this.montoTotal = montoPaga;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<PagoPagoCtaCte> getPagos() {
		return pagos;
	}

	public void setPagos(List<PagoPagoCtaCte> pagos) {
		this.pagos = pagos;
	}

}
