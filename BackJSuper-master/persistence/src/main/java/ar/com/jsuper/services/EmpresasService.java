package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.utils.FilterEntidad;
import ar.com.jsuper.dto.EmpresasDTO;
import ar.com.jsuper.dto.EmpresasListDTO;
import ar.com.jsuper.dto.EmpresasMinDTO;
import java.util.List;

public interface EmpresasService {

	Pagination<EmpresasListDTO> getEmpresasBypage(FilterEntidad filterEmpresasDTO, int numeroPagina, int pagina, String fieldOrder, boolean reverse) throws BussinessException;

	Pagination<EmpresasDTO> getEmpresasByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException;

	EmpresasDTO insertEmpresa(EmpresasDTO empresasDTO) throws BussinessException;

	EmpresasDTO get(Integer id);

	EmpresasDTO updateEmpresa(Integer id, EmpresasDTO empresaDTO) throws BussinessException;

	void delete(Integer id) throws BussinessException;

	List<EmpresasMinDTO> getByEmpresaMatch(String q);

	public EmpresasMinDTO getEmpresaMin(Integer id);

	public EmpresasMinDTO getByCuit(String cuit);
}
