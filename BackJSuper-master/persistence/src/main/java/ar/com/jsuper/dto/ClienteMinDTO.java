/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.TipoCliente;
import java.io.Serializable;

/**
 *
 * @author rafa22
 */
public class ClienteMinDTO implements Serializable {

	private Integer id;
	private Boolean estado;
	private TipoCliente tipoCliente;
	private Doc tipoDocCliente;
	private String nroDocCliente;
	private String observacion;
	private String descripcion;
	private String nombreCompleto;
	private EntidadMinDTO entidad;
	private CtaCteMinDTO cuentaCorriente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public Doc getTipoDocCliente() {
		return tipoDocCliente;
	}

	public void setTipoDocCliente(Doc tipoDocCliente) {
		this.tipoDocCliente = tipoDocCliente;
	}

	public String getNroDocCliente() {
		return nroDocCliente;
	}

	public void setNroDocCliente(String nroDocCliente) {
		this.nroDocCliente = nroDocCliente;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EntidadMinDTO getEntidad() {
		return entidad;
	}

	public void setEntidad(EntidadMinDTO entidad) {
		this.entidad = entidad;
	}

	public CtaCteMinDTO getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CtaCteMinDTO cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

}
