package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.dao.utils.PaginationData;
import ar.com.jsuper.domain.utils.FilterClientes;
import ar.com.jsuper.domain.utils.FilterMovCtaCte;
import ar.com.jsuper.dto.ClienteDTO;
import ar.com.jsuper.dto.ClienteListDTO;
import ar.com.jsuper.dto.ClienteMicDTO;
import ar.com.jsuper.dto.ClienteMinDTO;
import ar.com.jsuper.dto.ClienteMinSinEntDTO;
import ar.com.jsuper.dto.ContactosDTO;
import ar.com.jsuper.dto.CtaCteMinDTO;
import ar.com.jsuper.dto.ExistClienteOrPersonaDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.InfoCtaCteDTO;
import ar.com.jsuper.dto.MovimientosCtaCteDTO;
import ar.com.jsuper.dto.PagarCtaCteDTO;
import ar.com.jsuper.dto.PagoPagoCtaCteDTO;
import ar.com.jsuper.dto.PagosCtaCteDTO;
import java.util.List;
import java.util.Set;

public interface ClientesService {

	Pagination<ClienteListDTO> getClientesBypage(FilterClientes filterClientesDTO, int numeroPagina, int pagina, String fieldOrder, boolean reverse) throws BussinessException;

	Pagination<ClienteListDTO> getClientesByMultipleFilter(String query, Integer activo, Integer page, Integer pageSize, String fieldOrder, Boolean reverse);

	List<ClienteMinDTO> getClientesByNameOrDni(String query, Integer activo);

	ClienteMinDTO getClienteMinById(Integer id);

	ClienteMinSinEntDTO getClienteMinByEntidad(Integer id);

	ClienteMicDTO getClienteMicById(Integer id);

	ClienteDTO insertClienteFromEntidad(ClienteDTO clienteDTO) throws BussinessException;

	ClienteDTO insertCliente(ClienteDTO cliente) throws BussinessException;

	ExistClienteOrPersonaDTO getIsExistClienteOrPersona(String dni) throws BussinessException;

	ClienteDTO get(Integer id) throws BussinessException;

	ClienteDTO updateCliente(Integer id, ClienteDTO clienteDTO) throws BussinessException;

	void enabledOrdisabled(Set<ClienteDTO> clienteDTO, boolean value) throws BussinessException;

	void delete(Integer id) throws BussinessException;

	ClienteDTO getByDni(String dni) throws BussinessException;

	CtaCteMinDTO createCtaCte(Integer id, CtaCteMinDTO ctaCteSinCliente);

	CtaCteMinDTO updateCtaCte(Integer id, CtaCteMinDTO cajaDTO);

	CtaCteMinDTO getCtaCte(Integer id);

	List getListByDniOrName(FilterGenericDTO filter);

	PaginationData<MovimientosCtaCteDTO, InfoCtaCteDTO> getMovimientosCtaCte(FilterMovCtaCte clienteFilter, int page, int pageSize, String fieldOrder, boolean reverse);

	List<MovimientosCtaCteDTO> pagar(PagarCtaCteDTO pagarCtaCteDTO);

	PagarCtaCteDTO pagarPreview(PagarCtaCteDTO pagarCtaCteDTO);

	List<PagosCtaCteDTO> getPagosFromMtoCtaCte(Integer idMtoCtaCte);

	List<PagosCtaCteDTO> getDetailPagosFromMtoCtaCte(Integer idMtoCtaCte);

	List<PagoPagoCtaCteDTO> getPagosFromIdPagoCtaCte(Integer idPagoCtaCte);

	Set<ContactosDTO> getMails(Integer idCliente);

}
