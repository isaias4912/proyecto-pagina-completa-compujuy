/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.util.Set;

/**
 *
 * @author rafa22
 */
public class UsuariosDTO {

    private Integer id;
    private String usuario;
    private String password;
    private String tipo;
    private String observacion;
    private String mail;
    private Integer tipoUsuario;
    private String idRedSocial;
    private PersonasNanoDTO persona;
    private Boolean estado;
    private Set<RolesDTO> roles;
    private Set<SucursalesMinDTO> sucursales;
    private String logo;
    private String altLogo;
    private String tipoLogo;
    private String keyGravatar;
    private Boolean root;

    public UsuariosDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public PersonasNanoDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonasNanoDTO persona) {
        this.persona = persona;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Set<RolesDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolesDTO> roles) {
        this.roles = roles;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getIdRedSocial() {
        return idRedSocial;
    }

    public void setIdRedSocial(String idRedSocial) {
        this.idRedSocial = idRedSocial;
    }

    public Set<SucursalesMinDTO> getSucursales() {
        return sucursales;
    }

    public void setSucursales(Set<SucursalesMinDTO> sucursales) {
        this.sucursales = sucursales;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAltLogo() {
        return altLogo;
    }

    public void setAltLogo(String altLogo) {
        this.altLogo = altLogo;
    }

    public String getTipoLogo() {
        return tipoLogo;
    }

    public void setTipoLogo(String tipoLogo) {
        this.tipoLogo = tipoLogo;
    }

    public String getKeyGravatar() {
        return keyGravatar;
    }

    public void setKeyGravatar(String keyGravatar) {
        this.keyGravatar = keyGravatar;
    }

	public Boolean getRoot() {
		return root;
	}

	public void setRoot(Boolean root) {
		this.root = root;
	}

}
