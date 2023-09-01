/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

/**
 *
 * @author rafa22
 */
public class UsuariosMinDTO {

	private int id;
	private String usuario;
	private String tipo;
	private String observacion;
	private String mail;
	private String logo;
	private String tipoLogo;
	private PersonasNanoDTO persona;

	public UsuariosMinDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public PersonasNanoDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonasNanoDTO persona) {
		this.persona = persona;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTipoLogo() {
		return tipoLogo;
	}

	public void setTipoLogo(String tipoLogo) {
		this.tipoLogo = tipoLogo;
	}

}
