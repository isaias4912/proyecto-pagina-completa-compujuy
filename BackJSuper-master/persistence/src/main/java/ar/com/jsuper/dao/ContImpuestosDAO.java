package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Impuesto;
import ar.com.jsuper.domain.utils.FilterGeneric;
import java.util.Set;

public interface ContImpuestosDAO extends GenericDAO<Impuesto, Integer> {

    public Pagination<Impuesto> getContImpuestosByPage(FilterGeneric filterGeneric, int page, int pageSize, String fieldOrder, boolean reverse);

    int enabledOrdisabledOfertasProductos(Set<Impuesto> impuestos, boolean value);

}
