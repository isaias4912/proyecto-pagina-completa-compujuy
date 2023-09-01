/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.maps;

import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.dto.PersonasListDTO;
import java.util.Objects;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import org.springframework.stereotype.Component;

/**
 *
 * @author rafa22
 */
@Component
public class PersonasListMap extends CustomMapper<Entidad, PersonasListDTO> {

	@Override
	public void mapAtoB(Entidad a, PersonasListDTO b, MappingContext context) {
		b.setApellido(a.getPersona().getApellido());
		b.setNombre(a.getPersona().getNombre());
		b.setCuil(a.getPersona().getCuil());
		b.setDni(a.getPersona().getDni());
		b.setFechaNac(a.getPersona().getFechaNac());
		b.setFechAlta(a.getPersona().getFechAlta());
		b.setSexo(a.getPersona().getSexo());
		b.setIsCliente(Boolean.FALSE);
		if (Objects.nonNull(a.getClientes())) {
			if (a.getClientes().size() > 0) {
				b.setIsCliente(Boolean.TRUE);
			}
		}
	}

//    @Override
//    public void mapAtoB(Cliente a, ClienteMapDTO b, MappingContext context) {
//        b.setApellido(a.getEntidad().getPersona().getApellido());
//        b.setNombre(a.getEntidad().getPersona().getNombre());
//        b.setNombreCompleto(a.getEntidad().getPersona().getApellido() + " " + a.getEntidad().getPersona().getNombre());
//        b.setSearch(a.getEntidad().getPersona().getApellido() + " " + a.getEntidad().getPersona().getNombre() + " - " + a.getEntidad().getPersona().getDni());
//        b.setDni(a.getEntidad().getPersona().getDni());
//        if (Objects.isNull(a.getCuentaCorriente())) {
//            b.setIdCuentaCorriente(null);
//        } else {
//            b.setIdCuentaCorriente(a.getCuentaCorriente().getId());
//        }
//    }
}
