package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.ProductosCompuestos;
import java.util.Set;

public interface ProductosCompuestoDAO extends GenericDAO<ProductosCompuestos, Integer> {

    public void saveUpdateOrDelete(Producto producto, Set<ProductosCompuestos> productosCompuestos, Set<ProductosCompuestos> productosCompuestosBD) throws BussinessException;

    public Set<ProductosCompuestos> getProdCompuestosFromProductoPrincipal(Producto producto);

    public Set<ProductosCompuestos> getProdCompuestosFromProducto(Producto producto);

    public ProductosCompuestos getObject(ProductosCompuestos productosCompuestoOld, ProductosCompuestos productosCompuestoNew);

}
