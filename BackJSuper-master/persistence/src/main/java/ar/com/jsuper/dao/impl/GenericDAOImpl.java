package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.GenericDAO;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.security.TenantContext;
import java.util.LinkedHashSet;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Repository
public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	public T create() throws BussinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T insert(T entity) {
		Session session = sessionFactory.getCurrentSession();
		session.save(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		Session session = sessionFactory.getCurrentSession();
		session.update(entity);
		return entity;
	}

	@Override
	public T update(T entityOld, T entityNew) {
		return null;
	}

	@Override
	public T get(ID id) {
		Session session = sessionFactory.getCurrentSession();
		T entity = (T) session.get(getEntityClass(), id);
		return entity;
	}

	@Override
	public T getByApp(ID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(getEntityClass());
		Criterion c0 = Restrictions.eq("id", id);
		c.add(c0);
		/*###########################Control por APP############################*/
		c.setFetchMode("app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		return (T) c.uniqueResult();
	}

//    @Override
//    public T getByAppAndMD5(ID id) throws BussinessException {
//        Session session = sessionFactory.getCurrentSession();
//        Criteria c = session.createCriteria(getEntityClass());
//        /*###########################Control por APP############################*/
//        c.setFetchMode("app", FetchMode.JOIN);
//        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
//        c.add(c1);
//        /*######################################################################*/
//        c.add(Restrictions.sqlRestriction("MD5({alias}.id) =", id, StandardBasicTypes.INTEGER));
//        return (T) c.uniqueResult();
//    }
	@Override
	public T load(ID id) {
		Session session = sessionFactory.getCurrentSession();
		T entity = (T) session.load(getEntityClass(), id);
		return entity;
	}

	@Override
	public <C> C load(Class<C> cl, ID id) {
		Session session = sessionFactory.getCurrentSession();
		C entity = (C) session.load(cl, id);
		return entity;
	}

	@Override
	public void delete(T entity) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(entity);
	}

	@Override
	public void deleteByApp(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String deleteHQL = " DELETE " + getEntityClass().getSimpleName() + " where id = :id and app= :idApp";
		session.createQuery(deleteHQL)
				.setParameter("id", id)
				.setParameter("idApp", session.load(App.class, TenantContext.getCurrentTenant()))
				.executeUpdate();
	}

	@Override
	public Set<T> getAll() throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(getEntityClass());
		Set<T> listaSet = new HashSet<T>(c.list());
		return listaSet;
	}

	@Override
	public List<T> getAllList() throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(getEntityClass());
		List<T> listaSet = c.list();
		return listaSet;
	}

	@Override
	public List<T> getAllListActive() throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(getEntityClass());
		Criterion c1 = Restrictions.eq("activo", true);
		Criterion c2 = Restrictions.eq("app", TenantContext.getCurrentTenant());
		c.add(Restrictions.and(c1, c2));
		List<T> listaSet = c.list();
		return listaSet;
	}

	@Override
	public List<T> getAllListActiveId() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(getEntityClass());
		Criterion c1 = Restrictions.eq("activo", true);
		Criterion c2 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(Restrictions.and(c1, c2));
		List<T> listaSet = c.list();
		return listaSet;
	}

	/**
	 * este lo ordena segun el campo orden que tiene las parametricas
	 *
	 * @return
	 * @throws BussinessException
	 */
	@Override
	public List<T> getAllParamListActive() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(getEntityClass());
		Criterion c1 = Restrictions.eq("activo", true);
		c.add(c1);
		c.addOrder(Order.asc("orden"));
		List<T> listaSet = c.list();
		return listaSet;
	}

	@Override
	public Set<T> getAllActive() throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(getEntityClass());
		Criterion c1 = Restrictions.eq("activo", true);
		c.add(c1);
		Set<T> listaSet = new HashSet<T>(c.list());
		return listaSet;
	}

	@Override
	public Set<T> getAllActive(String order) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(getEntityClass(), "clase");
		Criterion c1 = Restrictions.eq("activo", true);
		c.add(c1);
		c.addOrder(Order.asc(order.trim()));
		Set<T> listaSet = new LinkedHashSet<>(c.list());
		return listaSet;
	}

	@Override
	public boolean isIdOnApp(Integer idAPP, Integer idEntity) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(getEntityClass(), "clase");
		Criterion c1 = Restrictions.eq("id", idEntity);
		Criterion c2 = Restrictions.eq("app", idAPP);
		c.add(Restrictions.and(c1, c2));
		List<T> lista = c.list();
		if (lista.size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	protected Class<T> getEntityClass() {
		ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class) thisType.getActualTypeArguments()[0];
	}

	@Override
	public Long getTotalRows() throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(getEntityClass(), "clase");
		c.setProjection(Projections.rowCount());
		return (Long) c.uniqueResult();
	}

	@Override
	public void evict(T entity) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		session.evict(entity);
	}

	@Override
	public T getObject(T usuarioOld, T usuarioNew) {
		return null;
	}
}
