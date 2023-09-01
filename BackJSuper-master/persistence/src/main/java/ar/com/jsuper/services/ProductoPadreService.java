package ar.com.jsuper.services;

import java.util.Set;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.dto.FilterProductoPadreDTO;
import ar.com.jsuper.dto.ProductoPadreDTO;
import ar.com.jsuper.dto.ProductoPadreSinProductoDTO;

public interface ProductoPadreService {

	Set<ProductoPadreDTO> getByName(String name) throws BussinessException;

	ProductoPadreSinProductoDTO getMin(Integer id);

	Pagination<ProductoPadreDTO> getProductoPadreByPage(FilterProductoPadreDTO filterProductoPadreDTO, int numeroPagina, int pagina, String fieldOrder, boolean reverse) throws BussinessException;

	ProductoPadreDTO insert(ProductoPadreDTO producto) throws BussinessException;

	ProductoPadreDTO update(ProductoPadreDTO producto) throws BussinessException;

	ProductoPadreDTO get(Integer id) throws BussinessException;

	void delete(ProductoPadreDTO productoPadreDTO);
}
