package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.CodigoBarras;
import ar.com.jsuper.domain.Producto;
import java.util.Set;

public interface CodigoBarrasDAO extends GenericDAO<CodigoBarras, Integer> {

    public void saveUpdateOrDelete(Producto producto, Set<CodigoBarras> codigoBarras, Set<CodigoBarras> codigoBarrasBD) throws BussinessException;

    public CodigoBarras getObject(CodigoBarras codigoBarrasOld, CodigoBarras codigoBarrasNew);

}
