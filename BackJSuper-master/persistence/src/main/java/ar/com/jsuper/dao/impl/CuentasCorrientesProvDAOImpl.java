package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.CuentasCorrientesProvDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.CbteCompEnc;
import ar.com.jsuper.domain.CuentasCorrientesProv;
import ar.com.jsuper.domain.MovimientosCtaCteProv;
import ar.com.jsuper.domain.PagoPagoCtaCte;
import ar.com.jsuper.domain.PagoPagoCtaCteProv;
import ar.com.jsuper.domain.PagosCtaCte;
import ar.com.jsuper.domain.PagosCtaCteProv;
import ar.com.jsuper.domain.Proveedores;
import ar.com.jsuper.domain.utils.FilterMovCtaCteProv;
import ar.com.jsuper.domain.utils.PagarCtaCteProv;
import ar.com.jsuper.security.TenantContext;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

@Repository
public class CuentasCorrientesProvDAOImpl extends GenericDAOImpl<CuentasCorrientesProv, Integer> implements CuentasCorrientesProvDAO {

	@Override
	public CuentasCorrientesProv getCtaCteByProveedor(Integer idProveedor) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(CuentasCorrientesProv.class, "cta");
		Criteria criteriaPoveedores = c.createCriteria("cta.proveedor", "proveedor", JoinType.LEFT_OUTER_JOIN);
		Criterion c1 = Restrictions.eq("id", idProveedor);
		criteriaPoveedores.add(c1);
		CuentasCorrientesProv corrientesProv = (CuentasCorrientesProv) c.uniqueResult();
		return corrientesProv;
	}

	@Override
	public MovimientosCtaCteProv pagarCuentaCteProv(MovimientosCtaCteProv movimientosCtaCteProvDTO) {
		Session session = sessionFactory.getCurrentSession();
		Boolean isValidTransaccion = false;
		MovimientosCtaCteProv movimientosCtaCteProv = session.load(MovimientosCtaCteProv.class, movimientosCtaCteProvDTO.getId());
//        if (!movimientosCtaCteProv.getPagado()) {
//            if (CollectionUtils.isEmpty(movimientosCtaCteProv.getPagosCtaCte())) {
//                movimientosCtaCteProv.setPagosCtaCte(new ArrayList<>());
//            }
//            BigDecimal pagoTotal = BigDecimal.ZERO;
//            Date fecha = new Date();
//            if (Objects.isNull(CollectionUtils.isEmpty(movimientosCtaCteProvDTO.getPagosCtaCte()))) {
//                throw new DataIntegrityViolationException("No existen pagos agregados para la transacción.");
//            }
//            for (PagosCtaCteProv pagosCtaCteProv : movimientosCtaCteProvDTO.getPagosCtaCte()) {
//                isValidTransaccion = false;
//                pagoTotal = pagoTotal.add(pagosCtaCteProv.getMontoPaga());
//                movimientosCtaCteProv.getPagosCtaCte().add(pagosCtaCteProv);
//                pagosCtaCteProv.setFecha(fecha);
//                session.save(pagosCtaCteProv);
//                if (pagosCtaCteProv.getFormaPago().getId() == 1) {// solo si es al contado agregamos la asociacion a alguna extraccion de caja
//                    // si la factura esta asociada modificamos el detalle de la trasacccion asociada
//                    if (!Objects.isNull(pagosCtaCteProv.getDetalleTransaccion())) {
//                        if (!Objects.isNull(pagosCtaCteProv.getDetalleTransaccion().getId())) {
//                            if (pagosCtaCteProv.getDetalleTransaccion().getId() > 0) {
//                                DetalleTransaccionCaja detalleTransaccionCaja = session.load(DetalleTransaccionCaja.class, pagosCtaCteProv.getDetalleTransaccion().getId());
//                                detalleTransaccionCaja.setAsociada(Boolean.TRUE);
//                                detalleTransaccionCaja.setAsociadaTipo(1);// tipo, pago de una factura de compra
//                                detalleTransaccionCaja.setAsociadaId(pagosCtaCteProv.getId());
//                                session.update(detalleTransaccionCaja);
//                                pagosCtaCteProv.setDetalleTransaccion(detalleTransaccionCaja);
//                                isValidTransaccion = true;
//                            }
//                        }
//                    }
//                    if (!isValidTransaccion) {
//                        pagosCtaCteProv.setDetalleTransaccion(null);
//                    }
//                }
//            }
//            BigDecimal saldoTotal = movimientosCtaCteProv.getSaldo().subtract(pagoTotal);
//            if (saldoTotal.compareTo(BigDecimal.ZERO) < 0) {
//                throw new DataIntegrityViolationException("Existe un problema, los pagos superan el saldo, el saldo no puede ser negativo");
//            }
//            if (saldoTotal.compareTo(BigDecimal.ZERO) <= 0) {
//                movimientosCtaCteProv.setPagado(Boolean.TRUE);
//                CbteCompEnc factura = session.load(CbteCompEnc.class, movimientosCtaCteProvDTO.getId());
//                factura.setPagada(Boolean.TRUE);
//                session.update(factura);
//            }
//            movimientosCtaCteProv.setSaldo(saldoTotal);
//            session.update(movimientosCtaCteProv);
//        } else {
//            throw new DataIntegrityViolationException("La factura ya figura como pagada");
//        }

		return movimientosCtaCteProv;
	}

	@Override
	public List<MovimientosCtaCteProv> pagar(PagarCtaCteProv pagarCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		PagosCtaCteProv pagoCtaCteProv = new PagosCtaCteProv();
		// por ahora la cta cte solo se puede pagar en efectivo
		pagoCtaCteProv.setDescripcion(pagarCtaCte.getDescripcion());
		pagoCtaCteProv.setMontoNeto(pagarCtaCte.getMontoNeto());
		pagoCtaCteProv.setMontoTotal(pagarCtaCte.getMontoTotal());// el monto con o sin interes
		pagoCtaCteProv.setPagoCon(pagarCtaCte.getPagoCon());
		pagoCtaCteProv.setTipo("PAGO_EFECTIVO"); // por ahora efectivo nada mas
		pagoCtaCteProv.setFecha(new Date());
		pagoCtaCteProv.setInteres(pagarCtaCte.getInteres());
		if (pagarCtaCte.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
			// si hay saldo significa que no pago el monto total 
			pagoCtaCteProv.setPago(pagarCtaCte.getMontoTotal().subtract(pagarCtaCte.getSaldo(), MathContext.DECIMAL128));

		} else {
			// si no hay saldo quiere decir que pago el monto  total
			pagoCtaCteProv.setPago(pagarCtaCte.getMontoTotal());
		}
		session.save(pagoCtaCteProv);
		// guardamops las formas de pago que se realizo
		List<PagoPagoCtaCteProv> pagos = pagarCtaCte.getPagos();
		pagoCtaCteProv.setPagos(new ArrayList<>());
		for (PagoPagoCtaCteProv pago : pagos) {
			pago.setPagoCtaCte(pagoCtaCteProv);
			pagoCtaCteProv.getPagos().add(pago);
			session.save(pago);
		}

		List<MovimientosCtaCteProv> movimientosCtaCte = pagarCtaCte.getMovimientosCtaCte();
		for (MovimientosCtaCteProv movimientoCtaCte : movimientosCtaCte) {
			MovimientosCtaCteProv movimientoCtaCteBD = session.load(MovimientosCtaCteProv.class, movimientoCtaCte.getId());
			movimientoCtaCteBD.setPagoCtaCte(pagoCtaCteProv);
			movimientoCtaCteBD.setSaldo(BigDecimal.ZERO);
			movimientoCtaCteBD.setPagado(true);
			// aqui se podria validar si el cliente del req coincide con el de la bd para seguridad
			session.update(movimientoCtaCteBD);
			if (movimientoCtaCteBD.getTipo() == 1) { // si es el pago de un cbte de compra  modificamos la venta
				CbteCompEnc ev = movimientoCtaCteBD.getPagoCbteComp().getCbteCompEnc();
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
			Proveedores proveedor = session.load(Proveedores.class, pagarCtaCte.getProveedor().getId());
			MovimientosCtaCteProv movimientoCtaCte = new MovimientosCtaCteProv();
			movimientoCtaCte.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
			movimientoCtaCte.setMonto(pagarCtaCte.getSaldo());
			movimientoCtaCte.setPagado(Boolean.FALSE);
			movimientoCtaCte.setFecha(new Date());
			movimientoCtaCte.setTipo(4); // el tipo 4 sera para saldos, segun bd
			movimientoCtaCte.setInteres(BigDecimal.ZERO);
			movimientoCtaCte.setSaldo(pagarCtaCte.getSaldo());
			movimientoCtaCte.setMontoTotal(pagarCtaCte.getSaldo());
			movimientoCtaCte.setCuentaCorriente(proveedor.getCuentaCorriente());
			movimientoCtaCte.setPagoCtaCteSaldo(pagoCtaCteProv);
			movimientoCtaCte.setPagoCbteComp(null);
			session.save(movimientoCtaCte);
		}
		return movimientosCtaCte;
	}

	@Override
	public Pagination<MovimientosCtaCteProv> getMovimientosCtaCte(FilterMovCtaCteProv filterMovCtaCte, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaMtoCtaCteProv = session.createCriteria(MovimientosCtaCteProv.class, "movctacte");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaMtoCtaCteProv.setProjection(idCountProjection);
		Criteria criteriaPagoCbteComp = criteriaMtoCtaCteProv.createCriteria("movctacte.pagoCbteComp", "pagoCbteComp", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaCbteComp = criteriaMtoCtaCteProv.createCriteria("pagoCbteComp.cbteCompEnc", "cbteCompEnc", JoinType.LEFT_OUTER_JOIN);

		Criteria criteriaCtaCte = criteriaMtoCtaCteProv.createCriteria("movctacte.cuentaCorriente", "cuentaCorriente", JoinType.LEFT_OUTER_JOIN);
		if (!Objects.isNull(filterMovCtaCte.getProveedor())) {
			if (filterMovCtaCte.getProveedor().getId() > 0) {
				criteriaCtaCte.add(Restrictions.eq("id", filterMovCtaCte.getProveedor().getId()));
			}
		}
		if (!Objects.isNull(filterMovCtaCte.getIdFacturaCompra())) {
			if (filterMovCtaCte.getIdFacturaCompra() > 0) {
				criteriaCbteComp.add(Restrictions.eq("id", filterMovCtaCte.getIdFacturaCompra()));
			}
		}
		/*###########################Control por APP############################*/
		criteriaMtoCtaCteProv.add(Restrictions.eq("app.id", TenantContext.getCurrentTenant()));
		/*######################################################################*/
		Integer estado = filterMovCtaCte.getEstado();
		if (estado != null && !estado.equals("") && estado != 2) {
			Criterion cEstado = Restrictions.eq("movctacte.pagado", (estado == 1));
			criteriaMtoCtaCteProv.add(cEstado);
		}
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaMtoCtaCteProv.uniqueResult()).intValue();
////
		if (reverse) {
			criteriaMtoCtaCteProv.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaMtoCtaCteProv.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaMtoCtaCteProv.setProjection(Projections.distinct(Projections.property("id")));
		if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
			criteriaMtoCtaCteProv.setFirstResult((page - 1) * pageSize);
			criteriaMtoCtaCteProv.setMaxResults(pageSize);
		}
		List uniqueSubList = criteriaMtoCtaCteProv.list();
		criteriaMtoCtaCteProv.setProjection(null);
		criteriaMtoCtaCteProv.setFirstResult(0);
		criteriaMtoCtaCteProv.setMaxResults(Integer.MAX_VALUE);
		criteriaMtoCtaCteProv.add(Restrictions.in("id", uniqueSubList));
		criteriaMtoCtaCteProv.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//        List listassss= criteriaCliente.list();
		ArrayList<MovimientosCtaCteProv> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaMtoCtaCteProv.list());
		}
		Pagination<MovimientosCtaCteProv> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public MovimientosCtaCteProv getMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(MovimientosCtaCteProv.class, idMtoCtaCte);
	}

	/**
	 * Devuelve registros de pagos para detalle, para un mto tipo 4, saldo
	 *
	 * @param movimientoCtaCte
	 * @return
	 */
	@Override
	public List<PagosCtaCteProv> getDetailPagosFromMtoCtaCteSaldo(MovimientosCtaCteProv movimientoCtaCte) {
		MovimientosCtaCteProv movimientosCtaCteFirst = this.getFirstMtoFromMtoCtaCte(movimientoCtaCte.getId());
		return this.getPrevPagosFromMtoCtaCte(movimientosCtaCteFirst.getId());
	}

	/**
	 * devuelve el primer mto del hilo de pagos (pago y saldos) desde un id de mto de cta cte este metodo seria pasa saldo nada mas si no es saldo siempre seria el primero
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	private MovimientosCtaCteProv getFirstMtoFromMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		MovimientosCtaCteProv movimientoCtaCte = session.get(MovimientosCtaCteProv.class, idMtoCtaCte);
		PagosCtaCteProv pagoCtaCte = movimientoCtaCte.getPagoCtaCte();
		if (Objects.isNull(pagoCtaCte)) {
			return movimientoCtaCte;
		} else {
			MovimientosCtaCteProv movimientosCtaCteTemp = this.getMtoFromPagoCtaCteSaldo(movimientoCtaCte.getPagoCtaCte().getId());
			if (!Objects.isNull(movimientosCtaCteTemp)) {
				return this.getFirstMtoFromMtoCtaCte(movimientosCtaCteTemp.getId());
			} else {
				return movimientoCtaCte;
			}
		}
	}

	/**
	 * Devuelve un mto desde cta cte saldo, deberia ser uno siempre
	 *
	 * @param idPagoCtaCteSaldo
	 * @return
	 */
	@Override
	public MovimientosCtaCteProv getMtoFromPagoCtaCteSaldo(Integer idPagoCtaCteSaldo) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaMto = session.createCriteria(MovimientosCtaCteProv.class, "movctacte");
		PagosCtaCteProv pagosCtaCte = session.load(PagosCtaCteProv.class, idPagoCtaCteSaldo);
		Criterion c1 = Restrictions.eq("movctacte.pagoCtaCteSaldo", pagosCtaCte);
		criteriaMto.add(c1);
		return (MovimientosCtaCteProv) criteriaMto.uniqueResult();
	}

	/**
	 * devuelve los mtos posteriores, des de un id de mto
	 *
	 * @param idMtoCtaCte
	 * @return
	 */
	private List<PagosCtaCteProv> getPrevPagosFromMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		List<PagosCtaCteProv> lista = new ArrayList<>();
		Criteria criteriaMto = session.createCriteria(MovimientosCtaCteProv.class, "movctacte");
		Criteria criteriaPagoCtaCte = criteriaMto.createCriteria("movctacte.pagoCtaCte", "pagoCtaCte", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPagoCtaCteSaldo = criteriaMto.createCriteria("movctacte.pagoCtaCteSaldo", "pagoCtaCteSaldo", JoinType.LEFT_OUTER_JOIN);
		criteriaPagoCtaCte.createAlias("pagoCtaCte.movimientos", "mtos", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPagoVentas = criteriaMto.createCriteria("movctacte.pagoCbteComp", "pagoCbteComp", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaCbteVenEnc = criteriaMto.createCriteria("pagoCbteComp.cbteCompEnc", "cbteCompEnc", JoinType.LEFT_OUTER_JOIN);
		Criterion c = Restrictions.eq("movctacte.id", idMtoCtaCte);
		criteriaMto.add(c);
		MovimientosCtaCteProv movimientoCtaCte = (MovimientosCtaCteProv) criteriaMto.uniqueResult();
		PagosCtaCteProv pagoCtaCte = movimientoCtaCte.getPagoCtaCteSaldo();
		lista.add(pagoCtaCte);
		Criteria criteriaMtoCtaCte = session.createCriteria(MovimientosCtaCteProv.class, "movctacte");
		Criterion c1 = Restrictions.eq("movctacte.pagoCtaCte", pagoCtaCte);
		criteriaMtoCtaCte.add(c1);
		List<MovimientosCtaCteProv> listMto = criteriaMtoCtaCte.list();
		for (MovimientosCtaCteProv mto : listMto) {
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
	public PagosCtaCteProv getPagoFromMtoCtaCte(Integer idMtoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaMto = session.createCriteria(MovimientosCtaCteProv.class, "movctacte");
		Criteria criteriaPagoCtaCte = criteriaMto.createCriteria("movctacte.pagoCtaCte", "pagoCtaCte", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPagoCtaCteSaldo = criteriaMto.createCriteria("movctacte.pagoCtaCteSaldo", "pagoCtaCteSaldo", JoinType.LEFT_OUTER_JOIN);
		criteriaPagoCtaCte.createAlias("pagoCtaCte.movimientos", "mtos", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaPagoVentas = criteriaMto.createCriteria("movctacte.pagoCbteComp", "pagoCbteComp", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaCbteVenEnc = criteriaMto.createCriteria("pagoCbteComp.cbteCompEnc", "cbteCompEnc", JoinType.LEFT_OUTER_JOIN);
		Criterion c = Restrictions.eq("movctacte.id", idMtoCtaCte);
		criteriaMto.add(c);
		MovimientosCtaCteProv movimientoCtaCte = (MovimientosCtaCteProv) criteriaMto.uniqueResult();
		PagosCtaCteProv pagoCtaCte = movimientoCtaCte.getPagoCtaCte();
		return pagoCtaCte;
	}

	@Override
	public List<PagoPagoCtaCteProv> getPagosFromIdPagoCtaCte(Integer idPagoCtaCte) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaPagoCtaCteProv = session.createCriteria(PagosCtaCteProv.class, "pagosCtaCte");
		Criteria criteriaPagoPagoCtaCte = criteriaPagoCtaCteProv.createCriteria("pagosCtaCte.pagos", "pagos", JoinType.LEFT_OUTER_JOIN);
		criteriaPagoCtaCteProv.add(Restrictions.eq("id", idPagoCtaCte));
		PagosCtaCteProv pagosCtaCteProv = (PagosCtaCteProv) criteriaPagoCtaCteProv.uniqueResult();
		return pagosCtaCteProv.getPagos();
	}
}
