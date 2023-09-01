/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto.maps;

import ar.com.jsuper.domain.IvasAfip;
import ar.com.jsuper.services.factElec.ws.AlicIva;
import ar.com.jsuper.utils.AfipUtil;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import org.springframework.stereotype.Component;

/**
 *
 * @author rafa22
 */
@Component
public class IvaAfipMap extends CustomMapper<AlicIva, IvasAfip> {

	@Override
	public void mapAtoB(AlicIva a, IvasAfip b, MappingContext context) {
		b.setImporte(a.getImporte());
		b.setBaseImponible(a.getBaseImp());
		b.setIdAfipIva(a.getId());
		b.setAlicuota(AfipUtil.getAlicuotaFromId(a.getId()));
		b.setDescripcion(AfipUtil.getDescripcionFromId(a.getId()));
	}

	@Override
	public void mapBtoA(IvasAfip b, AlicIva a, MappingContext context) {
		a.setBaseImp(b.getBaseImponible());
		a.setId(b.getIdAfipIva());
		a.setImporte(b.getImporte());
	}

}
