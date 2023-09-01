/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.maps;

import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.dto.ClienteListDTO;
import ar.com.jsuper.dto.ClienteMinDTO;
import ar.com.jsuper.utils.TipoEntidad;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import org.springframework.stereotype.Component;

/**
 *
 * @author rafa22
 */
@Component
public class ClienteMinMap extends CustomMapper<Cliente, ClienteMinDTO> {

	@Override
	public void mapAtoB(Cliente a, ClienteMinDTO b, MappingContext context) {
		if (a.getEntidad().getTipo().equals(TipoEntidad.EMPRESA)) {
			try {
				b.setDescripcion(a.getEntidad().getEmpresa().getRazonSocial() + " - " + a.getTipoDocCliente().toString() + " " + a.getNroDocCliente());
				b.setNombreCompleto(a.getEntidad().getEmpresa().getRazonSocial());
			} catch (Exception e) {
				b.setNombreCompleto(a.getEntidad().getEmpresa().getRazonSocial());
				b.setDescripcion(a.getEntidad().getEmpresa().getRazonSocial());
			}
		}
		if (a.getEntidad().getTipo().equals(TipoEntidad.PERSONA)) {
			try {
				b.setDescripcion(a.getEntidad().getPersona().getApellido() + " " + a.getEntidad().getPersona().getNombre() + " - " + a.getTipoDocCliente().toString() + " " + a.getNroDocCliente());
				b.setNombreCompleto(a.getEntidad().getPersona().getApellido() + " " + a.getEntidad().getPersona().getNombre());
			} catch (Exception e) {
				b.setDescripcion(a.getEntidad().getPersona().getApellido() + " " + a.getEntidad().getPersona().getNombre());
				b.setNombreCompleto(a.getEntidad().getPersona().getApellido() + " " + a.getEntidad().getPersona().getNombre());
			}
		}
	}

}
