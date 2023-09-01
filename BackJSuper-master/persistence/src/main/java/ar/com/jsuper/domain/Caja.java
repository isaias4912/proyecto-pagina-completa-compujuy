/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "caja")
public class Caja implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "ip_maquina")
	private String ipMaquina;
	@Column(name = "nombre_maquina")
	private String nombreMaquina;
	@Column(name = "activo")
	private Boolean activo;
	@Column(name = "modifica_precio")
	private Boolean modificaPrecio;
	@Column(name = "modifica_descuento")
	private Boolean modificaDescuento;
	@Column(name = "modifica_adicional")
	private Boolean modificaAdicional;
	@Column(name = "limite_consumidor_final")
	private BigDecimal limiteConsumidorFinal;
	@Column(name = "con_control_estricto")
	private Boolean conControlEstricto;
	@Column(name = "observacion")
	private String observacion;
	@Column(name = "punto_venta_id")
	private Integer idPuntoVenta;
	@Column(name = "comprobantes_hab")
	private String comprobantesHab;
	@OneToMany(mappedBy = "caja", fetch = FetchType.LAZY)
	private Set<TransaccionCaja> transacciones;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id", updatable = false)
	private App app;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "caja_sucursales", joinColumns = {
		@JoinColumn(name = "caja_id", nullable = false, updatable = false, insertable = false)},
			inverseJoinColumns = {
				@JoinColumn(name = "sucursales_id",
						nullable = false, updatable = false, insertable = false)})
	private Set<Sucursales> sucursales;

	public Caja() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIpMaquina() {
		return ipMaquina;
	}

	public void setIpMaquina(String ipMaquina) {
		this.ipMaquina = ipMaquina;
	}

	public String getNombreMaquina() {
		return nombreMaquina;
	}

	public void setNombreMaquina(String nombreMaquina) {
		this.nombreMaquina = nombreMaquina;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Set<TransaccionCaja> getTransacciones() {
		return transacciones;
	}

	public void setTransacciones(Set<TransaccionCaja> transacciones) {
		this.transacciones = transacciones;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getModificaPrecio() {
		return modificaPrecio;
	}

	public void setModificaPrecio(Boolean modificaPrecio) {
		this.modificaPrecio = modificaPrecio;
	}

	public Boolean getModificaDescuento() {
		return modificaDescuento;
	}

	public void setModificaDescuento(Boolean modificaDescuento) {
		this.modificaDescuento = modificaDescuento;
	}

	public Boolean getModificaAdicional() {
		return modificaAdicional;
	}

	public void setModificaAdicional(Boolean modificaAdicional) {
		this.modificaAdicional = modificaAdicional;
	}

	public BigDecimal getLimiteConsumidorFinal() {
		return limiteConsumidorFinal;
	}

	public void setLimiteConsumidorFinal(BigDecimal limiteConsumidorFinal) {
		this.limiteConsumidorFinal = limiteConsumidorFinal;
	}

	public Boolean getConControlEstricto() {
		return conControlEstricto;
	}

	public void setConControlEstricto(Boolean conControlEstricto) {
		this.conControlEstricto = conControlEstricto;
	}

	public Set<Sucursales> getSucursales() {
		return sucursales;
	}

	public void setSucursales(Set<Sucursales> sucursales) {
		this.sucursales = sucursales;
	}

	public Integer getIdPuntoVenta() {
		return idPuntoVenta;
	}

	public void setIdPuntoVenta(Integer idPuntoVenta) {
		this.idPuntoVenta = idPuntoVenta;
	}

	public String getComprobantesHab() {
		return comprobantesHab;
	}

	public void setComprobantesHab(String comprobantesHab) {
		this.comprobantesHab = comprobantesHab;
	}

}
