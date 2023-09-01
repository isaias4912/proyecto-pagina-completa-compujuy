package ar.com.jsuper.dao.impl;

import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.CodigoBarrasDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.CodigoBarras;
import ar.com.jsuper.domain.Producto;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Session;

@Repository
public class CodigoBarrasDAOImpl extends GenericDAOImpl<CodigoBarras, Integer> implements CodigoBarrasDAO {

    public void saveUpdateOrDelete(Producto producto, Set<CodigoBarras> codigoBarras, Set<CodigoBarras> codigoBarrasBD) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Boolean encontro = false;
        CodigoBarras cbTemp = null;
        for (CodigoBarras cb : codigoBarras) {
            encontro = false;
            for (CodigoBarras cbBD : codigoBarrasBD) {
                if (Objects.equals(cb.getId(), cbBD.getId())) {
                    encontro = true;
                    cbTemp = this.getObject(cbBD, cb);
                }
            }
            if (encontro) {
                session.update(cbTemp);
            } else {
                cb.setProducto(producto);
                session.save(cb);
            }
        }
        for (CodigoBarras cbBD : codigoBarrasBD) {
            encontro = false;
            for (CodigoBarras cb : codigoBarras) {
                if (Objects.equals(cb.getId(), cbBD.getId())) {
                    encontro = true;
                }
            }
            if (!encontro) {
                session.delete(cbBD);
            }
        }
    }

    public CodigoBarras getObject(CodigoBarras codigoBarrasOld, CodigoBarras codigoBarrasNew) {
        if (Objects.isNull(codigoBarrasOld)) {
            codigoBarrasOld = new CodigoBarras();
        }
        codigoBarrasOld.setNombre(codigoBarrasNew.getNombre());
        codigoBarrasOld.setCantidadBulto(codigoBarrasNew.getCantidadBulto());
        codigoBarrasOld.setCodigo(codigoBarrasNew.getCodigo());
        codigoBarrasOld.setDescripcion(codigoBarrasNew.getDescripcion());
        codigoBarrasOld.setTipo(codigoBarrasNew.getTipo());
        return codigoBarrasOld;
    }
}
