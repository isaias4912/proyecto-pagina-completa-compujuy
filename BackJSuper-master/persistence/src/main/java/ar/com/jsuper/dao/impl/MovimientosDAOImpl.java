package ar.com.jsuper.dao.impl;

import ar.com.jsuper.domain.*;
import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.MovimientosDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.utils.FilterGeneric;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

@Repository
public class MovimientosDAOImpl extends GenericDAOImpl<DetMovimientos, Integer> implements MovimientosDAO {

	private Set<StockSucursal> stockSucursales;

	@Override
	public DetMovimientos insert(DetMovimientos movimiento) {
		Session session = sessionFactory.getCurrentSession();
		Producto producto = session.get(Producto.class, movimiento.getProducto().getId());
		Sucursales sucursal = session.get(Sucursales.class, movimiento.getFactura().getSucursal().getId());
		this.stockSucursales = new HashSet<>();
		this.setStockSucursal(producto, sucursal, movimiento.getCantidad(), BigDecimal.ONE);
		for (StockSucursal stockSucursal : this.stockSucursales) {
			session.saveOrUpdate(stockSucursal);
		}
		EncMovimientos encMovimientos = movimiento.getFactura();
		encMovimientos.setFechaCarga(new Date());
		encMovimientos.setTipo(1);
		encMovimientos.setSubtipo(movimiento.getTipo());
		session.save(encMovimientos);
		session.save(movimiento);
		return movimiento;
	}

	@Override
	public Pagination<DetMovimientos> getMovimientosByProducto(List<Producto> productos, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaDetMovimiento = session.createCriteria(DetMovimientos.class, "det");
		Criteria criteriaEncMovimiento = criteriaDetMovimiento.createCriteria("det.factura", "factura", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEncMovimientoSuc = criteriaDetMovimiento.createCriteria("factura.sucursal", "sucursal", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaEncMovimientoFactCompra = criteriaEncMovimiento.createCriteria("factura.facturaCompra", "facturacompra", JoinType.LEFT_OUTER_JOIN);
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaDetMovimiento.setProjection(idCountProjection);
		Disjunction disjunctionNombres = Restrictions.disjunction();
		for (Producto producto : productos) {
			disjunctionNombres.add(Restrictions.eq("det.producto", producto));
		}
		criteriaDetMovimiento.add(disjunctionNombres);
//		Criterion c1 = Restrictions.eq("det.producto", producto);
//		criteriaDetMovimiento.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaDetMovimiento.uniqueResult()).intValue();

		if (reverse) {
			criteriaDetMovimiento.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaDetMovimiento.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaDetMovimiento.setProjection(Projections.distinct(Projections.property("id")));
		criteriaDetMovimiento.setFirstResult((page - 1) * pageSize);
		criteriaDetMovimiento.setMaxResults(pageSize);
		List uniqueSubList = criteriaDetMovimiento.list();
		criteriaDetMovimiento.setProjection(null);
		criteriaDetMovimiento.setFirstResult(0);
		criteriaDetMovimiento.setMaxResults(Integer.MAX_VALUE);
		criteriaDetMovimiento.add(Restrictions.in("id", uniqueSubList));
		criteriaDetMovimiento.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<DetMovimientos> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaDetMovimiento.list());
		}
		Pagination<DetMovimientos> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public Pagination<MovimientosProducto> getMovimientosProductoByProducto(List<Producto> productos, int page, int pageSize, String fieldOrder, boolean reverse, FilterGeneric filterGeneric) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaMovimiento = session.createCriteria(MovimientosProducto.class, "mov");
		Criteria criteriaMovimientoMov = criteriaMovimiento.createCriteria("mov.movimiento", "movimiento", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaMovimientoMovFactura = criteriaMovimientoMov.createCriteria("movimiento.factura", "factura", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaMovimientoMovFacturaSucursal = criteriaMovimientoMovFactura.createCriteria("factura.sucursal", "facsucursal", JoinType.LEFT_OUTER_JOIN);

		Projection idCountProjection = Projections.countDistinct("id");
		criteriaMovimiento.setProjection(idCountProjection);

		Disjunction disjunctionProd = Restrictions.disjunction();
		Disjunction disjunctionTipo = Restrictions.disjunction();

		disjunctionTipo.add(Restrictions.eq("tipo", 1));
		// si vienen el filtro para ventas incluimos 
		if (filterGeneric.getVentas()) {
			Criteria criteriaMovimientoVenta = criteriaMovimiento.createCriteria("mov.venta", "venta", JoinType.LEFT_OUTER_JOIN);
			Criteria criteriaMovimientoVentaEnc = criteriaMovimientoVenta.createCriteria("venta.cbteEnc", "enc", JoinType.LEFT_OUTER_JOIN);
			Criteria criteriaMovimientoVentaEncSuc = criteriaMovimientoVentaEnc.createCriteria("enc.sucursal", "encsucursal", JoinType.LEFT_OUTER_JOIN);
		}
		disjunctionTipo.add(Restrictions.eq("tipo", 2));
		for (Producto producto : productos) {
			if (filterGeneric.getVentas()) {
				disjunctionProd.add(Restrictions.eq("venta.producto", producto));
			}
			disjunctionProd.add(Restrictions.eq("movimiento.producto", producto));
		}

		criteriaMovimiento.add(disjunctionProd);
		criteriaMovimiento.add(disjunctionTipo);

		// filtro por sucursales
		Set<Integer> sucursalesIds = filterGeneric.getSucursales();
		Disjunction disjunctionSucursal = Restrictions.disjunction();
		for (Integer sucId : sucursalesIds) {
			if (filterGeneric.getVentas()) {
				disjunctionSucursal.add(Restrictions.eq("encsucursal.id", sucId));
			}
			disjunctionSucursal.add(Restrictions.eq("facsucursal.id", sucId));
		}
		criteriaMovimiento.add(disjunctionSucursal);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaMovimiento.uniqueResult()).intValue();

		if (reverse) {
			criteriaMovimiento.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaMovimiento.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaMovimiento.setProjection(Projections.distinct(Projections.property("id")));
		criteriaMovimiento.setFirstResult((page - 1) * pageSize);
		criteriaMovimiento.setMaxResults(pageSize);
		List uniqueSubList = criteriaMovimiento.list();
		criteriaMovimiento.setProjection(null);
		criteriaMovimiento.setFirstResult(0);
		criteriaMovimiento.setMaxResults(Integer.MAX_VALUE);
		criteriaMovimiento.add(Restrictions.in("id", uniqueSubList));
		criteriaMovimiento.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<MovimientosProducto> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaMovimiento.list());
		}
		Pagination<MovimientosProducto> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	/**
	 * Test para ingresar movimientos productos desde det movimientos
	 */
	private void insertMmovimientosProductos() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaDetMovimiento = session.createCriteria(DetMovimientos.class, "det");
		List<DetMovimientos> lista = criteriaDetMovimiento.list();
		for (DetMovimientos det : lista) {
			MovimientosProducto m = new MovimientosProducto();
			m.setCantidad(det.getCantidad());
			m.setFecha(det.getFactura().getFechaCarga());
			m.setMovimiento(det);
			m.setTipo(1);
			m.setValorFinal(det.getValorFinal());
			m.setValorInicial(det.getValorInicial());
			m.setVenta(null);
			session.save(m);
		}
	}

	@Override
	public Pagination<HistPrecios> getMovimientosPrecioByProducto(Producto producto, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaMovimiento = session.createCriteria(HistPrecios.class, "hist");
		criteriaMovimiento.setFetchMode("tipoPrecio", FetchMode.JOIN);
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaMovimiento.setProjection(idCountProjection);
		Criterion c1 = Restrictions.eq("hist.producto", producto);
		criteriaMovimiento.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaMovimiento.uniqueResult()).intValue();

		if (reverse) {
			criteriaMovimiento.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaMovimiento.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaMovimiento.setProjection(Projections.distinct(Projections.property("id")));
		criteriaMovimiento.setFirstResult((page - 1) * pageSize);
		criteriaMovimiento.setMaxResults(pageSize);
		List uniqueSubList = criteriaMovimiento.list();
		criteriaMovimiento.setProjection(null);
		criteriaMovimiento.setFirstResult(0);
		criteriaMovimiento.setMaxResults(Integer.MAX_VALUE);
		criteriaMovimiento.add(Restrictions.in("id", uniqueSubList));
		criteriaMovimiento.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<HistPrecios> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaMovimiento.list());
		}
		Pagination<HistPrecios> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	private void setStockSucursal(Producto producto, Sucursales suc, BigDecimal cantidad, BigDecimal multiplicadorStock) {
		if (producto.getProductosCompuestos().isEmpty()) {// es un  producto simple
			Set<StockSucursal> stockSucursales = producto.getStockSucursales();
			if (stockSucursales.size() == 0) {//para el caso de que sea simple y no tenga estock en la sucursal
				StockSucursal ss = new StockSucursal();
				ss.setDetalle("");
				ss.setUbicacion("");
				ss.setProducto(producto);
				ss.setStock(cantidad.multiply(multiplicadorStock));
				ss.setStockMinimo(BigDecimal.ZERO);
				ss.setPuntoReposicion(BigDecimal.ZERO);
				ss.setSucursal(suc);
				this.stockSucursales.add(ss);
			} else {
				for (StockSucursal stockSucursal : stockSucursales) {
					if (stockSucursal.getSucursal().getId() == suc.getId()) {
						stockSucursal.setStock(stockSucursal.getStock().add(cantidad.multiply(multiplicadorStock)));
						this.stockSucursales.add(stockSucursal);
					}
				}
			}
		} else {
			for (ProductosCompuestos productosCompuesto : producto.getProductosCompuestos()) {
				this.setStockSucursal(productosCompuesto.getProducto(), suc, cantidad, productosCompuesto.getCantidad().multiply(multiplicadorStock));
			}
		}
	}

