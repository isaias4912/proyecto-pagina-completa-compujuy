package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.TipoPrecios;
import ar.com.jsuper.dto.TipoPreciosDTO;
import java.util.Set;

public interface TipoPreciosService {
	
	void insert(TipoPrecios tipoPrecio) throws BussinessException;
	TipoPrecios get(Integer id) throws BussinessException;
        public Set<TipoPreciosDTO> getAllActive() throws BussinessException;
}
