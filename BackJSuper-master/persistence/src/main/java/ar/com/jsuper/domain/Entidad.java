/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import ar.com.jsuper.utils.TipoEntidad;
import ar.com.jsuper.utils.TipoEntidadConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 *
 * @author rafa22
 */
@Entity
@Table(name = "entidad")
public class Entidad implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "fecha_alta")
	private Date fechAlta;
	@Convert(converter = TipoEntidadConverter.class)
	@Column(name = "tipo")
	private TipoEntidad tipo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id")
	private App app;
	@OneToMany(mappedBy = "entidad", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Domicilios> domicilios;
	@OneToMany(mappedBy = "entidad", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Contactos> contactos;
	@OneToMany(mappedBy = "entidad", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Telefonos> telefonos;
	@OneToOne(mappedBy = "entidad", fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
	private Personas persona;
	@OneToOne(mappedBy = "entidad", fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	private Empresas empresa;
	@OneToMany(mappedBy = "entidad", fetch = FetchType.LAZY) // esto en realidad es uno a uno pero para no complicar mas se hizo asi
	private Set<Cliente> clientes;

	public Entidad() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechAlta() {
		return fechAlta;
	}

	public void setFechAlta(Date fechAlta) {
		this.fechAlta = fechAlta;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Set<Domicilios> getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(Set<Domicilios> domicilios) {
		this.domicilios = domicilios;
	}

	public Set<Contactos> getContactos() {
		return contactos;
	}

	public void setContactos(Set<Contactos> contactos) {
		this.contactos = contactos;
	}

	public Set<Telefonos> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(Set<Telefonos> telefonos) {
		this.telefonos = telefonos;
	}

	public Personas getPersona() {
		return persona;
	}

	public void setPersona(Personas persona) {
		this.persona = persona;
	}

	public Empresas getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresas empresa) {
		this.empresa = empresa;
	}

	public TipoEntidad getTipo() {
		return tipo;
	}

	public void setTipo(TipoEntidad tipo) {
		this.tipo = tipo;
	}

	public Set<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}

}
