package ar.com.jsuper.domain;

import ar.com.jsuper.domain.utils.IAfip;
import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.DocConverter;
import ar.com.jsuper.utils.TipoEmpresa;
import ar.com.jsuper.utils.TipoEmpresaConverter;
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

@Entity
@Table(name = "proveedores")
public class Proveedores implements Serializable, IAfip {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "estado")
	private Boolean estado;
	@Convert(converter = TipoEmpresaConverter.class)
	@Column(name = "tipo_proveedor")
	private TipoEmpresa tipoProveedor;
	@Convert(converter = DocConverter.class)
	@Column(name = "tipo_doc_proveedor")
	private Doc tipoDocProveedor;
	@Column(name = "nro_doc_proveedor")
	private String nroDocProveedor;
	@Column(name = "observacion")
	private String observacion;
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "entidad_id", nullable = true)
	private Entidad entidad;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id")
	private App app;
	@OneToOne(mappedBy = "proveedor",
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private CuentasCorrientesProv cuentaCorriente;

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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	public CuentasCorrientesProv getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CuentasCorrientesProv cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	public TipoEmpresa getTipoProveedor() {
		return tipoProveedor;
	}

	public void setTipoProveedor(TipoEmpresa tipoProveedor) {
		this.tipoProveedor = tipoProveedor;
	}

	public Doc getTipoDocProveedor() {
		return tipoDocProveedor;
	}

	public void setTipoDocProveedor(Doc tipoDocProveedor) {
		this.tipoDocProveedor = tipoDocProveedor;
	}

	public String getNroDocProveedor() {
		return nroDocProveedor;
	}

	public void setNroDocProveedor(String nroDocProveedor) {
		this.nroDocProveedor = nroDocProveedor;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}
	@Override
	public Integer getCondicion() {
		return this.tipoProveedor.theState;
	}
}
