/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author rafa22
 */
@Entity
@Table(name = "eventos_ventas")
public class EventosVentas implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "tipo")
	private Integer tipo;
	@Column(name = "nombre_usuario_auth")
	private String nombreUsuarioAuth;
	@Column(name = "id_usuario_auth")
	private String idUsuarioAuth;
	@Column(name = "detalle")
	private String detalle;
	@Column(name = "fecha")
	private Date fecha;
	@Column(name = "monto")
	private BigDecimal monto;
	@ManyToOne
	@JoinColumn(name = "cbte_ven_enc_id", nullable = false)
	private CbteEncVenta cbteEncVenta;

	public EventosVentas() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getNombreUsuarioAuth() {
		return nombreUsuarioAuth;
	}

	public void setNombreUsuarioAuth(String nombreUsuarioAuth) {
		this.nombreUsuarioAuth = nombreUsuarioAuth;
	}

	public String getIdUsuarioAuth() {
		return idUsuarioAuth;
	}

	public void setIdUsuarioAuth(String idUsuarioAuth) {
		this.idUsuarioAuth = idUsuarioAuth;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public CbteEncVenta getCbteVenEnc() {
		return cbteEncVenta;
	}

	public void setCbteVenEnc(CbteEncVenta cbteEncVenta) {
		this.cbteEncVenta = cbteEncVenta;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

}
