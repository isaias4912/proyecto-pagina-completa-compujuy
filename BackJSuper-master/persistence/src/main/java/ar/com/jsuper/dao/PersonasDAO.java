package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.domain.utils.FilterPersonas;

public interface PersonasDAO extends GenericDAO<Personas, Integer> {

	Pagination<Entidad> getPersonasBypage(FilterPersonas personaFilter, int page, int pageSize, String fieldOrder, boolean reverse);

	Pagination<Personas> getPersonasByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse);

	Personas getByDni(String dni) throws BussinessException;

	Personas getObject(Personas personaOld, Personas personaNew);

	Boolean isCliente(Integer id);
}
