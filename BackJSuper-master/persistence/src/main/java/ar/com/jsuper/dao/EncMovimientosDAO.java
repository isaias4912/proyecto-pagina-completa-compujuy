package ar.com.jsuper.dao;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.*;
import ar.com.jsuper.domain.utils.FilterGeneric;

import java.math.BigDecimal;

public interface EncMovimientosDAO extends GenericDAO<EncMovimientos, Integer> {

    PaseMovimientos insertPase(PaseMovimientos paseMovimiento);

    EncMovimientos insert(EncMovimientos factura, Boolean validateStock);

    PaseMovimientos updatePase(PaseMovimientos paseMovimiento);

    Pagination<PaseMovimientos> getPasesBypage(FilterGeneric filterGeneric, int page, int pageSize, String fieldOrder, boolean reverse);

    void removePase(PaseMovimientos paseMovimiento);

    StockSucursal getNewStockSucursal(Producto producto, Sucursales suc, BigDecimal cantidadVendida, BigDecimal multiplicadorStock);

    PaseMovimientos getPase(Integer idPase);
}
