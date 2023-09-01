package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.VencimientosProductos;
import ar.com.jsuper.domain.utils.FilterGeneric;
import java.util.Set;

public interface VencimientosProductoDAO extends GenericDAO<VencimientosProductos, Integer> {

    public VencimientosProductos saveVencimientoProducto(VencimientosProductos vencimientosProducto);

    public Pagination<VencimientosProductos> getVencimientosByPage(FilterGeneric filterGeneric, int page, int pageSize, String fieldOrder, boolean reverse);

    public int enabledOrdisabledVencimientos(Set<VencimientosProductos> vencimientos, boolean value);

    public int enabledOrdisabledAlertaVencimientos(VencimientosProductos vencimiento, boolean value);

    public Integer getCountAlerta();

}
