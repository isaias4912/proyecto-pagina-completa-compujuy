package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.RubrosDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Rubros;
import ar.com.jsuper.security.TenantContext;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class RubrosDAOImpl extends GenericDAOImpl<Rubros, Integer> implements RubrosDAO {

    @Override
    public Set<Rubros> getAll() throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Rubros.class, "rubro");
        
        /*###########################Control por APP############################*/
        c.setFetchMode("rubro.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        Set<Rubros> lista = new HashSet<Rubros>(c.list());
        return lista;
    }

}
