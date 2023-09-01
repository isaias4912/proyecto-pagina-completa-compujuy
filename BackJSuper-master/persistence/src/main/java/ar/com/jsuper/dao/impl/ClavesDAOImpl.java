package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.ClavesDAO;
import ar.com.jsuper.domain.Claves;
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.security.TenantContext;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

@Repository
public class ClavesDAOImpl extends GenericDAOImpl<Claves, Integer> implements ClavesDAO {

    @Override
    public Claves getByClave(String clave) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaClave = session.createCriteria(Claves.class, "clave");
        criteriaClave.createCriteria("clave.usuario", "usuario", JoinType.LEFT_OUTER_JOIN);
        criteriaClave.createCriteria("usuario.persona", "persona", JoinType.LEFT_OUTER_JOIN);
        /*###########################Control por APP############################*/
        criteriaClave.setFetchMode("clave.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaClave.add(c1);
        /*######################################################################*/
        // verificamos si existe el deni en personas para usuariodddyyyyyyyyyyfffffdddddddddddddddddddddddd
        c1 = Restrictions.eq("clave", clave);
        criteriaClave.add(c1);
        List list = criteriaClave.list();
        if (list.size() >= 1) {
            return (Claves) list.get(0);
        } else {
            return null;
        }
    }
}
