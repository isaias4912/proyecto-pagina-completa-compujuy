/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.io.Serializable;
import javax.persistence.Column;

/**
 *
 * @author rafa
 */
public class PuntoVentaPK implements Serializable {

	@Column(name = "nro")
	private Integer nro;

	@Column(name = "configuracion_id")
	private Integer configuracion;

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

	@Override
	public int hashCode() {
		return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
	}
}
