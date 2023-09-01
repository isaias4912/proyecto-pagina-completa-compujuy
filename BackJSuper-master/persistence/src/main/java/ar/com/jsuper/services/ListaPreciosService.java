package ar.com.jsuper.services;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.dto.ImpuestoDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.ListaPreciosDTO;
import java.util.List;
import java.util.Set;

public interface ListaPreciosService {

	public Pagination<ListaPreciosDTO> getListaPreciosByPage(FilterGenericDTO filterGenericDTO, int page, int pageSize, String fieldOrder, boolean reverse);

	public ListaPreciosDTO getListaPrecios(Integer id);

	public ListaPreciosDTO insertListaPrecios(ListaPreciosDTO listaPreciosDTO);

	public ImpuestoDTO updateListaPrecios(Integer id, ListaPreciosDTO listaPreciosDTO);

	public void deleteListaPrecios(Integer id);

	public List<ListaPreciosDTO> getAllListasPreciosActive();

	public void enabledOrdisabledListaPrecios(Set<ListaPreciosDTO> listaPreciosDTO, boolean value);

}
