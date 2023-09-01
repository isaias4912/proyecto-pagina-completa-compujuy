/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.Comprobante;

import java.math.BigDecimal;
import java.util.Set;

/**
 *
 * @author rafa22
 */
public class FilterVentasDTO {

	private String fechaInicial;
	private String fechaFinal;
	private BigDecimal totalMinimo;
	private BigDecimal totalMaximo;
	private SimpleObjectDTO caja;
	private SimpleObjectDTO usuario;
	private Integer pagada;
	private Integer afipValida;
	private Set<SimpleObjectDTO> sucursales;
	private Set<SimpleObjectDTO> productos;
	private Set<Comprobante> comprobantes;
	public FilterVentasDTO() {
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

	public SimpleObjectDTO getCaja() {
		return caja;
	}

	public void setCaja(SimpleObjectDTO caja) {
		this.caja = caja;
	}

	public SimpleObjectDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(SimpleObjectDTO usuario) {
		this.usuario = usuario;
	}

	public Integer getPagada() {
		return pagada;
	}

	public void setPagada(Integer pagada) {
		this.pagada = pagada;
	}

	public Set<SimpleObjectDTO> getSucursales() {
		return sucursales;
	}

	public void setSucursales(Set<SimpleObjectDTO> sucursales) {
		this.sucursales = sucursales;
	}

	public Set<SimpleObjectDTO> getProductos() {
		return productos;
	}

	public void setProductos(Set<SimpleObjectDTO> productos) {
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
}
