package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.*;
import ar.com.jsuper.domain.utils.FilterGeneric;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface MovimientosDAO extends GenericDAO<DetMovimientos, Integer> {

	Pagination<DetMovimientos> getMovimientosByProducto(List<Producto> productos, int page, int pageSize, String fieldOrder, boolean reverse);

	Pagination<HistPrecios> getMovimientosPrecioByProducto(Producto producto, int page, int pageSize, String fieldOrder, boolean reverse);

	Pagination<MovimientosProducto> getMovimientosProductoByProducto(List<Producto> productos, int page, int pageSize, String fieldOrder, boolean reverse, FilterGeneric filterGeneric);

	List<EncMovimientos> getFromFactura(Integer idFactura);
	void saveMovimientoProducto(Set<CbteDet> detalleVentas, Date fecha, StockSucursal ss);
}
