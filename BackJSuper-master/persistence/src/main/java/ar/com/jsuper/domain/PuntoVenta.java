package ar.com.jsuper.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "puntos_venta")
@IdClass(PuntoVentaPK.class)
public class PuntoVenta implements Serializable {

	@Id
	@Column(name = "nro")
	private Integer nro;
	@Id
	@Column(name = "configuracion_id")
	private Integer configuracion;
	@Column(name = "descripcion")
	private String descripcion;

	public Integer getNro() {
		return nro;
	}

	public void setNro(Integer nro) {
		this.nro = nro;
	}

	public Integer getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(Integer configuracion) {
		this.configuracion = configuracion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
