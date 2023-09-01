package ar.com.jsuper.dao.impl;

import org.springframework.stereotype.Repository;
import ar.com.jsuper.domain.EncMovimientos;
import ar.com.jsuper.domain.DetMovimientos;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.ProductosCompuestos;
import ar.com.jsuper.domain.StockSucursal;
import ar.com.jsuper.domain.Sucursales;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Session;
import ar.com.jsuper.dao.EncMovimientosDAO;
import ar.com.jsuper.dao.ProductoDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.PaseMovimientos;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.security.TenantContext;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@Repository
public class EncMovimientosDAOImpl extends GenericDAOImpl<EncMovimientos, Integer> implements EncMovimientosDAO {

	@Autowired
	ProductoDAO productoDAO;

	@Override
	public PaseMovimientos insertPase(PaseMovimientos paseMovimiento) {
		Session session = sessionFactory.getCurrentSession();
		session.save(paseMovimiento);
		return paseMovimiento;
	}

	@Override
	public void removePase(PaseMovimientos paseMovimiento) {
		Session session = sessionFactory.getCurrentSession();
		session.remove(paseMovimiento);
	}

	@Override
	public PaseMovimientos getPase(Integer idPase) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(PaseMovimientos.class, idPase);
	}

	@Override
	public PaseMovimientos updatePase(PaseMovimientos paseMovimiento) {
		Session session = sessionFactory.getCurrentSession();
		session.update(paseMovimiento);
		return paseMovimiento;
	}

	@Override
	public Pagination<PaseMovimientos> getPasesBypage(FilterGeneric filterGeneric, int page, int pageSize, String fieldOrder, boolean reverse) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaPase = session.createCriteria(PaseMovimientos.class, "pase");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaPase.setProjection(idCountProjection);
		Criterion c1;
		Integer estado = filterGeneric.getActivo();
		criteriaPase.setFetchMode("sucursalDestino", FetchMode.JOIN);
		criteriaPase.setFetchMode("sucursalOrigen", FetchMode.JOIN);
		criteriaPase.setFetchMode("producto", FetchMode.JOIN);

		if (!Objects.isNull(filterGeneric.getProducto())) {
			if (filterGeneric.getProducto() != null && filterGeneric.getProducto().getId() > 0) {
				criteriaPase.add(Restrictions.eq("producto", filterGeneric.getProducto()));
			}
		}
		// restriccion para activos o no activos
		if (estado != null && !estado.equals("") && estado != 2) {
			Criterion cActivo = Restrictions.eq("estado", (estado == 1));
			criteriaPase.add(cActivo);
		}
		if (!Objects.isNull(filterGeneric.getSucursal())) {
			criteriaPase.add(Restrictions.eq("sucursalDestino", filterGeneric.getSucursal()));
		}

		/*###########################Control por APP############################*/
		criteriaPase.setFetchMode("pase.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaPase.add(c1);
		/*######################################################################*/
		Integer totalResultCount = ((Long) criteriaPase.uniqueResult()).intValue();

		if (reverse) {
			criteriaPase.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaPase.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaPase.setProjection(Projections.distinct(Projections.property("id")));
		criteriaPase.setFirstResult((page - 1) * pageSize);
		criteriaPase.setMaxResults(pageSize);
		List uniqueSubList = criteriaPase.list();
		criteriaPase.setProjection(null);
		criteriaPase.setFirstResult(0);
		criteriaPase.setMaxResults(Integer.MAX_VALUE);
		criteriaPase.add(Restrictions.in("id", uniqueSubList));
		criteriaPase.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<PaseMovimientos> lista = null;
		if (totalResultCount > 0) {
			lista = new ArrayList<>(criteriaPase.list());
		}
		Pagination<PaseMovimientos> pagination = new Pagination<>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public EncMovimientos insert(EncMovimientos factura) {
		return this.insert(factura, false);
	}

	@Override
	public EncMovimientos insert(EncMovimientos factura, Boolean validateStock) {
		Session session = sessionFactory.getCurrentSession();
		factura.setFechaCarga(new Date());
		session.save(factura);
		for (DetMovimientos movimiento : factura.getMovimientos()) {

			Producto producto = session.get(Producto.class, movimiento.getProducto().getId());
			Sucursales sucursal = session.get(Sucursales.class, factura.getSucursal().getId());

			BigDecimal stockInicial = productoDAO.getStockSucursal(producto, sucursal);
			if (Objects.isNull(stockInicial)) {
				stockInicial = BigDecimal.ZERO;
			}
			// validamos si el pase de estock es valido, como por ejemplo para el caso de los pases
			if (validateStock) {
				if (factura.getTipo().equals(4)) { // si es pase controlamos
					if (factura.getSubtipo().equals(2)) { // si es pase controlamos el subtipo que sea egreso
						if (stockInicial.compareTo(movimiento.getCantidad()) < 0) {
							throw new DataIntegrityViolationException("No se puede realizar la operacion porque no hay stock sufuciente para realizar la transacción.");
						}
					}
				}
			}
			StockSucursal newData = this.getNewStockSucursal(producto, sucursal, movimiento.getCantidad(), BigDecimal.ONE);

			BigDecimal stockFinal = stockInicial.add(movimiento.getCantidad());
			// para saber si es ingreso o egreso
			if (stockInicial.compareTo(stockFinal) < 0) {
				movimiento.setTipo(1);
			}
			if (stockInicial.compareTo(stockFinal) == 0) {
				movimiento.setTipo(0);
			}
			if (stockInicial.compareTo(stockFinal) > 0) {
				movimiento.setTipo(2);
			}
			session.saveOrUpdate(newData);

			movimiento.setValorFinal(stockFinal);
			movimiento.setValorInicial(stockInicial);
			movimiento.setOrigenProductoId(producto.getId());
			movimiento.setIdProducto(producto.getId());
			movimiento.setFactura(factura);
			session.save(movimiento);
			//cargamos en la tabla movimientos de producto
			productoDAO.saveMovimientoProducto(movimiento, factura.getFechaCarga());
		}
		return factura;
	}

	public StockSucursal getNewStockSucursal(Producto producto, Sucursales suc, BigDecimal cantidad, BigDecimal multiplicadorStock) {
		StockSucursal tempStockSucursal = null;
		if (producto.getProductosCompuestos().isEmpty()) {// es un  producto simple
			Set<StockSucursal> stockSucursales = producto.getStockSucursales();
			if (stockSucursales.isEmpty()) {//para el caso de que sea simple y no tenga estock en la sucursal
				StockSucursal ss = new StockSucursal();
				ss.setDetalle("");
				ss.setUbicacion("");
				ss.setProducto(producto);
				ss.setStock(BigDecimal.ZERO.add(cantidad.multiply(multiplicadorStock)));
				ss.setStockMinimo(BigDecimal.ZERO);
				ss.setPuntoReposicion(BigDecimal.ZERO);
				ss.setSucursal(suc);
				ss.setStockAnterior(BigDecimal.ZERO);
				tempStockSucursal = ss;
			} else {
				for (StockSucursal stockSucursal : stockSucursales) {
					if (Objects.equals(stockSucursal.getSucursal().getId(), suc.getId())) { // en definitiva siempre el list de stock tendra un item
						if (Objects.isNull(stockSucursal.getStock())) {
							stockSucursal.setStock(BigDecimal.ZERO);// como es null seteamos a 0 el stock
						}
						stockSucursal.setStockAnterior(stockSucursal.getStock());
						stockSucursal.setStock(stockSucursal.getStock().add(cantidad.multiply(multiplicadorStock)));
						tempStockSucursal = stockSucursal;
					}
				}
				// esto  fue  agregado  porq para stocks nullos  daba error
				if (Objects.isNull(tempStockSucursal)) {
					StockSucursal ss = new StockSucursal();
					ss.setDetalle("");
					ss.setUbicacion("");
					ss.setProducto(producto);
					ss.setStock(BigDecimal.ZERO.add(cantidad.multiply(multiplicadorStock)));
					ss.setStockMinimo(BigDecimal.ZERO);
					ss.setPuntoReposicion(BigDecimal.ZERO);
					ss.setPuntoReposicion(BigDecimal.ZERO);
					ss.setStockAnterior(BigDecimal.ZERO);
					ss.setSucursal(suc);
					tempStockSucursal = ss;
				}
			}
		} else {
			for (ProductosCompuestos productosCompuesto : producto.getProductosCompuestos()) {
				tempStockSucursal = this.getNewStockSucursal(productosCompuesto.getProducto(), suc, cantidad, productosCompuesto.getCantidad().multiply(multiplicadorStock));
			}
		}
		return tempStockSucursal;
	}
}
