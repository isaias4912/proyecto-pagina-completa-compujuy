/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import ar.com.jsuper.domain.utils.IAfip;
import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.DocConverter;
import ar.com.jsuper.utils.TipoCliente;
import ar.com.jsuper.utils.TipoClienteConverter;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Formula;

/**
 *
 * @author rafa22
 */
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable, IAfip {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "estado")
	private Boolean estado;
	@Convert(converter = TipoClienteConverter.class)
	@Column(name = "tipo_cliente")
	private TipoCliente tipoCliente;
	@Convert(converter = DocConverter.class)
	@Column(name = "tipo_doc_cliente")
	private Doc tipoDocCliente;
	@Column(name = "nro_doc_cliente")
	private String nroDocCliente;
	@Column(name = "observacion")
	private String observacion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entidad_id", insertable = true)
	private Entidad entidad;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id")
	private App app;
	@OneToOne(mappedBy = "cliente",
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private CuentasCorrientes cuentaCorriente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public CuentasCorrientes getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CuentasCorrientes cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	public Doc getTipoDocCliente() {
		return tipoDocCliente;
	}

	public void setTipoDocCliente(Doc tipoDocCliente) {
		this.tipoDocCliente = tipoDocCliente;
	}

	public String getNroDocCliente() {
		return nroDocCliente;
	}

	public void setNroDocCliente(String nroDocCliente) {
		this.nroDocCliente = nroDocCliente;
	}

	@Override
	public Integer getCondicion() {
		return this.tipoCliente.theState;
	}

}
