package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.ListaPrecios;
import ar.com.jsuper.domain.utils.FilterGeneric;
import java.util.Set;

public interface ListaPreciosDAO extends GenericDAO<ListaPrecios, Integer> {

	public Pagination<ListaPrecios> getListaPreciosByPage(FilterGeneric filterGeneric, int page, int pageSize, String fieldOrder, boolean reverse);

	public int enabledOrdisabled(Set<ListaPrecios> impuestos, boolean value);

}
