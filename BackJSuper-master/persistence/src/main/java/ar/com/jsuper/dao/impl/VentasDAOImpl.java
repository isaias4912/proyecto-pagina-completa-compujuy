package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.EncMovimientosDAO;
import ar.com.jsuper.dao.MovimientosDAO;
import ar.com.jsuper.dao.VentasDAO;
import ar.com.jsuper.dao.async.VentasAsyncDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.CbteDet;
import ar.com.jsuper.domain.Dinero;
import ar.com.jsuper.domain.CbteEncVenta;
import ar.com.jsuper.domain.EventosVentas;
import ar.com.jsuper.domain.IvasAfip;
import ar.com.jsuper.domain.MovimientosCtaCte;
import ar.com.jsuper.domain.PagoCbteVen;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.ProductosCompuestos;
import ar.com.jsuper.domain.StockSucursal;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.domain.TransaccionCaja;
import ar.com.jsuper.domain.TributosAfip;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.domain.utils.*;
import ar.com.jsuper.exceptions.DataInvalidException;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.utils.ConstanstAfipTest;
import ar.com.jsuper.utils.DateUtils;
import ar.com.jsuper.utils.ErrorVentaType;
import ar.com.jsuper.utils.TipoCbte;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VentasDAOImpl extends GenericDAOImpl<CbteEncVenta, Integer> implements VentasDAO {

    private Logger logger = Logger.getLogger(VentasDAOImpl.class);

    @Autowired
    EncMovimientosDAO movimientosDAO;
    @Autowired
    VentasAsyncDAO ventasAsyncDAO;

    @Override
    public CbteEncVenta updateVenta(CbteEncVenta cbteEncVenta) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        session.update(cbteEncVenta);
        return cbteEncVenta;
    }

    @Override
    public CbteEncVenta insertVenta(CbteEncVenta cbteEncVenta, Boolean checkStock) {
        Session session = sessionFactory.getCurrentSession();
        /*###########################Control por APP############################*/
        App app = session.get(App.class, TenantContext.getCurrentTenant());
        cbteEncVenta.setApp(app);
        /*######################################################################*/
        // seteamos el id del usuario segun el token 
        Usuarios usuario = new Usuarios();
        usuario.setId(TenantContext.getCurrentIdUser());
        cbteEncVenta.setUsuario(usuario);
        cbteEncVenta.setEstado(Boolean.TRUE);
        cbteEncVenta.setPagada(Boolean.FALSE);
        cbteEncVenta.setSaldo(BigDecimal.ZERO);
        cbteEncVenta.setFechaCarga(new Date());
        if (Objects.isNull(cbteEncVenta.getFechaVenta())) {
            cbteEncVenta.setFechaVenta(new Date());
        }
        // seteamos el id de tipo de factura para tickets
        session.save(cbteEncVenta);
        Set<CbteDet> cbteDets = cbteEncVenta.getDetalleVentas();
        for (CbteDet cbteDet : cbteDets) {
            cbteDet.setCbteEnc(cbteEncVenta);
            cbteEncVenta.getDetalleVentas().add(cbteDet);
            session.save(cbteDet);
            if (!Objects.isNull(cbteDet.getProducto())) {// puede q sea un servicio yy no tiene stock entonces
                if (!Objects.isNull(cbteDet.getProducto().getId())) {
                    // disminuimos el stock  del producto que se vendio en el detalle
                    Producto producto = session.get(Producto.class, cbteDet.getProducto().getId());
                    StockSucursal ss = this.movimientosDAO.getNewStockSucursal(producto, cbteEncVenta.getSucursal(), cbteDet.getCantidad().negate(), BigDecimal.ONE);
                    // check stock
                    if (checkStock) {
                        if (ss.getStock().compareTo(BigDecimal.ZERO) < 0) {
                            // quiere decir que no fue suficiente el stock porque queda en menos de 0
                            String currentStock = ss.getStock().add(cbteDet.getCantidad()).toString();
                            throw new DataInvalidException(String.format("Stock insuficiente para el producto %s - stock actual -> <b>%s</b>", producto.getNombreFinal(), currentStock), ErrorVentaType.ERROR_STOCK);
                        }
                    }
                    this.ventasAsyncDAO.saveMovimientoProducto(cbteEncVenta, ss);
                    session.saveOrUpdate(ss);
                }
            }
        }
        Set<PagoCbteVen> pagoVentas = cbteEncVenta.getPagosCbte();
        BigDecimal saldo = BigDecimal.ZERO;
        for (PagoCbteVen pagoVenta : pagoVentas) {
            if (pagoVenta.getFormaPago().getId() == 4) {// controlamos para cta corrientes
                MovimientosCtaCte movimientoCtaCte = new MovimientosCtaCte();
                movimientoCtaCte.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
                movimientoCtaCte.setMonto(pagoVenta.getMonto());
                movimientoCtaCte.setPagado(Boolean.FALSE);
                movimientoCtaCte.setFecha(new Date());
                movimientoCtaCte.setTipo(1);
                if (Objects.isNull(pagoVenta.getInteres())) {
                    movimientoCtaCte.setInteres(BigDecimal.ZERO);
                    movimientoCtaCte.setSaldo(pagoVenta.getMonto());
                    movimientoCtaCte.setMontoTotal(pagoVenta.getMonto());
                } else {
                    movimientoCtaCte.setInteres(pagoVenta.getInteres());
                    if (pagoVenta.getInteres().compareTo(BigDecimal.ZERO) == 0) {
                        movimientoCtaCte.setMontoTotal(pagoVenta.getMonto());
                        movimientoCtaCte.setSaldo(pagoVenta.getMonto());
                    } else {
                        movimientoCtaCte.setMontoTotal(pagoVenta.getMontoConInteres());
                        movimientoCtaCte.setSaldo(pagoVenta.getMontoConInteres());
                    }
                }
                saldo = saldo.add(movimientoCtaCte.getSaldo());
                movimientoCtaCte.setCuentaCorriente(cbteEncVenta.getCliente().getCuentaCorriente());
                pagoVenta.setCbteEnc(cbteEncVenta);
                cbteEncVenta.getPagosCbte().add(pagoVenta);
                session.save(pagoVenta);
                movimientoCtaCte.setPagoVentas(pagoVenta);
                session.save(movimientoCtaCte);
            } else {
                pagoVenta.setCbteEnc(cbteEncVenta);
                cbteEncVenta.getPagosCbte().add(pagoVenta);
                session.save(pagoVenta);
            }
        }
        // verificamos si hay saldo quiere decir que la factura no esta pagada del todo
        if (saldo.compareTo(BigDecimal.ZERO) > 0) {
            cbteEncVenta.setSaldo(saldo);
            cbteEncVenta.setPagada(Boolean.FALSE);
        } else {
            cbteEncVenta.setPagada(Boolean.TRUE);
        }
        Set<EventosVentas> eventosVenta = cbteEncVenta.getEventosVenta();
        if (Objects.nonNull(eventosVenta)) {
            for (EventosVentas eventoVenta : eventosVenta) {
                eventoVenta.setCbteVenEnc(cbteEncVenta);
                cbteEncVenta.getEventosVenta().add(eventoVenta);
                session.save(eventoVenta);
            }
        }
        // insertamos ivas si es que lo tiene para fe
        Set<IvasAfip> ivas = cbteEncVenta.getIvas();
        if (Objects.nonNull(ivas)) {
            for (IvasAfip iva : ivas) {
                iva.setCbte(cbteEncVenta.getId());
                iva.setTipoCbte(1); // el 1 es del tipo cbte de venta
                cbteEncVenta.getIvas().add(iva);
                session.save(iva);
            }
        }
        // insertamos tributos si es que lo tiene para fe
        Set<TributosAfip> tributos = cbteEncVenta.getTributos();
        if (Objects.nonNull(tributos)) {
            for (TributosAfip tributo : tributos) {
                tributo.setCbte(cbteEncVenta.getId());
                tributo.setTipoCbte(1); // el 1 es del tipo cbte de venta
                cbteEncVenta.getTributos().add(tributo);
                session.save(tributo);
            }
        }
        // controlamos el cliente id
        if (Objects.nonNull(cbteEncVenta.getCliente())) {
            if (Objects.nonNull(cbteEncVenta.getCliente().getId())) {
                if (cbteEncVenta.getCliente().getId() > 0) {
                    cbteEncVenta.setIdCliente(cbteEncVenta.getCliente().getId());
                }
            }
        }
        return cbteEncVenta;
    }

