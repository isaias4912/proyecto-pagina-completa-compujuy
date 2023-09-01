/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author rafa22
 */
@Entity
@Table(name = "usuarios")
public class Usuarios implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "logo")
    private String logo;
    @Column(name = "alt_logo")
    private String altLogo;
    @Column(name = "tipo_logo")
    private String tipoLogo;
    @Column(name = "key_user")
    private String keyUser;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "password")
    private String password;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "tipo_usuario")
    private Integer tipoUsuario;
    @Column(name = "id_red_social")
    private String idRedSocial;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "mail")
    private String mail;
    @Column(name = "root")
    private Boolean root;
    @Column(name = "key_gravatar")
    private String keyGravatar;
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personas_entidad_id", insertable = true)
    private Personas persona;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id")
    private App app;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuarios_roles", joinColumns = {
        @JoinColumn(name = "usuarios_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "roles_id",
                        nullable = false, updatable = false)})
    private Set<Roles> roles;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuarios_sucursales", joinColumns = {
        @JoinColumn(name = "usuarios_id", nullable = false, updatable = false, insertable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "sucursales_id",
                        nullable = false, updatable = false, insertable = false)})
    private Set<Sucursales> sucursales;

    @Column(name = "estado")
    private Boolean estado;

    public Usuarios() {
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

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public String getKeyGravatar() {
        return keyGravatar;
    }

    public void setKeyGravatar(String keyGravatar) {
        this.keyGravatar = keyGravatar;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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

    public Set<Sucursales> getSucursales() {
        return sucursales;
    }

    public void setSucursales(Set<Sucursales> sucursales) {
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

    public Boolean getRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
