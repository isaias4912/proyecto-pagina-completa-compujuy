package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.ProductosCompuestoDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.ProductosCompuestos;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ProductosCompuestoDAOImpl extends GenericDAOImpl<ProductosCompuestos, Integer> implements ProductosCompuestoDAO {

    @Override
    public void saveUpdateOrDelete(Producto producto, Set<ProductosCompuestos> productosCompuestos, Set<ProductosCompuestos> productosCompuestosBD) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Boolean encontro = false;
        ProductosCompuestos pcTemp = null;
        for (ProductosCompuestos pcd : productosCompuestos) {
            encontro = false;
            for (ProductosCompuestos pcBD : productosCompuestosBD) {
                if (Objects.equals(pcd.getId(), pcBD.getId())) {
                    encontro = true;
                    pcTemp = this.getObject(pcBD, pcd);
                }
            }
            if (encontro) {
                session.update(pcTemp);
            } else {
                pcd.setProductoPrincipal(producto);
                session.save(pcd);
            }
        }
        for (ProductosCompuestos pcBD : productosCompuestosBD) {
            encontro = false;
            for (ProductosCompuestos pcd : productosCompuestos) {
                if (Objects.equals(pcd.getId(), pcBD.getId())) {
                    encontro = true;
                }
            }
            if (!encontro) {
                session.delete(pcBD);
            }
        }
    }

    @Override
    public Set<ProductosCompuestos> getProdCompuestosFromProductoPrincipal(Producto producto) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ProductosCompuestos.class, "pc");
        Criterion c1 = Restrictions.eq("pc.productoPrincipal", producto);
        c.add(c1);
        Set<ProductosCompuestos> listaSet = new HashSet<>(c.list());
        return listaSet;
    }

    @Override
    public Set<ProductosCompuestos> getProdCompuestosFromProducto(Producto producto) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ProductosCompuestos.class, "pc");
        Criterion c1 = Restrictions.eq("pc.producto", producto);
        c.add(c1);
        Set<ProductosCompuestos> listaSet = new HashSet<>(c.list());
        return listaSet;
    }

    @Override
    public ProductosCompuestos getObject(ProductosCompuestos productosCompuestoOld, ProductosCompuestos productosCompuestoNew) {
        if (Objects.isNull(productosCompuestoOld)) {
            productosCompuestoOld = new ProductosCompuestos();
        }
        productosCompuestoOld.setCantidad(productosCompuestoNew.getCantidad());
        productosCompuestoOld.setDescripcion(productosCompuestoNew.getDescripcion());
        productosCompuestoOld.setProducto(productosCompuestoNew.getProducto());
        return productosCompuestoOld;
    }
}
