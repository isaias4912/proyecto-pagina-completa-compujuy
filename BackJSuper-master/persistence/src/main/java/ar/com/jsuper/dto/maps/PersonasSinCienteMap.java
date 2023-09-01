/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.maps;

import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.dto.ClienteMapDTO;
import ar.com.jsuper.dto.ContactosDTO;
import ar.com.jsuper.dto.DomiciliosDTO;
import ar.com.jsuper.dto.PersonasListDTO;
import ar.com.jsuper.dto.PersonasSinClienteDTO;
import ar.com.jsuper.dto.TelefonosDTO;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

/**
 *
 * @author rafa22
 */
@Component
public class PersonasSinCienteMap extends CustomMapper<Entidad, PersonasSinClienteDTO> {
	@Autowired
	private OrikaBeanMapper modelMapper;

	@Override
	public void mapAtoB(Entidad a, PersonasSinClienteDTO b, MappingContext context) {
			b.setApellido(a.getPersona().getApellido());
		b.setNombre(a.getPersona().getNombre());
		b.setCuil(a.getPersona().getCuil());
		b.setDni(a.getPersona().getDni());
		b.setFechaNac(a.getPersona().getFechaNac());
		b.setFechAlta(a.getPersona().getFechAlta());
		b.setSexo(a.getPersona().getSexo());
		Set<TelefonosDTO> listaT = this.modelMapper.mapAsSet(a.getTelefonos(), TelefonosDTO.class);
		Set<DomiciliosDTO> listaD = this.modelMapper.mapAsSet(a.getDomicilios(), DomiciliosDTO.class);
		Set<ContactosDTO> listaC = this.modelMapper.mapAsSet(a.getContactos(), ContactosDTO.class);
		b.setTelefonos(listaT);
		b.setContactos(listaC);
		b.setDomicilios(listaD);
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
