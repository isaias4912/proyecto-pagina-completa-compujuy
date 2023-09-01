/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.maps;

import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.dto.EmpresasListDTO;
import ar.com.jsuper.dto.PersonasNanoDTO;
import java.util.Objects;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

/**
 *
 * @author rafa22
 */
@Component
public class EmpresasListMap extends CustomMapper<Entidad, EmpresasListDTO> {

	@Autowired
	private OrikaBeanMapper modelMapper;

	@Override
	public void mapAtoB(Entidad a, EmpresasListDTO b, MappingContext context) {
		b.setRazonSocial(a.getEmpresa().getRazonSocial());
		b.setCuit(a.getEmpresa().getCuit());
		b.setObservacion(a.getEmpresa().getObservacion());
		b.setTipoEmpresa(a.getEmpresa().getTipoEmpresa());
		if (Objects.nonNull(a.getEmpresa().getPersona())) {
			PersonasNanoDTO p = this.modelMapper.map(a.getEmpresa().getPersona(), PersonasNanoDTO.class);
			b.setPersona(p);
		}
		b.setIsCliente(Boolean.FALSE);
		if (Objects.nonNull(a.getClientes())) {
			if (a.getClientes().size() > 0) {
				b.setIsCliente(Boolean.TRUE);
			}
		}
	}

}
