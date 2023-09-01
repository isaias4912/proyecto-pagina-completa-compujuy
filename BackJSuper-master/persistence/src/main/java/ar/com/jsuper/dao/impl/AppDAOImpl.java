package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.AppDAO;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Caja;
import ar.com.jsuper.domain.CbteCompDet;
import ar.com.jsuper.domain.CbteCompEnc;
import ar.com.jsuper.domain.TransaccionCaja;
import ar.com.jsuper.domain.CbteDet;
import ar.com.jsuper.domain.CbteEncVenta;
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.Configuracion;
import ar.com.jsuper.domain.CuentasCorrientesProv;
import ar.com.jsuper.domain.DetMovimientos;
import ar.com.jsuper.domain.EncMovimientos;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Familias;
import ar.com.jsuper.domain.Proveedores;
import ar.com.jsuper.domain.PagoCbteComp;
import ar.com.jsuper.domain.PagoCbteVen;
import ar.com.jsuper.domain.Ofertas;
import ar.com.jsuper.domain.PagosCtaCte;
import ar.com.jsuper.domain.PagosCtaCteProv;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.PuntoVenta;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.security.TenantContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

@Repository
public class AppDAOImpl extends GenericDAOImpl<App, Integer> implements AppDAO {

	@Override
	public App get() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(App.class, "app");
		/*###########################Control por APP############################*/
		Criterion c1 = Restrictions.eq("id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		App app = (App) c.uniqueResult();
		return app;
	}

	@Override
	public App getAppAndConfig() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(App.class, "app");
		c.setFetchMode("app.configuracion", FetchMode.JOIN);
		/*###########################Control por APP############################*/
		Criterion c1 = Restrictions.eq("id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		App app = (App) c.uniqueResult();
		return app;
	}

	@Override
	public Configuracion getConfiguracion() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(App.class, "app");
		Criteria criteriaConfig = c.createCriteria("app.configuracion", "configuracion", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaConfigPtoVentas = c.createCriteria("configuracion.puntosVenta", "puntosVenta", JoinType.LEFT_OUTER_JOIN);
		/*###########################Control por APP############################*/
		Criterion c1 = Restrictions.eq("id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		App app = (App) c.uniqueResult();
		return app.getConfiguracion();
	}

	@Override
	public Configuracion getConfiguracionFromAppId(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(App.class, "app");
		Criterion c1 = Restrictions.eq("id", id);
		c.add(c1);
		App app = (App) c.uniqueResult();
		return app.getConfiguracion();
	}

	@Override
	public Map<String, Object> getEncabezadoReporte() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select c.logoReporte, c.encabezadoReporte, c.razonSocial, c.domicilioComercial, c.cuitEmpresa from App a INNER JOIN a.configuracion c where a.id= :appId")
				.setParameter("appId", TenantContext.getCurrentTenant());
		List<Object[]> rows = query.list();
		Map<String, Object> response = new HashMap<>();
		for (Object[] row : rows) {
			response.put("logo", row[0]);
			response.put("encabezado", row[1]);
			response.put("razonSocial", row[2]);
			response.put("domicilioComercial", row[3]);
			response.put("cuitEmpresa", row[4]);
		}
		return response;
	}

	@Override
	public App update(App app) {
		Session session = sessionFactory.getCurrentSession();
		App appDB = session.load(App.class, TenantContext.getCurrentTenant());
		appDB.setNombre(app.getNombre());
		appDB.setDescripcion(app.getDescripcion());
		appDB.setLogo(app.getLogo());
		appDB.setAltLogo(app.getAltLogo());
		session.update(appDB);
		return appDB;
	}

