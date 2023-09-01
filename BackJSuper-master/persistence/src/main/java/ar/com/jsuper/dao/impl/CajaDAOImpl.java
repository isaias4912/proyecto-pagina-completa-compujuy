package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.CajaDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Caja;
import ar.com.jsuper.domain.DetalleTransaccionCaja;
import ar.com.jsuper.domain.CbteEncVenta;
import ar.com.jsuper.domain.TransaccionCaja;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.domain.utils.DataTransaccionCaja;
import ar.com.jsuper.domain.utils.FilterCaja;
import ar.com.jsuper.domain.utils.FilterTransaccion;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.utils.DateUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class CajaDAOImpl extends GenericDAOImpl<Caja, Integer> implements CajaDAO {

	@Override
	public Pagination<Caja> getCajasBypage(FilterCaja filterCaja, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaCaja = session.createCriteria(Caja.class, "caja");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaCaja.setProjection(idCountProjection);
		Integer activo = filterCaja.getActivo();
		Criterion c1;
		criteriaCaja.setFetchMode("sucursales", FetchMode.JOIN);

		if (filterCaja.getNombre() != null && !filterCaja.getNombre().trim().equals("")) {
			c1 = Restrictions.like("nombre", filterCaja.getNombre(), MatchMode.ANYWHERE);
			criteriaCaja.add(c1);
		}
		if (filterCaja.getNombreMaquina() != null && !filterCaja.getNombreMaquina().trim().equals("")) {
			c1 = Restrictions.like("nombreMaquina", filterCaja.getNombreMaquina(), MatchMode.ANYWHERE);
			criteriaCaja.add(c1);
		}
		// restriccion para activos o no activos
		if (activo != null && !activo.equals("") && activo != 2) {
			Criterion cActivo = Restrictions.eq("activo", (activo == 1));
			criteriaCaja.add(cActivo);
		}
		/*###########################Control por APP############################*/
		criteriaCaja.setFetchMode("caja.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaCaja.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaCaja.uniqueResult()).intValue();

		if (reverse) {
			criteriaCaja.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaCaja.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaCaja.setProjection(Projections.distinct(Projections.property("id")));
		criteriaCaja.setFirstResult((page - 1) * pageSize);
		criteriaCaja.setMaxResults(pageSize);
//        List uniqueSubList = criteriaCaja.list();
		criteriaCaja.setProjection(null);
//        criteriaCaja.setFirstResult(0);
//        criteriaCaja.setMaxResults(Integer.MAX_VALUE);
//        criteriaCaja.add(Restrictions.in("id", uniqueSubList));
		criteriaCaja.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<Caja> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaCaja.list());
		}
		Pagination<Caja> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public List<Caja> getAllListActive() throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Caja.class, "caja");
		/*###########################Control por APP############################*/
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		c1 = Restrictions.eq("activo", true);
		c.add(c1);
		return c.list();
	}

	@Override
	public Caja get(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Caja.class, "caja");
		/*###########################Control por APP############################*/
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		c1 = Restrictions.eq("id", id);
		c.add(c1);
		Caja caja = (Caja) c.uniqueResult();
		return caja;
	}

	@Override
	public Caja getByNombrePC(String nombrePC) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Caja.class, "caja");
		/*###########################Control por APP############################*/
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		c1 = Restrictions.eq("nombreMaquina", nombrePC);
		c.add(c1);
		Caja caja = (Caja) c.uniqueResult();
		return caja;
	}

	@Override
	public boolean isExistPC(String nombreMaquina) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Caja.class, "caja");
		Criterion c1 = Restrictions.eq("nombreMaquina", nombreMaquina);
		c.add(c1);
		/*###########################Control por APP############################*/
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
	public boolean isExistCaja(String nombre) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Caja.class, "caja");
		Criterion c1 = Restrictions.eq("nombre", nombre);
		c.add(c1);
		/*###########################Control por APP############################*/
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
	public Caja insert(Caja caja) {
		Session session = sessionFactory.getCurrentSession();
		/*###########################Control por APP############################*/
		caja.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
		/*######################################################################*/
		session.save(caja);
		return caja;
	}

	@Override
	public Caja update(Caja cajaOld, Caja cajaNew) {
		Session session = sessionFactory.getCurrentSession();
		cajaOld = this.getObject(cajaOld, cajaNew);
		session.update(cajaOld);
		return cajaOld;
	}

	/**
	 * habilitamos o deshabilitamos
	 *
	 */
	@Override
	public int enabledOrdisabled(Set<Caja> cajas, boolean value) {
		Session session = sessionFactory.getCurrentSession();
		Set<Integer> setIds = new HashSet<>();
		for (Caja caja : cajas) {
			setIds.add(caja.getId());
		}
		String updateHQL = " UPDATE Caja c SET c.activo = :valueactivo"
				+ " WHERE  c.id in (:idsCajas)";
		int updatedEntities = session.createQuery(updateHQL)
				.setBoolean("valueactivo", value)
				.setParameterList("idsCajas", setIds)
				.executeUpdate();
		return updatedEntities;
	}

	@Override
	public Caja getObject(Caja cajaOld, Caja cajaNew) {
		if (Objects.isNull(cajaOld)) {
			cajaOld = new Caja();
		}
		cajaOld.setIpMaquina(cajaNew.getIpMaquina());
		cajaOld.setNombre(cajaNew.getNombre());
		cajaOld.setNombreMaquina(cajaNew.getNombreMaquina());
		cajaOld.setLimiteConsumidorFinal(cajaNew.getLimiteConsumidorFinal());
		cajaOld.setModificaAdicional(cajaNew.getModificaAdicional());
		cajaOld.setModificaDescuento(cajaNew.getModificaDescuento());
		cajaOld.setModificaPrecio(cajaNew.getModificaPrecio());
		cajaOld.setConControlEstricto(cajaNew.getConControlEstricto());
		cajaOld.setObservacion(cajaNew.getObservacion());
		cajaOld.setIdPuntoVenta(cajaNew.getIdPuntoVenta());
		cajaOld.setComprobantesHab(cajaNew.getComprobantesHab());
		return cajaOld;
	}

	/**
	 * Devolver la ultima transaccion de la caja
	 *
	 * @param caja
	 * @return
	 * @throws BussinessException
	 */
	@Override
	public TransaccionCaja getLastTransaccionCaja(Caja caja) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(TransaccionCaja.class, "t");
		c.setFetchMode("detallesTransaccionCaja", FetchMode.JOIN);
		c.createAlias("t.detallesTransaccionCaja", "detallesTransaccionCaja", JoinType.RIGHT_OUTER_JOIN);
		Criterion c1 = Restrictions.eq("caja", caja);
		c.add(c1);
		Criterion c2 = Restrictions.eq("estado", 1);
		c.add(c2);
		c.setMaxResults(1);
		c.addOrder(Order.desc("id"));
		List<TransaccionCaja> lista = c.list();
		if (lista.size() == 1) {
			return lista.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Devolver transaccion de la caja segun id
	 *
	 * @return
	 */
	@Override
	public TransaccionCaja getTransaccionCaja(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaTransaccion = session.createCriteria(TransaccionCaja.class, "t");
		criteriaTransaccion.createAlias("t.detallesTransaccionCaja", "detallesTransaccionCaja", JoinType.LEFT_OUTER_JOIN);
		criteriaTransaccion.createAlias("t.caja", "caja", JoinType.LEFT_OUTER_JOIN);
		criteriaTransaccion.setFetchMode("detallesTransaccionCaja.usuario", FetchMode.JOIN);
		criteriaTransaccion.setFetchMode("detallesTransaccionCaja.cbteVenEnc", FetchMode.JOIN);

		Criterion c1 = Restrictions.eq("id", id);
		criteriaTransaccion.add(c1);
		return (TransaccionCaja) criteriaTransaccion.uniqueResult();
	}

	/**
	 * Devolver tuna caja segun la transaccion
	 *
	 * @return
	 */
	@Override
	public Caja getCajaFromTransaccion(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaTransaccion = session.createCriteria(TransaccionCaja.class, "t");
		criteriaTransaccion.setFetchMode("t.caja", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("id", id);
		criteriaTransaccion.add(c1);
		TransaccionCaja transaccionCaja = (TransaccionCaja) criteriaTransaccion.uniqueResult();
		return transaccionCaja.getCaja();
	}

	/**
	 * Devuelve un detalle de transaccion por id
	 *
	 * @return
	 * @throws BussinessException
	 */
	@Override
	public DetalleTransaccionCaja getDetalleTransaccionCaja(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(DetalleTransaccionCaja.class, "detalleTransaccion");
		Criteria criteriaTransaccion = c.createCriteria("detalleTransaccion.transaccionCaja", "transaccionCaja", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaCaja = criteriaTransaccion.createCriteria("transaccionCaja.caja", "caja", JoinType.LEFT_OUTER_JOIN);
		c.add(Restrictions.eq("id", id));
		return (DetalleTransaccionCaja) c.uniqueResult();
	}

	/**
	 * Realiza una apertura de Caja
	 *
	 * @param dataTransaccionCaja
	 * @return
	 * @throws BussinessException
	 */
	@Override
	public TransaccionCaja abrirCaja(DataTransaccionCaja dataTransaccionCaja) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		TransaccionCaja transaccionCaja = new TransaccionCaja();
		Date fecha = new Date();
		transaccionCaja.setCaja(dataTransaccionCaja.getCaja());
		transaccionCaja.setEstado(1);//1: abierta
		transaccionCaja.setFechaApertura(fecha);
		session.save(transaccionCaja);
		DetalleTransaccionCaja detalleTransaccionCaja = dataTransaccionCaja.getDetalleTransaccionCaja();
		Usuarios usuario = new Usuarios();
		usuario.setId(TenantContext.getCurrentIdUser());
		detalleTransaccionCaja.setUsuario(usuario);
		detalleTransaccionCaja.setFecha(fecha);
		detalleTransaccionCaja.setTipo(1);// 1: apertura
		detalleTransaccionCaja.setTransaccionCaja(transaccionCaja);
		session.save(detalleTransaccionCaja);
		transaccionCaja.setDetallesTransaccionCaja(new ArrayList<DetalleTransaccionCaja>());
		transaccionCaja.getDetallesTransaccionCaja().add(detalleTransaccionCaja);
		return transaccionCaja;

	}

	/**
	 * Realiza cierre de una Caja
	 *
	 * @param dataTransaccionCaja
	 * @return
	 * @throws BussinessException
	 */
	@Override
	public TransaccionCaja cerrarCaja(DataTransaccionCaja dataTransaccionCaja) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		TransaccionCaja transaccionCaja = session.load(TransaccionCaja.class, dataTransaccionCaja.getTransaccionCaja().getId());
		Date fecha = new Date();
		transaccionCaja.setEstado(2); //2: cierre de caja
		transaccionCaja.setFechaCierre(fecha);
		session.update(transaccionCaja);
		DetalleTransaccionCaja detalleTransaccionCaja = dataTransaccionCaja.getDetalleTransaccionCaja();
		Usuarios usuario = new Usuarios();
		usuario.setId(TenantContext.getCurrentIdUser());
		detalleTransaccionCaja.setUsuario(usuario);
		detalleTransaccionCaja.setFecha(fecha);
		detalleTransaccionCaja.setTipo(20);// 20: cierre
		detalleTransaccionCaja.setTransaccionCaja(transaccionCaja);
		session.save(detalleTransaccionCaja);
		return transaccionCaja;
	}

	/**
	 * Realiza in ingreso, egreso etc de caja
	 *
	 * @param dataTransaccionCaja
	 * @return
	 * @throws BussinessException
	 */
	@Override
	public TransaccionCaja movimientoCaja(DataTransaccionCaja dataTransaccionCaja) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		TransaccionCaja transaccionCaja = session.load(TransaccionCaja.class, dataTransaccionCaja.getTransaccionCaja().getId());
		DetalleTransaccionCaja detalleTransaccionCaja = dataTransaccionCaja.getDetalleTransaccionCaja();
		Usuarios usuario = new Usuarios();
		usuario.setId(TenantContext.getCurrentIdUser());
		detalleTransaccionCaja.setUsuario(usuario);
		detalleTransaccionCaja.setFecha(new Date());
		detalleTransaccionCaja.setTransaccionCaja(transaccionCaja);
		session.save(detalleTransaccionCaja);
		return transaccionCaja;
	}

	/**
	 * Realiza una anulacion de venta
	 *
	 * @param dataTransaccionCaja
	 * @return
	 * @throws BussinessException
	 */
	@Override
	public TransaccionCaja anulaVenta(DataTransaccionCaja dataTransaccionCaja) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		TransaccionCaja transaccionCaja = session.load(TransaccionCaja.class, dataTransaccionCaja.getTransaccionCaja().getId());
		DetalleTransaccionCaja detalleTransaccionCaja = dataTransaccionCaja.getDetalleTransaccionCaja();
		detalleTransaccionCaja.setFecha(new Date());
		detalleTransaccionCaja.setTransaccionCaja(transaccionCaja);
		session.save(detalleTransaccionCaja);
		CbteEncVenta cbteEncVenta = detalleTransaccionCaja.getCbteVenEnc();
		Usuarios usuario = new Usuarios();
		usuario.setId(TenantContext.getCurrentIdUser());
		detalleTransaccionCaja.setUsuario(usuario);
		if (!Objects.isNull(cbteEncVenta)) {
			String updateHQL = " UPDATE CbteVenEnc e SET e.estado = :valueestado"
					+ " WHERE  e.id = :id";
			int updatedEntities = session.createQuery(updateHQL)
					.setBoolean("valueestado", Boolean.FALSE)
					.setInteger("id", cbteEncVenta.getId())
					.executeUpdate();
		} else {
			throw new DataIntegrityViolationException("Error - Se requiere el ingreso de una venta");
		}
		return transaccionCaja;
	}

	/**
	 * devuelve paginacion de las transacciones para una app
	 *
	 * @param filterTransaccion
	 * @param page
	 * @param pageSize
	 * @param fieldOrder
	 * @param reverse
	 * @return
	 */
	@Override
	public Pagination<TransaccionCaja> getTransaccionBypage(FilterTransaccion filterTransaccion, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaTransaccion = session.createCriteria(TransaccionCaja.class, "transaccion");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaTransaccion.setProjection(idCountProjection);
		Criteria criteriaCaja = criteriaTransaccion.createCriteria("transaccion.caja", "caja", JoinType.LEFT_OUTER_JOIN);

		// filtro para las fechas
		if (DateUtils.isValid(filterTransaccion.getFechaInicial()) && !DateUtils.isValid(filterTransaccion.getFechaFinal())) {
			criteriaTransaccion.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_apertura, '%Y-%m-%d') >= STR_TO_DATE(?, '%d-%m-%Y')", filterTransaccion.getFechaInicial().trim(), StandardBasicTypes.STRING));
		}
		if (DateUtils.isValid(filterTransaccion.getFechaFinal()) && !DateUtils.isValid(filterTransaccion.getFechaInicial())) {
			criteriaTransaccion.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_apertura, '%Y-%m-%d') <= STR_TO_DATE(?, '%d-%m-%Y')", filterTransaccion.getFechaFinal().trim(), StandardBasicTypes.STRING));
		}
		if (DateUtils.isValid(filterTransaccion.getFechaInicial()) && DateUtils.isValid(filterTransaccion.getFechaFinal())) {
			org.hibernate.type.Type[] tipos = {StandardBasicTypes.STRING, StandardBasicTypes.STRING};
			String[] values = {filterTransaccion.getFechaInicial().trim(), filterTransaccion.getFechaFinal().trim()};
			criteriaTransaccion.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.fecha_apertura, '%Y-%m-%d') BETWEEN STR_TO_DATE(?, '%d-%m-%Y') AND  STR_TO_DATE(?, '%d-%m-%Y')", values, tipos));
		}
		if (Objects.nonNull(filterTransaccion.getId())) {
			if (filterTransaccion.getId() > 0) {
				criteriaTransaccion.add(Restrictions.eq("id", filterTransaccion.getId()));
			}
		}
		/*###########################Control por APP############################*/
		criteriaCaja.setFetchMode("caja.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaCaja.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaTransaccion.uniqueResult()).intValue();

		if (reverse) {
			criteriaTransaccion.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaTransaccion.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaTransaccion.setProjection(Projections.distinct(Projections.property("id")));
		criteriaTransaccion.setFirstResult((page - 1) * pageSize);
		criteriaTransaccion.setMaxResults(pageSize);
		List uniqueSubList = criteriaTransaccion.list();
		criteriaTransaccion.setProjection(null);
		criteriaTransaccion.setFirstResult(0);
		criteriaTransaccion.setMaxResults(Integer.MAX_VALUE);
		criteriaTransaccion.add(Restrictions.in("id", uniqueSubList));
		criteriaTransaccion.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<TransaccionCaja> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaTransaccion.list());
		}
		Pagination<TransaccionCaja> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	/**
	 * devuelve paginacion de las transacciones para una app
	 *
	 * @param filterTransaccion
	 * @param page
	 * @param pageSize
	 * @param fieldOrder
	 * @param reverse
	 * @return
	 */
	@Override
	public Pagination<DetalleTransaccionCaja> getDetalleTransaccionBypage(FilterTransaccion filterTransaccion, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaDetalleTransaccion = session.createCriteria(DetalleTransaccionCaja.class, "detalleTransaccion");
		Criteria criteriaTransaccionEnc = criteriaDetalleTransaccion.createCriteria("detalleTransaccion.cbteVenEnc", "cbteVenEnc", JoinType.LEFT_OUTER_JOIN);
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaDetalleTransaccion.setProjection(idCountProjection);
		Criteria criteriaTransaccion = criteriaDetalleTransaccion.createCriteria("detalleTransaccion.transaccionCaja", "transaccionCaja", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaCaja = criteriaTransaccion.createCriteria("transaccionCaja.caja", "caja", JoinType.LEFT_OUTER_JOIN);

		// filtro para las fechas
		if (DateUtils.isValid(filterTransaccion.getFechaInicial()) && !DateUtils.isValid(filterTransaccion.getFechaFinal())) {
			criteriaDetalleTransaccion.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha, '%Y-%m-%d') >= STR_TO_DATE(?, '%d-%m-%Y')", filterTransaccion.getFechaInicial().trim(), StandardBasicTypes.STRING));
		}
		if (DateUtils.isValid(filterTransaccion.getFechaFinal()) && !DateUtils.isValid(filterTransaccion.getFechaInicial())) {
			criteriaDetalleTransaccion.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha, '%Y-%m-%d') <= STR_TO_DATE(?, '%d-%m-%Y')", filterTransaccion.getFechaFinal().trim(), StandardBasicTypes.STRING));
		}
		if (DateUtils.isValid(filterTransaccion.getFechaInicial()) && DateUtils.isValid(filterTransaccion.getFechaFinal())) {
			org.hibernate.type.Type[] tipos = {StandardBasicTypes.STRING, StandardBasicTypes.STRING};
			String[] values = {filterTransaccion.getFechaInicial().trim(), filterTransaccion.getFechaFinal().trim()};
			criteriaDetalleTransaccion.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.fecha, '%Y-%m-%d') BETWEEN STR_TO_DATE(?, '%d-%m-%Y') AND  STR_TO_DATE(?, '%d-%m-%Y')", values, tipos));
		}
		if (Objects.nonNull(filterTransaccion.getUsuario()) && Objects.nonNull(filterTransaccion.getUsuario().getId())) {
			if (filterTransaccion.getUsuario().getId() > 0) {
				criteriaDetalleTransaccion.add(Restrictions.eq("usuario.id", filterTransaccion.getUsuario().getId()));
			}
		}
		if (Objects.nonNull(filterTransaccion.getTransaccion())) {
			if (filterTransaccion.getTransaccion() > 0) {
				criteriaTransaccion.add(Restrictions.eq("id", filterTransaccion.getTransaccion()));
			}
		}

		// filtro si es mayor a  cierto monto
		if (Objects.nonNull(filterTransaccion.getTotalMinimo())) {
			if (filterTransaccion.getTotalMinimo().compareTo(BigDecimal.ZERO) > 0) {
				criteriaDetalleTransaccion.add(Restrictions.ge("monto", filterTransaccion.getTotalMinimo()));
			}
		}
		// filtro si es menor a  cierto monto
		if (Objects.nonNull(filterTransaccion.getTotalMaximo())) {
			if (filterTransaccion.getTotalMaximo().compareTo(BigDecimal.ZERO) > 0) {
				criteriaDetalleTransaccion.add(Restrictions.le("monto", filterTransaccion.getTotalMaximo()));
			}
		}

		/*###########################Control por APP############################*/
		criteriaCaja.setFetchMode("caja.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaCaja.add(c1);
		/*######################################################################*/

		if (Objects.nonNull(filterTransaccion.getCaja())) {
			criteriaCaja.add(Restrictions.eq("id", filterTransaccion.getCaja()));
		}
		if (Objects.nonNull(filterTransaccion.getTipo())) {
			criteriaDetalleTransaccion.add(Restrictions.eq("tipo", filterTransaccion.getTipo()));
		}
		Integer totalResultCount = ((Long) criteriaDetalleTransaccion.uniqueResult()).intValue();

		if (reverse) {
			criteriaDetalleTransaccion.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaDetalleTransaccion.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaDetalleTransaccion.setProjection(Projections.distinct(Projections.property("id")));
		criteriaDetalleTransaccion.setFirstResult((page - 1) * pageSize);
		criteriaDetalleTransaccion.setMaxResults(pageSize);
		List uniqueSubList = criteriaDetalleTransaccion.list();
		criteriaDetalleTransaccion.setProjection(null);
		criteriaDetalleTransaccion.setFirstResult(0);
		criteriaDetalleTransaccion.setMaxResults(Integer.MAX_VALUE);
		criteriaDetalleTransaccion.add(Restrictions.in("id", uniqueSubList));
		criteriaDetalleTransaccion.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<DetalleTransaccionCaja> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaDetalleTransaccion.list());
		}
		Pagination<DetalleTransaccionCaja> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public List getAllDetalleTransacciones(Integer idTransaccion, Integer tipo) {
		Session session = sessionFactory.getCurrentSession();
//        TransaccionCaja transaccionCaja = session.load(TransaccionCaja.class, idTransaccion);
		Criteria criteriaDetalleTransaccion = session.createCriteria(DetalleTransaccionCaja.class, "detalleTransaccion");
		Criteria criteriaTransaccion = criteriaDetalleTransaccion.createCriteria("detalleTransaccion.transaccionCaja", "transaccionCaja", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaCaja = criteriaTransaccion.createCriteria("transaccionCaja.caja", "caja", JoinType.LEFT_OUTER_JOIN);
//         Criteria c = session.createCriteria(DetalleTransaccionCaja.class, "det");
//        criteriaTransaccion.setFetchMode("transaccionCaja.caja", FetchMode.JOIN);
		criteriaTransaccion.add(Restrictions.eq("id", idTransaccion));

		if (tipo > 0) {// si es 0 que devuelva todos los tipos
			Criterion c1 = Restrictions.eq("tipo", tipo);
			criteriaDetalleTransaccion.add(c1);
		}
//        Criterion c2 = Restrictions.eq("transaccionCaja", transaccionCaja);
//        criteriaDetalleTransaccion.add(c2);
		criteriaDetalleTransaccion.addOrder(Order.desc("id"));
		return criteriaDetalleTransaccion.list();
	}
}
