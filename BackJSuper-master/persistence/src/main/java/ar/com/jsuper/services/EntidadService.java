package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.utils.FilterEntidad;
import ar.com.jsuper.dto.EmailDTO;
import ar.com.jsuper.dto.EntidadDTO;
import ar.com.jsuper.dto.EntidadMinDTO;
import java.util.List;

public interface EntidadService {

	Pagination<EntidadMinDTO> getEntidadesBypage(FilterEntidad empresaFilter, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException;

	Pagination<EntidadMinDTO> getEntidadesByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse);

	Pagination<EntidadDTO> getEntidadesByMultipleFilterFull(String query, int page, int pageSize, String fieldOrder, boolean reverse);

	List<EntidadMinDTO> getAllEntidadesAndClienteByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse);

	List<EntidadDTO> getAllEntidadesAndClienteByMultipleFilterFull(String query, int page, int pageSize, String fieldOrder, boolean reverse);

	List<EmailDTO> getEntidadesAndEmail(String query);

}
