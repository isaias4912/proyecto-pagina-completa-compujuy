package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.StockSucursal;
import java.util.Set;

public interface StockSucursalDAO extends GenericDAO<StockSucursal, Integer> {

    public void saveUpdateOrDelete(Producto producto, Set<StockSucursal> stockSucursales, Set<StockSucursal> stockSucursalesBD) throws BussinessException;

    public StockSucursal getObject(StockSucursal stockSucursalOld, StockSucursal stockSucursalBD);

}
