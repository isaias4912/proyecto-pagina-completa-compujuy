package ar.com.jsuper.dto;

import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.TipoEmpresa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProveedoresListDTO implements Serializable {

	private Integer id;
	private Boolean estado;
	private TipoEmpresa tipoProveedor;
	private Doc tipoDocProveedor;
	private String nroDocProveedor;
	private String observacion;
	private String descripcion;
	private String nombreCompleto;
	private EntidadMinDTO entidad;
	private CtaCteMinDTO cuentaCorriente;

	public ProveedoresListDTO() {
	}

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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public EntidadMinDTO getEntidad() {
		return entidad;
	}

	public void setEntidad(EntidadMinDTO entidad) {
		this.entidad = entidad;
	}

	public CtaCteMinDTO getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CtaCteMinDTO cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

}
