/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import ar.com.jsuper.utils.TipoEntidad;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author rafa22
 */
public class EntidadMinDTO implements Serializable {

	private Integer id;
	private TipoEntidad tipo;
	private PersonasNanoDTO persona;
	private EmpresasMinDTO empresa;
	private ClienteMinSinEntDTO cliente;
	private String descripcion;
	private String tipoDocNro;

	public EntidadMinDTO() {
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

	public String getTipoDocNro() {
		if (this.tipo.equals(TipoEntidad.EMPRESA)) {
			if (Objects.nonNull(this.empresa)) {
				return "CUIT-" + this.empresa.getCuit();
			}
		}
		if (this.tipo.equals(TipoEntidad.PERSONA)) {
			if (Objects.nonNull(this.persona)) {
				return "DNI-" + this.persona.getDni();
			}
		}
		return this.id.toString();
	}

	public void setTipoDocNro(String tipoDocNro) {
		this.tipoDocNro = tipoDocNro;
	}

}
