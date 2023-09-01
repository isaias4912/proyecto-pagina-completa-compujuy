package ar.com.jsuper.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.FamiliasDAO;
import ar.com.jsuper.dao.ProductoDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Familias;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.security.TenantContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class FamiliasDAOImpl extends GenericDAOImpl<Familias, Integer> implements FamiliasDAO {

	@Autowired
	ProductoDAO productoDAO;

	@Override
	public Set<Familias> getByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Familias.class, "familia");
		Criterion c1 = Restrictions.like("familia.nombre", name, MatchMode.ANYWHERE);
		c.add(c1);
		/*###########################Control por APP############################*/
		c.setFetchMode("familia.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		Set<Familias> lista = new HashSet<>(c.list());
		return lista;
	}

	@Override
	public Set<Familias> getAllMayorLevel() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Familias.class, "familia");
		Criterion c1 = Restrictions.eq("familia.nivel", 1);
		c.add(c1);
		c.setFetchMode("familia.familias", FetchMode.SELECT);
		/*###########################Control por APP############################*/
		c.setFetchMode("familia.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/

		Set<Familias> lista = new HashSet<>(c.list());
		return lista;
	}

	@Override
	public Set<Familias> getAllMayorLevelMin() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Familias.class, "familia");
		/*###########################Control por APP############################*/
		c.setFetchMode("familia.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		Set<Familias> lista = new HashSet<>(c.list());
		return lista;
	}

	@Override
	public Pagination<Familias> getFamiliasBypage(FilterGeneric familiaFilter, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaFamilia = session.createCriteria(Familias.class, "familia");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaFamilia.setProjection(idCountProjection);
		Criterion c1;
		Criterion c2;
		if (familiaFilter.getText() != null && !familiaFilter.getText().trim().equals("")) {
			c1 = Restrictions.like("familia.nombre", familiaFilter.getText(), MatchMode.ANYWHERE);
			c2 = Restrictions.like("familia.nombreCorto", familiaFilter.getText(), MatchMode.ANYWHERE);
			criteriaFamilia.add(Restrictions.or(c1, c2));
		}
		// nos fijamos el id de la flia
		if (!Objects.isNull(familiaFilter.getId())) {
			if (familiaFilter.getId() > 0) {
				criteriaFamilia.add(Restrictions.eq("id", familiaFilter.getId()));
			}
		}
		/*###########################Control por APP############################*/
		criteriaFamilia.setFetchMode("familia.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaFamilia.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaFamilia.uniqueResult()).intValue();

		if (reverse) {
			criteriaFamilia.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaFamilia.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaFamilia.setProjection(Projections.distinct(Projections.property("id")));
		criteriaFamilia.setFirstResult((page - 1) * pageSize);
		criteriaFamilia.setMaxResults(pageSize);
		List uniqueSubList = criteriaFamilia.list();
		criteriaFamilia.setProjection(null);
		criteriaFamilia.setFirstResult(0);
		criteriaFamilia.setMaxResults(Integer.MAX_VALUE);
		criteriaFamilia.add(Restrictions.in("id", uniqueSubList));
		criteriaFamilia.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Familias> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaFamilia.list());
		}
		Pagination<Familias> pagination = new Pagination<Familias>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public Familias insert(Familias familia) {
		Session session = sessionFactory.getCurrentSession();
		/*###########################Control por APP############################*/
		familia.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
		/*######################################################################*/
		if (familia.getFamilia() == null) {
			familia.setNivel(1);//  si no tiene familia es de primer nivel
		} else {
			familia.setNivel(familia.getFamilia().getNivel() + 1); // si tiene faminilia es  ese nivel mas uno
		}
		session.save(familia);
		return familia;
	}

	@Override
	public Familias update(Familias familia) {
		Session session = sessionFactory.getCurrentSession();
		if (familia.getFamilia() == null) {
			familia.setNivel(1);//  si no tiene familia es de primer nivel
		} else {
			familia.setNivel(familia.getFamilia().getNivel() + 1); // si tiene faminilia es  ese nivel mas uno
		}
		session.update(familia);
		return familia;
	}

	@Override
	public Familias update(Familias famOld, Familias famNew) {
		Session session = sessionFactory.getCurrentSession();
		// si cambia el nombre corto se debe modificar los codigos especiales de los productos
		if (!Objects.equals(famNew.getNombreCorto().trim(), famOld.getNombreCorto().trim())) {
			productoDAO.updateCodigosEspecialFromFamilia(famOld.getId(), famOld.getNombreCorto().trim(), famNew.getNombreCorto().trim());
		}
		famOld = this.getObject(famOld, famNew);
		session.update(famOld);
		return famOld;
	}

	@Override
	public Familias getObject(Familias famOld, Familias famNew) {
		if (Objects.isNull(famOld)) {
			famOld = new Familias();
		}
		famOld.setFamilia(famNew.getFamilia());
		famOld.setNivel(famNew.getNivel());
		famOld.setNombre(famNew.getNombre());
		famOld.setNombreCorto(famNew.getNombreCorto());
		return famOld;
	}

	@Override
	public boolean isExistNombreCorto(String nombreCorto) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Familias.class, "familias");
		Criterion c1 = Restrictions.eq("nombreCorto", nombreCorto);
		c.add(c1);
		/*###########################Control por APP############################*/
		c.setFetchMode("familia.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		try {
			return !Objects.isNull(c.uniqueResult());
		} catch (HibernateException e) {// en caso con resultado mas de una  o uno que no vale
			return true;
		}
	}

	@Override
	public List<Familias> getFamiliasMin(Set<Integer> ids) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaFamilias = session.createCriteria(Familias.class, "familias");
		Disjunction disjunctionId = Restrictions.disjunction();
		for (Integer id : ids) {
			disjunctionId.add(Restrictions.eq("id", id));
		}
		criteriaFamilias.add(disjunctionId);
		return criteriaFamilias.list();
	}
}
