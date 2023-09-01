/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.services.SecurityService;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rafa22
 */
@Repository
public class SecurityServiceImpl<T> implements SecurityService<T> {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public void checkIfIdBelognsApp(Integer id, Class<T> clazz) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(clazz, "clase");
        Criterion c1 = Restrictions.eq("id", id);
        c.add(c1);
        /*###########################Control por APP############################*/
        c.setFetchMode("clase.app", FetchMode.JOIN);
        c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        List<T> lista = c.list();
        if (lista.size() != 1) {
            throw new UnauthorizedUserException("Usted no esta autorizado para esta tarea.");
        }
    }

    /**
     * Comprobamos quee un producto es correspondiendo con su app, para
     * producto debmos realizarlo a travez de su producto padre
     * @param id
     * @param clazz
     * @throws BussinessException
     */
    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public void checkIfIdBelognsAppForProducto(Integer id, Class<T> clazz) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(clazz, "clase");
        Criterion c1 = Restrictions.eq("id", id);
        c.add(c1);
        Criteria  criteriaProductos = c.createCriteria("clase.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        criteriaProductos.setFetchMode("productoPadre.app", FetchMode.JOIN);
        /*###########################Control por APP############################*/
        c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductos.add(c1);
        /*######################################################################*/
        List<T> lista = c.list();
        if (lista.size() != 1) {
            throw new UnauthorizedUserException("Usted no esta autorizado para esta tarea.");
        }
    }
}