	/**
	 * MOdificamos la configuracion pero no los datos de afip, estos se modifican en otro parte
	 *
	 * @param config
	 * @return
	 */
	@Override
	public Configuracion updateConfiguracion(Configuracion config) {
		Session session = sessionFactory.getCurrentSession();
		Configuracion configuracion = session.load(Configuracion.class, config.getId());
		configuracion.setCliCtaCteInteres(config.getCliCtaCteInteres());
		configuracion.setLogoReporte(config.getLogoReporte());
		configuracion.setEncabezadoReporte(config.getEncabezadoReporte());
		configuracion.setIva(config.getIva());
		configuracion.setMargenGanancia(config.getMargenGanancia());
		configuracion.setTipoEmpresa(config.getTipoEmpresa());
		configuracion.setCuitEmpresa(config.getCuitEmpresa());
		configuracion.setPais(config.getPais());
		configuracion.setRazonSocial(config.getRazonSocial());
		configuracion.setFechaIniAct(config.getFechaIniAct());
		configuracion.setIngresosBrutos(config.getIngresosBrutos());
		configuracion.setDomicilioComercial(config.getDomicilioComercial());
		configuracion.setPrintHost(config.getPrintHost());
		configuracion.setPrintPort(config.getPrintPort());
//		configuracion.setCertCN(config.getCertCN());
//		configuracion.setCertNameCRT(config.getCertNameCRT());
//		configuracion.setCertO(config.getCertO());
//		configuracion.setCertPassword(config.getCertPassword());
//		configuracion.setCertSerialNumber(config.getCertSerialNumber());
		configuracion.setEnabledVenta(config.getEnabledVenta());
		session.update(configuracion);
		return config;
	}
	/**
	 * MOdificamos la configuracion pero solo la oconfiguracion basica nesecsaria
	 *
	 * @param config
	 * @return
	 */
	@Override
	public Configuracion updateConfiguracionBasica(Configuracion config) {
		Session session = sessionFactory.getCurrentSession();
		Configuracion configuracion = session.load(Configuracion.class, config.getId());
		configuracion.setTipoEmpresa(config.getTipoEmpresa());
		configuracion.setCuitEmpresa(config.getCuitEmpresa());
		configuracion.setPais(config.getPais());
		configuracion.setRazonSocial(config.getRazonSocial());
		configuracion.setFechaIniAct(config.getFechaIniAct());
		configuracion.setIngresosBrutos(config.getIngresosBrutos());
		configuracion.setDomicilioComercial(config.getDomicilioComercial());
		configuracion.setEnabledVenta(config.getEnabledVenta());
		session.update(configuracion);
		return config;
	}

	@Override
	public void saveUpdateOrDelete(Configuracion configuracion, Set<PuntoVenta> puntosVenta, Set<PuntoVenta> puntosVentaDB) {
		Session session = sessionFactory.getCurrentSession();
		Boolean encontro = false;
		PuntoVenta pvTemp = null;
		if (Objects.nonNull(puntosVenta)) {
			for (PuntoVenta pv : puntosVenta) {
				encontro = false;
				if (Objects.nonNull(puntosVentaDB)) {
					for (PuntoVenta pvBD : puntosVentaDB) {
						if (Objects.equals(pv.getNro(), pvBD.getNro())) {
							encontro = true;
							pvTemp = this.getObjectPtoVenta(pvBD, pv);
						}
					}
				}
				if (encontro) {
					session.update(pvTemp);
				} else {
					pv.setConfiguracion(configuracion.getId());
					session.save(pv);
				}
			}
		}
		if (Objects.nonNull(puntosVentaDB)) {
			for (PuntoVenta pvBD : puntosVentaDB) {
				encontro = false;
				if (Objects.nonNull(puntosVenta)) {
					for (PuntoVenta pv : puntosVenta) {
						if (Objects.equals(pv.getNro(), pvBD.getNro())) {
							encontro = true;
						}
					}
				}
				if (!encontro) {
					session.delete(pvBD);
				}
			}
		}
	}

	public PuntoVenta getObjectPtoVenta(PuntoVenta puntoVentaOld, PuntoVenta puntoVentaNew) {
		if (Objects.isNull(puntoVentaOld)) {
			puntoVentaOld = new PuntoVenta();
		}
		puntoVentaOld.setNro(puntoVentaNew.getNro());
		puntoVentaOld.setDescripcion(puntoVentaNew.getDescripcion());
		return puntoVentaOld;
	}

	/**
	 * habilitamos o deshabilitamos el modo producccion de afip en configuracion
	 *
	 */
	@Override
	public Configuracion enabledOrdisabledProdAfip(boolean value) {
		Session session = sessionFactory.getCurrentSession();
		Configuracion configuracion = this.getConfiguracion();
		configuracion.setAfipProduccion(value);
		session.save(configuracion);
		return configuracion;
	}

	/**
	 * modificamos los datos de config de backup
	 *
	 */
	@Override
	public Configuracion setBackup(Configuracion config) {
		Session session = sessionFactory.getCurrentSession();
		Configuracion configuracion = this.getConfiguracion();
		configuracion.setBackupCron(config.getBackupCron());
		configuracion.setBackupHab(config.getBackupHab());
		session.save(configuracion);
		return configuracion;
	}

