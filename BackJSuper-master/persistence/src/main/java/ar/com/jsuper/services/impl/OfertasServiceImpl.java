package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.OfertasDAO;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Ofertas;
import ar.com.jsuper.domain.OfertasProducto;
import ar.com.jsuper.domain.utils.FilterOferta;
import ar.com.jsuper.domain.utils.FilterOfertaProductos;
import ar.com.jsuper.dto.FechaDTO;
import ar.com.jsuper.dto.FilterOfertaDTO;
import ar.com.jsuper.dto.FilterOfertaProductosDTO;
import ar.com.jsuper.dto.OfertasDTO;
import ar.com.jsuper.dto.OfertasProdDTO;
import ar.com.jsuper.dto.OfertasProductoSinOfertaDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.OfertasService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OfertasServiceImpl implements OfertasService {

	@Autowired
	private OfertasDAO ofertasDAO;
	@Autowired
	private OrikaBeanMapper modelMapper;

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public OfertasDTO insert(OfertasDTO ofertaDTO) throws BussinessException {
		Ofertas oferta = modelMapper.map(ofertaDTO, Ofertas.class);
		ofertasDAO.insert(oferta);
		return modelMapper.map(oferta, OfertasDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public Set<OfertasProductoSinOfertaDTO> insertOfertaProductos(OfertasProdDTO ofertaDTO) {
		Ofertas oferta = modelMapper.map(ofertaDTO, Ofertas.class);
		Set<OfertasProducto> ofertasProducto = ofertasDAO.insertOfertaProductos(oferta);
		return modelMapper.mapAsSet(ofertasProducto, OfertasProductoSinOfertaDTO.class);
	}

	@Override
	public List<OfertasDTO> getAll() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public OfertasDTO get(Integer id) throws BussinessException {
		Ofertas oferta = ofertasDAO.get(id);
		return modelMapper.map(oferta, OfertasDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public OfertasProdDTO getOfertaAndProductos(Integer id) throws BussinessException {
		Ofertas oferta = ofertasDAO.get(id);
		return modelMapper.map(oferta, OfertasProdDTO.class);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<OfertasDTO> getOfertasBypage(FilterOfertaDTO filterOfertaDTO, int page, int pageSize, String fieldOrder, boolean reverse) {
		FilterOferta filterOferta = modelMapper.map(filterOfertaDTO, FilterOferta.class);
		// armamos los filtros extras para los daos
		Pagination<Ofertas> paginacionOfertas = ofertasDAO.getOfertasBypage(filterOferta, page, pageSize, fieldOrder, reverse);
		List<OfertasDTO> lista = new ArrayList<>();
		if (paginacionOfertas.getData() != null) {
			lista = modelMapper.mapAsList(paginacionOfertas.getData(), OfertasDTO.class);
		}
		// controlamos la vigencia
		for (OfertasDTO ofertaDTO : lista) {
			if (filterOferta.getVigente() == 0) {
				ofertaDTO.setVigente(false);
			}
			if (filterOferta.getVigente() == 1) {
				ofertaDTO.setVigente(true);
			}
			if (filterOferta.getVigente() == 2) {
				Date fdesde = ofertaDTO.getFechaDesde();
				Date fhasta = ofertaDTO.getFechaHasta();
				Date hoy = new Date();
				if ((hoy.compareTo(fhasta) < 0 || hoy.compareTo(fhasta) == 0)
						&& (hoy.compareTo(fdesde) > 0) || hoy.compareTo(fdesde) == 0) {
					ofertaDTO.setVigente(true);
				} else {
					ofertaDTO.setVigente(false);
				}
			}
		}
		Pagination<OfertasDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionOfertas.getTotal());
		pag.setPageSize(paginacionOfertas.getPageSize());
		pag.setPage(paginacionOfertas.getPage());
		return pag;
	}

	/**
	 * Devuelve los productos que tienen una oferta
	 *
	 * @param filterOfertaProductosDTO
	 * @param page
	 * @param pageSize
	 * @param fieldOrder
	 * @param reverse
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<OfertasProductoSinOfertaDTO> getOfertasProductosBypage(FilterOfertaProductosDTO filterOfertaProductosDTO, int page, int pageSize, String fieldOrder, boolean reverse) {
		FilterOfertaProductos filterOfertaProductos = modelMapper.map(filterOfertaProductosDTO, FilterOfertaProductos.class);
		Pagination<OfertasProducto> paginacionOfertas = ofertasDAO.getOfertasProductosBypage(filterOfertaProductos, page, pageSize, fieldOrder, reverse);
		List<OfertasProductoSinOfertaDTO> lista = new ArrayList<>();
		if (paginacionOfertas.getData() != null) {
			lista = modelMapper.mapAsList(paginacionOfertas.getData(), OfertasProductoSinOfertaDTO.class);
		}
		Pagination<OfertasProductoSinOfertaDTO> pag = new Pagination<>();
		pag.setData(lista);
		pag.setTotal(paginacionOfertas.getTotal());
		pag.setPageSize(paginacionOfertas.getPageSize());
		pag.setPage(paginacionOfertas.getPage());
		return pag;
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void enabledOrdisabled(Set<OfertasDTO> ofertasDTO, boolean value) {
		Set<Ofertas> ofertas = modelMapper.mapAsSet(ofertasDTO, Ofertas.class);
		ofertasDAO.enabledOrdisabled(ofertas, value);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public void enabledOrdisabledOfertasProductos(Set<OfertasProductoSinOfertaDTO> ofertasProductoSinOfertaDTOs, boolean value) {
		Set<OfertasProducto> ofertas = modelMapper.mapAsSet(ofertasProductoSinOfertaDTOs, OfertasProducto.class);
		ofertasDAO.enabledOrdisabledOfertasProductos(ofertas, value);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public Boolean updatePriridad(Integer idoferta, Map<String, Integer> prioridad) {
		int res = ofertasDAO.updatePriridad(idoferta, prioridad.get("prioridad"));
		return (res > 0);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public Boolean updateFechaHasta(Integer idoferta, FechaDTO fechaDTO) {
		ofertasDAO.updateFechaHasta(idoferta, fechaDTO.getFecha());
		return true;
	}
}
