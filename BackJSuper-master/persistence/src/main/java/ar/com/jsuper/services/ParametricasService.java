package ar.com.jsuper.services;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.dto.ImpuestoDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.FormaPagosDTO;
import java.util.List;
import java.util.Set;

public interface ParametricasService {

	List<FormaPagosDTO> getFormaPagosForProveedores();

	public List<FormaPagosDTO> getListAllActiveForCtaCteCli();

	public List<FormaPagosDTO> getListAllActiveForCtaCteProv();

	public List<FormaPagosDTO> getListAllActiveForcliente();

	public ImpuestoDTO insertContImpuesto(ImpuestoDTO contImpuestosDTO);

	public ImpuestoDTO updateContImpuesto(Integer id, ImpuestoDTO contImpuestosDTO);

	public void deleteContImpuesto(Integer id);

	public void enabledOrdisabledContImpuestos(Set<ImpuestoDTO> contImpuestosDTOs, boolean value);

	public Pagination<ImpuestoDTO> getContImpuestosByPage(FilterGenericDTO filterGenericDTO, int page, int pageSize, String fieldOrder, boolean reverse);

	public List<FormaPagosDTO> getFormaPagosForFacElec();
}
