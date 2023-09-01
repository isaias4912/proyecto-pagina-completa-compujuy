package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.PersonasDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.domain.utils.FilterPersonas;
import ar.com.jsuper.security.TenantContext;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.stereotype.Repository;

@Repository
public class PersonasDAOImpl extends GenericDAOImpl<Personas, Integer> implements PersonasDAO {

	@Override
	public Pagination<Entidad> getPersonasBypage(FilterPersonas personaFilter, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaEntidad = session.createCriteria(Entidad.class, "entidad");
//		criteriaPersonas.createAlias("entidad", "entidad", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPersona = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
//		Cr<iteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);
//		Criteria criteriaApp = criteriaEntidad.createCriteria("entidad.app", "app", JoinType.LEFT_OUTER_JOIN);

//		criteriaPersonas.createAlias("cliente.cuentaCorriente", "ctaCte", JoinType.LEFT_OUTER_JOIN);
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaEntidad.setProjection(idCountProjection);
		Integer activo = personaFilter.getActivo();
		Criterion c1;
		if (personaFilter.getApellido() != null && !personaFilter.getApellido().trim().equals("")) {
			c1 = Restrictions.like("apellido", personaFilter.getApellido(), MatchMode.ANYWHERE);
			criteriaPersona.add(c1);
		}
		if (personaFilter.getNombre() != null && !personaFilter.getNombre().trim().equals("")) {
			c1 = Restrictions.like("nombre", personaFilter.getNombre(), MatchMode.ANYWHERE);
			criteriaPersona.add(c1);
		}
		if (personaFilter.getDni() != null && !personaFilter.getDni().trim().equals("")) {
			c1 = Restrictions.like("dni", personaFilter.getDni(), MatchMode.ANYWHERE);
			criteriaPersona.add(c1);
		}
		// nos fijamos el id 
		if (!Objects.isNull(personaFilter.getId())) {
			if (personaFilter.getId() > 0) {
				criteriaEntidad.add(Restrictions.eq("id", personaFilter.getId()));
			}
		}
		/*###########################Control por APP############################*/
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaEntidad.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaEntidad.uniqueResult()).intValue();

