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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "detalle_transaccion_caja")
public class DetalleTransaccionCaja implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "fecha")
	private Date fecha;
	@Column(name = "tipo")
	private Integer tipo;
	@Column(name = "subtipo")
	private Integer subtipo;
	@Column(name = "asociada")
	private Boolean asociada;
	@Column(name = "asociada_tipo")
	private Integer asociadaTipo;
	@Column(name = "asociada_id")
	private Integer asociadaId;
	@Column(name = "monto")
	private BigDecimal monto;
	@Column(name = "nombre_usuario_auth")
	private String nombreUsuarioAuth;
	@Column(name = "id_usuario_auth")
	private String idUsuarioAuth;
	@Column(name = "dni_usuario_auth")
	private String dniUsuarioAuth;
	@Column(name = "observacion")
	private String observacion;
	@Column(name = "motivo")
	private String motivo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarios_id")
	private Usuarios usuario;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaccion_caja_id")
	private TransaccionCaja transaccionCaja;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cbte_ven_enc_id")
	private CbteEncVenta cbteEncVenta;

	public DetalleTransaccionCaja() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public TransaccionCaja getTransaccionCaja() {
		return transaccionCaja;
	}

	public void setTransaccionCaja(TransaccionCaja transaccionCaja) {
		this.transaccionCaja = transaccionCaja;
	}

	public CbteEncVenta getCbteVenEnc() {
		return cbteEncVenta;
	}

	public void setCbteVenEnc(CbteEncVenta cbteEncVenta) {
		this.cbteEncVenta = cbteEncVenta;
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

	public String getDniUsuarioAuth() {
		return dniUsuarioAuth;
	}

	public void setDniUsuarioAuth(String dniUsuarioAuth) {
		this.dniUsuarioAuth = dniUsuarioAuth;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Integer getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(Integer subtipo) {
		this.subtipo = subtipo;
	}

	public Boolean getAsociada() {
		return asociada;
	}

	public void setAsociada(Boolean asociada) {
		this.asociada = asociada;
	}

	public Integer getAsociadaTipo() {
		return asociadaTipo;
	}

	public void setAsociadaTipo(Integer asociadaTipo) {
		this.asociadaTipo = asociadaTipo;
	}

	public Integer getAsociadaId() {
		return asociadaId;
	}

	public void setAsociadaId(Integer asociadaId) {
		this.asociadaId = asociadaId;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

}
