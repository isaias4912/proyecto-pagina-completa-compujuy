package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.Contactos;
import ar.com.jsuper.domain.utils.FilterClientes;
import ar.com.jsuper.domain.utils.FilterGeneric;
import java.util.List;
import java.util.Set;

public interface ClientesDAO extends GenericDAO<Cliente, Integer> {

	Pagination<Cliente> getClientesBypage(FilterClientes clienteFilter, int page, int pageSize, String fieldOrder, boolean reverse);

	List<Cliente> getClientesByNameOrDni(String query, Integer activo);

	Pagination<Cliente> getClientesByMultipleFilter(String query, Integer activo, Integer page, Integer pageSize, String fieldOrder, Boolean reverse);

	Cliente getClienteMinByEntidad(Integer id);

	Cliente getClienteMinById(Integer id);

	Cliente getClienteMicById(Integer id);

	Set<Contactos> getContactos(Integer clienteId, Integer tipo);

	Cliente getByDni(String dni);

	Cliente getOnlyClienteByDni(String dni);

	List getListByDniOrName(FilterGeneric filter);

}
