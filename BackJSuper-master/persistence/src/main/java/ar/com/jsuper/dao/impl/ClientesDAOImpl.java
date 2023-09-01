package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.ClientesDAO;
import ar.com.jsuper.dao.PersonasDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.Contactos;
import ar.com.jsuper.domain.utils.FilterClientes;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.security.TenantContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
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
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientesDAOImpl extends GenericDAOImpl<Cliente, Integer> implements ClientesDAO {

	@Autowired
	PersonasDAO personasDAO;

	@Override
	public Pagination<Cliente> getClientesBypage(FilterClientes clienteFilter, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaCliente.setProjection(idCountProjection);
		Criteria criteriaEntidad = criteriaCliente.createCriteria("cliente.entidad", "entidad", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPersonas = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);
		criteriaCliente.setFetchMode("cuentaCorriente", FetchMode.JOIN);

		Integer activo = clienteFilter.getActivo();
		Criterion c1;
		if (clienteFilter.getNombre() != null && !clienteFilter.getNombre().trim().equals("")) {
			String[] nombresArray = clienteFilter.getNombre().split("\\s+");
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
		if (clienteFilter.getDni() != null && !clienteFilter.getDni().trim().equals("")) {
			c1 = Restrictions.like("persona.dni", clienteFilter.getDni(), MatchMode.ANYWHERE);
			criteriaPersonas.add(c1);
		}
		if (clienteFilter.getCuit() != null && !clienteFilter.getCuit().trim().equals("")) {
			c1 = Restrictions.like("empresa.cuit", clienteFilter.getCuit(), MatchMode.ANYWHERE);
			criteriaEmpresa.add(c1);
		}
		if (Objects.nonNull(clienteFilter.getTipoCliente())) {
			c1 = Restrictions.eq("tipoCliente", clienteFilter.getTipoCliente());
			criteriaCliente.add(c1);
		}
		// restriccion para activos o no activos
		if (activo != null && !activo.equals("") && activo != 2) {
			Criterion cActivo = Restrictions.eq("cliente.estado", (activo == 1));
			criteriaCliente.add(cActivo);
		}
		/*###########################Control por APP############################*/
		criteriaCliente.setFetchMode("cliente.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaCliente.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaCliente.uniqueResult()).intValue();
//
		if (reverse) {
			criteriaCliente.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaCliente.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaCliente.setProjection(Projections.distinct(Projections.property("id")));
		criteriaCliente.setFirstResult((page - 1) * pageSize);
		criteriaCliente.setMaxResults(pageSize);
		List uniqueSubList = criteriaCliente.list();
		criteriaCliente.setProjection(null);
		criteriaCliente.setFirstResult(0);
		criteriaCliente.setMaxResults(Integer.MAX_VALUE);
		criteriaCliente.add(Restrictions.in("id", uniqueSubList));
		criteriaCliente.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//        List listassss= criteriaCliente.list();
		ArrayList<Cliente> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaCliente.list());
		}
		Pagination<Cliente> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public List<Cliente> getClientesByNameOrDni(String query, Integer activo) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaCliente.setProjection(idCountProjection);
		criteriaCliente.setFetchMode("cuentaCorriente", FetchMode.JOIN);
		Criteria criteriaPersonas = criteriaCliente.createCriteria("cliente.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criterion c1;
		Criterion c3;
//        Conjunction disjunctionNombres = new Conjunction();
		query = query.trim();
		if (query != null && !query.equals("")) {
			Conjunction disjunctionNombres = new Conjunction();
			String nombreFinal = query;
			if (nombreFinal != null && !nombreFinal.equals("")) {
				String[] nombresArray = nombreFinal.split("\\s+");
				disjunctionNombres = Restrictions.conjunction();
				for (String nombre : nombresArray) {
					if (!nombre.equals("")) {
						disjunctionNombres.add(Restrictions.sqlRestriction("concat({alias}.apellido, {alias}.nombre) like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
					}
				}
			}
			c3 = Restrictions.like("dni", query, MatchMode.ANYWHERE);
			criteriaPersonas.add(Restrictions.or(disjunctionNombres, c3));
		}
		// restriccion para activos o no activos
		if (activo != null && !activo.equals("") && activo != 2) {
			Criterion cActivo = Restrictions.eq("cliente.estado", (activo == 1));
			criteriaCliente.add(cActivo);
		}
		/*###########################Control por APP############################*/
		criteriaCliente.setFetchMode("cliente.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaCliente.add(c1);
		/*######################################################################*/

		criteriaCliente.setProjection(Projections.distinct(Projections.property("id")));
		criteriaCliente.setProjection(null);
		criteriaCliente.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteriaCliente.list();
	}

	@Override
	public Pagination<Cliente> getClientesByMultipleFilter(String query, Integer activo, Integer page, Integer pageSize, String fieldOrder, Boolean reverse) {
//		Session session = sessionFactory.getCurrentSession();
//		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
//		Projection idCountProjection = Projections.countDistinct("id");
//		criteriaCliente.setProjection(idCountProjection);
//		criteriaCliente.setFetchMode("cuentaCorriente", FetchMode.JOIN);
//		Criteria criteriaPersonas = criteriaCliente.createCriteria("cliente.persona", "persona", JoinType.LEFT_OUTER_JOIN);
//		Criterion c1;
//		Criterion c3;
////        Conjunction disjunctionNombres = new Conjunction();
//		query = query.trim();
//		if (query != null && !query.equals("")) {
//			Conjunction disjunctionNombres = new Conjunction();
//			String nombreFinal = query;
//			if (nombreFinal != null && !nombreFinal.equals("")) {
//				String[] nombresArray = nombreFinal.split("\\s+");
//				disjunctionNombres = Restrictions.conjunction();
//				for (String nombre : nombresArray) {
//					if (!nombre.equals("")) {
//						disjunctionNombres.add(Restrictions.sqlRestriction("concat({alias}.apellido, {alias}.nombre) like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
//					}
//				}
//			}
//			c3 = Restrictions.like("dni", query, MatchMode.ANYWHERE);
//			criteriaPersonas.add(Restrictions.or(disjunctionNombres, c3));
//		}
//		// restriccion para activos o no activos
//		if (activo != null && !activo.equals("") && activo != 2) {
//			Criterion cActivo = Restrictions.eq("cliente.estado", (activo == 1));
//			criteriaCliente.add(cActivo);
//		}
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaCliente.setProjection(idCountProjection);
		Criteria criteriaEntidad = criteriaCliente.createCriteria("cliente.entidad", "entidad", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPersonas = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);
		criteriaCliente.setFetchMode("cuentaCorriente", FetchMode.JOIN);

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
			criteriaCliente.add(Restrictions.or(disjunctionGral, c1, c2));
		}
		/*###########################Control por APP############################*/
		criteriaCliente.setFetchMode("cliente.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaCliente.add(c1);
		/*######################################################################*/

		Integer totalResultCount = ((Long) criteriaPersonas.uniqueResult()).intValue();

		if (reverse) {
			criteriaCliente.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaCliente.addOrder(Order.desc(fieldOrder.trim()));
		}
		if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
			criteriaCliente.setFirstResult((page - 1) * pageSize);
			criteriaCliente.setMaxResults(pageSize);
		}
		criteriaCliente.setProjection(Projections.distinct(Projections.property("id")));
		List uniqueSubList = criteriaCliente.list();
		criteriaCliente.setProjection(null);
		criteriaCliente.setFirstResult(0);
		criteriaCliente.setMaxResults(Integer.MAX_VALUE);
		criteriaCliente.add(Restrictions.in("id", uniqueSubList));
		criteriaCliente.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Cliente> lista = null;
		if (totalResultCount <= 0) {
			lista = null;
		} else {
			lista = new ArrayList<>(criteriaCliente.list());
		}
		Pagination<Cliente> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public Cliente getClienteMinById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		Criteria criteriaEntidad = criteriaCliente.createCriteria("cliente.entidad", "entidad", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPersonas = criteriaEntidad.createCriteria("entidad.persona", "persona", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEmpresa = criteriaEntidad.createCriteria("entidad.empresa", "empresa", JoinType.LEFT_OUTER_JOIN);
		criteriaCliente.setFetchMode("cuentaCorriente", FetchMode.JOIN);
		criteriaCliente.add(Restrictions.eq("id", id));
		Criterion c1;
		/*###########################Control por APP############################*/
		criteriaCliente.setFetchMode("cliente.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaCliente.add(c1);
		/*######################################################################*/
		return (Cliente) criteriaCliente.uniqueResult();
	}

	@Override
	public Cliente getClienteMinByEntidad(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		Criteria criteriaEntidad = criteriaCliente.createCriteria("cliente.entidad", "entidad", JoinType.LEFT_OUTER_JOIN);
		criteriaCliente.setFetchMode("cuentaCorriente", FetchMode.JOIN);
		criteriaEntidad.add(Restrictions.eq("id", id));
		Criterion c1;
		/*###########################Control por APP############################*/
		criteriaCliente.setFetchMode("cliente.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaCliente.add(c1);
		/*######################################################################*/
		return (Cliente) criteriaCliente.uniqueResult();
	}

	@Override
	public Cliente getClienteMicById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		Criteria criteriaEntidad = criteriaCliente.createCriteria("cliente.entidad", "entidad", JoinType.LEFT_OUTER_JOIN);
		criteriaCliente.add(Restrictions.eq("id", id));
		Criterion c1;
		/*###########################Control por APP############################*/
		criteriaCliente.setFetchMode("cliente.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaCliente.add(c1);
		/*######################################################################*/
		return (Cliente) criteriaCliente.uniqueResult();
	}

	@Override
	public Cliente insert(Cliente cliente) {
		Session session = sessionFactory.getCurrentSession();
		/*###########################Control por APP############################*/
		cliente.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
		/*######################################################################*/
		cliente.setEstado(Boolean.TRUE);
		session.save(cliente);
		return cliente;
	}

	@Override
	public Cliente update(Cliente clienteOld, Cliente clienteNew) {
		Session session = sessionFactory.getCurrentSession();
		clienteOld = this.getObject(clienteOld, clienteNew);
		session.update(clienteOld);
		return clienteOld;
	}

	@Override
	public Cliente getByDni(String dni) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		Criteria criteriaPersonas = criteriaCliente.createCriteria("cliente.persona", "persona", JoinType.LEFT_OUTER_JOIN);
//        criteriaPersonas.setFetchMode("persona.domicilios", FetchMode.JOIN);
		criteriaCliente.setFetchMode("cuentaCorriente", FetchMode.JOIN);
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
		List list = criteriaCliente.list();
		if (list.size() >= 1) {
			return (Cliente) list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List getListByDniOrName(FilterGeneric filter) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		Criteria criteriaPersonas = criteriaCliente.createCriteria("cliente.persona", "persona", JoinType.RIGHT_OUTER_JOIN);
		criteriaCliente.setFetchMode("cuentaCorriente", FetchMode.JOIN);
//        criteriaPersonas.createAlias("persona.domicilios", "domicilios", JoinType.LEFT_OUTER_JOIN);
//        criteriaPersonas.createAlias("persona.contactos", "contactos", JoinType.LEFT_OUTER_JOIN);
//        criteriaPersonas.createAlias("persona.telefonos", "telefonos", JoinType.LEFT_OUTER_JOIN);
		/*###########################Control por APP############################*/
		criteriaPersonas.setFetchMode("persona.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaPersonas.add(c1);
		/*######################################################################*/
		// primero me aseguro si es un numero esta buscando por d
		if (!"".equals(filter.getText().trim())) {
			if (StringUtils.isNumeric(filter.getText().trim())) {
				c1 = Restrictions.eq("dni", filter.getText().trim());
				criteriaPersonas.add(c1);
			} else {
//        Conjunction conjunctionGral = Restrictions.conjunction();
//        Disjunction disjunctionGral = Restrictions.disjunction();
//        Disjunction disjunctionNombre = Restrictions.disjunction();
				String nombreFinal = filter.getText().trim();
				// restriccion para el nombre
				if (nombreFinal != null && !nombreFinal.equals("")) {// este trim se le agrega al final
					String[] nombresArray = nombreFinal.split("\\s+");
					Conjunction conjunctionNombres = Restrictions.conjunction();
//            Conjunction conjunctionNombresFinal = Restrictions.conjunction();
//            Disjunction disjunctionNombres = Restrictions.disjunction();
					for (String nombre : nombresArray) {
						if (!nombre.equals("")) {
							conjunctionNombres.add(Restrictions.sqlRestriction("concat({alias}.apellido, {alias}.nombre) like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
						}
					}
					criteriaPersonas.add(conjunctionNombres);
//            disjunctionNombres.add(conjunctionNombres);
//            disjunctionGral.add(disjunctionNombres);
				}
			}
		}
//        criteriaCliente.setProjection(Projections.projectionList());
//        criteriaCliente.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return criteriaCliente.list();
	}

	@Override
	public Cliente getOnlyClienteByDni(String dni) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		Criteria criteriaPersonas = criteriaCliente.createCriteria("cliente.persona", "persona", JoinType.LEFT_OUTER_JOIN);
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
		List list = criteriaCliente.list();
		if (list.size() >= 1) {
			return (Cliente) list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Set<Contactos> getContactos(Integer clienteId, Integer tipo) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		Criteria criteriaEntidad = criteriaCliente.createCriteria("cliente.entidad", "entidad", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaContactos = criteriaEntidad.createCriteria("entidad.contactos", "contactos", JoinType.LEFT_OUTER_JOIN);
		/*###########################Control por APP############################*/
		criteriaCliente.setFetchMode("entidad.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaCliente.add(c1);
		/*######################################################################*/
		criteriaCliente.add(Restrictions.eq("id", clienteId));
		criteriaContactos.add(Restrictions.eq("tipo", tipo));
		Cliente cliente = (Cliente) criteriaCliente.uniqueResult();
		if (Objects.nonNull(cliente)) {
			return cliente.getEntidad().getContactos();
		}
		return new HashSet<>();
	}

	@Override
	public Cliente getObject(Cliente clienteOld, Cliente clienteNew) {
		if (Objects.isNull(clienteOld)) {
			clienteOld = new Cliente();
		}
		clienteOld.setTipoCliente(clienteNew.getTipoCliente());
		clienteOld.setObservacion(clienteNew.getObservacion());
		clienteOld.setTipoDocCliente(clienteNew.getTipoDocCliente());
		clienteOld.setNroDocCliente(clienteNew.getNroDocCliente());
		return clienteOld;
	}
}
