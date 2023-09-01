/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.maps;

import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.dto.ClienteMapDTO;
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
public class ClientesSearchMap extends CustomMapper<Cliente, ClienteMapDTO> {

	@Override
	public void mapAtoB(Cliente a, ClienteMapDTO b, MappingContext context) {
		if (a.getEntidad().getTipo().equals(TipoEntidad.EMPRESA)) {
			b.setRazonSocial(a.getEntidad().getEmpresa().getRazonSocial());
			b.setDescripcion(a.getEntidad().getEmpresa().getRazonSocial() + " - " + a.getTipoDocCliente().toString() + " " + a.getNroDocCliente());
		}
		if (a.getEntidad().getTipo().equals(TipoEntidad.PERSONA)) {
			b.setDescripcion(a.getEntidad().getPersona().getApellido() + " " + a.getEntidad().getPersona().getNombre() + " - " + a.getTipoDocCliente().toString() + " " + a.getNroDocCliente());
			b.setApellido(a.getEntidad().getPersona().getApellido());
			b.setNombre(a.getEntidad().getPersona().getNombre());
			b.setSearch(a.getEntidad().getPersona().getApellido() + " " + a.getEntidad().getPersona().getNombre() + " - " + a.getEntidad().getPersona().getDni());
		}
		b.setTipoCliente(a.getTipoCliente());
		b.setTipoDocCliente(a.getTipoDocCliente());
		b.setNroDocCliente(a.getNroDocCliente());
		b.setDni(a.getEntidad().getPersona().getDni());
		if (Objects.isNull(a.getCuentaCorriente())) {
			b.setIdCuentaCorriente(null);
		} else {
			b.setIdCuentaCorriente(a.getCuentaCorriente().getId());
		}
	}
}
