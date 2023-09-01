/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 *
 * @author rafael
 */
public class CajaDTO implements Serializable {

	private int id;
	private String nombre;
	private String ipMaquina;
	private String nombreMaquina;
	private String observacion;
	private Boolean activo;
	private Boolean modificaPrecio;
	private Boolean modificaDescuento;
	private Boolean modificaAdicional;
	private BigDecimal limiteConsumidorFinal;
	private Boolean conControlEstricto;
	private Set<SucursalesDTO> sucursales;
	private Integer idPuntoVenta;
	private String comprobantesHab;

	public CajaDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIpMaquina() {
		return ipMaquina;
	}

	public void setIpMaquina(String ipMaquina) {
		this.ipMaquina = ipMaquina;
	}

	public String getNombreMaquina() {
		return nombreMaquina;
	}

	public void setNombreMaquina(String nombreMaquina) {
		this.nombreMaquina = nombreMaquina;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getModificaPrecio() {
		return modificaPrecio;
	}

	public void setModificaPrecio(Boolean modificaPrecio) {
		this.modificaPrecio = modificaPrecio;
	}

	public Boolean getModificaDescuento() {
		return modificaDescuento;
	}

	public void setModificaDescuento(Boolean modificaDescuento) {
		this.modificaDescuento = modificaDescuento;
	}

	public Boolean getModificaAdicional() {
		return modificaAdicional;
	}

	public void setModificaAdicional(Boolean modificaAdicional) {
		this.modificaAdicional = modificaAdicional;
	}

	public BigDecimal getLimiteConsumidorFinal() {
		return limiteConsumidorFinal;
	}

	public void setLimiteConsumidorFinal(BigDecimal limiteConsumidorFinal) {
		this.limiteConsumidorFinal = limiteConsumidorFinal;
	}

	public Boolean getConControlEstricto() {
		return conControlEstricto;
	}

	public void setConControlEstricto(Boolean conControlEstricto) {
		this.conControlEstricto = conControlEstricto;
	}

	public Set<SucursalesDTO> getSucursales() {
		return sucursales;
	}

	public void setSucursales(Set<SucursalesDTO> sucursales) {
		this.sucursales = sucursales;
	}

	public Integer getIdPuntoVenta() {
		return idPuntoVenta;
	}

	public void setIdPuntoVenta(Integer idPuntoVenta) {
		this.idPuntoVenta = idPuntoVenta;
	}

	public String getComprobantesHab() {
		return comprobantesHab;
	}

	public void setComprobantesHab(String comprobantesHab) {
		this.comprobantesHab = comprobantesHab;
	}

}
