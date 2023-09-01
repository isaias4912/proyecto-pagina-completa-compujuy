package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import java.util.Set;

import ar.com.jsuper.domain.Familias;
import ar.com.jsuper.domain.utils.FilterGeneric;
import java.util.List;

public interface FamiliasDAO extends GenericDAO<Familias, Integer> {

	Set<Familias> getByName(String name);

	Set<Familias> getAllMayorLevel();

	Set<Familias> getAllMayorLevelMin();

	Pagination<Familias> getFamiliasBypage(FilterGeneric familiaFilter, int numeroPagina, int pagina, String fieldOrder, boolean reverse);

	boolean isExistNombreCorto(String nombreCorto);

	public List<Familias> getFamiliasMin(Set<Integer> ids);

}
