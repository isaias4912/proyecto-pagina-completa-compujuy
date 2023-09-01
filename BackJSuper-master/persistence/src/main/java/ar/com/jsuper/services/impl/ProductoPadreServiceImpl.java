package ar.com.jsuper.services.impl;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.ProductoPadreDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.ProductoPadre;
import ar.com.jsuper.domain.utils.FilterProductoPadre;
import ar.com.jsuper.dto.FilterProductoPadreDTO;
import ar.com.jsuper.dto.ProductoPadreDTO;
import ar.com.jsuper.dto.ProductoPadreSinProductoDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.ProductoPadreService;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoPadreServiceImpl implements ProductoPadreService {

	@Autowired
	private ProductoPadreDAO productoPadreDAO;

	@Autowired
	private OrikaBeanMapper modelMapper;

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Set<ProductoPadreDTO> getByName(String name) throws BussinessException {
		Set<ProductoPadre> productosPadre = productoPadreDAO.getByName(name);
		Set<ProductoPadreDTO> productosPadreDTO = null;
		productosPadreDTO = modelMapper.mapAsSet(productosPadre, ProductoPadreDTO.class);
		return productosPadreDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public ProductoPadreSinProductoDTO getMin(Integer id){
		ProductoPadre productosPadre = productoPadreDAO.getMin(id);
		ProductoPadreSinProductoDTO productosPadreDTO = modelMapper.map(productosPadre, ProductoPadreSinProductoDTO.class);
		return productosPadreDTO;
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public ProductoPadreDTO insert(ProductoPadreDTO productoPadreDTO) throws BussinessException {
		ProductoPadre productoPadre = modelMapper.map(productoPadreDTO, ProductoPadre.class);
		productoPadre = productoPadreDAO.insert(productoPadre);
		productoPadreDTO = modelMapper.map(productoPadre, ProductoPadreDTO.class);
		return productoPadreDTO;
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class)
	public ProductoPadreDTO update(ProductoPadreDTO productoPadreDTO) throws BussinessException {
		ProductoPadre productoPadre = modelMapper.map(productoPadreDTO, ProductoPadre.class);
		productoPadre = productoPadreDAO.update(productoPadre);
		productoPadreDTO = modelMapper.map(productoPadre, ProductoPadreDTO.class);
		return productoPadreDTO;
	}

	@Override
	@Transactional()
	public void delete(ProductoPadreDTO productoPadreDTO) {
		ProductoPadre productoPadre = modelMapper.map(productoPadreDTO, ProductoPadre.class);
		productoPadreDAO.delete(productoPadre);
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public ProductoPadreDTO get(Integer id) throws BussinessException {
		ProductoPadre productoPadre = productoPadreDAO.getByApp(id);
		if (productoPadre == null) {
			return null;
		} else {
			ProductoPadreDTO productoPadreDTO = modelMapper.map(productoPadre, ProductoPadreDTO.class);
			return productoPadreDTO;
		}
	}

	@Override
	@Transactional(rollbackFor = BussinessException.class, readOnly = true)
	public Pagination<ProductoPadreDTO> getProductoPadreByPage(FilterProductoPadreDTO filterProductoPadreDTO, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
		FilterProductoPadre filterProductoPadre = modelMapper.map(filterProductoPadreDTO, FilterProductoPadre.class);
		// armamos los filtros extras para los daos
		Pagination<ProductoPadre> paginacionProductosPadre = productoPadreDAO.getProductoPadreByPage(filterProductoPadre, page, pageSize,
				fieldOrder, reverse);
		List<ProductoPadre> detalleProductos = paginacionProductosPadre.getData();
		List<ProductoPadreDTO> detalleProductosDTO = new ArrayList<>();
		if (detalleProductos != null) {
			detalleProductosDTO = modelMapper.mapAsList(detalleProductos, ProductoPadreDTO.class);
		}
		Pagination<ProductoPadreDTO> pag = new Pagination<>();
		pag.setData(detalleProductosDTO);
		pag.setTotal(paginacionProductosPadre.getTotal());
		pag.setPageSize(paginacionProductosPadre.getPageSize());
		pag.setPage(paginacionProductosPadre.getPage());
		return pag;
	}

}