	@Override
	public void deleteVentasByApp(Integer idApp) {
		Session session = sessionFactory.getCurrentSession();

		//-----------VENTAS---------------------
		Criteria criteriaVentas = session.createCriteria(CbteEncVenta.class, "producto");
		criteriaVentas.add(Restrictions.eq("app.id", idApp));
		List<CbteEncVenta> ventas = criteriaVentas.list();
		List<Integer> listaIdVentas = ventas.stream().map(item -> item.getId()).collect(Collectors.toList());

		if (Objects.nonNull(listaIdVentas) && listaIdVentas.size() > 0) {
			// recuperamos los detalle de ventas
			Criteria criteriaDetVentas = session.createCriteria(CbteDet.class, "cbteVentDet");
			criteriaDetVentas.add(Restrictions.in("cbteVenEnc.id", listaIdVentas));
			List<CbteDet> ventasDet = criteriaDetVentas.list();
			List<Integer> listaIdDetVen = ventasDet.stream().map(item -> item.getId()).collect(Collectors.toList());
			// eliminamos los  movimientos_producto
			if (Objects.nonNull(listaIdDetVen) && listaIdDetVen.size() > 0) {
				session.createSQLQuery("DELETE FROM movimientos_producto where cbte_ven_det_id in (:idVentaDet)")
						.setParameterList("idVentaDet", listaIdDetVen).executeUpdate();
			}
			// eliminamos la venta  detalle
			session.createSQLQuery("DELETE FROM cbte_ven_det where cbte_ven_enc_id in(:idVenta)")
					.setParameterList("idVenta", listaIdVentas).executeUpdate();
			// eliminamos lo que tenga que ver  con movimientos_cta_cte, pagra poder eliminar el pago

			Criteria criteriaPagoCtaCte = session.createCriteria(PagosCtaCte.class, "pagosCtaCte");
			Criteria criteriaPagoCtaCteMovCtaCte = criteriaPagoCtaCte.createCriteria("pagosCtaCte.movimientos", "movimientos", JoinType.LEFT_OUTER_JOIN);
			criteriaPagoCtaCteMovCtaCte.add(Restrictions.eq("app.id", idApp));
			List<PagosCtaCte> pagosCtaCte = criteriaPagoCtaCte.list();
			List<Integer> listaIdPagoCtaCte = pagosCtaCte.stream().map(item -> item.getId()).collect(Collectors.toList());

			Criteria criteriaPagoVenta = session.createCriteria(PagoCbteVen.class, "pagoCbteVen");
			criteriaPagoVenta.add(Restrictions.in("cbteVenEnc.id", listaIdVentas));
			List<PagoCbteVen> pagosVentas = criteriaPagoVenta.list();
			List<Integer> listaIdPagoVentas = pagosVentas.stream().map(item -> item.getId()).collect(Collectors.toList());

			session.createSQLQuery("DELETE FROM movimientos_cta_cte where app_id=:idApp")
					.setInteger("idApp", idApp).executeUpdate();

			if (Objects.nonNull(listaIdPagoVentas) && listaIdPagoVentas.size() > 0) {
				session.createSQLQuery("DELETE FROM movimientos_cta_cte where pago_cbte_ven_id in(:idPagoVenta)")
						.setParameterList("idPagoVenta", listaIdPagoVentas).executeUpdate();
			}

			if (Objects.nonNull(listaIdPagoCtaCte) && listaIdPagoCtaCte.size() > 0) {
				session.createSQLQuery("DELETE FROM pago_pago_cta_cte where pagos_cta_cte_id in(:idPagoCtaCte)")
						.setParameterList("idPagoCtaCte", listaIdPagoCtaCte).executeUpdate();

				session.createSQLQuery("DELETE FROM pagos_cta_cte where id in(:idPagoCtaCte)")
						.setParameterList("idPagoCtaCte", listaIdPagoCtaCte).executeUpdate();
			}
			// eliminamos la el pago de la venta
			session.createSQLQuery("DELETE FROM pago_cbte_ven where cbte_ven_enc_id in (:idVenta)")
					.setParameterList("idVenta", listaIdVentas).executeUpdate();
			// eliminamos los eventos de la venta
			session.createSQLQuery("DELETE FROM eventos_ventas where cbte_ven_enc_id in (:idVenta)")
					.setParameterList("idVenta", listaIdVentas).executeUpdate();
			// eliminamos los ivas_afip de la venta
			session.createSQLQuery("DELETE FROM ivas_afip where id_cbte in (:idVenta)")
					.setParameterList("idVenta", listaIdVentas).executeUpdate();
			// eliminamos los tributos_afip de la venta
			session.createSQLQuery("DELETE FROM tributos_afip where id_cbte in (:idVenta)")
					.setParameterList("idVenta", listaIdVentas).executeUpdate();
			
			
//		});
		}
		// eliminamos las cajas asociadas
		Criteria criteriaCaja = session.createCriteria(Caja.class, "caja");
		criteriaCaja.add(Restrictions.eq("app.id", idApp));
		List<Caja> cajas = criteriaCaja.list();
		List<Integer> listaIdCaja = cajas.stream().map(item -> item.getId()).collect(Collectors.toList());
		List<Integer> listaIdTransaCaja = null;
		if (Objects.nonNull(listaIdCaja) && listaIdCaja.size() > 0) {
			Criteria criteriaTransCaja = session.createCriteria(TransaccionCaja.class, "transaccionCaja");
			criteriaTransCaja.add(Restrictions.in("caja.id", listaIdCaja));
			List<TransaccionCaja> transaccionesCaja = criteriaTransCaja.list();
			listaIdTransaCaja = transaccionesCaja.stream().map(item -> item.getId()).collect(Collectors.toList());

			session.createSQLQuery("DELETE FROM detalle_transaccion_caja where transaccion_caja_id in (:idTransCaja)")
					.setParameterList("idTransCaja", listaIdTransaCaja).executeUpdate();
		}
		// eliminam os primero la venta por que esta asociada la transaccion caja, 

		if (Objects.nonNull(listaIdVentas) && listaIdVentas.size() > 0) {
			session.createSQLQuery("DELETE FROM cbte_enc where id in (:idVenta)")
					.setParameterList("idVenta", listaIdVentas).executeUpdate();
		}
		if (Objects.nonNull(listaIdTransaCaja) && listaIdTransaCaja.size() > 0) {
			session.createSQLQuery("DELETE FROM transaccion_caja where id in (:idTransCaja)")
					.setParameterList("idTransCaja", listaIdTransaCaja).executeUpdate();
		}
		if (Objects.nonNull(listaIdCaja) && listaIdCaja.size() > 0) {
			session.createSQLQuery("DELETE FROM caja_sucursales where caja_id in (:idCaja)")
					.setParameterList("idCaja", listaIdCaja).executeUpdate();

			session.createSQLQuery("DELETE FROM caja where id in (:idCaja)")
					.setParameterList("idCaja", listaIdCaja).executeUpdate();
		}
		// =======================================================================================================
		// ===========================eliminamos las compras===============================
		// =======================================================================================================
		Criteria criteriacompras = session.createCriteria(CbteCompEnc.class, "compras");
		criteriacompras.add(Restrictions.eq("app.id", idApp));
		List<CbteCompEnc> compras = criteriacompras.list();
		List<Integer> listaIdCompras = compras.stream().map(item -> item.getId()).collect(Collectors.toList());

		if (Objects.nonNull(listaIdCompras) && listaIdCompras.size() > 0) {
			// recuperamos los detalle de compras
			Criteria criteriaDetCompras = session.createCriteria(CbteCompDet.class, "cbteCompDet");
			criteriaDetCompras.add(Restrictions.in("cbteCompEnc.id", listaIdCompras));
			List<CbteCompDet> comprasDet = criteriaDetCompras.list();
			List<Integer> listaIdDetComp = comprasDet.stream().map(item -> item.getId()).collect(Collectors.toList());
			if (Objects.nonNull(listaIdDetComp) && listaIdDetComp.size() > 0) {
				session.createSQLQuery("DELETE FROM vencimientos_productos where cbte_comp_det_id in(:idDetComp)")
						.setParameterList("idDetComp", listaIdDetComp).executeUpdate();
			}
			session.createSQLQuery("DELETE FROM cbte_comp_adicional where cbte_comp_enc_id in(:idEncComp)")
					.setParameterList("idEncComp", listaIdCompras).executeUpdate();

			session.createSQLQuery("DELETE FROM cbte_comp_descuento where cbte_comp_enc_id in(:idEncComp)")
					.setParameterList("idEncComp", listaIdCompras).executeUpdate();

			session.createSQLQuery("DELETE FROM cbte_comp_impuestos where cbte_comp_enc_id in(:idEncComp)")
					.setParameterList("idEncComp", listaIdCompras).executeUpdate();

			session.createSQLQuery("DELETE FROM cbte_comp_det where cbte_comp_enc_id in(:idEncComp)")
					.setParameterList("idEncComp", listaIdCompras).executeUpdate();

			//aliminamos los movimientos relacionados
			Criteria criteriaEncMov = session.createCriteria(EncMovimientos.class, "encMovimientos");
			criteriaEncMov.add(Restrictions.in("facturaCompra.id", listaIdCompras));
			List<EncMovimientos> encMovimientos = criteriaEncMov.list();
			List<Integer> listaIdEncMovimientos = encMovimientos.stream().map(item -> item.getId()).collect(Collectors.toList());

			if (Objects.nonNull(listaIdEncMovimientos) && listaIdEncMovimientos.size() > 0) {
				Criteria criteriaDetMov = session.createCriteria(DetMovimientos.class, "encMovimientos");
				criteriaDetMov.add(Restrictions.in("factura.id", listaIdEncMovimientos));
				List<DetMovimientos> detMovimientos = criteriaDetMov.list();
				List<Integer> listaIdDetMovimientos = detMovimientos.stream().map(item -> item.getId()).collect(Collectors.toList());
				if (Objects.nonNull(listaIdDetMovimientos) && listaIdDetMovimientos.size() > 0) {
					session.createSQLQuery("DELETE FROM movimientos_producto where det_movimientos_id in(:idDetMov)")
							.setParameterList("idDetMov", listaIdDetMovimientos).executeUpdate();

					session.createSQLQuery("DELETE FROM det_movimientos where id in(:idDetMov)")
							.setParameterList("idDetMov", listaIdDetMovimientos).executeUpdate();
				}
				session.createSQLQuery("DELETE FROM enc_movimientos where id in(:idEncMov)")
						.setParameterList("idEncMov", listaIdEncMovimientos).executeUpdate();
			}

			// eliminamos los pagos
			Criteria criteriaPagoCtaCteProv = session.createCriteria(PagosCtaCteProv.class, "pagosCtaCteProv");
			Criteria criteriaPagoCtaCteMovCtaCteProv = criteriaPagoCtaCteProv.createCriteria("pagosCtaCteProv.movimientos", "movimientos", JoinType.LEFT_OUTER_JOIN);
			criteriaPagoCtaCteMovCtaCteProv.add(Restrictions.eq("app.id", idApp));
			List<PagosCtaCteProv> pagosCtaCteProv = criteriaPagoCtaCteProv.list();
			List<Integer> listaIdPagoCtaCteProv = pagosCtaCteProv.stream().map(item -> item.getId()).collect(Collectors.toList());

			Criteria criteriaPagoCompra = session.createCriteria(PagoCbteComp.class, "pagoCbteComp");
			criteriaPagoCompra.add(Restrictions.in("cbteCompEnc.id", listaIdCompras));
			List<PagoCbteComp> pagosCompra = criteriaPagoCompra.list();
			List<Integer> listaIdPagoCompras = pagosCompra.stream().map(item -> item.getId()).collect(Collectors.toList());

			session.createSQLQuery("DELETE FROM movimientos_cta_cte_prov where app_id=:idApp")
					.setInteger("idApp", idApp).executeUpdate();
			if (Objects.nonNull(listaIdPagoCompras) && listaIdPagoCompras.size() > 0) {
				session.createSQLQuery("DELETE FROM movimientos_cta_cte_prov where pago_cbte_comp_id in(:idPagoCompra)")
						.setParameterList("idPagoCompra", listaIdPagoCompras).executeUpdate();
			}
			if (Objects.nonNull(listaIdPagoCtaCteProv) && listaIdPagoCtaCteProv.size() > 0) {
				session.createSQLQuery("DELETE FROM pago_pago_cta_cte_prov where pagos_cta_cte_prov_id in(:idPagoCtaCte)")
						.setParameterList("idPagoCtaCte", listaIdPagoCtaCteProv).executeUpdate();

				session.createSQLQuery("DELETE FROM pagos_cta_cte_prov where id in(:idPagoCtaCte)")
						.setParameterList("idPagoCtaCte", listaIdPagoCtaCteProv).executeUpdate();
				// eliminamos la el pago de la venta
			}
			session.createSQLQuery("DELETE FROM pago_cbte_comp where cbte_comp_enc_id in (:idCompra)")
					.setParameterList("idCompra", listaIdCompras).executeUpdate();

			session.createSQLQuery("DELETE FROM cbte_comp_enc where id in (:idCompra)")
					.setParameterList("idCompra", listaIdCompras).executeUpdate();

		}

		// =======================================================================================================
		// ===========================eliminamos lo que tenga que ver con productos===============================
		// =======================================================================================================
		Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "producto");
		Criteria criteriaProductos = criteriaDetalleProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
		/*###########################Control por APP############################*/
		criteriaProductos.setFetchMode("productoPadre.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", idApp);
		criteriaProductos.add(c1);
		/*######################################################################*/
		List<Producto> productos = criteriaDetalleProductos.list();
		List<Integer> listaIdProductos = productos.stream().map(item -> item.getId()).collect(Collectors.toList());
		if (Objects.nonNull(listaIdProductos) && listaIdProductos.size() > 0) {
			// codigo de barras
			session.createSQLQuery("DELETE FROM codigos_barras where producto_id in(:idProductos)")
					.setParameterList("idProductos", listaIdProductos).executeUpdate();
			// imagen prod
			session.createSQLQuery("DELETE FROM imagen_producto where producto_id in(:idProductos)")
					.setParameterList("idProductos", listaIdProductos).executeUpdate();

			// eliminamos los movimientos
			Criteria criteriaDetMovimientos = session.createCriteria(DetMovimientos.class, "detMovimientos");
			Criteria criteriaDetMovProductos = criteriaDetMovimientos.createCriteria("detMovimientos.producto", "producto", JoinType.LEFT_OUTER_JOIN);
			Criteria criteriaDetMovFactura = criteriaDetMovimientos.createCriteria("detMovimientos.factura", "factura", JoinType.LEFT_OUTER_JOIN);

			criteriaDetMovProductos.add(Restrictions.in("producto.id", listaIdProductos));
			List<DetMovimientos> detsMovimientos = criteriaDetMovimientos.list();
			List<Integer> listaIdDetMov = detsMovimientos.stream().map(item -> item.getId()).collect(Collectors.toList());
			List<Integer> listaIdEncMov = detsMovimientos.stream().map(item -> item.getFactura().getId()).collect(Collectors.toList());
			// movimientos_producto
			if (Objects.nonNull(listaIdDetMov) && listaIdDetMov.size() > 0) {
				session.createSQLQuery("DELETE FROM movimientos_producto where det_movimientos_id in(:idDetMov)")
						.setParameterList("idDetMov", listaIdDetMov).executeUpdate();

				session.createSQLQuery("DELETE FROM det_movimientos where id in(:idDetMov)")
						.setParameterList("idDetMov", listaIdDetMov).executeUpdate();
			}
			if (Objects.nonNull(listaIdEncMov) && listaIdEncMov.size() > 0) {
				session.createSQLQuery("DELETE FROM enc_movimientos where id in(:idEncMov)")
						.setParameterList("idEncMov", listaIdEncMov).executeUpdate();
			}
			session.createSQLQuery("DELETE FROM pase_movimientos where producto_id in(:idProductos)")
					.setParameterList("idProductos", listaIdProductos).executeUpdate();

			session.createSQLQuery("DELETE FROM stock_sucursal where producto_id in(:idProductos)")
					.setParameterList("idProductos", listaIdProductos).executeUpdate();

			session.createSQLQuery("DELETE FROM hist_precios where producto_id in(:idProductos)")
					.setParameterList("idProductos", listaIdProductos).executeUpdate();

			session.createSQLQuery("DELETE FROM producto_proveedores where producto_id in(:idProductos)")
					.setParameterList("idProductos", listaIdProductos).executeUpdate();

			session.createSQLQuery("DELETE FROM vencimientos_productos where producto_id in(:idProductos)")
					.setParameterList("idProductos", listaIdProductos).executeUpdate();


			session.createSQLQuery("DELETE FROM ofertas_producto where producto_id in(:idProductos)")
					.setParameterList("idProductos", listaIdProductos).executeUpdate();

			session.createSQLQuery("DELETE FROM productos_compuestos where producto_id in(:idProductos)")
					.setParameterList("idProductos", listaIdProductos).executeUpdate();

			session.createSQLQuery("DELETE FROM producto where id in(:idProductos)")
					.setParameterList("idProductos", listaIdProductos).executeUpdate();

		}
		session.createSQLQuery("DELETE FROM producto_padre where app_id=:idApp")
				.setInteger("idApp", idApp).executeUpdate();
		// =======================================================================================================
		// ===========================eliminamos las entidades===============================
		// =======================================================================================================

