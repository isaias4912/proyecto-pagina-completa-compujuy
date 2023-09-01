package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.PersonasDAO;
import ar.com.jsuper.dao.EmpresasDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.utils.FilterEmpresas;
import ar.com.jsuper.security.TenantContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmpresasDAOImpl extends GenericDAOImpl<Empresas, Integer> implements EmpresasDAO {

	@Autowired
	PersonasDAO personasDAO;

	@Override
	public Pagination<Empresas> getEmpresasBypage(FilterEmpresas filterEmpresas, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaEmpresas = session.createCriteria(Empresas.class, "empresa");
		criteriaEmpresas.setFetchMode("persona", FetchMode.JOIN);
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaEmpresas.setProjection(idCountProjection);
		Integer activo = filterEmpresas.getActivo();
		Criterion c1;
		if (filterEmpresas.getRazonSocial() != null && !filterEmpresas.getRazonSocial().trim().equals("")) {
			c1 = Restrictions.like("empresa.razonSocial", filterEmpresas.getRazonSocial(), MatchMode.ANYWHERE);
			criteriaEmpresas.add(c1);
		}
		if (filterEmpresas.getCuit() != null && !filterEmpresas.getCuit().trim().equals("")) {
			c1 = Restrictions.like("empresa.cuit", filterEmpresas.getCuit(), MatchMode.ANYWHERE);
			criteriaEmpresas.add(c1);
		}
		// restriccion para activos o no activos
		if (activo != null && !activo.equals("") && activo != 2) {
			Criterion cActivo = Restrictions.eq("empresa.estado", (activo == 1));
			criteriaEmpresas.add(cActivo);
		}
		/*###########################Control por APP############################*/
		criteriaEmpresas.setFetchMode("empresa.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaEmpresas.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaEmpresas.uniqueResult()).intValue();

		if (reverse) {
			criteriaEmpresas.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaEmpresas.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaEmpresas.setProjection(Projections.distinct(Projections.property("id")));
		criteriaEmpresas.setFirstResult((page - 1) * pageSize);
		criteriaEmpresas.setMaxResults(pageSize);
		List uniqueSubList = criteriaEmpresas.list();
		criteriaEmpresas.setProjection(null);
		criteriaEmpresas.setFirstResult(0);
		criteriaEmpresas.setMaxResults(Integer.MAX_VALUE);
		criteriaEmpresas.add(Restrictions.in("id", uniqueSubList));
		criteriaEmpresas.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Empresas> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaEmpresas.list());
		}
		Pagination<Empresas> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public Pagination<Empresas> getEmpresasByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaEmpresa = session.createCriteria(Empresas.class, "empresa");
		criteriaEmpresa.createAlias("domicilios", "domicilios", JoinType.LEFT_OUTER_JOIN);
		criteriaEmpresa.createAlias("contactos", "contactos", JoinType.LEFT_OUTER_JOIN);
		criteriaEmpresa.createAlias("telefonos", "telefonos", JoinType.LEFT_OUTER_JOIN);
		criteriaEmpresa.setFetchMode("persona", FetchMode.JOIN);

		Projection idCountProjection = Projections.countDistinct("id");
		criteriaEmpresa.setProjection(idCountProjection);
		Criterion c1;
		Criterion c3;
		query = query.trim();
		if (query != null && !query.equals("")) {
			Conjunction conjunctionRazSoc = new Conjunction();
			String nombreFinal = query;
			if (nombreFinal != null && !nombreFinal.equals("")) {
				String[] nombresArray = nombreFinal.split("\\s+");
				conjunctionRazSoc = Restrictions.conjunction();
				for (String nombre : nombresArray) {
					if (!nombre.equals("")) {
						conjunctionRazSoc.add(Restrictions.sqlRestriction("{alias}.razon_social like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
					}
				}
			}
			c3 = Restrictions.like("cuit", query, MatchMode.ANYWHERE);
			criteriaEmpresa.add(Restrictions.or(conjunctionRazSoc, c3));
		}
		/*###########################Control por APP############################*/
		criteriaEmpresa.setFetchMode("empresa.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaEmpresa.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaEmpresa.uniqueResult()).intValue();

		if (reverse) {
			criteriaEmpresa.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaEmpresa.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaEmpresa.setProjection(Projections.distinct(Projections.property("id")));
		criteriaEmpresa.setFirstResult((page - 1) * pageSize);
		criteriaEmpresa.setMaxResults(pageSize);
		List uniqueSubList = criteriaEmpresa.list();
		criteriaEmpresa.setProjection(null);
		criteriaEmpresa.setFirstResult(0);
		criteriaEmpresa.setMaxResults(Integer.MAX_VALUE);
		criteriaEmpresa.add(Restrictions.in("id", uniqueSubList));
		criteriaEmpresa.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Empresas> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaEmpresa.list());
		}
		Pagination<Empresas> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public Empresas insert(Empresas empresa) {
		Session session = sessionFactory.getCurrentSession();
		/*###########################Control por APP############################*/
//		empresa.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
		/*######################################################################*/
		session.save(empresa);
		return empresa;
	}

	@Override
	public Empresas updatePersona(Empresas empresaOld, Empresas empresaNew) {
		Session session = sessionFactory.getCurrentSession();
		empresaOld = this.getObject(empresaOld, empresaNew);
		session.update(empresaOld);
		return empresaOld;
	}

	@Override
	public Empresas getObject(Empresas empresaOld, Empresas empresaNew) {
		if (Objects.isNull(empresaOld)) {
			empresaOld = new Empresas();
		}
		empresaOld.setTipoEmpresa(empresaNew.getTipoEmpresa());
		empresaOld.setRazonSocial(empresaNew.getRazonSocial());
		empresaOld.setCuit(empresaNew.getCuit());
		empresaOld.setObservacion(empresaNew.getObservacion());
		if (Objects.isNull(empresaNew.getPersona())) {
			empresaOld.setPersona(null);
		} else {
			if (empresaNew.getPersona().getId() == 0) {
				empresaOld.setPersona(null);
			} else {
				empresaOld.setPersona(empresaNew.getPersona());
			}
		}
		return empresaOld;
	}

	@Override
	public List<Empresas> getByEmpresaMatch(String q) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Empresas.class, "empresa");
		Criteria criteriaApp = c.createCriteria("empresa.app", "app", JoinType.LEFT_OUTER_JOIN);
		/*###########################Control por APP############################*/
		Criterion ca = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(ca);
		/*######################################################################*/
		Criterion c1 = Restrictions.like("cuit", q, MatchMode.ANYWHERE);
		Criterion c2 = Restrictions.like("razonSocial", q, MatchMode.ANYWHERE);
		c.add(Restrictions.or(c1, c2));
		c.add(Restrictions.eq("estado", true));
		return c.list();
	}

	@Override
	public Empresas getEmpresaMin(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Empresas.class, "empresa");
		/*###########################Control por APP############################*/
		Criterion ca = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(ca);
		/*######################################################################*/
		Criterion c1 = Restrictions.eq("id", id);
		c.add(c1);
		return (Empresas) c.uniqueResult();
	}
}
