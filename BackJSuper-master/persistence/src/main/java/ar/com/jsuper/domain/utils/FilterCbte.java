/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.utils.Comprobante;
import java.math.BigDecimal;
import java.util.Set;

/**
 *
 * @author rafa22
 */
public class FilterCbte {


	private String fechaInicial;
	private String fechaFinal;
	private BigDecimal totalMinimo;
	private BigDecimal totalMaximo;
	private SimpleObject caja;
	private SimpleObject usuario;
	private Integer pagada;
	private Integer estadoCbte;
	private Integer afipValida;
	private Set<SimpleObject> sucursales;
	private Set<SimpleObject> productos;
	private Set<Comprobante> comprobantes;

	public FilterCbte() {
	}

	public String getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public BigDecimal getTotalMinimo() {
		return totalMinimo;
	}

	public void setTotalMinimo(BigDecimal totalMinimo) {
		this.totalMinimo = totalMinimo;
	}

	public BigDecimal getTotalMaximo() {
		return totalMaximo;
	}

	public void setTotalMaximo(BigDecimal totalMaximo) {
		this.totalMaximo = totalMaximo;
	}

	public SimpleObject getCaja() {
		return caja;
	}

	public void setCaja(SimpleObject caja) {
		this.caja = caja;
	}

	public SimpleObject getUsuario() {
		return usuario;
	}

	public void setUsuario(SimpleObject usuario) {
		this.usuario = usuario;
	}

	public Integer getPagada() {
		return pagada;
	}

	public void setPagada(Integer pagada) {
		this.pagada = pagada;
	}

	public Set<SimpleObject> getSucursales() {
		return sucursales;
	}

	public void setSucursales(Set<SimpleObject> sucursales) {
		this.sucursales = sucursales;
	}

	public Set<SimpleObject> getProductos() {
		return productos;
	}

	public void setProductos(Set<SimpleObject> productos) {
		this.productos = productos;
	}

    public Integer getAfipValida() {
        return afipValida;
    }

    public void setAfipValida(Integer afipValida) {
        this.afipValida = afipValida;
    }

    public Set<Comprobante> getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(Set<Comprobante> comprobantes) {
        this.comprobantes = comprobantes;
    }

	public Integer getEstadoCbte() {
		return estadoCbte;
	}

	public void setEstadoCbte(Integer estadoCbte) {
		this.estadoCbte = estadoCbte;
	}
}