		Criteria criteriaCliente = session.createCriteria(Cliente.class, "cliente");
		criteriaCliente.add(Restrictions.eq("app.id", idApp));
		List<Cliente> clientes = criteriaCliente.list();
		List<Integer> listaIdClientes = clientes.stream().map(item -> item.getId()).collect(Collectors.toList());
		if (Objects.nonNull(listaIdClientes) && listaIdClientes.size() > 0) {
			session.createSQLQuery("DELETE FROM cuentas_corrientes where cliente_id in(:idClientes)")
					.setParameterList("idClientes", listaIdClientes).executeUpdate();

			session.createSQLQuery("DELETE FROM clientes where id in(:idClientes)")
					.setParameterList("idClientes", listaIdClientes).executeUpdate();
		}
		Criteria criteriaProv = session.createCriteria(Proveedores.class, "proveedores");
		criteriaProv.add(Restrictions.eq("app.id", idApp));
		List<Proveedores> proveedores = criteriaProv.list();
		List<Integer> listaIdProveedores = proveedores.stream().map(item -> item.getId()).collect(Collectors.toList());
		if (Objects.nonNull(listaIdProveedores) && listaIdProveedores.size() > 0) {
			Criteria criteriaCtaCteProv = session.createCriteria(CuentasCorrientesProv.class, "ctaCteProv");
			criteriaCtaCteProv.add(Restrictions.in("proveedor.id", listaIdProveedores));
			List<CuentasCorrientesProv> ctaCtesProv = criteriaCtaCteProv.list();
			List<Integer> listaIdCtaCteProv = ctaCtesProv.stream().map(item -> item.getId()).collect(Collectors.toList());
			if (Objects.nonNull(listaIdCtaCteProv) && listaIdCtaCteProv.size() > 0) {
				session.createSQLQuery("DELETE FROM movimientos_cta_cte_prov where cuentas_corrientes_prov_proveedores_id in(:idCtaCetProv)")
						.setParameterList("idCtaCetProv", listaIdCtaCteProv).executeUpdate();
			}
			session.createSQLQuery("DELETE FROM cuentas_corrientes_prov where proveedores_id in(:idProveedores)")
					.setParameterList("idProveedores", listaIdProveedores).executeUpdate();

			session.createSQLQuery("DELETE FROM producto_proveedores where proveedores_id in(:idProveedores)")
					.setParameterList("idProveedores", listaIdProveedores).executeUpdate();

			session.createSQLQuery("DELETE FROM proveedores where id in(:idProveedores)")
					.setParameterList("idProveedores", listaIdProveedores).executeUpdate();
		}
		Criteria criteriaEntidad = session.createCriteria(Entidad.class, "entidad");
		criteriaEntidad.add(Restrictions.eq("app.id", idApp));
		List<Entidad> entidades = criteriaEntidad.list();
		List<Integer> listaIdEntidades = entidades.stream().map(item -> item.getId()).collect(Collectors.toList());
		if (Objects.nonNull(listaIdEntidades) && listaIdEntidades.size() > 0) {
			session.createSQLQuery("DELETE FROM empresas where entidad_id in(:idEntidades)")
					.setParameterList("idEntidades", listaIdEntidades).executeUpdate();
		}
		Criteria criteriaUsuarios = session.createCriteria(Usuarios.class, "usuarios");
		criteriaUsuarios.add(Restrictions.eq("app.id", idApp));
		List<Usuarios> usuarios = criteriaUsuarios.list();
		List<Integer> listaIdUsuarios = usuarios.stream().map(item -> item.getId()).collect(Collectors.toList());
		if (Objects.nonNull(listaIdUsuarios) && listaIdUsuarios.size() > 0) {
			session.createSQLQuery("DELETE FROM log_users where usuarios_id in(:idUsuarios)")
					.setParameterList("idUsuarios", listaIdUsuarios).executeUpdate();

			session.createSQLQuery("DELETE FROM log where usuarios_id in(:idUsuarios)")
					.setParameterList("idUsuarios", listaIdUsuarios).executeUpdate();

			session.createSQLQuery("DELETE FROM claves where usuarios_id in(:idUsuarios)")
					.setParameterList("idUsuarios", listaIdUsuarios).executeUpdate();

			session.createSQLQuery("DELETE FROM usuarios_sucursales where usuarios_id in(:idUsuarios)")
					.setParameterList("idUsuarios", listaIdUsuarios).executeUpdate();

			session.createSQLQuery("DELETE FROM usuarios_roles where usuarios_id in(:idUsuarios)")
					.setParameterList("idUsuarios", listaIdUsuarios).executeUpdate();

			session.createSQLQuery("DELETE FROM usuarios_roles where usuarios_id in(:idUsuarios)")
					.setParameterList("idUsuarios", listaIdUsuarios).executeUpdate();

			session.createSQLQuery("DELETE FROM usuarios where id in(:idUsuarios)")
					.setParameterList("idUsuarios", listaIdUsuarios).executeUpdate();
		}
		//eliminamos personas
		if (Objects.nonNull(listaIdEntidades) && listaIdEntidades.size() > 0) {
			session.createSQLQuery("DELETE FROM personas where entidad_id in(:idEntidades)")
					.setParameterList("idEntidades", listaIdEntidades).executeUpdate();

			session.createSQLQuery("DELETE FROM contactos where entidad_id in(:idEntidades)")
					.setParameterList("idEntidades", listaIdEntidades).executeUpdate();

			session.createSQLQuery("DELETE FROM domicilios where entidad_id in(:idEntidades)")
					.setParameterList("idEntidades", listaIdEntidades).executeUpdate();

			session.createSQLQuery("DELETE FROM telefonos where entidad_id in(:idEntidades)")
					.setParameterList("idEntidades", listaIdEntidades).executeUpdate();

			session.createSQLQuery("DELETE FROM entidad where id in(:idEntidades)")
					.setParameterList("idEntidades", listaIdEntidades).executeUpdate();
		}
		// =======================================================================================================
		// ===========================eliminamos las tablas restantes===============================
		// =======================================================================================================
		Criteria criteriaSuc = session.createCriteria(Sucursales.class, "sucursales");
		criteriaSuc.add(Restrictions.eq("app.id", idApp));
		List<Sucursales> sucursales = criteriaSuc.list();
		List<Integer> listaIdSucursales = sucursales.stream().map(item -> item.getId()).collect(Collectors.toList());
		if (Objects.nonNull(listaIdSucursales) && listaIdSucursales.size() > 0) {
			session.createSQLQuery("DELETE FROM stock_sucursal where sucursales_id in(:idSuc)")
					.setParameterList("idSuc", listaIdSucursales).executeUpdate();
			session.createSQLQuery("DELETE FROM sucursales where id in(:idSuc)")
					.setParameterList("idSuc", listaIdSucursales).executeUpdate();
		}
		session.createSQLQuery("DELETE FROM sucursales where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();
		session.createSQLQuery("DELETE FROM sucursales where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();
		session.createSQLQuery("DELETE FROM afip_pto_venta where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();

		session.createSQLQuery("DELETE FROM afip_tpo_cbte where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();

		session.createSQLQuery("DELETE FROM afip_tpo_concepto where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();

		session.createSQLQuery("DELETE FROM afip_tpo_doc where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();

		session.createSQLQuery("DELETE FROM afip_tpo_iva where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();

		session.createSQLQuery("DELETE FROM afip_tpo_tributo where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();
		
		session.createSQLQuery("DELETE FROM vencimientos_productos where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();
		
		session.createSQLQuery("DELETE FROM impuestos where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();

		Criteria criteriaFamilias = session.createCriteria(Familias.class, "familias");
		criteriaFamilias.add(Restrictions.eq("app.id", idApp));
		List<Familias> familias = criteriaFamilias.list();
		List<Integer> listaIdFamilias = familias.stream().map(item -> item.getId()).collect(Collectors.toList());
		if (Objects.nonNull(listaIdFamilias) && listaIdFamilias.size() > 0) {
			session.createSQLQuery("UPDATE familias SET familias_id=null where id in(:idFam)")
					.setParameterList("idFam", listaIdFamilias).executeUpdate();
			session.createSQLQuery("DELETE FROM familias where id in(:idFam)")
					.setParameterList("idFam", listaIdFamilias).executeUpdate();
		}
		session.createSQLQuery("UPDATE familias SET familias_id=null where app_id=:idApp")
				.setInteger("idApp", idApp).executeUpdate();

		session.createSQLQuery("DELETE FROM familias where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();

		session.createSQLQuery("DELETE FROM lista_precios where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();

		Criteria criteriaOferta = session.createCriteria(Ofertas.class, "ofertas");
		criteriaOferta.add(Restrictions.eq("app.id", idApp));
		List<Ofertas> ofertas = criteriaOferta.list();
		List<Integer> listaIdOfertas = ofertas.stream().map(item -> item.getId()).collect(Collectors.toList());
		if (Objects.nonNull(listaIdOfertas) && listaIdOfertas.size() > 0) {
			session.createSQLQuery("DELETE FROM ofertas_producto where ofertas_id in(:idOfertas)")
					.setParameterList("idOfertas", listaIdOfertas).executeUpdate();
		}
		session.createSQLQuery("DELETE FROM ofertas where app_id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();

		//aliminamos al final la app
		Criteria criteriaApp = session.createCriteria(App.class, "app");
		criteriaApp.add(Restrictions.eq("id", idApp));
		App app = (App) criteriaApp.uniqueResult();
		Integer idConfig = app.getConfiguracion().getId();
		session.createSQLQuery("DELETE FROM app where id =:idApp")
				.setInteger("idApp", idApp).executeUpdate();

		session.createSQLQuery("DELETE FROM puntos_venta where configuracion_id =:idConfig")
				.setInteger("idConfig", idConfig).executeUpdate();

		session.createSQLQuery("DELETE FROM configuracion where id =:idConfig")
				.setInteger("idConfig", idConfig).executeUpdate();

	}
}
