/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.maps;

import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Telefonos;
import ar.com.jsuper.domain.Domicilios;
import ar.com.jsuper.domain.Contactos;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.dto.PersonasDTO;
import ar.com.jsuper.dto.TelefonosDTO;
import ar.com.jsuper.dto.DomiciliosDTO;
import ar.com.jsuper.dto.ContactosDTO;
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
public class PersonasMap extends CustomMapper<Entidad, PersonasDTO> {

	@Autowired
	private OrikaBeanMapper modelMapper;

	@Override
	public void mapAtoB(Entidad a, PersonasDTO b, MappingContext context) {
		b.setApellido(a.getPersona().getApellido());
		b.setNombre(a.getPersona().getNombre());
		b.setCuil(a.getPersona().getCuil());
		b.setDni(a.getPersona().getDni());
		b.setFechaNac(a.getPersona().getFechaNac());
		b.setFechAlta(a.getPersona().getFechAlta());
		b.setSexo(a.getPersona().getSexo());
		if (Objects.nonNull(b.getTelefonos())) {
			Set<TelefonosDTO> listaT = this.modelMapper.mapAsSet(a.getTelefonos(), TelefonosDTO.class);
			b.setTelefonos(listaT);
		}
		if (Objects.nonNull(b.getDomicilios())) {
			Set<DomiciliosDTO> listaD = this.modelMapper.mapAsSet(a.getDomicilios(), DomiciliosDTO.class);
			b.setDomicilios(listaD);
		}
		if (Objects.nonNull(b.getDomicilios())) {
			Set<ContactosDTO> listaC = this.modelMapper.mapAsSet(a.getContactos(), ContactosDTO.class);
			b.setContactos(listaC);
		}
	}

	@Override
	public void mapBtoA(PersonasDTO b, Entidad a, MappingContext context) {
		a.setId(b.getId());
		a.setFechAlta(b.getFechAlta());
		a.setPersona(new Personas());
		a.getPersona().setApellido(b.getApellido());
		a.getPersona().setNombre(b.getNombre());
		a.getPersona().setCuil(b.getCuil());
		a.getPersona().setDni(b.getDni());
		a.getPersona().setFechaNac(b.getFechaNac());
		a.getPersona().setSexo(b.getSexo());
		if (Objects.nonNull(b.getTelefonos())) {
			Set<Telefonos> listaT = this.modelMapper.mapAsSet(b.getTelefonos(), Telefonos.class);
			a.setTelefonos(listaT);
		}
		if (Objects.nonNull(b.getDomicilios())) {
			Set<Domicilios> listaD = this.modelMapper.mapAsSet(b.getDomicilios(), Domicilios.class);
			a.setDomicilios(listaD);
		}
		if (Objects.nonNull(b.getDomicilios())) {
			Set<Contactos> listaC = this.modelMapper.mapAsSet(b.getContactos(), Contactos.class);
			a.setContactos(listaC);
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
