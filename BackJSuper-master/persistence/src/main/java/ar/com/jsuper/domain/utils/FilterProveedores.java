/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.utils.TipoEmpresa;

/**
 *
 * @author rafa22
 */
public class FilterProveedores {

	private String nombre;
	private String dni;
	private String cuit;
	private TipoEmpresa tipoProveedor;
	private Integer activo;

	public FilterProveedores() {
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

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public TipoEmpresa getTipoProveedor() {
		return tipoProveedor;
	}

	public void setTipoProveedor(TipoEmpresa tipoProveedor) {
		this.tipoProveedor = tipoProveedor;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

}
