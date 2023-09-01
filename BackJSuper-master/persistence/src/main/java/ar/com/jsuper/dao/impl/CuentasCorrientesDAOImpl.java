package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.CuentasCorrientesDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.*;
import ar.com.jsuper.domain.utils.FilterMovCtaCte;
import ar.com.jsuper.domain.utils.PagarCtaCte;
import ar.com.jsuper.security.TenantContext;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class CuentasCorrientesDAOImpl extends GenericDAOImpl<CuentasCorrientes, Integer> implements CuentasCorrientesDAO {

	@Override
	public CuentasCorrientes createCtaCte(Integer idCliente, CuentasCorrientes cuentaCorriente) {
		Session session = sessionFactory.getCurrentSession();
		Cliente cliente = session.load(Cliente.class, idCliente);
		cuentaCorriente.setActivo(Boolean.TRUE);
		cuentaCorriente.setCliente(cliente);
		cuentaCorriente.setFechaAlta(new Date());
		session.save(cuentaCorriente);
		return cuentaCorriente;
	}

	@Override
	public CuentasCorrientes update(CuentasCorrientes entityOld, CuentasCorrientes entityNew) {
		Session session = sessionFactory.getCurrentSession();
		entityOld = this.getObject(entityOld, entityNew);
		session.update(entityOld);
		return entityOld;
	}

	@Override
	public List<PagoPagoCtaCte> getPagosFromIdPagoCtaCte(Integer idPagoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaPagoCtaCte = session.createCriteria(PagosCtaCte.class, "pagosCtaCte");
		Criteria criteriaPagoPagoCtaCte = criteriaPagoCtaCte.createCriteria("pagosCtaCte.pagos", "pagos", JoinType.LEFT_OUTER_JOIN);
		criteriaPagoCtaCte.add(Restrictions.eq("id", idPagoCtaCte));
		PagosCtaCte pagosCtaCte = (PagosCtaCte) criteriaPagoCtaCte.uniqueResult();
		return pagosCtaCte.getPagos();
	}

	@Override
	public List<MovimientosCtaCte> pagar(PagarCtaCte pagarCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		PagosCtaCte pagoCtaCte = new PagosCtaCte();
		// por ahora la cta cte solo se puede pagar en efectivo
		pagoCtaCte.setDescripcion(pagarCtaCte.getDescripcion());
		pagoCtaCte.setMontoNeto(pagarCtaCte.getMontoNeto());
		pagoCtaCte.setMontoTotal(pagarCtaCte.getMontoTotal());// el monto con o sin interes
		pagoCtaCte.setPagoCon(pagarCtaCte.getPagoCon());
		pagoCtaCte.setTipo("PAGO_EFECTIVO"); // por ahora efectivo nada mas
		pagoCtaCte.setFecha(new Date());
		pagoCtaCte.setInteres(pagarCtaCte.getInteres());
		if (pagarCtaCte.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
			// si hay saldo significa que no pago el monto total 
			pagoCtaCte.setPago(pagarCtaCte.getMontoTotal().subtract(pagarCtaCte.getSaldo(), MathContext.DECIMAL128));

		} else {
			// si no hay saldo quiere decir que pago el monto  total
			pagoCtaCte.setPago(pagarCtaCte.getMontoTotal());
		}
		session.save(pagoCtaCte);
		// guardamops las formas de pago que se realizo
		List<PagoPagoCtaCte> pagos = pagarCtaCte.getPagos();
		pagoCtaCte.setPagos(new ArrayList<>());
		for (PagoPagoCtaCte pago : pagos) {
			pago.setPagoCtaCte(pagoCtaCte);
			pagoCtaCte.getPagos().add(pago);
			session.save(pago);
		}

		List<MovimientosCtaCte> movimientosCtaCte = pagarCtaCte.getMovimientosCtaCte();
		for (MovimientosCtaCte movimientoCtaCte : movimientosCtaCte) {
			MovimientosCtaCte movimientoCtaCteBD = session.load(MovimientosCtaCte.class, movimientoCtaCte.getId());
			movimientoCtaCteBD.setPagoCtaCte(pagoCtaCte);
			movimientoCtaCteBD.setSaldo(BigDecimal.ZERO);
			movimientoCtaCteBD.setPagado(true);
			// aqui se podria validar si el cliente del req coincide con el de la bd para seguridad
			session.update(movimientoCtaCteBD);
			if (movimientoCtaCteBD.getTipo() == 1) { // si es el pago de una venta  modificamos la venta, en enero 2023 se quito eso en realidad ya no se muestra en la lista
				CbteEnc ev = movimientoCtaCteBD.getPagoVentas().getCbteEnc();
				ev.setPagada(true);
				ev.setSaldo(BigDecimal.ZERO);
			}
			if (movimientoCtaCteBD.getTipo() == 4) { // si es el pago de un saldo
			}
		}
		// para el caso de que no haya saldo
		if (pagarCtaCte.getSaldo().compareTo(BigDecimal.ZERO) == 0 || pagarCtaCte.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
		}
		// si es que hay saldo se agrega un nuevo movimiento
		if (pagarCtaCte.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
			Cliente cliente = session.load(Cliente.class, pagarCtaCte.getCliente().getId());
			MovimientosCtaCte movimientoCtaCte = new MovimientosCtaCte();
			movimientoCtaCte.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
			movimientoCtaCte.setMonto(pagarCtaCte.getSaldo());
			movimientoCtaCte.setPagado(Boolean.FALSE);
			movimientoCtaCte.setFecha(new Date());
			movimientoCtaCte.setTipo(4); // el tipo 4 sera para saldos, segun bd
			movimientoCtaCte.setInteres(BigDecimal.ZERO);
			movimientoCtaCte.setSaldo(pagarCtaCte.getSaldo());
			movimientoCtaCte.setMontoTotal(pagarCtaCte.getSaldo());
			movimientoCtaCte.setCuentaCorriente(cliente.getCuentaCorriente());
			movimientoCtaCte.setPagoCtaCteSaldo(pagoCtaCte);
			movimientoCtaCte.setPagoVentas(null);
			session.save(movimientoCtaCte);
		}
		return movimientosCtaCte;
	}

	@Override
	public BigDecimal getSaldoTotalFromCtaCte(Integer idCuentaCte) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "select sum(saldo) as saldo from movimientos_cta_cte where saldo > 0 "
				+ " AND cuentas_corrientes_clientes_id=:idCuentaCorriente"
				+ " AND (pagado=0 OR pagado is NULL)";
		Query query = session.createSQLQuery(sql)
				.setParameter("idCuentaCorriente", idCuentaCte);
		BigDecimal saldo = (BigDecimal) query.uniqueResult();
		if (Objects.isNull(saldo)) {
			return BigDecimal.ZERO;
		} else {
			return saldo;
		}
	}

	/**
	 * devuelve el boolean para ver si es correcto el saldo
	 *
	 * @param cliente
	 * @param saldo
	 * @return
	 */
	@Override
	public Cliente verifiedClienteAndCtaCte(Cliente cliente, BigDecimal monto) {
		Session session = sessionFactory.getCurrentSession();
		Cliente clienteBD = session.get(Cliente.class, cliente.getId());
		if (Objects.isNull(clienteBD)) {
			throw new DataIntegrityViolationException("Se debe seleccionar un cliente para verificacion de Cta. Cte.");
		}
		if (Objects.isNull(clienteBD.getCuentaCorriente())) {
			throw new DataIntegrityViolationException("Se debe seleccionar un cliente para con Cta. Cte.");
		}
		CuentasCorrientes cuentaCorriente = clienteBD.getCuentaCorriente();
		BigDecimal saldo = this.getSaldoTotalFromCtaCte(cuentaCorriente.getId());
		BigDecimal margen = cuentaCorriente.getLimite().subtract(saldo);
		if (monto.compareTo(margen) > 0) {
			throw new DataIntegrityViolationException("El monto ingresado supera al margen de la Cta. Cte");
		}
		return clienteBD;
	}

	@Override
	public List<PagosCtaCte> getPagosFromMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaMtoCtaCte = session.createCriteria(MovimientosCtaCte.class, "movctacte");
		criteriaMtoCtaCte.setFetchMode("pagosCtaCte", FetchMode.JOIN);
		MovimientosCtaCte movimientoCtaCte = session.get(MovimientosCtaCte.class, idMtoCtaCte);
		if (Objects.isNull(movimientoCtaCte)) {
			throw new DataIntegrityViolationException("No se encontro el movimiento de cta. cte :" + idMtoCtaCte);
		} else {
			return null;
		}
	}

	/**
	 * Devuelve registros de pagos para detalle, para un mto tipo 4, saldo
	 *
	 * @param movimientoCtaCte
	 * @return
	 */
	@Override
	public List<PagosCtaCte> getDetailPagosFromMtoCtaCteSaldo(MovimientosCtaCte movimientoCtaCte) {
		MovimientosCtaCte movimientosCtaCteFirst = this.getFirstMtoFromMtoCtaCte(movimientoCtaCte.getId());
		return this.getPrevPagosFromMtoCtaCte(movimientosCtaCteFirst.getId());
	}

	/**
	 * Devuelve registros de pagos para detalle, para un mto tipo 1, venta
	 *
	 * @param movimientoCtaCte
	 * @return
	 */
	@Override
	public List<MovimientosCtaCte> getDetailPagosFromMtoCtaCteSale(MovimientosCtaCte movimientoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		List<MovimientosCtaCte> lista = new ArrayList<>();
//		MovimientosCtaCte movimientoCtaCte = session.get(MovimientosCtaCte.class, idMtoCtaCte);
		PagosCtaCte pagoCtaCte = movimientoCtaCte.getPagoCtaCte();
		// traemos las ventas asociadas al pago 

//		lista.add(movimientoCtaCte);
//		PagosCtaCte pagoCtaCte = movimientoCtaCte.getPagoCtaCte();
//		Criteria criteriaMtoCtaCte = session.createCriteria(MovimientosCtaCte.class, "movctacte");
//		Criterion c1 = Restrictions.eq("movctacte.pagoCtaCteSaldo", pagoCtaCte);
//		criteriaMtoCtaCte.add(c1);
//		List<MovimientosCtaCte> listMto = criteriaMtoCtaCte.list();
//		for (MovimientosCtaCte mto : listMto) {
//			lista.addAll(this.getDetailPagosFromMtoCtaCte(mto.getId()));
//		}
//		if (movimientoCtaCte.getTipo() == 4) {
//			MovimientosCtaCte mtoFirst = this.getFirstMtoFromMtoCtaCte(movimientoCtaCte.getId());
//			lista.addAll(this.getNextsPagosFromMtoCtaCte(mtoFirst.getId()));
//		}
		if (movimientoCtaCte.getTipo() == 1) {
			lista.addAll(this.getNextsMtoFromMtoCtaCte(movimientoCtaCte.getId()));
		}
		return lista;

	}

	/**
	 * Devuelve un mto desde cta cte saldo, deberia ser uno siempre
	 *
	 * @param idPagoCtaCteSaldo
	 * @return
	 */
	@Override
	public MovimientosCtaCte getMtoFromPagoCtaCteSaldo(Integer idPagoCtaCteSaldo) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaMto = session.createCriteria(MovimientosCtaCte.class, "movctacte");
		PagosCtaCte pagosCtaCte = session.load(PagosCtaCte.class, idPagoCtaCteSaldo);
		Criterion c1 = Restrictions.eq("movctacte.pagoCtaCteSaldo", pagosCtaCte);
		criteriaMto.add(c1);
		return (MovimientosCtaCte) criteriaMto.uniqueResult();
	}

	/**
	 * devuelve los mtos posteriores, des de un id de mto
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	private List<PagosCtaCte> getNextsPagosFromMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		List<PagosCtaCte> lista = new ArrayList<>();
		MovimientosCtaCte movimientoCtaCte = session.get(MovimientosCtaCte.class, idMtoCtaCte);
		PagosCtaCte pagoCtaCte = movimientoCtaCte.getPagoCtaCte();
		lista.add(pagoCtaCte);
//		PagosCtaCte pagoCtaCte = movimientoCtaCte.getPagoCtaCte();
		Criteria criteriaMtoCtaCte = session.createCriteria(MovimientosCtaCte.class, "movctacte");
		Criterion c1 = Restrictions.eq("movctacte.pagoCtaCteSaldo", pagoCtaCte);
		criteriaMtoCtaCte.add(c1);
		List<MovimientosCtaCte> listMto = criteriaMtoCtaCte.list();
		for (MovimientosCtaCte mto : listMto) {
			lista.addAll(this.getNextsPagosFromMtoCtaCte(mto.getId()));
		}
		return lista;
	}

	/**
	 * devuelve los mtos posteriores, des de un id de mto
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	private List<PagosCtaCte> getPrevPagosFromMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		List<PagosCtaCte> lista = new ArrayList<>();
		Criteria criteriaMto = session.createCriteria(MovimientosCtaCte.class, "movctacte");
		Criteria criteriaPagoCtaCte = criteriaMto.createCriteria("movctacte.pagoCtaCte", "pagoCtaCte", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPagoCtaCteSaldo = criteriaMto.createCriteria("movctacte.pagoCtaCteSaldo", "pagoCtaCteSaldo", JoinType.LEFT_OUTER_JOIN);
		criteriaPagoCtaCte.createAlias("pagoCtaCte.movimientos", "mtos", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPagoVentas = criteriaMto.createCriteria("movctacte.pagoVentas", "pagoVentas", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaCbteVenEnc = criteriaMto.createCriteria("pagoVentas.cbteEnc", "cbteVenEnc", JoinType.LEFT_OUTER_JOIN);
		Criterion c = Restrictions.eq("movctacte.id", idMtoCtaCte);
		criteriaMto.add(c);
		MovimientosCtaCte movimientoCtaCte = (MovimientosCtaCte) criteriaMto.uniqueResult();
		PagosCtaCte pagoCtaCte = movimientoCtaCte.getPagoCtaCteSaldo();
		lista.add(pagoCtaCte);
		Criteria criteriaMtoCtaCte = session.createCriteria(MovimientosCtaCte.class, "movctacte");
		Criterion c1 = Restrictions.eq("movctacte.pagoCtaCte", pagoCtaCte);
		criteriaMtoCtaCte.add(c1);
		List<MovimientosCtaCte> listMto = criteriaMtoCtaCte.list();
		for (MovimientosCtaCte mto : listMto) {
			lista.addAll(this.getPrevPagosFromMtoCtaCte(mto.getId()));
		}
		return lista;
	}

	/**
	 * devuelve un pago CtaCte desde un movimiento
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	@Override
	public PagosCtaCte getPagoFromMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaMto = session.createCriteria(MovimientosCtaCte.class, "movctacte");
		Criteria criteriaPagoCtaCte = criteriaMto.createCriteria("movctacte.pagoCtaCte", "pagoCtaCte", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPagoCtaCteSaldo = criteriaMto.createCriteria("movctacte.pagoCtaCteSaldo", "pagoCtaCteSaldo", JoinType.LEFT_OUTER_JOIN);
		criteriaPagoCtaCte.createAlias("pagoCtaCte.movimientos", "mtos", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPagoVentas = criteriaMto.createCriteria("movctacte.pagoVentas", "pagoVentas", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaCbteVenEnc = criteriaMto.createCriteria("pagoVentas.cbteEnc", "cbteVenEnc", JoinType.LEFT_OUTER_JOIN);
		Criterion c = Restrictions.eq("movctacte.id", idMtoCtaCte);
		criteriaMto.add(c);
		MovimientosCtaCte movimientoCtaCte = (MovimientosCtaCte) criteriaMto.uniqueResult();
		PagosCtaCte pagoCtaCte = movimientoCtaCte.getPagoCtaCte();
		return pagoCtaCte;
	}

	/**
	 * devuelve los mtos posteriores, des de un id de mto
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	private List<MovimientosCtaCte> getNextsMtoFromMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		List<MovimientosCtaCte> lista = new ArrayList<>();
		MovimientosCtaCte movimientoCtaCte = session.get(MovimientosCtaCte.class, idMtoCtaCte);
		lista.add(movimientoCtaCte);
		PagosCtaCte pagoCtaCte = movimientoCtaCte.getPagoCtaCte();
		Criteria criteriaMtoCtaCte = session.createCriteria(MovimientosCtaCte.class, "movctacte");
		Criterion c1 = Restrictions.eq("movctacte.pagoCtaCteSaldo", pagoCtaCte);
		criteriaMtoCtaCte.add(c1);
		List<MovimientosCtaCte> listMto = criteriaMtoCtaCte.list();
		for (MovimientosCtaCte mto : listMto) {
			lista.addAll(this.getNextsMtoFromMtoCtaCte(mto.getId()));
		}
		return lista;
	}

	/**
	 * devuelve los mtos previos, des de un id de mto, como puede ser un saldo
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	private List<MovimientosCtaCte> getPrevMtoFromMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		List<MovimientosCtaCte> lista = new ArrayList<>();
		MovimientosCtaCte movimientoCtaCte = session.get(MovimientosCtaCte.class, idMtoCtaCte);
		lista.add(movimientoCtaCte);
		PagosCtaCte pagoCtaCte = movimientoCtaCte.getPagoCtaCteSaldo();
		Criteria criteriaMtoCtaCte = session.createCriteria(MovimientosCtaCte.class, "movctacte");
		Criterion c1 = Restrictions.eq("movctacte.pagoCtaCte", pagoCtaCte);
		criteriaMtoCtaCte.add(c1);
		List<MovimientosCtaCte> listMto = criteriaMtoCtaCte.list();
		for (MovimientosCtaCte mto : listMto) {
			lista.addAll(this.getPrevMtoFromMtoCtaCte(mto.getId()));
		}
		return lista;
	}

	/**
	 * devuelve el primer mto del hilo de pagos (pago y saldos) desde un id de mto de cta cte este metodo seria pasa saldo nada mas si no es saldo siempre seria el primero
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	private MovimientosCtaCte getFirstMtoFromMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		MovimientosCtaCte movimientoCtaCte = session.get(MovimientosCtaCte.class, idMtoCtaCte);
		PagosCtaCte pagoCtaCte = movimientoCtaCte.getPagoCtaCte();
		if (Objects.isNull(pagoCtaCte)) {
			return movimientoCtaCte;
		} else {
			MovimientosCtaCte movimientosCtaCteTemp = this.getMtoFromPagoCtaCteSaldo(movimientoCtaCte.getPagoCtaCte().getId());
			if (!Objects.isNull(movimientosCtaCteTemp)) {
				return this.getFirstMtoFromMtoCtaCte(movimientosCtaCteTemp.getId());
			} else {
				return movimientoCtaCte;
			}
		}
	}

	@Override
	public MovimientosCtaCte getMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(MovimientosCtaCte.class, idMtoCtaCte);
	}

	@Override
	public Pagination<MovimientosCtaCte> getMovimientosCtaCte(FilterMovCtaCte filterMovCtaCte, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaMtoCtaCte = session.createCriteria(MovimientosCtaCte.class, "movctacte");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaMtoCtaCte.setProjection(idCountProjection);
//        criteriaMtoCtaCte.setFetchMode("pagoVentas", FetchMode.JOIN);
		Criteria criteriaPagoVentas = criteriaMtoCtaCte.createCriteria("movctacte.pagoVentas", "pagoVentas", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaCbteVenEnc = criteriaMtoCtaCte.createCriteria("pagoVentas.cbteEnc", "cbteVenEnc", JoinType.LEFT_OUTER_JOIN);

		Criteria criteriaCtaCte = criteriaMtoCtaCte.createCriteria("movctacte.cuentaCorriente", "cuentaCorriente", JoinType.LEFT_OUTER_JOIN);
		if (Objects.nonNull(filterMovCtaCte.getCliente())) {
			if (filterMovCtaCte.getCliente().getId() > 0) {
				criteriaCtaCte.add(Restrictions.eq("cliente", filterMovCtaCte.getCliente()));
			}
		}
		if (Objects.nonNull(filterMovCtaCte.getIdFacturaVenta())) {
			if (filterMovCtaCte.getIdFacturaVenta() > 0) {
				criteriaCbteVenEnc.add(Restrictions.eq("id", filterMovCtaCte.getIdFacturaVenta()));
			}
		}
		/*###########################Control por APP############################*/
		criteriaMtoCtaCte.add(Restrictions.eq("app.id", TenantContext.getCurrentTenant()));
		/*######################################################################*/
		Integer estado = filterMovCtaCte.getEstado();
		if (estado != null && !estado.equals("") && estado != 2) {
			Criterion cEstado = Restrictions.eq("movctacte.pagado", (estado == 1));
			criteriaMtoCtaCte.add(cEstado);
		}
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaMtoCtaCte.uniqueResult()).intValue();
////
		if (reverse) {
			criteriaMtoCtaCte.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaMtoCtaCte.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaMtoCtaCte.setProjection(Projections.distinct(Projections.property("id")));
		if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
			criteriaMtoCtaCte.setFirstResult((page - 1) * pageSize);
			criteriaMtoCtaCte.setMaxResults(pageSize);
		}
		List uniqueSubList = criteriaMtoCtaCte.list();
		criteriaMtoCtaCte.setProjection(null);
		criteriaMtoCtaCte.setFirstResult(0);
		criteriaMtoCtaCte.setMaxResults(Integer.MAX_VALUE);
		criteriaMtoCtaCte.add(Restrictions.in("id", uniqueSubList));
		criteriaMtoCtaCte.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//        List listassss= criteriaCliente.list();
		ArrayList<MovimientosCtaCte> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaMtoCtaCte.list());
		}
		Pagination<MovimientosCtaCte> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	/**
	 * Devuelve el saldo que tenga una cuenta corriente
	 *
	 * @param ctaCteOld
	 * @param ctaCteNew
	 * @return
	 */
	@Override
	public CuentasCorrientes getObject(CuentasCorrientes ctaCteOld, CuentasCorrientes ctaCteNew) {
		if (Objects.isNull(ctaCteOld)) {
			ctaCteOld = new CuentasCorrientes();
		}
		ctaCteOld.setActivo(ctaCteNew.getActivo());
		ctaCteOld.setDescripcion(ctaCteNew.getDescripcion());
		ctaCteOld.setLimite(ctaCteNew.getLimite());
		return ctaCteOld;
	}

}