		if (reverse) {
			criteriaEntidad.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaEntidad.addOrder(Order.desc(fieldOrder.trim()));
		}
		///////////////////////////////////////////////////
//		Entidad entidad = new Entidad();
//		entidad.setApp(new App(1));
//		entidad.setFechAlta(new Date());
//		Personas persona = new Personas();
//		persona.setApellido("Ramotts");
//		persona.setDni("314428888");
//		persona.setFechAlta(new Date());
//		persona.setNombre("Rafael Aldo");
//		persona.setEntidad(entidad);
//		entidad.setPersona(persona);
//		session.save(persona);
//		session.save(entidad);
		///////////////////////////////////////////////////
		criteriaEntidad.setProjection(Projections.distinct(Projections.property("id")));
		criteriaEntidad.setFirstResult((page - 1) * pageSize);
		criteriaEntidad.setMaxResults(pageSize);
		List uniqueSubList = criteriaEntidad.list();
		criteriaEntidad.setProjection(null);
		criteriaEntidad.setFirstResult(0);
		criteriaEntidad.setMaxResults(Integer.MAX_VALUE);
		criteriaEntidad.add(Restrictions.in("id", uniqueSubList));
		criteriaEntidad.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Entidad> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaEntidad.list());
		}
		Pagination<Entidad> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public Pagination<Personas> getPersonasByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaPersonas = session.createCriteria(Personas.class, "persona");
		criteriaPersonas.createAlias("cliente", "cliente", JoinType.LEFT_OUTER_JOIN);
		criteriaPersonas.createAlias("cliente.cuentaCorriente", "ctaCte", JoinType.LEFT_OUTER_JOIN);
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaPersonas.setProjection(idCountProjection);
		Criterion c1;
		Criterion c3;
		query = query.trim();
		if (query != null && !query.equals("")) {
			Conjunction disjunctionNombres = new Conjunction();
			String nombreFinal = query;
			if (nombreFinal != null && !nombreFinal.equals("")) {
				String[] nombresArray = nombreFinal.split("\\s+");
				disjunctionNombres = Restrictions.conjunction();
				for (String nombre : nombresArray) {
					if (!nombre.equals("")) {
						disjunctionNombres.add(Restrictions.sqlRestriction("concat({alias}.apellido, this_.nombre) like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
					}
				}
			}
			c3 = Restrictions.like("dni", query, MatchMode.ANYWHERE);
			criteriaPersonas.add(Restrictions.or(disjunctionNombres, c3));
		}
		/*###########################Control por APP############################*/
		criteriaPersonas.setFetchMode("cliente.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaPersonas.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaPersonas.uniqueResult()).intValue();

		if (reverse) {
			criteriaPersonas.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaPersonas.addOrder(Order.desc(fieldOrder.trim()));
		}
		if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
			criteriaPersonas.setFirstResult((page - 1) * pageSize);
			criteriaPersonas.setMaxResults(pageSize);
		}
		criteriaPersonas.setProjection(Projections.distinct(Projections.property("id")));
		List uniqueSubList = criteriaPersonas.list();
		criteriaPersonas.setProjection(null);
		criteriaPersonas.setFirstResult(0);
		criteriaPersonas.setMaxResults(Integer.MAX_VALUE);
		criteriaPersonas.add(Restrictions.in("id", uniqueSubList));
		criteriaPersonas.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Personas> lista = null;
		if (totalResultCount <= 0) {
			lista = null;
		} else {
			lista = new ArrayList<>(criteriaPersonas.list());
		}
		Pagination<Personas> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public Personas getByDni(String dni) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaPersonas = session.createCriteria(Personas.class, "persona");
		criteriaPersonas.createAlias("cliente", "cliente", JoinType.LEFT_OUTER_JOIN);
		criteriaPersonas.createAlias("cliente.cuentaCorriente", "ctaCte", JoinType.LEFT_OUTER_JOIN);
		criteriaPersonas.createAlias("persona.domicilios", "domicilios", JoinType.LEFT_OUTER_JOIN);
		criteriaPersonas.createAlias("persona.contactos", "contactos", JoinType.LEFT_OUTER_JOIN);
		criteriaPersonas.createAlias("persona.telefonos", "telefonos", JoinType.LEFT_OUTER_JOIN);
		/*###########################Control por APP############################*/
		criteriaPersonas.setFetchMode("persona.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaPersonas.add(c1);
		/*######################################################################*/
		// verificamos si existe el deni en personas para usuario
		c1 = Restrictions.eq("dni", dni);
		criteriaPersonas.add(c1);
		List list = criteriaPersonas.list();
		if (list.size() >= 1) {
			return (Personas) list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Personas update(Personas personaOld, Personas personaNew) {
		Session session = sessionFactory.getCurrentSession();
		personaOld = this.getObject(personaOld, personaNew);
		session.update(personaOld);
		return personaOld;
	}

	@Override
	public Personas insert(Personas persona) {
		Session session = sessionFactory.getCurrentSession();
		persona.setFechAlta(new Date());
		/*###########################Control por APP############################*/
//		persona.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
		/*######################################################################*/
		return super.insert(persona); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Personas get(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaPersonas = session.createCriteria(Personas.class, "persona");
		criteriaPersonas.createAlias("cliente", "cliente", JoinType.LEFT_OUTER_JOIN);
		criteriaPersonas.createAlias("cliente.cuentaCorriente", "ctaCte", JoinType.LEFT_OUTER_JOIN);
		criteriaPersonas.createAlias("persona.domicilios", "domicilios", JoinType.LEFT_OUTER_JOIN);
		criteriaPersonas.createAlias("persona.contactos", "contactos", JoinType.LEFT_OUTER_JOIN);
		criteriaPersonas.createAlias("persona.telefonos", "telefonos", JoinType.LEFT_OUTER_JOIN);
		/*###########################Control por APP############################*/
		criteriaPersonas.setFetchMode("persona.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaPersonas.add(c1);
		/*######################################################################*/
		criteriaPersonas.add(Restrictions.eq("id", id));
		Personas persona = (Personas) criteriaPersonas.uniqueResult();
		return persona;
	}

	@Override
	public Boolean isCliente(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaPersonas = session.createCriteria(Personas.class, "persona");
		criteriaPersonas.createAlias("cliente", "cliente", JoinType.LEFT_OUTER_JOIN);
		/*###########################Control por APP############################*/
		criteriaPersonas.setFetchMode("persona.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaPersonas.add(c1);
		/*######################################################################*/
		criteriaPersonas.add(Restrictions.eq("id", id));
		Personas persona = (Personas) criteriaPersonas.uniqueResult();
//		if (!Objects.isNull(persona)) {
//			return !Objects.isNull(persona.getCliente());
//		}
		return null;
	}

	@Override
	public Personas getObject(Personas personaOld, Personas personaNew) {
		if (Objects.isNull(personaOld)) {
			personaOld = new Personas();
		}
		personaOld.setApellido(personaNew.getApellido());
		personaOld.setNombre(personaNew.getNombre());
		personaOld.setCuil(personaNew.getCuil());
		personaOld.setSexo(personaNew.getSexo());
		personaOld.setFechaNac(personaNew.getFechaNac());
		return personaOld;
	}
}