	@Override
	public List<EncMovimientos> getFromFactura(Integer idFactura) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaMovimientos = session.createCriteria(EncMovimientos.class, "mov");
//        criteriaMovimientos.setFetchMode("movimientos", FetchMode.JOIN);
		criteriaMovimientos.createCriteria("mov.movimientos", "movimientos", JoinType.LEFT_OUTER_JOIN);
		Criteria criteriaFactura = criteriaMovimientos.createCriteria("mov.facturaCompra", "factura", JoinType.LEFT_OUTER_JOIN);
		Criterion c1 = Restrictions.eq("factura.id", idFactura);
		criteriaFactura.add(c1);
		criteriaMovimientos.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteriaMovimientos.list();
	}

	@Override
	public void saveMovimientoProducto(Set<CbteDet> detalleVentas, Date fecha, StockSucursal ss) {
		Session session = sessionFactory.getCurrentSession();
		for (CbteDet detalleVenta : detalleVentas) {
			MovimientosProducto movimientosProducto = new MovimientosProducto();
			movimientosProducto.setCantidad(detalleVenta.getCantidad());
			movimientosProducto.setMovimiento(null);
			movimientosProducto.setTipo(2); //venta-detalle de venta
			movimientosProducto.setValorFinal(ss.getStock());
			movimientosProducto.setValorInicial(ss.getStockAnterior());
			movimientosProducto.setVenta(detalleVenta);
			movimientosProducto.setFecha(fecha);
			session.save(movimientosProducto);
		}
	}
}
