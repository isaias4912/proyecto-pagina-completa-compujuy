package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.ProductoPadre;
import ar.com.jsuper.domain.utils.FilterProductoPadre;
import java.util.Set;

public interface ProductoPadreDAO extends GenericDAO<ProductoPadre, Integer> {

	Set<ProductoPadre> getByName(String name);

	public ProductoPadre getMin(Integer id);

	public Pagination<ProductoPadre> getProductoPadreByPage(FilterProductoPadre filterProductoPadre, int numeroPagina, int pagina, String fieldOrder, boolean reverse) throws BussinessException;

}
