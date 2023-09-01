/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.maps;

import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.dto.PersonasMinDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import org.springframework.stereotype.Component;

/**
 *
 * @author rafa22
 */
@Component
public class PersonasMinMap extends CustomMapper<Entidad, PersonasMinDTO> {

	@Override
	public void mapAtoB(Entidad a, PersonasMinDTO b, MappingContext context) {
		b.setApellido(a.getPersona().getApellido());
		b.setNombre(a.getPersona().getNombre());
		b.setCuil(a.getPersona().getCuil());
		b.setDni(a.getPersona().getDni());
		b.setFechaNac(a.getPersona().getFechaNac());
		b.setFechAlta(a.getPersona().getFechAlta());
		b.setSexo(a.getPersona().getSexo());
	}
	

}
