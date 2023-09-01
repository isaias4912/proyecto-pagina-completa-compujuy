package ar.com.jsuper.dto;

import java.io.Serializable;

public class PuntoVentaDTO implements Serializable {

	private Integer nro;
	private Integer configuracion;
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
