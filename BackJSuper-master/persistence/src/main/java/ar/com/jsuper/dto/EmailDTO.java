/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.TipoEntidad;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author rafa
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDTO {

	private String id;
	private String nombre;
	private TipoEntidad tipoEntidad;
	private String email;
	private String descripcion;

	public String getNombre() {
		return nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoEntidad getTipoEntidad() {
		return tipoEntidad;
	}

	public void setTipoEntidad(TipoEntidad tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
