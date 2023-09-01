package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.CbteCompEnc;
import ar.com.jsuper.domain.MovimientosCtaCteProv;
import ar.com.jsuper.domain.utils.FilterFacturas;
import java.util.Map;
import java.util.Set;

public interface CbteCompDAO extends GenericDAO<CbteCompEnc, Integer> {

    public CbteCompEnc insert(CbteCompEnc cbteCompEnc);

    public Pagination<CbteCompEnc> getFacturasByPage(FilterFacturas filterFacturas, int page, int pageSize, String fieldOrder, boolean reverse, Integer tipo);

    public CbteCompEnc getFactura(int id);

    public MovimientosCtaCteProv getMovimientoFromFactura(int id);

    public CbteCompEnc getFacturaByNum(String q);

    public int updateCantidadAgregada(Set<Integer> ids, boolean value);

    public int updateFacturaCompleta(Integer id, Integer value);

    public CbteCompEnc getFacturaByPago(Integer idPago);

    public Pagination<CbteCompEnc> getLibroIvaByPage(FilterFacturas filterFacturas, int page, int pageSize, String fieldOrder, boolean reverse);

    public Map getTotalesLibroIva(FilterFacturas filterCompras);

}
