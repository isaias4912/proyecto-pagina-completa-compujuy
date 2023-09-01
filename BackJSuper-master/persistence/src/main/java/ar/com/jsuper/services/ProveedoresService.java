package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.dao.utils.PaginationData;
import ar.com.jsuper.domain.utils.FilterProveedores;
import ar.com.jsuper.dto.FilterMovCtaCteProvDTO;
import ar.com.jsuper.dto.InfoCtaCteProvDTO;
import ar.com.jsuper.dto.MovimientosCtaCteProvDTO;
import ar.com.jsuper.dto.PagarCtaCteProvDTO;
import ar.com.jsuper.dto.PagoPagoCtaCteDTO;
import ar.com.jsuper.dto.PagosCtaCteProvDTO;
import ar.com.jsuper.dto.ProveedoresDTO;
import ar.com.jsuper.dto.ProveedoresListDTO;
import ar.com.jsuper.dto.ProveedoresMinDTO;
import java.util.List;
import java.util.Set;

public interface ProveedoresService {

	Pagination<ProveedoresListDTO> getProveedoresBypage(FilterProveedores filterProveedoresDTO, int numeroPagina, int pagina, String fieldOrder, boolean reverse) throws BussinessException;

	Pagination<ProveedoresListDTO> getProveedoresByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException;

	ProveedoresDTO insertProveedor(ProveedoresDTO proveedoresDTO) throws BussinessException;

	ProveedoresDTO get(Integer id);

	ProveedoresDTO updateProveedor(Integer id, ProveedoresDTO proveedorDTO) throws BussinessException;

	void enabledOrdisabled(Set<ProveedoresDTO> proveedorDTO, boolean value) throws BussinessException;

	void delete(Integer id) throws BussinessException;

	List<ProveedoresMinDTO> getByProveedorMatch(String q);

	MovimientosCtaCteProvDTO pagarCuentaCteProv(MovimientosCtaCteProvDTO pagarCtaCteProvDTO);

	public ProveedoresMinDTO getProveedorMin(Integer id);

	public PaginationData<MovimientosCtaCteProvDTO, InfoCtaCteProvDTO> getMovimientosCtaCte(FilterMovCtaCteProvDTO filter, int page, int pageSize, String fieldOrder, boolean reverse);

	public PagarCtaCteProvDTO pagarPreview(PagarCtaCteProvDTO pagarCtaCteProvDTO);

	public List<MovimientosCtaCteProvDTO> pagar(PagarCtaCteProvDTO pagarCtaCteProvDTO);

	public List<PagosCtaCteProvDTO> getDetailPagosFromMtoCtaCte(Integer idMtoCtaCte);

	public List<PagoPagoCtaCteDTO> getPagosFromIdPagoCtaCte(Integer idPagoCtaCte);

}
