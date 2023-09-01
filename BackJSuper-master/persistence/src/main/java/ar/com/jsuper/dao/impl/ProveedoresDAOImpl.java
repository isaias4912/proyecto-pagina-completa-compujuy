package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.PersonasDAO;
import ar.com.jsuper.dao.ProveedoresDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.Proveedores;
import ar.com.jsuper.domain.utils.FilterProveedores;
import ar.com.jsuper.security.TenantContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProveedoresDAOImpl extends GenericDAOImpl<Proveedores, Integer> implements ProveedoresDAO {

	@Autowired
	PersonasDAO personasDAO;

	@Override
	public Pagination<Proveedores> getProveedoresBypage(FilterProveedores proveedorFilter, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaProveedor = session.createCriteria(Proveedores.class, "proveedor");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaProveedor.setProjection(idCountProjection);
		Criteria criteriaEntidad = criteriaProveedor.createCriteria("proveedor.entidad", "entidad", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPersonas = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);
		criteriaProveedor.setFetchMode("cuentaCorriente", FetchMode.JOIN);

		Integer activo = proveedorFilter.getActivo();
		Criterion c1;
		if (proveedorFilter.getNombre() != null && !proveedorFilter.getNombre().trim().equals("")) {
			String[] nombresArray = proveedorFilter.getNombre().split("\\s+");
			Conjunction conjunctionNombres = Restrictions.conjunction();
			Conjunction conjunctionRazonSocial = Restrictions.conjunction();
			Disjunction disjunctionPersona = Restrictions.disjunction();
			Disjunction disjunctionEmpresa = Restrictions.disjunction();
			for (String nombre : nombresArray) {
				if (!nombre.equals("")) {
					conjunctionNombres.add(Restrictions.like("persona.nombreFinal", "%" + nombre + "%"));
				}
			}
			disjunctionPersona.add(conjunctionNombres);

			for (String nombre : nombresArray) {
				if (!nombre.equals("")) {
					conjunctionRazonSocial.add(Restrictions.like("empresa.razonSocial", "%" + nombre + "%"));
				}
			}
			disjunctionEmpresa.add(conjunctionRazonSocial);

			Disjunction disjunctionGral = Restrictions.disjunction();
			disjunctionGral.add(disjunctionPersona);
			disjunctionGral.add(disjunctionEmpresa);
			criteriaEntidad.add(disjunctionGral);
		}
		if (proveedorFilter.getDni() != null && !proveedorFilter.getDni().trim().equals("")) {
			c1 = Restrictions.like("persona.dni", proveedorFilter.getDni(), MatchMode.ANYWHERE);
			criteriaPersonas.add(c1);
		}
		if (proveedorFilter.getCuit() != null && !proveedorFilter.getCuit().trim().equals("")) {
			c1 = Restrictions.like("empresa.cuit", proveedorFilter.getCuit(), MatchMode.ANYWHERE);
			criteriaEmpresa.add(c1);
		}
		if (Objects.nonNull(proveedorFilter.getTipoProveedor())) {
			c1 = Restrictions.eq("tipoProveedor", proveedorFilter.getTipoProveedor());
			criteriaProveedor.add(c1);
		}
		// restriccion para activos o no activos
		if (activo != null && !activo.equals("") && activo != 2) {
			Criterion cActivo = Restrictions.eq("proveedor.estado", (activo == 1));
			criteriaProveedor.add(cActivo);
		}
		/*###########################Control por APP############################*/
		criteriaProveedor.setFetchMode("proveedor.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaProveedor.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaProveedor.uniqueResult()).intValue();
//
		if (reverse) {
			criteriaProveedor.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaProveedor.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaProveedor.setProjection(Projections.distinct(Projections.property("id")));
		criteriaProveedor.setFirstResult((page - 1) * pageSize);
		criteriaProveedor.setMaxResults(pageSize);
		List uniqueSubList = criteriaProveedor.list();
		criteriaProveedor.setProjection(null);
		criteriaProveedor.setFirstResult(0);
		criteriaProveedor.setMaxResults(Integer.MAX_VALUE);
		criteriaProveedor.add(Restrictions.in("id", uniqueSubList));
		criteriaProveedor.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Proveedores> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaProveedor.list());
		}
		Pagination<Proveedores> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public Pagination<Proveedores> getProveedoresByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaProveedor = session.createCriteria(Proveedores.class, "proveedor");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaProveedor.setProjection(idCountProjection);
		Criteria criteriaEntidad = criteriaProveedor.createCriteria("proveedor.entidad", "entidad", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPersonas = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);
		criteriaProveedor.setFetchMode("cuentaCorriente", FetchMode.JOIN);

		query = query.trim();
		Criterion c1, c2;
		if (query != null && !query.equals("")) {
			String[] nombresArray = query.split("\\s+");
			Conjunction conjunctionNombres = Restrictions.conjunction();
			Conjunction conjunctionRazonSocial = Restrictions.conjunction();
			Disjunction disjunctionPersona = Restrictions.disjunction();
			Disjunction disjunctionEmpresa = Restrictions.disjunction();
			for (String nombre : nombresArray) {
				if (!nombre.equals("")) {
					conjunctionNombres.add(Restrictions.like("persona.nombreFinal", "%" + nombre + "%"));
				}
			}
			disjunctionPersona.add(conjunctionNombres);

			for (String nombre : nombresArray) {
				if (!nombre.equals("")) {
					conjunctionRazonSocial.add(Restrictions.like("empresa.razonSocial", "%" + nombre + "%"));
				}
			}
			disjunctionEmpresa.add(conjunctionRazonSocial);

			Disjunction disjunctionGral = Restrictions.disjunction();
			disjunctionGral.add(disjunctionPersona);
			disjunctionGral.add(disjunctionEmpresa);
			c1 = Restrictions.like("empresa.cuit", query, MatchMode.ANYWHERE);
			c2 = Restrictions.like("persona.dni", query, MatchMode.ANYWHERE);
            criteriaProveedor.add(Restrictions.or(disjunctionGral, c1, c2));
		}
		/*###########################Control por APP############################*/
		criteriaProveedor.setFetchMode("proveedor.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaProveedor.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaProveedor.uniqueResult()).intValue();

		if (reverse) {
			criteriaProveedor.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaProveedor.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaProveedor.setProjection(Projections.distinct(Projections.property("id")));
		criteriaProveedor.setFirstResult((page - 1) * pageSize);
		criteriaProveedor.setMaxResults(pageSize);
		List uniqueSubList = criteriaProveedor.list();
		criteriaProveedor.setProjection(null);
		criteriaProveedor.setFirstResult(0);
		criteriaProveedor.setMaxResults(Integer.MAX_VALUE);
		criteriaProveedor.add(Restrictions.in("id", uniqueSubList));
		criteriaProveedor.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Proveedores> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaProveedor.list());
		}
		Pagination<Proveedores> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public Proveedores insert(Proveedores proveedor) {
		Session session = sessionFactory.getCurrentSession();
		/*###########################Control por APP############################*/
		proveedor.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
		/*######################################################################*/
		proveedor.setEstado(Boolean.TRUE);
		session.save(proveedor);
		return proveedor;
	}

	@Override
	public Proveedores updateProveedor(Proveedores proveedorOld, Proveedores proveedorNew) {
		Session session = sessionFactory.getCurrentSession();
		proveedorOld = this.getObject(proveedorOld, proveedorNew);
		session.update(proveedorOld);
		return proveedorOld;
	}

	@Override
	public Proveedores getObject(Proveedores proveedorOld, Proveedores proveedorNew) {
		if (Objects.isNull(proveedorOld)) {
			proveedorOld = new Proveedores();
		}
		proveedorOld.setTipoProveedor(proveedorNew.getTipoProveedor());
		proveedorOld.setNroDocProveedor(proveedorNew.getNroDocProveedor());
		proveedorOld.setTipoDocProveedor(proveedorNew.getTipoDocProveedor());
		proveedorOld.setObservacion(proveedorNew.getObservacion());
		return proveedorOld;
	}

	@Override
	public void saveUpdateOrDelete(Producto producto, Set<Proveedores> proveedores, Set<Proveedores> proveedoresBD) throws BussinessException {
		Boolean encontro = false;
		for (Proveedores prov : proveedores) {
			encontro = false;
			for (Proveedores provBD : proveedoresBD) {
				if (Objects.equals(prov.getId(), provBD.getId())) {
					encontro = true;
				}
			}
			if (!encontro) {
				producto.getProveedores().add(this.load(prov.getId()));
			}
		}
		for (Proveedores provBD : proveedoresBD) {
			encontro = false;
			for (Proveedores prov : proveedores) {
				if (Objects.equals(prov.getId(), provBD.getId())) {
					encontro = true;
				}
			}
			if (!encontro) {
				producto.getProveedores().remove(provBD);
			}
		}
	}

	@Override
	public List<Proveedores> getByProveedorMatch(String q) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Proveedores.class, "proveedor");
		Criteria criteriaApp = c.createCriteria("proveedor.app", "app", JoinType.LEFT_OUTER_JOIN);
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
	public Proveedores getProveedorMin(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Proveedores.class, "proveedor");
		/*###########################Control por APP############################*/
		Criterion ca = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(ca);
		/*######################################################################*/
		Criterion c1 = Restrictions.eq("id", id);
		c.add(c1);
		return (Proveedores) c.uniqueResult();
	}
}
