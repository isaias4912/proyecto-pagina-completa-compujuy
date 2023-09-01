package ar.com.jsuper.domain;

import ar.com.jsuper.utils.TipoEmpresa;
import ar.com.jsuper.utils.TipoEmpresaConverter;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "empresas")
public class Empresas implements Serializable {

	@Id
	private Integer id;
	@Column(name = "razon_social")
	private String razonSocial;
	@Column(name = "cuit")
	private String cuit;
	@Convert(converter = TipoEmpresaConverter.class)
	@Column(name = "tipo")
	private TipoEmpresa tipoEmpresa;
	@Column(name = "observacion")
	private String observacion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personas_entidad_id", insertable = true)
	private Personas persona;
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@MapsId
	private Entidad entidad;
	public Empresas() {
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

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Personas getPersona() {
		return persona;
	}

	public void setPersona(Personas persona) {
		this.persona = persona;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

}
