package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.FormasPagoDAO;
import ar.com.jsuper.domain.FormaPagos;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class FormasPagoDAOImpl extends GenericDAOImpl<FormaPagos, Integer> implements FormasPagoDAO {

    @Override
    public List<FormaPagos> getListAllActive() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(FormaPagos.class, "f");
        Criterion c1 = Restrictions.eq("f.activo", true);
        c.add(c1);
        c.addOrder(Order.asc("id"));
        return c.list();
    }

    @Override
    public List<FormaPagos> getListAllActiveForProveedor() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(FormaPagos.class, "f");
        Criterion c1 = Restrictions.eq("f.activo", true);
        c.add(c1);
        c1 = Restrictions.eq("f.proveedor", true);
        c.add(c1);
        c.addOrder(Order.asc("id"));
        return c.list();
    }
    @Override
    public List<FormaPagos> getListAllActiveForcliente() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(FormaPagos.class, "f");
        Criterion c1 = Restrictions.eq("f.activo", true);
        c.add(c1);
        c1 = Restrictions.eq("f.cliente", true);
        c.add(c1);
        c.addOrder(Order.asc("id"));
        return c.list();
    }
    @Override
    public List<FormaPagos> getListAllActiveForCtaCteCli() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(FormaPagos.class, "f");
        Criterion c1 = Restrictions.eq("f.activo", true);
        c.add(c1);
        c1 = Restrictions.eq("f.ctaCteCli", true);
        c.add(c1);
        c.addOrder(Order.asc("id"));
        return c.list();
    }
    @Override
    public List<FormaPagos> getListAllActiveForCtaCteProv() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(FormaPagos.class, "f");
        Criterion c1 = Restrictions.eq("f.activo", true);
        c.add(c1);
        c1 = Restrictions.eq("f.ctaCteProv", true);
        c.add(c1);
        c.addOrder(Order.asc("id"));
        return c.list();
    }
    @Override
    public List<FormaPagos> getListAllActiveForFacElec() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(FormaPagos.class, "f");
        Criterion c1 = Restrictions.eq("f.activo", true);
        c.add(c1);
        c1 = Restrictions.eq("f.facElec", true);
        c.add(c1);
        c.addOrder(Order.asc("id"));
        return c.list();
    }

}
