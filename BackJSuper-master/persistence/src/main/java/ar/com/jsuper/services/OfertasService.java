package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.dto.FechaDTO;
import ar.com.jsuper.dto.FilterOfertaDTO;
import ar.com.jsuper.dto.FilterOfertaProductosDTO;
import ar.com.jsuper.dto.OfertasDTO;
import ar.com.jsuper.dto.OfertasProdDTO;
import ar.com.jsuper.dto.OfertasProductoSinOfertaDTO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OfertasService {

    OfertasDTO insert(OfertasDTO ofertaDTO) throws BussinessException;

    Set<OfertasProductoSinOfertaDTO> insertOfertaProductos(OfertasProdDTO oferta);

    List<OfertasDTO> getAll();

    Pagination<OfertasDTO> getOfertasBypage(FilterOfertaDTO filterOfertaDTO, int page, int pageSize, String fieldOrder, boolean reverse);

    Pagination<OfertasProductoSinOfertaDTO> getOfertasProductosBypage(FilterOfertaProductosDTO filterOfertaProductosDTO, int page, int pageSize, String fieldOrder, boolean reverse);

    void enabledOrdisabled(Set<OfertasDTO> ofertasDTO, boolean value);

    void enabledOrdisabledOfertasProductos(Set<OfertasProductoSinOfertaDTO> ofertasProductoSinOfertaDTOs, boolean value);

    OfertasDTO get(Integer id) throws BussinessException;

    OfertasProdDTO getOfertaAndProductos(Integer id) throws BussinessException;

    Boolean updatePriridad(Integer idoferta, Map<String, Integer> prioridad);

    Boolean updateFechaHasta(Integer idoferta, FechaDTO fechaDTO);

}
