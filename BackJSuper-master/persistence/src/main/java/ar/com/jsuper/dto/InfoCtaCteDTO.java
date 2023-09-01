package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class InfoCtaCteDTO implements Serializable {

	private BigDecimal saldo;
	private BigDecimal margen;
	private BigDecimal limite;
	private Boolean activoCliente;
	private Boolean activoCtaCte;
	private ClienteMinDTO cliente;

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getMargen() {
		return margen;
	}

	public void setMargen(BigDecimal margen) {
		this.margen = margen;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

	public Boolean getActivoCliente() {
		return activoCliente;
	}

	public void setActivoCliente(Boolean activoCliente) {
		this.activoCliente = activoCliente;
	}

	public Boolean getActivoCtaCte() {
		return activoCtaCte;
	}

	public void setActivoCtaCte(Boolean activoCtaCte) {
		this.activoCtaCte = activoCtaCte;
	}

	public ClienteMinDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteMinDTO cliente) {
		this.cliente = cliente;
	}

}
