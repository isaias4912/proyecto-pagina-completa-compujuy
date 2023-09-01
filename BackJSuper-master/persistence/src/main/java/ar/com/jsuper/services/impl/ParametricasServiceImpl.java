package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.ContImpuestosDAO;
import ar.com.jsuper.dao.FormasPagoDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Impuesto;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.dto.ImpuestoDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.FormaPagosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.services.ParametricasService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParametricasServiceImpl implements ParametricasService {

	@Autowired
	private OrikaBeanMapper modelMapper;
	@Autowired
	private FormasPagoDAO formasPagoDAO;
	@Autowired
	private ContImpuestosDAO contImpuestosDAO;

	@Override
	@Transactional(readOnly = true)
	public List<FormaPagosDTO> getFormaPagosForProveedores() {
		return modelMapper.mapAsList(formasPagoDAO.getListAllActiveForProveedor(), FormaPagosDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FormaPagosDTO> getFormaPagosForFacElec() {
		return modelMapper.mapAsList(formasPagoDAO.getListAllActiveForFacElec(), FormaPagosDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FormaPagosDTO> getListAllActiveForCtaCteCli() {
		return modelMapper.mapAsList(formasPagoDAO.getListAllActiveForCtaCteCli(), FormaPagosDTO.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<FormaPagosDTO> getListAllActiveForcliente() {
		return modelMapper.mapAsList(formasPagoDAO.getListAllActiveForcliente(), FormaPagosDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FormaPagosDTO> getListAllActiveForCtaCteProv() {
		return modelMapper.mapAsList(formasPagoDAO.getListAllActiveForCtaCteProv(), FormaPagosDTO.class);
	}

	@Override
	@Transactional()
	public ImpuestoDTO insertContImpuesto(ImpuestoDTO contImpuestosDTO) {
		Impuesto contImpuestos = modelMapper.map(contImpuestosDTO, Impuesto.class);
		contImpuestos.setApp(TenantContext.getCurrentTenant());
		return modelMapper.map(contImpuestosDAO.insert(contImpuestos), ImpuestoDTO.class);
	}

	@Override
	@Transactional()
	public ImpuestoDTO updateContImpuesto(Integer id, ImpuestoDTO contImpuestosDTO) {
		Impuesto contImpuestosBD = contImpuestosDAO.get(id);
		Impuesto contImpuestos = modelMapper.map(contImpuestosDTO, Impuesto.class);
		return modelMapper.map(contImpuestosDAO.update(contImpuestosBD, contImpuestos), ImpuestoDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination<ImpuestoDTO> getContImpuestosByPage(FilterGenericDTO filterGenericDTO, int page, int pageSize, String fieldOrder, boolean reverse) {
		FilterGeneric filterGeneric = modelMapper.map(filterGenericDTO, FilterGeneric.class);
		Pagination<Impuesto> paginacionImpuestos = contImpuestosDAO.getContImpuestosByPage(filterGeneric, page, pageSize, fieldOrder, reverse);
		List<ImpuestoDTO> lista = new ArrayList<>();
		if (paginacionImpuestos.getData() != null) {
			lista = modelMapper.mapAsList(paginacionImpuestos.getData(), ImpuestoDTO.class);
		}
		Pagination<ImpuestoDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionImpuestos.getTotal());
		pag.setPageSize(paginacionImpuestos.getPageSize());
		pag.setPage(paginacionImpuestos.getPage());
		return pag;
	}

	@Override
	@Transactional()
	public void deleteContImpuesto(Integer id) {
		Impuesto contImpuestosBD = contImpuestosDAO.get(id);
		try {
			contImpuestosDAO.delete(contImpuestosBD);
		} catch (Exception ex) {
			throw new DataIntegrityViolationException("No se puedo eliminar el impuesto, porque esta asociado a otros registros.");
		}
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void enabledOrdisabledContImpuestos(Set<ImpuestoDTO> contImpuestosDTOs, boolean value) {
		Set<Impuesto> impuestos = modelMapper.mapAsSet(contImpuestosDTOs, Impuesto.class);
		contImpuestosDAO.enabledOrdisabledOfertasProductos(impuestos, value);
	}
}
