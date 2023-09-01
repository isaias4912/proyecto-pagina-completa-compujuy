package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Ofertas;
import ar.com.jsuper.domain.OfertasProducto;
import ar.com.jsuper.domain.utils.FilterOferta;
import ar.com.jsuper.domain.utils.FilterOfertaProductos;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface OfertasDAO extends GenericDAO<Ofertas, Integer> {

    Pagination<Ofertas> getOfertasBypage(FilterOferta filterOferta, int page, int pageSize, String fieldOrder, boolean reverse);

    List<Ofertas> getAllVigentes();

    int enabledOrdisabled(Set<Ofertas> ofertas, boolean value);

    int enabledOrdisabledOfertasProductos(Set<OfertasProducto> ofertasProductos, boolean value);

    int updatePriridad(Integer idOferta, Integer prioridad);

    Ofertas updateFechaHasta(Integer idOferta, Date fechaHasta);

    Ofertas getOfertaAndProductos(Integer id);

    Set<OfertasProducto> insertOfertaProductos(Ofertas ofertasProducto);

    Pagination<OfertasProducto> getOfertasProductosBypage(FilterOfertaProductos filterOferta, int page, int pageSize, String fieldOrder, boolean reverse);

}
