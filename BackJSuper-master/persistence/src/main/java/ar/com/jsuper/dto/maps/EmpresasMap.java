/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.maps;

import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Telefonos;
import ar.com.jsuper.domain.Domicilios;
import ar.com.jsuper.domain.Contactos;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.dto.TelefonosDTO;
import ar.com.jsuper.dto.DomiciliosDTO;
import ar.com.jsuper.dto.ContactosDTO;
import ar.com.jsuper.dto.EmpresasDTO;
import ar.com.jsuper.dto.PersonasNanoDTO;
import ar.com.jsuper.utils.TipoEntidad;
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
public class EmpresasMap extends CustomMapper<Entidad, EmpresasDTO> {

	@Autowired
	private OrikaBeanMapper modelMapper;

@Override
	public void mapAtoB(Entidad a, EmpresasDTO b, MappingContext context) {
		b.setRazonSocial(a.getEmpresa().getRazonSocial());
		b.setCuit(a.getEmpresa().getCuit());
		b.setObservacion(a.getEmpresa().getObservacion());
		b.setTipoEmpresa(a.getEmpresa().getTipoEmpresa());
		if (Objects.nonNull(a.getEmpresa().getPersona())) {
			PersonasNanoDTO p = this.modelMapper.map(a.getEmpresa().getPersona(), PersonasNanoDTO.class);
			b.setPersona(p);
		}
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
	public void mapBtoA(EmpresasDTO b, Entidad a, MappingContext context) {
		a.setId(b.getId());
		a.setEmpresa(new Empresas());
		a.getEmpresa().setRazonSocial(b.getRazonSocial());
		a.getEmpresa().setCuit(b.getCuit());
		a.getEmpresa().setObservacion(b.getObservacion());
		a.getEmpresa().setTipoEmpresa(b.getTipoEmpresa());
		a.setTipo(TipoEntidad.EMPRESA);
		if (Objects.nonNull(b.getPersona())) {
			Personas p = this.modelMapper.map(b.getPersona(), Personas.class);
			a.getEmpresa().setPersona(p);
		}

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
}
