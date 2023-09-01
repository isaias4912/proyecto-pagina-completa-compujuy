package ar.com.jsuper.dao;

import java.io.Serializable;
import java.util.Set;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.domain.utils.SimpleObject;
import ar.com.jsuper.security.TenantContext;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

public interface GenericDAO<T, ID extends Serializable> {

	T create() throws BussinessException;

	T insert(T entity);

	T update(T entity);

	T get(ID id);

	T getByApp(ID id);

	T update(T entityOld, T entityNew);
//    T getByAppAndMD5(ID id) throws BussinessException;

	T load(ID id);

	<C> C load(Class<C> cl, ID id);

	void delete(T entity);

	void deleteByApp(Integer id);

	void evict(T entity) throws BussinessException;

	Set<T> getAll() throws BussinessException;

	List<T> getAllList() throws BussinessException;

	Set<T> getAllActive() throws BussinessException;

	public List<T> getAllListActiveId();

	public List<T> getAllParamListActive();

	Set<T> getAllActive(String ordeer) throws BussinessException;

	List<T> getAllListActive() throws BussinessException;

	boolean isIdOnApp(Integer idAPP, Integer idEntity) throws BussinessException;

	Long getTotalRows() throws BussinessException;

	public T getObject(T usuarioOld, T usuarioNew);

	default Criteria addRestrictionSucursales(Set<Integer> sucursales, Criteria criteria, Session session) {
		Disjunction disjunctionSuc = Restrictions.disjunction();
		sucursales.forEach((sucursal) -> {
			disjunctionSuc.add(Restrictions.eq("sucursal", session.load(Sucursales.class, sucursal)));
		});
		criteria.add(disjunctionSuc);
		return criteria;
	}

	default Criteria addRestrictionSucursalesObj(Set<SimpleObject> sucursales, Criteria criteria, Session session) {
		Disjunction disjunctionSuc = Restrictions.disjunction();
		sucursales.forEach((sucursal) -> {
			disjunctionSuc.add(Restrictions.eq("sucursal", session.load(Sucursales.class, sucursal.getId())));
		});
		criteria.add(disjunctionSuc);
		return criteria;
	}

	default List<Sucursales> getSucursalesByUser(Session session) {
		StringBuilder hqlUpdate = new StringBuilder("select s from Sucursales s ");
		hqlUpdate.append("join s.usuarios su where su.id= :idUser and s.app = :idApp");
		return session.createQuery(hqlUpdate.toString())
				.setInteger("idApp", TenantContext.getCurrentTenant())
				.setInteger("idUser", TenantContext.getCurrentIdUser())
				.list();
	}
}
