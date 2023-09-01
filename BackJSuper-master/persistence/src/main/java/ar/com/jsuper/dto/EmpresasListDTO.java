package ar.com.jsuper.dto;

import ar.com.jsuper.utils.TipoEmpresa;
import java.io.Serializable;

public class EmpresasListDTO implements Serializable {

	private Integer id;
	private String razonSocial;
	private String cuit;
	private TipoEmpresa tipoEmpresa;
	private Boolean estado;
	private Boolean isCliente;
	private String observacion;
	private PersonasNanoDTO persona;

	public EmpresasListDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

//	public TipoEmpresa getTipo() {
//		return tipo;
//	}
//
//	public void setTipo(TipoEmpresa tipo) {
//		this.tipo = tipo;
//	}
	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public PersonasNanoDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonasNanoDTO persona) {
		this.persona = persona;
	}

	public Boolean getIsCliente() {
		return isCliente;
	}

	public void setIsCliente(Boolean isCliente) {
		this.isCliente = isCliente;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

}
