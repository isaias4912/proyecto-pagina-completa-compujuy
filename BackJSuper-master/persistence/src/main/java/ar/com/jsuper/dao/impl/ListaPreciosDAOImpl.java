package ar.com.jsuper.dao.impl;

import org.springframework.stereotype.Repository;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.ListaPrecios;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.security.TenantContext;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import ar.com.jsuper.dao.ListaPreciosDAO;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Repository
public class ListaPreciosDAOImpl extends GenericDAOImpl<ListaPrecios, Integer> implements ListaPreciosDAO {

	@Override
	public Pagination<ListaPrecios> getListaPreciosByPage(FilterGeneric filterGeneric, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cirteriaListaPrecios = session.createCriteria(ListaPrecios.class, "lista");
		Projection idCountProjection = Projections.countDistinct("id");
		cirteriaListaPrecios.setProjection(idCountProjection);
		if (filterGeneric.getNombre()!= null && !filterGeneric.getNombre().trim().equals("")) {
			Criterion c1 = Restrictions.like("lista.nombre", filterGeneric.getNombre(), MatchMode.ANYWHERE);
			cirteriaListaPrecios.add(c1);
		}
		Integer activo = filterGeneric.getActivo();
        if (activo != null && !activo.equals("") && activo != 2) {
            cirteriaListaPrecios.add(Restrictions.eq("activo", activo==1));
        }
		/*###########################Control por APP############################*/
		cirteriaListaPrecios.setFetchMode("lista.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		cirteriaListaPrecios.add(c1);
		/*######################################################################*/
		// setup criteria, joins etc here
		Integer totalResultCount = ((Long) cirteriaListaPrecios.uniqueResult()).intValue();

		if (reverse) {
			cirteriaListaPrecios.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			cirteriaListaPrecios.addOrder(Order.desc(fieldOrder.trim()));
		}
		cirteriaListaPrecios.setProjection(Projections.distinct(Projections.property("id")));
		cirteriaListaPrecios.setFirstResult((page - 1) * pageSize);
		cirteriaListaPrecios.setMaxResults(pageSize);
		List uniqueSubList = cirteriaListaPrecios.list();
		cirteriaListaPrecios.setProjection(null);
		cirteriaListaPrecios.setFirstResult(0);
		cirteriaListaPrecios.setMaxResults(Integer.MAX_VALUE);
		cirteriaListaPrecios.add(Restrictions.in("id", uniqueSubList));
		cirteriaListaPrecios.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<ListaPrecios> lista = new ArrayList<>();
		if (totalResultCount > 0) {
			lista = new ArrayList<>(cirteriaListaPrecios.list());
		}
		Pagination<ListaPrecios> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public ListaPrecios update(ListaPrecios entityOld, ListaPrecios entityNew) {
		Session session = sessionFactory.getCurrentSession();
		entityOld = this.getObject(entityOld, entityNew);
		session.update(entityOld);
		return entityOld;
	}

	@Override
	public ListaPrecios getObject(ListaPrecios impOld, ListaPrecios impNew) {
		if (Objects.isNull(impOld)) {
			impOld = new ListaPrecios();
		}
		impOld.setActivo(impNew.isActivo());
		impOld.setDetalle(impNew.getDetalle());
		impOld.setTipo(impNew.getTipo());
		impOld.setValor(impNew.getValor());
		impOld.setNombre(impNew.getNombre());
		return impOld;
	}

	/**
	 * habilitamos o deshabilitamos un producto por oferta
	 *
	 */
	@Override
	public int enabledOrdisabled(Set<ListaPrecios> impuestos, boolean value) {
		Session session = sessionFactory.getCurrentSession();
		Set<Integer> setIds = new HashSet<>();
		for (ListaPrecios contImpuestos : impuestos) {
			setIds.add(contImpuestos.getId());
		}
		String updateHQL = " UPDATE ListaPrecios c SET c.activo = :valueactivo"
				+ " WHERE  c.id in (:setIds)";
		int updatedEntities = session.createQuery(updateHQL)
				.setBoolean("valueactivo", value)
				.setParameterList("setIds", setIds)
				.executeUpdate();
		return updatedEntities;
	}
}
