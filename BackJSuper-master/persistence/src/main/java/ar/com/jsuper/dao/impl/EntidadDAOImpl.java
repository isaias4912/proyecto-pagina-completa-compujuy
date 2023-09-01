package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.EntidadDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.Contactos;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.domain.Proveedores;
import ar.com.jsuper.domain.utils.FilterEntidad;
import ar.com.jsuper.domain.utils.IAfip;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.utils.TipoEntidad;
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
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

@Repository
public class EntidadDAOImpl extends GenericDAOImpl<Entidad, Integer> implements EntidadDAO {

	@Override
	public Pagination<Entidad> getEntidadesBypage(FilterEntidad entidadFilter, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaEntidad = session.createCriteria(Entidad.class, "entidad");
		Criteria criteriaPersona = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresaPersona = criteriaEntidad.createCriteria("empresa.persona", "empresaPersona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaCliente = criteriaEntidad.createCriteria("entidad.clientes", "clientes", JoinType.LEFT_OUTER_JOIN);

		Projection idCountProjection = Projections.countDistinct("id");
		criteriaEntidad.setProjection(idCountProjection);
		if (entidadFilter.getTipo().equals(TipoEntidad.PERSONA.theState)) {// persona
			this.setFilterFromPersona(criteriaPersona, entidadFilter);
			criteriaEntidad.add(Restrictions.eq("tipo", TipoEntidad.PERSONA));
		}
		if (entidadFilter.getTipo().equals(TipoEntidad.EMPRESA.theState)) {// empresa
			this.setFilterFromEmpresa(criteriaEmpresa, entidadFilter);
			criteriaEntidad.add(Restrictions.eq("tipo", TipoEntidad.EMPRESA));
		}
		if (entidadFilter.getTipo().equals(TipoEntidad.TODAS.theState)) {// empresa
			this.setFilter(criteriaEntidad, entidadFilter);
		}
		Criterion c1;
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
	public Pagination<Entidad> getEntidadesByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaEntidad = session.createCriteria(Entidad.class, "entidad");
		Criteria criteriaPersona = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);

		Projection idCountProjection = Projections.countDistinct("id");
		criteriaEntidad.setProjection(idCountProjection);
		Criterion c1;
		Criterion c3;
		Criterion c4;
		query = query.trim();
		if (query != null && !query.equals("")) {
			Conjunction conjunctionNombres = new Conjunction();
			Disjunction disjunctionRazonSocial = Restrictions.disjunction();
			String nombreFinal = query;
			if (nombreFinal != null && !nombreFinal.equals("")) {
				String[] nombresArray = nombreFinal.split("\\s+");
				conjunctionNombres = Restrictions.conjunction();
				for (String nombre : nombresArray) {
					if (!nombre.equals("")) {
						conjunctionNombres.add(Restrictions.like("persona.nombreFinal", "%" + nombre + "%"));
					}
				}
				for (String nombre : nombresArray) {
					if (!nombre.equals("")) {
						disjunctionRazonSocial.add(Restrictions.like("empresa.razonSocial", "%" + nombre + "%"));
					}
				}
			}
			c3 = Restrictions.like("persona.dni", query, MatchMode.ANYWHERE);
			c4 = Restrictions.like("empresa.cuit", query, MatchMode.ANYWHERE);
			criteriaEntidad.add(Restrictions.or(conjunctionNombres, disjunctionRazonSocial, c3, c4));
		}
		/*###########################Control por APP############################*/
		criteriaEntidad.setFetchMode("cliente.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaEntidad.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaEntidad.uniqueResult()).intValue();

		if (reverse) {
			criteriaEntidad.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaEntidad.addOrder(Order.desc(fieldOrder.trim()));
		}
		if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
			criteriaEntidad.setFirstResult((page - 1) * pageSize);
			criteriaEntidad.setMaxResults(pageSize);
		}
		criteriaEntidad.setProjection(Projections.distinct(Projections.property("id")));
		List uniqueSubList = criteriaEntidad.list();
		criteriaEntidad.setProjection(null);
		criteriaEntidad.setFirstResult(0);
		criteriaEntidad.setMaxResults(Integer.MAX_VALUE);
		criteriaEntidad.add(Restrictions.in("id", uniqueSubList));
		criteriaEntidad.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Entidad> lista = null;
		if (totalResultCount <= 0) {
			lista = null;
		} else {
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
	public List<Entidad> getEntidadesAndEmail(String query) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaEntidad = session.createCriteria(Entidad.class, "entidad");
		Criteria criteriaPersona = criteriaEntidad.createAlias("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createAlias("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);
		criteriaEntidad.createAlias("entidad.contactos", "contactos", JoinType.LEFT_OUTER_JOIN);
		Disjunction disjunctionGral = Restrictions.disjunction();
		Disjunction disjunctionEntidad = Restrictions.disjunction();
		Conjunction conjunctionEntidad = Restrictions.conjunction();
		Criterion c1;
		Criterion c3;
		Criterion c4;
		query = query.trim();
		if (query != null && !query.equals("")) {
			Conjunction conjunctionNombres = new Conjunction();
			Disjunction disjunctionRazonSocial = Restrictions.disjunction();
			String nombreFinal = query;
			if (nombreFinal != null && !nombreFinal.equals("")) {
				String[] nombresArray = nombreFinal.split("\\s+");
				conjunctionNombres = Restrictions.conjunction();
				for (String nombre : nombresArray) {
					if (!nombre.equals("")) {
						conjunctionNombres.add(Restrictions.like("persona.nombreFinal", "%" + nombre + "%"));
					}
				}
				for (String nombre : nombresArray) {
					if (!nombre.equals("")) {
						disjunctionRazonSocial.add(Restrictions.like("empresa.razonSocial", "%" + nombre + "%"));
					}
				}
			}
			c3 = Restrictions.like("persona.dni", query, MatchMode.ANYWHERE);
			c4 = Restrictions.like("empresa.cuit", query, MatchMode.ANYWHERE);
               
			disjunctionEntidad.add(Restrictions.or(conjunctionNombres, disjunctionRazonSocial, c3, c4));
			conjunctionEntidad.add(disjunctionEntidad);
			conjunctionEntidad.add(Restrictions.eq("contactos.tipo", 1));
			disjunctionGral.add(conjunctionEntidad);

		}
		Disjunction disjunctionContactos = Restrictions.disjunction();
		disjunctionContactos.add(Restrictions.and(Restrictions.eq("contactos.tipo", 1), Restrictions.like("contactos.descripcion", query, MatchMode.ANYWHERE)));
		disjunctionGral.add(disjunctionContactos);
		criteriaEntidad.add(disjunctionGral);

		/*###########################Control por APP############################*/
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaEntidad.add(c1);
		/*######################################################################*/
		criteriaEntidad.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteriaEntidad.list();
	}

	@Override
	public Pagination<Entidad> getEntidadesByMultipleFilterFull(String query, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaEntidad = session.createCriteria(Entidad.class, "entidad");
		Criteria criteriaPersona = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);
		criteriaEntidad.createAlias("entidad.domicilios", "domicilios", JoinType.LEFT_OUTER_JOIN);
		criteriaEntidad.createAlias("entidad.contactos", "contactos", JoinType.LEFT_OUTER_JOIN);
		criteriaEntidad.createAlias("entidad.telefonos", "telefonos", JoinType.LEFT_OUTER_JOIN);
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaEntidad.setProjection(idCountProjection);
		Criterion c1;
		Criterion c3;
		Criterion c4;
		query = query.trim();
		if (query != null && !query.equals("")) {
			Conjunction conjunctionNombres = new Conjunction();
			Disjunction disjunctionRazonSocial = Restrictions.disjunction();
			String nombreFinal = query;
			if (nombreFinal != null && !nombreFinal.equals("")) {
				String[] nombresArray = nombreFinal.split("\\s+");
				conjunctionNombres = Restrictions.conjunction();
				for (String nombre : nombresArray) {
					if (!nombre.equals("")) {
						conjunctionNombres.add(Restrictions.like("persona.nombreFinal", "%" + nombre + "%"));
					}
				}
				for (String nombre : nombresArray) {
					if (!nombre.equals("")) {
						disjunctionRazonSocial.add(Restrictions.like("empresa.razonSocial", "%" + nombre + "%"));
					}
				}
			}
			c3 = Restrictions.like("persona.dni", query, MatchMode.ANYWHERE);
			c4 = Restrictions.like("empresa.cuit", query, MatchMode.ANYWHERE);
			criteriaEntidad.add(Restrictions.or(conjunctionNombres, disjunctionRazonSocial, c3, c4));
		}
		/*###########################Control por APP############################*/
		criteriaEntidad.setFetchMode("cliente.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaEntidad.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaEntidad.uniqueResult()).intValue();

		if (reverse) {
			criteriaEntidad.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaEntidad.addOrder(Order.desc(fieldOrder.trim()));
		}
		if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
			criteriaEntidad.setFirstResult((page - 1) * pageSize);
			criteriaEntidad.setMaxResults(pageSize);
		}
		criteriaEntidad.setProjection(Projections.distinct(Projections.property("id")));
		List uniqueSubList = criteriaEntidad.list();
		criteriaEntidad.setProjection(null);
		criteriaEntidad.setFirstResult(0);
		criteriaEntidad.setMaxResults(Integer.MAX_VALUE);
		criteriaEntidad.add(Restrictions.in("id", uniqueSubList));
		criteriaEntidad.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Entidad> lista = null;
		if (totalResultCount <= 0) {
			lista = null;
		} else {
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
	public Pagination<Entidad> getEntidadesPerByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaEntidad = session.createCriteria(Entidad.class, "entidad");
		Criteria criteriaPersona = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);

		Projection idCountProjection = Projections.countDistinct("id");
		criteriaEntidad.setProjection(idCountProjection);
		Criterion c1;
		Criterion c3;
		Criterion c4;
		query = query.trim();
		if (query != null && !query.equals("")) {
			Conjunction conjunctionNombres = new Conjunction();
			String nombreFinal = query;
			if (nombreFinal != null && !nombreFinal.equals("")) {
				String[] nombresArray = nombreFinal.split("\\s+");
				conjunctionNombres = Restrictions.conjunction();
				for (String nombre : nombresArray) {
					if (!nombre.equals("")) {
						conjunctionNombres.add(Restrictions.like("persona.nombreFinal", "%" + nombre + "%"));
					}
				}

			}
			c3 = Restrictions.like("persona.dni", query, MatchMode.ANYWHERE);
			criteriaEntidad.add(Restrictions.or(conjunctionNombres, c3));
		}
		/*###########################Control por APP############################*/
		criteriaEntidad.setFetchMode("cliente.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaEntidad.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaEntidad.uniqueResult()).intValue();

		if (reverse) {
			criteriaEntidad.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaEntidad.addOrder(Order.desc(fieldOrder.trim()));
		}
		if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
			criteriaEntidad.setFirstResult((page - 1) * pageSize);
			criteriaEntidad.setMaxResults(pageSize);
		}
		criteriaEntidad.setProjection(Projections.distinct(Projections.property("id")));
		List uniqueSubList = criteriaEntidad.list();
		criteriaEntidad.setProjection(null);
		criteriaEntidad.setFirstResult(0);
		criteriaEntidad.setMaxResults(Integer.MAX_VALUE);
		criteriaEntidad.add(Restrictions.in("id", uniqueSubList));
		criteriaEntidad.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Entidad> lista = null;
		if (totalResultCount <= 0) {
			lista = null;
		} else {
			lista = new ArrayList<>(criteriaEntidad.list());
		}
		Pagination<Entidad> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	public void setFilterFromPersona(Criteria criteriaPersona, FilterEntidad personaFilter) {
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
				criteriaPersona.add(Restrictions.eq("id", personaFilter.getId()));
			}
		}

	}

	public void setFilterFromEmpresa(Criteria criteriaEmpresa, FilterEntidad empresaFilter) {
		Integer activo = empresaFilter.getActivo();
		Criterion c1;
		if (empresaFilter.getRazonSocial() != null && !empresaFilter.getRazonSocial().trim().equals("")) {
			c1 = Restrictions.like("razonSocial", empresaFilter.getRazonSocial(), MatchMode.ANYWHERE);
			criteriaEmpresa.add(c1);
		}
		if (empresaFilter.getCuit() != null && !empresaFilter.getCuit().trim().equals("")) {
			c1 = Restrictions.like("cuit", empresaFilter.getCuit(), MatchMode.ANYWHERE);
			criteriaEmpresa.add(c1);
		}
		// nos fijamos el id 
		if (Objects.nonNull(empresaFilter.getId())) {
			if (empresaFilter.getId() > 0) {
				criteriaEmpresa.add(Restrictions.eq("id", empresaFilter.getId()));
			}
		}
		//fijamos  si esta definido el tipo
		if (Objects.nonNull(empresaFilter.getTipoEmpresa())) {
			criteriaEmpresa.add(Restrictions.eq("tipoEmpresa", empresaFilter.getTipoEmpresa()));
		}

	}

	public void setFilter(Criteria criteriaEntidad, FilterEntidad entidadFilter) {
		Criterion c1;
		if (entidadFilter.getNombre() != null && !entidadFilter.getNombre().trim().equals("")) {
			String[] nombresArray = entidadFilter.getNombre().split("\\s+");
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
		if (entidadFilter.getDni() != null && !entidadFilter.getDni().trim().equals("")) {
			c1 = Restrictions.like("persona.dni", entidadFilter.getDni(), MatchMode.ANYWHERE);
			criteriaEntidad.add(c1);
		}
		if (entidadFilter.getCuit() != null && !entidadFilter.getCuit().trim().equals("")) {
			c1 = Restrictions.like("empresa.cuit", entidadFilter.getCuit(), MatchMode.ANYWHERE);
			criteriaEntidad.add(c1);
		}
		if (Objects.nonNull(entidadFilter.getTipoEntidad())) {
			c1 = Restrictions.eq("tipo", entidadFilter.getTipoEntidad());
			criteriaEntidad.add(c1);
		}
		if (Objects.nonNull(entidadFilter.getTipoEmpresa())) {
			c1 = Restrictions.eq("empresa.tipoEmpresa", entidadFilter.getTipoEmpresa());
			criteriaEntidad.add(c1);
		}

	}

	@Override
	public Entidad getByDni(String dni) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaEntidad = session.createCriteria(Entidad.class, "entidad");
		Criteria criteriaPersona = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);

		/*###########################Control por APP############################*/
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaEntidad.add(c1);
		/*######################################################################*/
		// verificamos si existe el deni en personas para usuario
		c1 = Restrictions.eq("dni", dni);
		criteriaPersona.add(c1);
		c1 = Restrictions.eq("tipo", TipoEntidad.PERSONA);//persona
		criteriaEntidad.add(c1);
		List list = criteriaEntidad.list();
		if (list.size() >= 1) {
			return (Entidad) list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Entidad getByCuit(String cuit) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaEntidad = session.createCriteria(Entidad.class, "entidad");
		Criteria criteriaPersona = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);

		/*###########################Control por APP############################*/
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaEntidad.add(c1);
		/*######################################################################*/
		// verificamos si existe el deni en personas para usuario
		c1 = Restrictions.eq("cuit", cuit);
		criteriaEmpresa.add(c1);
		c1 = Restrictions.eq("tipo", TipoEntidad.EMPRESA);//empresa
		criteriaEntidad.add(c1);
		List list = criteriaEntidad.list();
		if (list.size() >= 1) {
			return (Entidad) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * MOdifica el tipo de empresa si es que en clientes lo modifica, tiene que conconrodar lo que dice en emrpesa y cliente
	 *
	 * @param entidad
	 * @param subEntidad, es cliente o proveedor
	 */
	@Override
	public void setDoc(Entidad entidad, IAfip subEntidad) {
		Boolean update = false;
		Session session = sessionFactory.getCurrentSession();
		if (entidad.getTipo().equals(TipoEntidad.EMPRESA)) {
			if (!entidad.getEmpresa().getTipoEmpresa().theState.equals(subEntidad.getCondicion())) {
				update = true;
			}
			if (update) {
				String updateSQL = " update empresas set tipo= :tipo where entidad_id=:id";
				int updatedEntities = session.createSQLQuery(updateSQL)
						.setInteger("tipo", subEntidad.getCondicion())
						.setInteger("id", entidad.getId())
						.executeUpdate();
			}
		}
	}

	@Override
	public Entidad get(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaEntidad = session.createCriteria(Entidad.class, "entidad");
		Criteria criteriaPersona = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);

		criteriaEntidad.createAlias("entidad.domicilios", "domicilios", JoinType.LEFT_OUTER_JOIN);
		criteriaEntidad.createAlias("entidad.contactos", "contactos", JoinType.LEFT_OUTER_JOIN);
		criteriaEntidad.createAlias("entidad.telefonos", "telefonos", JoinType.LEFT_OUTER_JOIN);

		/*###########################Control por APP############################*/
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaEntidad.add(c1);
		/*######################################################################*/
		criteriaEntidad.add(Restrictions.eq("id", id));
		Entidad entidad = (Entidad) criteriaEntidad.uniqueResult();
		return entidad;
	}

	@Override
	public Boolean isCliente(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		Criteria criteriaEntidad = criteriaCliente.createCriteria("cliente.entidad", "entidad", JoinType.LEFT_OUTER_JOIN);

		/*###########################Control por APP############################*/
		criteriaCliente.setFetchMode("entidad.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaCliente.add(c1);
		/*######################################################################*/
		criteriaEntidad.add(Restrictions.eq("id", id));
		Cliente cliente = (Cliente) criteriaCliente.uniqueResult();
		return Objects.nonNull(cliente);
	}

	@Override
	public Boolean isProveedor(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaProveedor = session.createCriteria(Proveedores.class, "proveedor");
		Criteria criteriaEntidad = criteriaProveedor.createCriteria("proveedor.entidad", "entidad", JoinType.LEFT_OUTER_JOIN);

		/*###########################Control por APP############################*/
		criteriaProveedor.setFetchMode("entidad.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaProveedor.add(c1);
		/*######################################################################*/
		criteriaEntidad.add(Restrictions.eq("id", id));
		Proveedores proveedor = (Proveedores) criteriaProveedor.uniqueResult();
		return Objects.nonNull(proveedor);
	}

	@Override
	public Entidad insertPersona(Entidad entidad) {
		Session session = sessionFactory.getCurrentSession();
		entidad.setFechAlta(new Date());
		entidad.setTipo(TipoEntidad.PERSONA);//persona
		/*###########################Control por APP############################*/
		entidad.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
		/*######################################################################*/
		Personas persona = entidad.getPersona();
		session.save(entidad);
		persona.setEntidad(entidad);
		session.save(persona);
		return entidad;
	}

	@Override
	public Entidad insertEmpresa(Entidad entidad) {
		Session session = sessionFactory.getCurrentSession();
		entidad.setFechAlta(new Date());
		entidad.setTipo(TipoEntidad.EMPRESA);//empresa
		/*###########################Control por APP############################*/
		entidad.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
		/*######################################################################*/
		Empresas empresa = entidad.getEmpresa();
		session.save(entidad);
		empresa.setEntidad(entidad);
		session.save(empresa);
		return entidad;
	}

	@Override
	public Entidad updatePersona(Entidad personaOld, Entidad personaNew) {
		Session session = sessionFactory.getCurrentSession();
		personaOld = this.getObjectPersona(personaOld, personaNew);
		session.update(personaOld);
		return personaOld;
	}

	@Override
	public Entidad updateEmpresa(Entidad personaOld, Entidad personaNew) {
		Session session = sessionFactory.getCurrentSession();
		personaOld = this.getObjectEmpresa(personaOld, personaNew);
		session.update(personaOld);
		return personaOld;
	}

	@Override
	public Entidad getObjectPersona(Entidad entidadOld, Entidad entidadNew) {
		if (Objects.isNull(entidadOld)) {
			entidadOld = new Entidad();
		}
		if (Objects.isNull(entidadOld.getPersona())) {
			entidadOld.setPersona(new Personas());
		}
		entidadOld.getPersona().setApellido(entidadNew.getPersona().getApellido());
		entidadOld.getPersona().setNombre(entidadNew.getPersona().getNombre());
		entidadOld.getPersona().setCuil(entidadNew.getPersona().getCuil());
		entidadOld.getPersona().setSexo(entidadNew.getPersona().getSexo());
		entidadOld.getPersona().setFechaNac(entidadNew.getPersona().getFechaNac());
		return entidadOld;
	}

	@Override
	public Entidad getObjectEmpresa(Entidad entidadOld, Entidad entidadNew) {
		if (Objects.isNull(entidadOld)) {
			entidadOld = new Entidad();
		}
		if (Objects.isNull(entidadOld.getEmpresa())) {
			entidadOld.setEmpresa(new Empresas());
		}
		entidadOld.getEmpresa().setCuit(entidadNew.getEmpresa().getCuit());
		entidadOld.getEmpresa().setObservacion(entidadNew.getEmpresa().getObservacion());
		entidadOld.getEmpresa().setPersona(entidadNew.getEmpresa().getPersona());
		entidadOld.getEmpresa().setRazonSocial(entidadNew.getEmpresa().getRazonSocial());
		entidadOld.getEmpresa().setTipoEmpresa(entidadNew.getEmpresa().getTipoEmpresa());
		return entidadOld;
	}

}
