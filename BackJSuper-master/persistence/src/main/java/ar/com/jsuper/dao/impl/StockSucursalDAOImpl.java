package ar.com.jsuper.dao.impl;

import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.StockSucursalDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.StockSucursal;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.domain.Usuarios;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Session;

@Repository
public class StockSucursalDAOImpl extends GenericDAOImpl<StockSucursal, Integer> implements StockSucursalDAO {

    public void saveUpdateOrDelete(Producto producto, Set<StockSucursal> stockSucursales, Set<StockSucursal> stockSucursalesBD) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Boolean encontro = false;
        StockSucursal ssTemp = null;
        for (StockSucursal ss : stockSucursales) {
            encontro = false;
            for (StockSucursal stockSucBD : stockSucursalesBD) {
                if (Objects.equals(ss.getId(), stockSucBD.getId())) {
                    encontro = true;
                    ssTemp = this.getObject(stockSucBD, ss);
                }
            }
            if (encontro) {
                session.update(ssTemp);
            } else {
                ss.setProducto(producto);
                session.save(ss);
            }
        }
        for (StockSucursal ssBD : stockSucursalesBD) {
            encontro = false;
            for (StockSucursal ss : stockSucursales) {
                if (Objects.equals(ss.getId(), ssBD.getId())) {
                    encontro = true;
                }
            }
            if (!encontro) {
                session.delete(ssBD);
            }
        }
    }
    public void saveUpdateOrDelete(Usuarios usuario, Set<Sucursales> sucursales, Set<Sucursales> sucursalesBD) throws BussinessException {
          Boolean encontro = false;
        for (Sucursales prov : sucursales) {
            encontro = false;
            for (Sucursales provBD : sucursalesBD) {
                if (Objects.equals(prov.getId(), provBD.getId())) {
                    encontro = true;
                }
            }
            if (!encontro) {
                usuario.getSucursales().add(this.load(Sucursales.class, prov.getId()));
            }
        }
        for (Sucursales provBD : sucursalesBD) {
            encontro = false;
            for (Sucursales prov : sucursales) {
                if (Objects.equals(prov.getId(), provBD.getId())) {
                    encontro = true;
                }
            }
            if (!encontro) {
                usuario.getSucursales().remove(provBD);
            }
        }
    }

    public StockSucursal getObject(StockSucursal stockSucursalOld, StockSucursal stockSucursalBD) {
        if (Objects.isNull(stockSucursalOld)) {
            stockSucursalOld = new StockSucursal();
        }
        stockSucursalOld.setDetalle(stockSucursalBD.getDetalle());
        stockSucursalOld.setPuntoReposicion(stockSucursalBD.getPuntoReposicion());
        stockSucursalOld.setStock(stockSucursalBD.getStock());
        stockSucursalOld.setStockMinimo(stockSucursalBD.getStockMinimo());
        stockSucursalOld.setSucursal(stockSucursalBD.getSucursal());
        stockSucursalOld.setUbicacion(stockSucursalBD.getUbicacion());
//        stockSucursalOld.setUbicacionStocks(stockSucursalBD.getUbicacionStocks());
        return stockSucursalOld;
    }
}
