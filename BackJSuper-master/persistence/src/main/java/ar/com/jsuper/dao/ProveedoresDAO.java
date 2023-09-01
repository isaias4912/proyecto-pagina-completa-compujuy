package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.Proveedores;
import ar.com.jsuper.domain.utils.FilterProveedores;
import java.util.List;
import java.util.Set;

public interface ProveedoresDAO extends GenericDAO<Proveedores, Integer> {

    Pagination<Proveedores> getProveedoresBypage(FilterProveedores proveedorFilter, int page, int pageSize, String fieldOrder, boolean reverse);

    Pagination<Proveedores> getProveedoresByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse);

    Proveedores getObject(Proveedores proveedorOld, Proveedores proveedorNew);

    Proveedores updateProveedor(Proveedores proveedorOld, Proveedores proveedorNew);

    void saveUpdateOrDelete(Producto producto, Set<Proveedores> proveedores, Set<Proveedores> proveedoresBD) throws BussinessException;

    public List<Proveedores> getByProveedorMatch(String q);

    public Proveedores getProveedorMin(Integer id);

}
