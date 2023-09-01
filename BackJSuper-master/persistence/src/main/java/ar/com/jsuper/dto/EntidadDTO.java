/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.TipoEntidad;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author rafa22
 */
public class EntidadDTO implements Serializable {

	private Integer id;
	private TipoEntidad tipo;
	private PersonasNanoDTO persona;
	private EmpresasMinDTO empresa;
	private Set<DomiciliosDTO> domicilios;
	private Set<ContactosDTO> contactos;
	private Set<TelefonosDTO> telefonos;
	private ClienteMinSinEntDTO cliente;
	private String descripcion;

	public EntidadDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoEntidad getTipo() {
		return tipo;
	}

	public void setTipo(TipoEntidad tipo) {
		this.tipo = tipo;
	}

	public PersonasNanoDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonasNanoDTO persona) {
		this.persona = persona;
	}

	public EmpresasMinDTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresasMinDTO empresa) {
		this.empresa = empresa;
	}

	public Set<DomiciliosDTO> getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(Set<DomiciliosDTO> domicilios) {
		this.domicilios = domicilios;
	}

	public Set<ContactosDTO> getContactos() {
		return contactos;
	}

	public void setContactos(Set<ContactosDTO> contactos) {
		this.contactos = contactos;
	}

	public Set<TelefonosDTO> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(Set<TelefonosDTO> telefonos) {
		this.telefonos = telefonos;
	}

	public ClienteMinSinEntDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteMinSinEntDTO cliente) {
		this.cliente = cliente;
	}

	public String getDescripcion() {
		if (this.tipo.equals(TipoEntidad.EMPRESA)) {
			if (Objects.nonNull(this.empresa)) {
				return this.empresa.getRazonSocial();
			}
		}
		if (this.tipo.equals(TipoEntidad.PERSONA)) {
			if (Objects.nonNull(this.persona)) {
				return this.persona.getApellido() + " " + this.persona.getNombre();
			}
		}
		return this.id.toString();
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
