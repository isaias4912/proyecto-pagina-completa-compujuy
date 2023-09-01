/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.maps;

import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.dto.ClienteListDTO;
import ar.com.jsuper.dto.ClienteMinDTO;
import ar.com.jsuper.dto.EmailDTO;
import ar.com.jsuper.utils.TipoEntidad;
import java.util.Objects;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import org.springframework.stereotype.Component;

/**
 *
 * @author rafa22
 */
@Component
public class EmailMap extends CustomMapper<Entidad, EmailDTO> {

	@Override
	public void mapAtoB(Entidad a, EmailDTO b, MappingContext context) {
		if (a.getTipo().equals(TipoEntidad.EMPRESA)) {
			try {
				b.setNombre(a.getEmpresa().getRazonSocial() + " - " + a.getEmpresa().getCuit());
			} catch (Exception e) {
				b.setNombre(a.getEmpresa().getRazonSocial());
			}
		}
		if (a.getTipo().equals(TipoEntidad.PERSONA)) {
			try {
				b.setDescripcion(a.getPersona().getApellido() + " " + a.getPersona().getNombre() + " - " + a.getPersona().getDni());
			} catch (Exception e) {
				b.setDescripcion(a.getPersona().getApellido() + " " + a.getPersona().getNombre());
			}
		}
		if (Objects.nonNull(a.getContactos())){
			
		}
	}

}
