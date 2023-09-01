/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import ar.com.jsuper.domain.Entidad;

/**
 *
 * @author rafa
 */
public class UtilEntidad {

	public static String getDescripcion(Entidad entidad) {
		if (entidad.getTipo().equals(TipoEntidad.EMPRESA)) {
			try {
				return entidad.getEmpresa().getRazonSocial() + " - " + entidad.getEmpresa().getCuit();
			} catch (Exception e) {
				return entidad.getEmpresa().getRazonSocial();
			}
		}
		if (entidad.getTipo().equals(TipoEntidad.PERSONA)) {
			try {
				return entidad.getPersona().getApellido() + " " + entidad.getPersona().getNombre() + " - " + entidad.getPersona().getDni();
			} catch (Exception e) {
				return entidad.getPersona().getApellido() + " " + entidad.getPersona().getNombre();
			}
		}
		return "";
	}

}
