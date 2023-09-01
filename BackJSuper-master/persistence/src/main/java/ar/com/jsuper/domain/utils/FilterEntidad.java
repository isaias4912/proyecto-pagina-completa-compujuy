/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.utils.TipoEmpresa;
import ar.com.jsuper.utils.TipoEntidad;

/**
 *
 * @author rafa22
 */
public class FilterEntidad {

	private String apellido;
	private Integer id;
	private String nombre;
	private String dni;
	private Integer activo;
	private Integer tipo;
	private TipoEmpresa tipoEmpresa;
	private TipoEntidad tipoEntidad;
	private String razonSocial;
	private String cuit;

	public FilterEntidad() {
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public TipoEntidad getTipoEntidad() {
		return tipoEntidad;
	}

	public void setTipoEntidad(TipoEntidad tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

}
