package ar.com.jsuper.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.dao.ListaPreciosDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.ListaPrecios;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.dto.ImpuestoDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.ListaPreciosDTO;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.services.ListaPreciosService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class ListaPreciosServiceImpl implements ListaPreciosService {

	@Autowired
	private ListaPreciosDAO listaPreciosDAO;
	@Autowired
	private OrikaBeanMapper modelMapper;

	@Override
	@Transactional(readOnly = true)
	public Pagination<ListaPreciosDTO> getListaPreciosByPage(FilterGenericDTO filterGenericDTO, int page, int pageSize, String fieldOrder, boolean reverse) {
		FilterGeneric filterGeneric = modelMapper.map(filterGenericDTO, FilterGeneric.class);
		Pagination<ListaPrecios> listaPrecios = listaPreciosDAO.getListaPreciosByPage(filterGeneric, page, pageSize, fieldOrder, reverse);
		List<ListaPreciosDTO> lista = new ArrayList<>();
		if (listaPrecios.getData() != null) {
			lista = modelMapper.mapAsList(listaPrecios.getData(), ListaPreciosDTO.class);
		}
		Pagination<ListaPreciosDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(listaPrecios.getTotal());
		pag.setPageSize(listaPrecios.getPageSize());
		pag.setPage(listaPrecios.getPage());
		return pag;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ListaPreciosDTO> getAllListasPreciosActive(){
		List<ListaPrecios> contImpuestos = listaPreciosDAO.getAllListActiveId();
		return modelMapper.mapAsList(contImpuestos, ListaPreciosDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public ListaPreciosDTO getListaPrecios(Integer id) {
		return modelMapper.map(listaPreciosDAO.get(id), ListaPreciosDTO.class);
	}

	@Override
	@Transactional()
	public ListaPreciosDTO insertListaPrecios(ListaPreciosDTO listaPreciosDTO) {
		ListaPrecios lista = modelMapper.map(listaPreciosDTO, ListaPrecios.class);
		lista.setFechaCreacion(new Date());
		lista.setApp(new App(TenantContext.getCurrentTenant()));
		return modelMapper.map(listaPreciosDAO.insert(lista), ListaPreciosDTO.class);
	}

	@Override
	@Transactional()
	public ImpuestoDTO updateListaPrecios(Integer id, ListaPreciosDTO listaPreciosDTO) {
		ListaPrecios listaPrecios = listaPreciosDAO.get(id);
		ListaPrecios contImpuestos = modelMapper.map(listaPreciosDTO, ListaPrecios.class);
		return modelMapper.map(listaPreciosDAO.update(listaPrecios, contImpuestos), ImpuestoDTO.class);
	}

	@Override
	@Transactional()
	public void deleteListaPrecios(Integer id) {
		ListaPrecios listaPreciosDB = listaPreciosDAO.get(id);
		try {
			listaPreciosDAO.delete(listaPreciosDB);
		} catch (Exception ex) {
			throw new DataIntegrityViolationException("No se puedo eliminar la lista de precios, porque esta asociado a otros registros.");
		}
	}

	@Override
	@Transactional()
	public void enabledOrdisabledListaPrecios(Set<ListaPreciosDTO> listaPreciosDTO, boolean value) {
		Set<ListaPrecios> listaPrecioses = modelMapper.mapAsSet(listaPreciosDTO, ListaPrecios.class);
		listaPreciosDAO.enabledOrdisabled(listaPrecioses, value);
	}
}