//    private StockSucursal getNewStockSucursal(Producto producto, Sucursales suc, BigDecimal cantidadVendida, BigDecimal multiplicadorStock) {
//        StockSucursal tempStockSucursal = null;
//        if (producto.getProductosCompuestos().isEmpty()) {// es un  producto simple
//            Set<StockSucursal> stockSucursales = producto.getStockSucursales();
//            if (stockSucursales.isEmpty()) {//para el caso de que sea simple y no tenga estock en la sucursal
//                StockSucursal ss = new StockSucursal();
//                ss.setDetalle("");
//                ss.setUbicacion("");
//                ss.setProducto(producto);
//                ss.setStock(BigDecimal.ZERO.subtract(cantidadVendida.multiply(multiplicadorStock)));
//                ss.setStockMinimo(BigDecimal.ZERO);
//                ss.setPuntoReposicion(BigDecimal.ZERO);
//                ss.setSucursal(suc);
//                ss.setStockAnterior(BigDecimal.ZERO);
//                tempStockSucursal = ss;
//            } else {
//                for (StockSucursal stockSucursal : stockSucursales) {
//                    if (Objects.equals(stockSucursal.getSucursal().getId(), suc.getId())) { // en definitiva siempre el llist de stock tendra un item
//                        if (Objects.isNull(stockSucursal.getStock())) {
//                            stockSucursal.setStock(BigDecimal.ZERO);// como es null seteamos a 0 el stock
//                        }
//                        stockSucursal.setStockAnterior(stockSucursal.getStock());
//                        stockSucursal.setStock(stockSucursal.getStock().subtract(cantidadVendida.multiply(multiplicadorStock)));
//                        tempStockSucursal = stockSucursal;
//                    }
//                }
//                // esto  fue  agregado  porq para estocks nullos  daba error
//                if (Objects.isNull(tempStockSucursal)) {
//                    StockSucursal ss = new StockSucursal();
//                    ss.setDetalle("");
//                    ss.setUbicacion("");
//                    ss.setProducto(producto);
//                    ss.setStock(BigDecimal.ZERO.subtract(cantidadVendida.multiply(multiplicadorStock)));
//                    ss.setStockMinimo(BigDecimal.ZERO);
//                    ss.setPuntoReposicion(BigDecimal.ZERO);
//                    ss.setPuntoReposicion(BigDecimal.ZERO);
//                    ss.setStockAnterior(BigDecimal.ZERO);
//                    tempStockSucursal = ss;
//                }
//            }
//        } else {
//            for (ProductosCompuestos productosCompuesto : producto.getProductosCompuestos()) {
//                tempStockSucursal = this.getNewStockSucursal(productosCompuesto.getProducto(), suc, cantidadVendida, productosCompuesto.getCantidad().multiply(multiplicadorStock));
//            }
//        }
//        return tempStockSucursal;
//    }

    @Override
    public Set<CbteEncVenta> getAll() throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaCbteVenEnc = session.createCriteria(CbteEncVenta.class, "cbteVenEnc");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        Criterion c2 = Restrictions.eq("tipo", TipoCbte.VENTA);
        criteriaCbteVenEnc.add(c1);
        criteriaCbteVenEnc.add(c2);
        /*######################################################################*/
        criteriaCbteVenEnc.setFetchMode("detalleVentas", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("pagoCbtes", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("detalleVentas.producto", FetchMode.JOIN);
        Set<CbteEncVenta> resultado = new HashSet<>(criteriaCbteVenEnc.list());
        return resultado;
    }

    @Override
    public List<CbteDet> getDetalleVentaByProducto(Producto producto) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleVentas = session.createCriteria(CbteDet.class, "detalle");
        /*###########################Control por APP############################*/
//		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
//		criteriaDetalleVentas.add(c1);
        /*######################################################################*/
        criteriaDetalleVentas.setFetchMode("cbteVenEnc", FetchMode.JOIN);
        criteriaDetalleVentas.add(Restrictions.eq("producto", producto));
        List<CbteDet> resultado = criteriaDetalleVentas.list();
        return resultado;
    }

    /**
     * Devuelve un encabezado ventas segun el id Devue
     *
     * @param id
     * @return
     * @throws BussinessException
     */
    @Override
    public CbteEncVenta get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaCbteVenEnc = session.createCriteria(CbteEncVenta.class, "cbteVenEnc");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaCbteVenEnc.add(c1);
        /*######################################################################*/
        Criterion c3 = Restrictions.eq("id", id);
        criteriaCbteVenEnc.add(c3);
        criteriaCbteVenEnc.setFetchMode("detalleVentas", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("pagoVentas", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("eventosVenta", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("detalleVentas.producto", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("detalleVentas.producto", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("ivas", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("tributos", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("sucursal", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("usuario", FetchMode.JOIN);
        return (CbteEncVenta) criteriaCbteVenEnc.uniqueResult();
    }

    /**
     * Devuelve un encabezado ventas segun el id Devue
     *
     * @return
     */
    @Override
    public CbteEncVenta getByUUID(String uuid) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaCbteVenEnc = session.createCriteria(CbteEncVenta.class, "cbteVenEnc");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaCbteVenEnc.add(c1);
        /*######################################################################*/
        Criterion c2 = Restrictions.eq("uuid", uuid);
        criteriaCbteVenEnc.add(c2);
        criteriaCbteVenEnc.setFetchMode("detalleVentas", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("pagoCbtes", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("detalleVentas.producto", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("detalleVentas.producto", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("ivas", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("tributos", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("sucursal", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("usuario", FetchMode.JOIN);
        criteriaCbteVenEnc.setFetchMode("usuario", FetchMode.JOIN);
        return (CbteEncVenta) criteriaCbteVenEnc.uniqueResult();
    }

    /**
     * verifica si existe un enc venta segun su uuid
     *
     * @return
     */
    @Override
    public Boolean existByUUID(String uuid) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaCbteVenEnc = session.createCriteria(CbteEncVenta.class, "cbteVenEnc");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaCbteVenEnc.add(c1);
        /*######################################################################*/
        Criterion c2 = Restrictions.eq("uuid", uuid);
        criteriaCbteVenEnc.add(c2);
        return Objects.nonNull(criteriaCbteVenEnc.uniqueResult());
    }

    @Override
    public Long getTotalVentasToday() throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaVentas = session.createCriteria(CbteEncVenta.class, "cbteVenEnc");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaVentas.add(c1);
        /*######################################################################*/
        // esto sirve para mysql nada mas
        criteriaVentas.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') = CURDATE()"));
        criteriaVentas.setProjection(Projections.rowCount());
        return (Long) criteriaVentas.uniqueResult();
    }

    /**
     * Devuelve la cantidad en pesos de las ventas
     *
     * @return
     * @throws BussinessException
     */
    @Override
    public DataSummaryVentas getSummaryVentasFromFilter(FilterCbte filterCbte) {
        Session session = sessionFactory.getCurrentSession();
        BigDecimal value;
        String sql = "";
        String sqlTotal = "";
        String sqlEfectivo = "";
        String sqlCredito = "";
        String sqlDebito = "";
        String sqlCtaCte = "";
        Query queryTotal = null;
        Query queryEfectivo = null;
        Query queryCredito = null;
        Query queryDebito = null;
        Query queryCtaCte = null;
        String whereMinimo = null;
        String whereMaximo = null;
        String whereDateInicial = null;
        String whereDateFinal = null;
        String whereDate = null;
        String whereCaja = null;
        String whereUsuario = null;
        if (filterCbte.getTotalMinimo().compareTo(BigDecimal.ZERO) > 0) {
            whereMinimo = " AND e.total >= :minimo";
        }
        // filtro si es menor a  cierto monto
        if (filterCbte.getTotalMaximo().compareTo(BigDecimal.ZERO) > 0) {
            whereMaximo = " AND e.total <= :maximo";
        }
        // filtro para las fechas
        if (!filterCbte.getFechaInicial().trim().equals("") && filterCbte.getFechaFinal().trim().equals("")) {
            whereDateInicial = " AND DATE_FORMAT(e.fecha_carga, '%Y-%m-%d') >= STR_TO_DATE(:dateInicial, '%d-%m-%Y')";
        }
        if (!filterCbte.getFechaFinal().trim().equals("") && filterCbte.getFechaInicial().trim().equals("")) {
            whereDateFinal = " AND DATE_FORMAT(e.fecha_carga, '%Y-%m-%d') <= STR_TO_DATE(:dateFinal, '%d-%m-%Y')";
        }
        if (!filterCbte.getFechaInicial().trim().equals("") && !filterCbte.getFechaFinal().trim().equals("")) {
            whereDate = " AND DATE_FORMAT(e.fecha_carga, '%Y-%m-%d') BETWEEN STR_TO_DATE(:dateInicial, '%d-%m-%Y') AND  STR_TO_DATE(:dateFinal, '%d-%m-%Y')";
        }
        // filter para la caja
        if (!Objects.isNull(filterCbte.getCaja())) {
            whereCaja = " AND c.id = :caja";
        }
        // filter para el usuario
        if (!Objects.isNull(filterCbte.getUsuario())) {
            whereUsuario = " AND u.id = :usuario";
        }

        sql = "select distinct p.id, p.monto as total from cbte_enc e "
                + "left outer join app a on e.app_id=a.id "
                + "left outer join pago_cbte_ven p on p.cbte_ven_enc_id= e.id "
                + "left outer join transaccion_caja t on e.transaccion_caja_id=t.id "
                + "left outer join caja c on t.caja_id=c.id "
                + "left outer join usuarios u on e.usuarios_id=u.id where a.id= :app AND e.estado=1 ";

        if (!Objects.isNull(whereMinimo)) {
            sql = sql + whereMinimo;
        }
        if (!Objects.isNull(whereMaximo)) {
            sql = sql + whereMaximo;
        }
        if (!Objects.isNull(whereDateInicial)) {
            sql = sql + whereDateInicial;
        }
        if (!Objects.isNull(whereDateFinal)) {
            sql = sql + whereDateFinal;
        }
        if (!Objects.isNull(whereDate)) {
            sql = sql + whereDate;
        }
        if (!Objects.isNull(whereCaja)) {
            sql = sql + whereCaja;
        }
        if (!Objects.isNull(whereUsuario)) {
            sql = sql + whereUsuario;
        }
        sqlTotal = "select sum(t.total) from ( " + sql + " ) t";
        queryTotal = session.createSQLQuery(sqlTotal);
        sqlEfectivo = sql + " AND p.formapagos_id=1";
        sqlEfectivo = "select sum(t.total) from ( " + sqlEfectivo + " ) t";
        sqlCredito = sql + " AND p.formapagos_id=2";
        sqlCredito = "select sum(t.total) from ( " + sqlCredito + " ) t";
        sqlDebito = sql + " AND p.formapagos_id=3";
        sqlDebito = "select sum(t.total) from ( " + sqlDebito + " ) t";
        sqlCtaCte = sql + " AND p.formapagos_id=4";
        sqlCtaCte = "select sum(t.total) from ( " + sqlCtaCte + " ) t";
//        logger.info("SQL EFECTIVO:" + sqlEfectivo);
        queryEfectivo = session.createSQLQuery(sqlEfectivo);
//        logger.info("SQL CREDITO:" + sqlCredito);
        queryCredito = session.createSQLQuery(sqlCredito);
//        logger.info("SQL DEBITO:" + sqlDebito);
        queryDebito = session.createSQLQuery(sqlDebito);
//        logger.info("SQL CTA CTE:" + sqlCtaCte);
        queryCtaCte = session.createSQLQuery(sqlCtaCte);
        queryTotal.setParameter("app", TenantContext.getCurrentTenant());
        queryEfectivo.setParameter("app", TenantContext.getCurrentTenant());
        queryCredito.setParameter("app", TenantContext.getCurrentTenant());
        queryDebito.setParameter("app", TenantContext.getCurrentTenant());
        queryCtaCte.setParameter("app", TenantContext.getCurrentTenant());
        if (!Objects.isNull(whereMinimo)) {
            queryTotal.setParameter("minimo", filterCbte.getTotalMinimo());
            queryEfectivo.setParameter("minimo", filterCbte.getTotalMinimo());
            queryCredito.setParameter("minimo", filterCbte.getTotalMinimo());
            queryDebito.setParameter("minimo", filterCbte.getTotalMinimo());
            queryCtaCte.setParameter("minimo", filterCbte.getTotalMinimo());
        }
        if (!Objects.isNull(whereMaximo)) {
            queryTotal.setParameter("maximo", filterCbte.getTotalMaximo());
            queryEfectivo.setParameter("maximo", filterCbte.getTotalMaximo());
            queryCredito.setParameter("maximo", filterCbte.getTotalMaximo());
            queryDebito.setParameter("maximo", filterCbte.getTotalMaximo());
            queryCtaCte.setParameter("maximo", filterCbte.getTotalMaximo());
        }
        if (!Objects.isNull(whereDateInicial)) {
            queryTotal.setParameter("dateInicial", filterCbte.getFechaInicial().trim());
            queryEfectivo.setParameter("dateInicial", filterCbte.getFechaInicial().trim());
            queryCredito.setParameter("dateInicial", filterCbte.getFechaInicial().trim());
            queryDebito.setParameter("dateInicial", filterCbte.getFechaInicial().trim());
            queryCtaCte.setParameter("dateInicial", filterCbte.getFechaInicial().trim());
        }
        if (!Objects.isNull(whereDateFinal)) {
            queryTotal.setParameter("dateFinal", filterCbte.getFechaFinal().trim());
            queryEfectivo.setParameter("dateFinal", filterCbte.getFechaFinal().trim());
            queryCredito.setParameter("dateFinal", filterCbte.getFechaFinal().trim());
            queryDebito.setParameter("dateFinal", filterCbte.getFechaFinal().trim());
            queryCtaCte.setParameter("dateFinal", filterCbte.getFechaFinal().trim());
        }
        if (!Objects.isNull(whereDate)) {
            queryTotal.setParameter("dateInicial", filterCbte.getFechaInicial().trim());
            queryTotal.setParameter("dateFinal", filterCbte.getFechaFinal().trim());
            queryEfectivo.setParameter("dateInicial", filterCbte.getFechaInicial().trim());
            queryEfectivo.setParameter("dateFinal", filterCbte.getFechaFinal().trim());
            queryCredito.setParameter("dateInicial", filterCbte.getFechaInicial().trim());
            queryCredito.setParameter("dateFinal", filterCbte.getFechaFinal().trim());
            queryDebito.setParameter("dateInicial", filterCbte.getFechaInicial().trim());
            queryDebito.setParameter("dateFinal", filterCbte.getFechaFinal().trim());
            queryCtaCte.setParameter("dateInicial", filterCbte.getFechaInicial().trim());
            queryCtaCte.setParameter("dateFinal", filterCbte.getFechaFinal().trim());
        }
        if (!Objects.isNull(whereCaja)) {
            queryTotal.setParameter("caja", filterCbte.getCaja());
            queryEfectivo.setParameter("caja", filterCbte.getCaja());
            queryCredito.setParameter("caja", filterCbte.getCaja());
            queryDebito.setParameter("caja", filterCbte.getCaja());
            queryCtaCte.setParameter("caja", filterCbte.getCaja());
        }
        if (!Objects.isNull(whereUsuario)) {
            queryTotal.setParameter("usuario", filterCbte.getUsuario());
            queryEfectivo.setParameter("usuario", filterCbte.getUsuario());
            queryCredito.setParameter("usuario", filterCbte.getUsuario());
            queryDebito.setParameter("usuario", filterCbte.getUsuario());
            queryCtaCte.setParameter("usuario", filterCbte.getUsuario());
        }
        DataSummaryVentas data = new DataSummaryVentas();
        //total 
        value = (BigDecimal) queryTotal.uniqueResult();
        data.setTotal(value == null ? new BigDecimal("0.0") : value);
        // efectivo
        value = (BigDecimal) queryEfectivo.uniqueResult();
        data.setTotalEfectivo(value == null ? new BigDecimal("0.0") : value);
        // credito
        value = (BigDecimal) queryCredito.uniqueResult();
        data.setTotalTarjetaCredito(value == null ? new BigDecimal("0.0") : value);
        // debito
        value = (BigDecimal) queryDebito.uniqueResult();
        data.setTotalTarjetaDebito(value == null ? new BigDecimal("0.0") : value);
        // cta cte
        value = (BigDecimal) queryCtaCte.uniqueResult();
        data.setTotalCtaCte(value == null ? new BigDecimal("0.0") : value);
        return data;
    }

    /**
     * Ventas ddel dia de ayer
     *
     * @return
     * @throws BussinessException
     */
    @Override
    public Long getTotalVentasYesterday() throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaVentas = session.createCriteria(CbteEncVenta.class, "cbteVenEnc");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaVentas.add(c1);
        /*######################################################################*/
        // esto sirve para mysql nada mas
        criteriaVentas.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') = SUBDATE(CURDATE(),1)"));
        criteriaVentas.setProjection(Projections.rowCount());
        return (Long) criteriaVentas.uniqueResult();
    }

    /**
     * Devuelve la cantidad en dinero de una transaccion, las que estan en activas
     *
     * @return
     * @throws BussinessException
     */
    @Override
    public Dinero getTotalVentasFromTransaccion(Integer idTransaccion) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        String sql = "select sum(total) as total from cbte_ven_enc where transaccion_caja_id= :idT and estado=1";
        Query query = session.createSQLQuery(sql)
                .setParameter("idT", idTransaccion);
        BigDecimal ingreso = (BigDecimal) query.uniqueResult();
        Dinero dinero = new Dinero();
        dinero.setIngreso(Objects.isNull(ingreso) ? BigDecimal.ZERO : ingreso);
        sql = "select sum(total) as total from cbte_ven_enc where transaccion_caja_id= :idT and estado=0";
        query = session.createSQLQuery(sql)
                .setParameter("idT", idTransaccion);
        BigDecimal egreso = (BigDecimal) query.uniqueResult();
        dinero.setEgreso(Objects.isNull(egreso) ? BigDecimal.ZERO : egreso);
        return dinero;
    }

    /**
     * Devuelve un resumen de las ventas realizaas para una transaccion de caja activas
     *
     * @return
     * @throws BussinessException
     */
    @Override
    public DataSummaryVentas getSummaryVentasFromTransaccion(Integer idTransaccion) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        BigDecimal value;
        String sql = "select sum(total) as total from cbte_ven_enc where transaccion_caja_id= :idT and estado=1";
        Query query = session.createSQLQuery(sql)
                .setParameter("idT", idTransaccion);
        value = (BigDecimal) query.uniqueResult();
        DataSummaryVentas summaryVentas = new DataSummaryVentas();
        summaryVentas.setTotalActivas(Objects.isNull(value) ? BigDecimal.ZERO : value);

        sql = "select sum(total) as total from cbte_ven_enc where transaccion_caja_id= :idT and estado=0";
        query = session.createSQLQuery(sql)
                .setParameter("idT", idTransaccion);
        value = (BigDecimal) query.uniqueResult();
        summaryVentas.setTotalAnuladas(Objects.isNull(value) ? BigDecimal.ZERO : value);

        sql = "select sum(pago_cbte_ven.monto) as total from pago_cbte_ven "
                + "inner join cbte_ven_enc on pago_cbte_ven.cbte_ven_enc_id= cbte_ven_enc.id "
                + "where cbte_ven_enc.transaccion_caja_id= :idT and cbte_ven_enc.estado=1 "
                + "and pago_cbte_ven.formapagos_id=2";
        query = session.createSQLQuery(sql)
                .setParameter("idT", idTransaccion);
        value = (BigDecimal) query.uniqueResult();
        summaryVentas.setTotalTarjetaCredito(Objects.isNull(value) ? BigDecimal.ZERO : value);

        sql = "select sum(pago_cbte_ven.monto) as total from pago_cbte_ven "
                + "inner join cbte_ven_enc on pago_cbte_ven.cbte_ven_enc_id= cbte_ven_enc.id "
                + "where cbte_ven_enc.transaccion_caja_id= :idT and cbte_ven_enc.estado=1 "
                + "and pago_cbte_ven.formapagos_id=1";
        query = session.createSQLQuery(sql)
                .setParameter("idT", idTransaccion);
        value = (BigDecimal) query.uniqueResult();
        summaryVentas.setTotalEfectivo(Objects.isNull(value) ? BigDecimal.ZERO : value);

        sql = "select sum(pago_cbte_ven.monto) as total from pago_cbte_ven "
                + "inner join cbte_ven_enc on pago_cbte_ven.cbte_ven_enc_id= cbte_ven_enc.id "
                + "where cbte_ven_enc.transaccion_caja_id= :idT and cbte_ven_enc.estado=1 "
                + "and pago_cbte_ven.formapagos_id=3";
        query = session.createSQLQuery(sql)
                .setParameter("idT", idTransaccion);
        value = (BigDecimal) query.uniqueResult();
        summaryVentas.setTotalTarjetaDebito(Objects.isNull(value) ? BigDecimal.ZERO : value);

        // cuenta corriente
        sql = "select sum(pago_cbte_ven.monto) as total from pago_cbte_ven "
                + "inner join cbte_ven_enc on pago_cbte_ven.cbte_ven_enc_id= cbte_ven_enc.id "
                + "where cbte_ven_enc.transaccion_caja_id= :idT and cbte_ven_enc.estado=1 "
                + "and pago_cbte_ven.formapagos_id=4";
        query = session.createSQLQuery(sql)
                .setParameter("idT", idTransaccion);
        value = (BigDecimal) query.uniqueResult();
        summaryVentas.setTotalCtaCte(Objects.isNull(value) ? BigDecimal.ZERO : value);

        summaryVentas.setTotalTarjeta(summaryVentas.getTotalTarjetaCredito().add(summaryVentas.getTotalTarjetaDebito()));

        return summaryVentas;
    }

    /**
     * @param monthLater
     * @return cantidad de ventas el anterior mes segun monthLater
     * @throws BussinessException
     */
    @Override
    public Long getTotalVentasByMonthLater(Integer monthLater) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaVentas = session.createCriteria(CbteEncVenta.class, "cbteVenEnc");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaVentas.add(c1);
        /*######################################################################*/
        // esto sirve para mysql nada mas
        criteriaVentas.add(Restrictions.sqlRestriction("MONTH({alias}.fecha_carga)= MONTH(CURRENT_DATE - INTERVAL ? MONTH) and YEAR({alias}.fecha_carga)= YEAR(CURRENT_DATE);", monthLater, StandardBasicTypes.INTEGER));
        criteriaVentas.setProjection(Projections.rowCount());
        return (Long) criteriaVentas.uniqueResult();
    }

    /**
     * @param monthLater
     * @return cantidad de ventas en pesos el anterior mes segun monthLater
     * @throws BussinessException
     */
    @Override
    public BigDecimal getTotalPesosVentasByMonthLater(Integer monthLater) {
        Session session = sessionFactory.getCurrentSession();
        BigDecimal value;
        String sql = "";
        Query query = null;
        sql = "select sum(e.total) as total from cbte_enc e "
                + "left outer join app a on e.app_id=a.id where a.id= :app AND e.tipo=1 "
                + "AND MONTH(e.fecha_carga)= MONTH(CURRENT_DATE - INTERVAL :mes MONTH) and YEAR(e.fecha_carga)= YEAR(CURRENT_DATE) ";
        query = session.createSQLQuery(sql);
        query.setParameter("app", TenantContext.getCurrentTenant());
        query.setParameter("mes", monthLater);
        //total 
        value = (BigDecimal) query.uniqueResult();
        return value;
    }

    /**
     * @param dayLater
     * @return cantidad de ventas del dia en pesos anterior mes segun dayLater
     */
    @Override
    public BigDecimal getTotalPesosVentasByDayLater(Integer dayLater) {
        Session session = sessionFactory.getCurrentSession();
        BigDecimal value;
        String sql = "";
        Query query = null;
        sql = "select sum(e.total) as total from cbte_enc e "
                + "left outer join app a on e.app_id=a.id where a.id= :app AND e.tipo=1 "
                + "AND DAY(e.fecha_carga)= DAY(CURRENT_DATE - INTERVAL :day DAY) and YEAR(e.fecha_carga)= YEAR(CURRENT_DATE) and MONTH(e.fecha_carga)= MONTH(CURRENT_DATE) ";
        query = session.createSQLQuery(sql);
        query.setParameter("app", TenantContext.getCurrentTenant());
        query.setParameter("day", dayLater);
        //total 
        value = (BigDecimal) query.uniqueResult();
        return value;
    }

    /**
     * @return Devuelve los pesos por datetime
     */
    @Override
    public BigDecimal getTotalPesosVentasByDateTime(String dateTimeInicial, String dateTimeFinal) {
        Session session = sessionFactory.getCurrentSession();
        BigDecimal value;
        String sql = "";
        Query query = null;
        sql = "select sum(e.total) as total from cbte_enc e "
                + "left outer join app a on e.app_id=a.id where a.id= :app AND e.tipo=1 "
                + "AND e.fecha_carga BETWEEN STR_TO_DATE(:dateTimeInicial, '%d-%m-%Y %H:%i') AND  STR_TO_DATE(:dateTimeFinal, '%d-%m-%Y %H:%i') ";

        query = session.createSQLQuery(sql);
        query.setParameter("app", TenantContext.getCurrentTenant());
        query.setParameter("dateTimeInicial", dateTimeInicial);
        query.setParameter("dateTimeFinal", dateTimeFinal);
        //total 
        value = (BigDecimal) query.uniqueResult();
        return value;
    }

    /**
     * @param dayLater
     * @return cantidad de ventas el anterior mes segun dayLater
     * @throws BussinessException
     */
    @Override
    public Long getTotalVentasByDayLater(Integer dayLater) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaVentas = session.createCriteria(CbteEncVenta.class, "cbteVenEnc");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaVentas.add(c1);
        /*######################################################################*/
        // esto sirve para mysql nada mas
        criteriaVentas.add(Restrictions.sqlRestriction("DAY({alias}.fecha_carga)= DAY(CURRENT_DATE - INTERVAL ? DAY) and YEAR({alias}.fecha_carga)= YEAR(CURRENT_DATE) and MONTH({alias}.fecha_carga)= MONTH(CURRENT_DATE)", dayLater, StandardBasicTypes.INTEGER));
        criteriaVentas.setProjection(Projections.rowCount());
        return (Long) criteriaVentas.uniqueResult();
    }

    /**
     * @throws BussinessException
     */
    @Override
    public Pagination<CbteEncVenta> getVentasByPage(FilterCbte filterCbte, int page, int pageSize, String fieldOrder, boolean reverse) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaVentas = session.createCriteria(CbteEncVenta.class, "cbteVenEnc");
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaVentas.setProjection(idCountProjection);
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaVentas.add(c1);
        /*######################################################################*/
//        Criterion c2 = Restrictions.eq("tipo", TipoCbte.VENTA);
//        criteriaVentas.add(c2);

        criteriaVentas.setFetchMode("pagoVentas", FetchMode.JOIN);
        criteriaVentas.setFetchMode("pagoVentas.formaPago", FetchMode.JOIN);
        criteriaVentas.setFetchMode("transaccionCaja", FetchMode.JOIN);
        criteriaVentas.setFetchMode("transaccionCaja.caja", FetchMode.JOIN);
        Criteria criteriaTransaccion = criteriaVentas.createCriteria("cbteVenEnc.transaccionCaja", "transaccionCaja", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaCaja = criteriaTransaccion.createCriteria("transaccionCaja.caja", "caja", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaUsuario = criteriaTransaccion.createCriteria("cbteVenEnc.usuario", "usuario", JoinType.LEFT_OUTER_JOIN);

        // filtro si es mayor a  cierto monto
        if (filterCbte.getTotalMinimo().compareTo(BigDecimal.ZERO) > 0) {
            criteriaVentas.add(Restrictions.ge("total", filterCbte.getTotalMinimo()));
        }
        // filtro si es menor a  cierto monto
        if (filterCbte.getTotalMaximo().compareTo(BigDecimal.ZERO) > 0) {
            criteriaVentas.add(Restrictions.le("total", filterCbte.getTotalMaximo()));
        }
        // filtro para las fechas
        if (DateUtils.isValid(filterCbte.getFechaInicial()) && !DateUtils.isValid(filterCbte.getFechaFinal())) {
            criteriaVentas.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') >= STR_TO_DATE(?, '%d-%m-%Y')", filterCbte.getFechaInicial().trim(), StandardBasicTypes.STRING));
        }
        if (DateUtils.isValid(filterCbte.getFechaFinal()) && !DateUtils.isValid(filterCbte.getFechaInicial())) {
            criteriaVentas.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') <= STR_TO_DATE(?, '%d-%m-%Y')", filterCbte.getFechaFinal().trim(), StandardBasicTypes.STRING));
        }
        if (DateUtils.isValid(filterCbte.getFechaInicial()) && DateUtils.isValid(filterCbte.getFechaFinal())) {
            org.hibernate.type.Type[] tipos = {StandardBasicTypes.STRING, StandardBasicTypes.STRING};
            String[] values = {filterCbte.getFechaInicial().trim(), filterCbte.getFechaFinal().trim()};
            criteriaVentas.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') BETWEEN STR_TO_DATE(?, '%d-%m-%Y') AND  STR_TO_DATE(?, '%d-%m-%Y')", values, tipos));
        }
        // restriccion para activos o no activos
        if (filterCbte.getPagada() != null && !filterCbte.getPagada().equals("") && filterCbte.getPagada() != 2) {
            Criterion cPagada = Restrictions.eq("pagada", (filterCbte.getPagada() == 1));
            criteriaVentas.add(cPagada);
        }
        // filter para la caja
        if (Objects.nonNull(filterCbte.getCaja()) && Objects.nonNull(filterCbte.getCaja().getId())) {
            criteriaCaja.add(Restrictions.eq("id", filterCbte.getCaja().getId()));
        }
        // filter para el usuario
        if (Objects.nonNull(filterCbte.getUsuario()) && Objects.nonNull(filterCbte.getUsuario().getId())) {
            criteriaUsuario.add(Restrictions.eq("id", filterCbte.getUsuario().getId()));
        }
        // filter para sucursal
        if (Objects.nonNull(filterCbte.getSucursales())) {
            this.addRestrictionSucursalesObj(filterCbte.getSucursales(), criteriaVentas, session);
        }
        //filter de ventas por productos
        if (Objects.nonNull(filterCbte.getProductos())) {
            if (filterCbte.getProductos().size() > 0) {
                Disjunction disjunctionProducto = Restrictions.disjunction();
                Criteria criteriaDetalle = criteriaVentas.createCriteria("cbteVenEnc.detalleVentas", "detalleVentas", JoinType.LEFT_OUTER_JOIN);
                Criteria criteriaDetalleProducto = criteriaDetalle.createCriteria("detalleVentas.producto", "producto", JoinType.LEFT_OUTER_JOIN);
                for (SimpleObject producto : filterCbte.getProductos()) {
                    if (Objects.nonNull(producto.getId())) {
                        disjunctionProducto.add(Restrictions.eq("producto.id", producto.getId()));
                    }
                }
                criteriaDetalleProducto.add(disjunctionProducto);
            }
        }
        // setup criteria, joins etc here
        Integer totalResultCount = ((Long) criteriaVentas.uniqueResult()).intValue();
        if (reverse) {
            criteriaVentas.addOrder(Order.asc(fieldOrder.trim()));
        } else {
            criteriaVentas.addOrder(Order.desc(fieldOrder.trim()));
        }
        criteriaVentas.setProjection(Projections.distinct(Projections.property("id")));
        if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
            criteriaVentas.setFirstResult((page - 1) * pageSize);
            criteriaVentas.setMaxResults(pageSize);
        }
        List uniqueSubList = criteriaVentas.list();
        criteriaVentas.setProjection(null);
        criteriaVentas.setFirstResult(0);
        criteriaVentas.setMaxResults(Integer.MAX_VALUE);
        criteriaVentas.add(Restrictions.in("id", uniqueSubList));
        criteriaVentas.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<CbteEncVenta> lista = new ArrayList<>();
        if (totalResultCount > 0) {
            lista = new ArrayList<>(criteriaVentas.list());
        }
        Pagination<CbteEncVenta> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    /**
     * @param idTransaccionCaja id de la transaccion
     * @return List<CbteVenEnc>
     * @throws BussinessException
     */
    @Override
    public List<CbteEncVenta> getAllVentasFromTransaccionCaja(Integer idTransaccionCaja) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        TransaccionCaja transaccionCaja = session.load(TransaccionCaja.class, idTransaccionCaja);
        Criteria criteriaVentas = session.createCriteria(CbteEncVenta.class, "cbteVenEnc");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaVentas.add(c1);
        /*######################################################################*/
        criteriaVentas.setFetchMode("detalleVentas", FetchMode.JOIN);
        criteriaVentas.setFetchMode("pagoCbtes", FetchMode.JOIN);
        criteriaVentas.setFetchMode("detalleVentas.producto", FetchMode.JOIN);
        criteriaVentas.setFetchMode("usuario", FetchMode.JOIN);

        // filtro si es mayor a  cierto monto
        if (!Objects.isNull(transaccionCaja)) {
            if (!Objects.isNull(transaccionCaja.getId())) {
                criteriaVentas.add(Restrictions.ge("transaccionCaja", transaccionCaja));
            }
        }
        // setup criteria, joins etc here
        criteriaVentas.addOrder(Order.desc("id"));
        criteriaVentas.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteriaVentas.list();
    }

    /**
     * Devuelve los productos mas vendidos
     */
    @Override
    public List<ReportesVentaProducto> getProductosMasVendidos(FilterGeneric filterGeneric) {
        Session session = sessionFactory.getCurrentSession();
        List<ReportesVentaProducto> list = new ArrayList<>();
        String sql = "select producto.id, producto.nombre_final, t.cantidad from producto, "
                + "(SELECT sum(cantidad) as cantidad, de.productos_id as depid, de.id as deid, en.id as enid, en.fecha_carga as enfecha FROM cbte_det de  "
                + "INNER JOIN cbte_enc en on en.id=de.cbte_enc_id "
                + "WHERE DATE_FORMAT(en.fecha_carga, '%Y-%m-%d') BETWEEN STR_TO_DATE(:dateInicial, '%d-%m-%Y') AND  STR_TO_DATE(:dateFinal, '%d-%m-%Y')  ";
        Set<Integer> sucursales = filterGeneric.getSucursales();
        if (!Objects.isNull(sucursales) && !sucursales.equals("")) {
            int i = 0;
            for (Integer suc : sucursales) {
                if (i == 0) {
                    sql += " AND (en.sucursales_id = " + suc;
                } else {
                    sql += " OR  en.sucursales_id = " + suc;
                }
                i++;
            }
            sql += " )";
        }
        sql += "group by productos_id order by cantidad desc limit 0, :limit) as t "
                + "where t.depid=producto.id ";
        Query query = session.createSQLQuery(sql).addScalar("cantidad", StandardBasicTypes.BIG_DECIMAL)
                .addScalar("id", StandardBasicTypes.INTEGER)
                .addScalar("nombre_final", StandardBasicTypes.STRING)
                .setParameter("limit", filterGeneric.getUltimos())
                .setParameter("dateInicial", filterGeneric.getFechaInicial().trim())
                .setParameter("dateFinal", filterGeneric.getFechaFinal().trim());
        ReportesVentaProducto reportesVentaProducto = null;
        for (Object rows : query.list()) {
            reportesVentaProducto = new ReportesVentaProducto();
            Object[] row = (Object[]) rows;
            Integer id = (Integer) row[1];
            String nombre = (String) row[2];
            BigDecimal valor = (BigDecimal) row[0];
            reportesVentaProducto.setProducto(new Producto());
            reportesVentaProducto.getProducto().setId(id);
            reportesVentaProducto.getProducto().setNombreFinal(nombre);
            reportesVentaProducto.setValor(valor);
            list.add(reportesVentaProducto);
        }
        return list;
    }

    /**
     * Devuelve los productos que mas dinero dejaron
     */
    @Override
    public List<ReportesVentaProducto> getProductosDeMasEntradaMonetaria(FilterGeneric filterGeneric) {
        Session session = sessionFactory.getCurrentSession();
        List<ReportesVentaProducto> list = new ArrayList<>();
        String sql = "select producto.id, producto.nombre_final, t.subtotal from producto, "
                + "(SELECT sum(subtotal) as subtotal, de.productos_id as depid, de.id as deid, en.id as enid, en.fecha_carga as enfecha FROM cbte_det de  "
                + "INNER JOIN cbte_enc en on en.id=de.cbte_enc_id "
                + "WHERE DATE_FORMAT(en.fecha_carga, '%Y-%m-%d') BETWEEN STR_TO_DATE(:dateInicial, '%d-%m-%Y') AND  STR_TO_DATE(:dateFinal, '%d-%m-%Y')  ";
        Set<Integer> sucursales = filterGeneric.getSucursales();
        if (!Objects.isNull(sucursales) && !sucursales.equals("")) {
            int i = 0;
            for (Integer suc : sucursales) {
                if (i == 0) {
                    sql += " AND (en.sucursales_id = " + suc;
                } else {
                    sql += " OR  en.sucursales_id = " + suc;
                }
                i++;
            }
            sql += " )";
        }
        sql += "group by productos_id order by subtotal desc limit 0, :limit) as t "
                + "where t.depid=producto.id ";
        Query query = session.createSQLQuery(sql).addScalar("subtotal", StandardBasicTypes.BIG_DECIMAL)
                .addScalar("id", StandardBasicTypes.INTEGER)
                .addScalar("nombre_final", StandardBasicTypes.STRING)
                .setParameter("limit", filterGeneric.getUltimos())
                .setParameter("dateInicial", filterGeneric.getFechaInicial().trim())
                .setParameter("dateFinal", filterGeneric.getFechaFinal().trim());
        ReportesVentaProducto reportesVentaProducto = null;
        for (Object rows : query.list()) {
            reportesVentaProducto = new ReportesVentaProducto();
            Object[] row = (Object[]) rows;
            Integer id = (Integer) row[1];
            String nombre = (String) row[2];
            BigDecimal valor = (BigDecimal) row[0];
            reportesVentaProducto.setProducto(new Producto());
            reportesVentaProducto.getProducto().setId(id);
            reportesVentaProducto.getProducto().setNombreFinal(nombre);
            reportesVentaProducto.setValor(valor);
            list.add(reportesVentaProducto);
        }
        return list;
    }

    /**
     * Calculo de ganancias
     */
    @Override
    public Ganancias getGanancias(FilterGeneric filterGeneric
    ) {
        Session session = sessionFactory.getCurrentSession();
        Ganancias ganancia = new Ganancias();
        Integer[] tiposVenta = new Integer[4];
        Query query = null;
        BigDecimal value = null;
        String sql = "";
        tiposVenta[0] = 1;// contado
        tiposVenta[1] = 2;// tarjeta credito
        tiposVenta[2] = 3;// tarjeta debito
        tiposVenta[3] = 4;// cuenta corriente
        // precio venta
        for (Integer tipoVenta : tiposVenta) {
            sql = "select sum(t.total) from ( "
                    + "select distinct p.id, p.monto as total from cbte_ven_enc e "
                    + "left outer join app a on e.app_id=a.id "
                    + "left outer join pago_cbte_ven p on p.cbte_ven_enc_id= e.id "
                    + "left outer join transaccion_caja t on e.transaccion_caja_id=t.id "
                    + "left outer join caja c on t.caja_id=c.id "
                    + "left outer join usuarios u on e.usuarios_id=u.id "
                    + "where a.id= :app "
                    + "AND e.estado=1 "
                    + "AND DATE_FORMAT(e.fecha_carga, '%Y-%m-%d') BETWEEN STR_TO_DATE(:dateInicial, '%d-%m-%Y') AND  STR_TO_DATE(:dateFinal, '%d-%m-%Y') "
                    + "AND p.formapagos_id=:tipoVenta) t ";

            query = session.createSQLQuery(sql)
                    .setParameter("app", TenantContext.getCurrentTenant())
                    .setParameter("dateInicial", filterGeneric.getFechaInicial().trim() + " 00:00")
                    .setParameter("dateFinal", filterGeneric.getFechaFinal().trim() + " 23:59")
                    .setParameter("tipoVenta", tipoVenta);
            value = (BigDecimal) query.uniqueResult();
            value = Objects.isNull(value) ? BigDecimal.ZERO : value;
            if (tipoVenta == 1) {
                ganancia.setTotalNetPesosFactVentaContado(value);
            }
            if (tipoVenta == 2) {
                ganancia.setTotalNetPesosFactVentaTarjCred(value);
            }
            if (tipoVenta == 3) {
                ganancia.setTotalNetPesosFactVentaTarjDeb(value);
            }
            if (tipoVenta == 4) {
                ganancia.setTotalNetPesosFactVentaCtaCte(value);
            }
        }
        // precio de venta total
        sql = "select sum(d.precio * cantidad) as totalPrecioVenta FROM cbte_ven_det as d, (SELECT d.id as idDetalle FROM cbte_ven_det d "
                + "INNER JOIN  cbte_ven_enc e ON d.cbte_ven_enc_id=e.id  "
                + "INNER JOIN  app a ON a.id=e.app_id "
                + "WHERE a.id= :app "
                + "AND e.fecha_carga BETWEEN STR_TO_DATE(:dateInicial, '%d-%m-%Y %H:%i') AND  STR_TO_DATE(:dateFinal, '%d-%m-%Y %H:%i') "
                + "AND e.estado=1) as t "
                + "where d.id=t.idDetalle ";
        query = session.createSQLQuery(sql)
                .setParameter("app", TenantContext.getCurrentTenant())
                .setParameter("dateInicial", filterGeneric.getFechaInicial().trim() + " 00:00")
                .setParameter("dateFinal", filterGeneric.getFechaFinal().trim() + " 23:59");
        value = (BigDecimal) query.uniqueResult();
        value = Objects.isNull(value) ? BigDecimal.ZERO : value;

        ganancia.setTotalNetPesosFactVenta(value);
        // precio bruto de venta total
        sql = "select sum(d.precio_costo * cantidad) as totalPrecioVenta FROM cbte_ven_det as d, (SELECT d.id as idDetalle FROM cbte_ven_det d "
                + "INNER JOIN  cbte_ven_enc e ON d.cbte_ven_enc_id=e.id  "
                + "INNER JOIN  app a ON a.id=e.app_id "
                + "WHERE a.id= :app "
                + "AND e.fecha_carga BETWEEN STR_TO_DATE(:dateInicial, '%d-%m-%Y %H:%i') AND  STR_TO_DATE(:dateFinal, '%d-%m-%Y %H:%i') "
                + "AND e.estado=1) as t "
                + "where d.id=t.idDetalle ";
        query = session.createSQLQuery(sql)
                .setParameter("app", TenantContext.getCurrentTenant())
                .setParameter("dateInicial", filterGeneric.getFechaInicial().trim() + " 00:00")
                .setParameter("dateFinal", filterGeneric.getFechaFinal().trim() + " 23:59");
        value = (BigDecimal) query.uniqueResult();
        value = Objects.isNull(value) ? BigDecimal.ZERO : value;
        ganancia.setTotalBruPesosFactVenta(value);
        ganancia.setTotalGananciasPesosFactVenta(ganancia.getTotalNetPesosFactVenta().subtract(ganancia.getTotalBruPesosFactVenta(), MathContext.DECIMAL128));

// pagos por las facturas de compras, segun fecha de pago
        sql = "SELECT sum(p.monto_paga) as total FROM pagos_cta_cte_prov p "
                + "INNER JOIN movimientos_cta_cte_prov_pagos_cta_cte_prov mcpp ON p.id=mcpp.pagos_cta_cte_prov_id  "
                + "INNER JOIN movimientos_cta_cte_prov mc ON mc.cont_factura_enc_id= mcpp.movimientos_cta_cte_prov_cont_factura_enc_id  "
                + "INNER JOIN cont_factura_enc cf ON cf.id= mc.cont_factura_enc_id  "
                + "WHERE p.fecha BETWEEN STR_TO_DATE(:dateInicial, '%d-%m-%Y %H:%i') AND  STR_TO_DATE(:dateFinal, '%d-%m-%Y %H:%i')   "
                + "AND cf.tipo=1 AND cf.app_id=:app";
        query = session.createSQLQuery(sql)
                .setParameter("app", TenantContext.getCurrentTenant())
                .setParameter("dateInicial", filterGeneric.getFechaInicial().trim() + " 00:00")
                .setParameter("dateFinal", filterGeneric.getFechaFinal().trim() + " 23:59");
        value = (BigDecimal) query.uniqueResult();
        value = Objects.isNull(value) ? BigDecimal.ZERO : value;
        ganancia.setTotalFacturasPagadasFecPago(value);

// pagos por las facturas de compras, segun fecha de factura
        sql = "SELECT sum(p.monto_paga) as total FROM pagos_cta_cte_prov p "
                + "INNER JOIN movimientos_cta_cte_prov_pagos_cta_cte_prov mcpp ON p.id=mcpp.pagos_cta_cte_prov_id  "
                + "INNER JOIN movimientos_cta_cte_prov mc ON mc.cont_factura_enc_id= mcpp.movimientos_cta_cte_prov_cont_factura_enc_id  "
                + "INNER JOIN cont_factura_enc cf ON cf.id= mc.cont_factura_enc_id  "
                + "WHERE cf.fecha_factura BETWEEN STR_TO_DATE(:dateInicial, '%d-%m-%Y %H:%i') AND  STR_TO_DATE(:dateFinal, '%d-%m-%Y %H:%i')   "
                + "AND cf.tipo=1 AND cf.app_id=:app";
        query = session.createSQLQuery(sql)
                .setParameter("app", TenantContext.getCurrentTenant())
                .setParameter("dateInicial", filterGeneric.getFechaInicial().trim() + " 00:00")
                .setParameter("dateFinal", filterGeneric.getFechaFinal().trim() + " 23:59");
        value = (BigDecimal) query.uniqueResult();
        value = Objects.isNull(value) ? BigDecimal.ZERO : value;
        ganancia.setTotalFacturasPagadasFecFacCompra(value);

        // pagos realizados de la cuentas corrientes
        sql = "select sum(monto) as total from pagos_cta_cte p, "
                + "(select pc.pagos_cta_cte_id as pc_id from movimientos_cta_cte_pagos_cta_cte pc, "
                + "(select mov.id as mov_id from movimientos_cta_cte mov, "
                + "(select pv.id as pv_id from pago_cbte_ven pv "
                + "inner join cbte_ven_enc e on pv.cbte_ven_enc_id=e.id "
                + "inner join app a on e.app_id=a.id "
                + "where a.id=:app "
                + "AND DATE_FORMAT(e.fecha_carga, '%Y-%m-%d') "
                + "BETWEEN STR_TO_DATE(:dateInicial, '%d-%m-%Y') AND  STR_TO_DATE(:dateFinal, '%d-%m-%Y')) as t "
                + "where mov.pago_cbte_ven_id= t.pv_id) as t1 "
                + "where pc.movimientos_cta_cte_id= t1.mov_id) as t2 "
                + "where p.id= t2.pc_id ";
        query = session.createSQLQuery(sql)
                .setParameter("app", TenantContext.getCurrentTenant())
                .setParameter("dateInicial", filterGeneric.getFechaInicial().trim())
                .setParameter("dateFinal", filterGeneric.getFechaFinal().trim());
        value = (BigDecimal) query.uniqueResult();
        value = Objects.isNull(value) ? BigDecimal.ZERO : value;
        ganancia.setTotalPagosCtaCteFecFacVenta(value);
        // pagos realizados de las cuentas corrientes independientes de las fechas del encabezado venta
        sql = "SELECT sum(p.monto_paga) as total FROM pagos_cta_cte p "
                + "left outer join movimientos_cta_cte_pagos_cta_cte mcp on mcp.pagos_cta_cte_id=p.id "
                + "left outer join movimientos_cta_cte mc on mc.id= mcp.movimientos_cta_cte_id "
                + "left outer join cuentas_corrientes cc on cc.cliente_personas_id= mc.cuentas_corrientes_cliente_personas_id "
                + "left outer join clientes c on c.personas_id= cc.cliente_personas_id "
                + "where c.app_id=:app "
                + "AND DATE_FORMAT(p.fecha, '%Y-%m-%d') "
                + "BETWEEN STR_TO_DATE(:dateInicial, '%d-%m-%Y') AND  STR_TO_DATE(:dateFinal, '%d-%m-%Y') ";
        query = session.createSQLQuery(sql)
                .setParameter("app", TenantContext.getCurrentTenant())
                .setParameter("dateInicial", filterGeneric.getFechaInicial().trim())
                .setParameter("dateFinal", filterGeneric.getFechaFinal().trim());
        value = (BigDecimal) query.uniqueResult();
        value = Objects.isNull(value) ? BigDecimal.ZERO : value;
        ganancia.setTotalPagosCtaCteFecPago(value);
        return ganancia;
    }

    private Integer getIdTipoFactura(String tipoFactura) {
        if (tipoFactura.trim().toUpperCase().equals("FACTURA A")) {
            return 1;
        }
        if (tipoFactura.trim().toUpperCase().equals("FACTURA B")) {
            return 6;
        }
        if (tipoFactura.trim().toUpperCase().equals("FACTURA C")) {
            return 11;
        }
        if (tipoFactura.trim().toUpperCase().equals("CONS. FINAL")) {
            return 500;
        }
        if (tipoFactura.trim().toUpperCase().equals("FACTURA X")) {
            return 501;
        }
        return 0; // sin tipo o por algun error
    }

    /**
     * *****************************UTILS PARA USO DE ADMIN SOLAMENTE*********************************************
     */
    /**
     * Eliminar ventas y todas sus dependencias
     */
    @Override
    public int deleteVentaAdminMayor(Integer idVenta) {
        Session session = sessionFactory.getCurrentSession();
        //traemos el id de venta para eliminar otras tablas
        String sql = "SELECT id as idPagoVenta FROM pago_cbte_ven where cbte_ven_enc_id >= :idVenta";
        String sqlSelMovCtaCte = "SELECT id as idMtoCtaCte FROM movimientos_cta_cte where pago_cbte_ven_id >= :idPagoVenta";
        String sqlSelPagosCtaCte = "SELECT pagos_cta_cte_id as idPagoCtaCte FROM movimientos_cta_cte_pagos_cta_cte where movimientos_cta_cte_id = :idMtoCtaCte";

        String sqlDeleteDetVenta = "delete FROM cbte_ven_det where cbte_ven_enc_id >= :idVenta";
        String sqlDeleteEncVenta = "delete from cbte_ven_enc where id >= :idVenta";
        String sqlDeleteMtoCtaCte = "delete from  movimientos_cta_cte where pago_cbte_ven_id= :idPagoVenta";
        String sqlDeletePagoVentas = "delete from  pago_cbte_ven where cbte_ven_enc_id  >= :idVenta";
        String sqlDeleteMtoCtaCtePagCtaCte = "delete from  movimientos_cta_cte_pagos_cta_cte where movimientos_cta_cte_id= :idMtoCtaCte";
        String sqlDeleteEventos = "delete from eventos_ventas where cbte_ven_enc_id >= :idVenta";
        String sqlDeleteIvasAfip = "delete from ivas_afip where cbte_ven_enc_id >= :idVenta";
        String sqlDeleteTributosAfip = "delete from tributos_afip where cbte_ven_enc_id >= :idVenta";
        String sqlDeletePagoVentasCtaCte = "delete from  pagos_cta_cte where id  = :IdPagoCtaCte";
        Query query = session.createSQLQuery(sql)
                .addScalar("idPagoVenta", StandardBasicTypes.INTEGER)
                .setParameter("idVenta", idVenta);
        List idsVentas = query.list();

        for (Object rowIdVenta : idsVentas) {
            Integer idPagoVenta = (Integer) rowIdVenta;
            logger.info("Id pago venta: " + idPagoVenta);
            // traemos los id de mot cota cte para eliminar en otras tablas
            List idsMtoCtaCte = session.createSQLQuery(sqlSelMovCtaCte)
                    .addScalar("idMtoCtaCte", StandardBasicTypes.INTEGER)
                    .setParameter("idPagoVenta", idPagoVenta).list();
            for (Object rowIdMtoCtaCte : idsMtoCtaCte) {
                Integer idMtoCtaCte = (Integer) rowIdMtoCtaCte;
                logger.info("Id rowIdMtoCtaCte: " + idMtoCtaCte);
                //traemos los pagos cta cte
                List idsPagoCtaCte = session.createSQLQuery(sqlSelPagosCtaCte)
                        .addScalar("idPagoCtaCte", StandardBasicTypes.INTEGER)
                        .setParameter("idMtoCtaCte", idMtoCtaCte).list();
                //eliminamos los movimientos_cta_cte_pagos_cta_cte
                query = session.createSQLQuery(sqlDeleteMtoCtaCtePagCtaCte)
                        .setParameter("idMtoCtaCte", idMtoCtaCte);
                query.executeUpdate();
                // eliminamos los pagos ventas cte
                for (Object rowIdPagoCtaCte : idsPagoCtaCte) {
                    Integer IdPagoCtaCte = (Integer) rowIdPagoCtaCte;
                    //eliminamos los pagos ventas  cte
                    query = session.createSQLQuery(sqlDeletePagoVentasCtaCte)
                            .setParameter("IdPagoCtaCte", IdPagoCtaCte);
                    query.executeUpdate();
                }
            }

            //eliminamos los pagos ventas 
            query = session.createSQLQuery(sqlDeleteMtoCtaCte)
                    .setParameter("idPagoVenta", idPagoVenta);
            query.executeUpdate();
        }
        query = session.createSQLQuery(sqlDeletePagoVentas)
                .setParameter("idVenta", idVenta);
        query.executeUpdate();

        query = session.createSQLQuery(sqlDeleteEventos)
                .setParameter("idVenta", idVenta);
        query.executeUpdate();
        query = session.createSQLQuery(sqlDeleteTributosAfip)
                .setParameter("idVenta", idVenta);
        query.executeUpdate();
        query = session.createSQLQuery(sqlDeleteIvasAfip)
                .setParameter("idVenta", idVenta);
        query.executeUpdate();
        query = session.createSQLQuery(sqlDeleteDetVenta)
                .setParameter("idVenta", idVenta);
        query.executeUpdate();
        query = session.createSQLQuery(sqlDeleteEncVenta)
                .setParameter("idVenta", idVenta);
        query.executeUpdate();
        return 1;
    }

    /**
     * Devuleve libro de iva ventas
     */
    @Override
    public Pagination<CbteEncVenta> getLibroIvaByPage(FilterCbte filterCbte, Integer page, Integer pageSize, String fieldOrder, Boolean reverse) {
        Projection idCountProjection = Projections.countDistinct("id");
        Criteria criteriaVentas = this.getCriteriaBaseLibroIVA(filterCbte);
        criteriaVentas.setProjection(idCountProjection);
        // setup criteria, joins etc here
        Integer totalResultCount = ((Long) criteriaVentas.uniqueResult()).intValue();
        if (reverse) {
            criteriaVentas.addOrder(Order.asc(fieldOrder.trim()));
        } else {
            criteriaVentas.addOrder(Order.desc(fieldOrder.trim()));
        }

        criteriaVentas.setProjection(Projections.distinct(Projections.property("id")));
        if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
            criteriaVentas.setFirstResult((page - 1) * pageSize);
            criteriaVentas.setMaxResults(pageSize);
        }
        List uniqueSubList = criteriaVentas.list();
        criteriaVentas.setProjection(null);
        criteriaVentas.setFirstResult(0);
        criteriaVentas.setMaxResults(Integer.MAX_VALUE);
        criteriaVentas.add(Restrictions.in("id", uniqueSubList));
        criteriaVentas.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<CbteEncVenta> lista = new ArrayList<>();
        if (totalResultCount > 0) {
            lista = new ArrayList<>(criteriaVentas.list());
        }
        Pagination<CbteEncVenta> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    @Override
    public Map getTotalesLibroIva(FilterCbte filterCbte) {
        Criteria criteriaVentas = this.getCriteriaBaseLibroIVA(filterCbte);
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaVentas.setProjection(idCountProjection);
        Map<String, Object> extraData = new HashMap<>();
        criteriaVentas.setProjection(Projections.sum("total"));
        BigDecimal total = (BigDecimal) criteriaVentas.uniqueResult();
        extraData.put("total", total);
        // sumamos el total en importe neto
        criteriaVentas.setProjection(Projections.sum("totalBaseImp"));
        BigDecimal totalBaseImp = (BigDecimal) criteriaVentas.uniqueResult();
        extraData.put("totalBaseImp", totalBaseImp);
        // sumamos el total en iva
        criteriaVentas.setProjection(Projections.sum("totalIva"));
        BigDecimal totalIva = (BigDecimal) criteriaVentas.uniqueResult();
        extraData.put("totalIva", totalIva);
        // sumamos el total en tributos
        criteriaVentas.setProjection(Projections.sum("totalTrib"));
        BigDecimal totalTrib = (BigDecimal) criteriaVentas.uniqueResult();
        extraData.put("totalTrib", totalTrib);
        extraData.put("ivas", this.getIvasTotalesLibroIva(filterCbte));
        return extraData;
    }

    public ArrayNode getIvasTotalesLibroIva(FilterCbte filterCbte) {
        // para ivas igual a 0
        ArrayNode ivas = ConstanstAfipTest.getTpoIvas();
        Map<String, Number> extraData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode temp = null;
        ArrayNode arrayNode = mapper.createArrayNode();

        for (JsonNode iva : ivas) {
            Criteria criteriaVentas = this.getCriteriaBaseLibroIVA(filterCbte);
            Criteria criteriaVentasIva = criteriaVentas.createCriteria("cbteVenEnc.ivas", "ivas", JoinType.LEFT_OUTER_JOIN);
            criteriaVentasIva.add(Restrictions.eq("idAfipIva", iva.get("id").asInt()));
            criteriaVentas.setProjection(Projections.sum("ivas.importe"));
            Object res = criteriaVentas.uniqueResult();
            String total = "0";
            if (Objects.nonNull(res)) {
                total = res.toString();
            }
            temp = mapper.createObjectNode();
            temp.put("total", new Double(total));
            temp.put("iva", iva);
            arrayNode.add(temp);
        }

        return arrayNode;
    }

    private Criteria getCriteriaBaseLibroIVA(FilterCbte filterCbte) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaVentas = session.createCriteria(CbteEncVenta.class, "cbteVenEnc");
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaVentas.setProjection(idCountProjection);
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaVentas.add(c1);
        /*######################################################################*/
//        criteriaVentas.createCriteria("cbteVenEnc.ivas", "ivas", JoinType.LEFT_OUTER_JOIN);
        criteriaVentas.setFetchMode("ivas", FetchMode.JOIN);
        criteriaVentas.setFetchMode("tributos", FetchMode.JOIN);
        // filtro para las fechas
        if (DateUtils.isValid(filterCbte.getFechaInicial()) && !DateUtils.isValid(filterCbte.getFechaFinal())) {
            criteriaVentas.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') >= STR_TO_DATE(?, '%d-%m-%Y')", filterCbte.getFechaInicial().trim(), StandardBasicTypes.STRING));
        }
        if (DateUtils.isValid(filterCbte.getFechaFinal()) && !DateUtils.isValid(filterCbte.getFechaInicial())) {
            criteriaVentas.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') <= STR_TO_DATE(?, '%d-%m-%Y')", filterCbte.getFechaFinal().trim(), StandardBasicTypes.STRING));
        }
        if (DateUtils.isValid(filterCbte.getFechaInicial()) && DateUtils.isValid(filterCbte.getFechaFinal())) {
            org.hibernate.type.Type[] tipos = {StandardBasicTypes.STRING, StandardBasicTypes.STRING};
            String[] values = {filterCbte.getFechaInicial().trim(), filterCbte.getFechaFinal().trim()};
            criteriaVentas.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') BETWEEN STR_TO_DATE(?, '%d-%m-%Y') AND  STR_TO_DATE(?, '%d-%m-%Y')", values, tipos));
        }
        // restriccion para activos o no activos
        if (filterCbte.getAfipValida() != null && !filterCbte.getAfipValida().equals("") && filterCbte.getAfipValida() != 2) {
            Criterion cAfipValida = null;
            if (filterCbte.getAfipValida() == 1) {
                cAfipValida = Restrictions.eq("afipValida", true);
            } else {
                cAfipValida = Restrictions.or(Restrictions.eq("afipValida", false), Restrictions.eq("afipValida", null));
            }
            criteriaVentas.add(cAfipValida);
        }
        // filter para sucursal
        if (Objects.nonNull(filterCbte.getSucursales()) && filterCbte.getSucursales().size() > 0) {
            this.addRestrictionSucursalesObj(filterCbte.getSucursales(), criteriaVentas, session);
        }
        // filtro de comprobantes
        if (Objects.nonNull(filterCbte.getComprobantes()) && filterCbte.getComprobantes().size() > 0) {
            Disjunction disjunctionCbte = Restrictions.disjunction();
            filterCbte.getComprobantes().forEach((cbte) -> {
                disjunctionCbte.add(Restrictions.eq("tipoCbte", cbte));
            });
            criteriaVentas.add(disjunctionCbte);
        }
        return criteriaVentas;
    }

    /**
     * modificamos solo el error que tiene en afip
     */
//    @Override
    public int updateErrorAfip(Integer idCbteVen, String dataError) {
        Session session = sessionFactory.getCurrentSession();
        String hqlUpdate = "UPDATE CbteVenEnc c set c.afipError = :dataError  where c.id = :id";
        int updatedEntities = session.createQuery(hqlUpdate)
                .setString("precioCosto", dataError)
                .setInteger("id", idCbteVen)
                .executeUpdate();
        return updatedEntities;
    }
}
