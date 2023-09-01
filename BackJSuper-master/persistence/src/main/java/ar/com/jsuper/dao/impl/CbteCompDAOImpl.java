package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.CuentasCorrientesProvDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.CbteCompDet;
import ar.com.jsuper.domain.CbteCompEnc;
import ar.com.jsuper.domain.CuentasCorrientesProv;
import ar.com.jsuper.domain.PagoCbteComp;
import ar.com.jsuper.domain.MovimientosCtaCteProv;
import ar.com.jsuper.domain.utils.FilterFacturas;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.utils.TipoPago;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.CbteCompDAO;
import ar.com.jsuper.domain.IvasAfip;
import ar.com.jsuper.domain.TributosAfip;
import ar.com.jsuper.utils.ConstanstAfipTest;
import ar.com.jsuper.utils.DateUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.criterion.Disjunction;

@Repository
public class CbteCompDAOImpl extends GenericDAOImpl<CbteCompEnc, Integer> implements CbteCompDAO {

    @Autowired
    CuentasCorrientesProvDAO cuentasCorrientesProvDAO;

    @Override
    public CbteCompEnc insert(CbteCompEnc cbteCompEnc) {
        Session session = sessionFactory.getCurrentSession();
        Set<CbteCompDet> cbteCompDets = cbteCompEnc.getItems();
//		Set<CbteCompImpuesto> contFacturasImpuestos = cbteCompEnc.getImpuestos();
//		Set<CbteCompAdicional> contFacturasAdicionales = cbteCompEnc.getAdicionales();
//		Set<CbteCompDescuento> contFacturasDescuentos = cbteCompEnc.getDescuentos();
        /*###########################Control por APP############################*/
        cbteCompEnc.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
        /*######################################################################*/
        cbteCompEnc.setIdProveedor(cbteCompEnc.getProveedor().getId());
        cbteCompEnc.setIdSucursal(cbteCompEnc.getSucursal().getId());

        session.save(cbteCompEnc);
        Set<CbteCompDet> cbtesCompDet = cbteCompEnc.getItems();
        for (CbteCompDet cbteCompDet : cbtesCompDet) {
            cbteCompDet.setCbteCompEnc(cbteCompEnc);
            cbteCompDet.setCantAgregadaStock(Boolean.FALSE);
            cbteCompEnc.getItems().add(cbteCompDet);
            session.save(cbteCompDet);
        }
//		if (!Objects.isNull(contFacturasImpuestos)) {
//			for (CbteCompImpuesto cfi : contFacturasImpuestos) {
//				cfi.setCbteCompEnc(cbteCompEnc);
//				session.save(cfi);
//			}
//		}
//		if (!Objects.isNull(contFacturasAdicionales)) {
//			for (CbteCompAdicional cfa : contFacturasAdicionales) {
//				cfa.setCbteCompEnc(cbteCompEnc);
//				session.save(cfa);
//			}
//		}
//		if (!Objects.isNull(contFacturasDescuentos)) {
//			for (CbteCompDescuento cfd : contFacturasDescuentos) {
//				cfd.setCbteCompEnc(cbteCompEnc);
//				session.save(cfd);
//			}
//		}

        String stringTipoPago = "";
        Set<PagoCbteComp> pagos = cbteCompEnc.getPagos();
        BigDecimal saldo = BigDecimal.ZERO;
        CuentasCorrientesProv cta = null;
        for (PagoCbteComp pago : pagos) {
            if (pago.getFormaPago().getId() == TipoPago.CUENTA_CORRIENTE.theState) {// controlamos para cta corrientes
                if (Objects.isNull(cta)) {
                    cta = cuentasCorrientesProvDAO.getCtaCteByProveedor(cbteCompEnc.getProveedor().getId());
                }
                if (Objects.isNull(cta)) {
                    cta = new CuentasCorrientesProv();
                    cta.setActivo(Boolean.TRUE);
                    cta.setDescripcion("Cta. corriente creada automáticamente por el sistema.");
                    cta.setFechaAlta(new Date());
                    cta.setLimite(new BigDecimal(9999999));
                    cta.setProveedor(cbteCompEnc.getProveedor());
                    session.save(cta);
                }
                MovimientosCtaCteProv movimientoCtaCteProv = new MovimientosCtaCteProv();
                movimientoCtaCteProv.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
                movimientoCtaCteProv.setMonto(pago.getMonto());
                movimientoCtaCteProv.setPagado(Boolean.FALSE);
                movimientoCtaCteProv.setFecha(new Date());
                movimientoCtaCteProv.setTipo(1); //1 proveedores, falta definir, capo para futuro
                if (Objects.isNull(pago.getInteres())) {
                    movimientoCtaCteProv.setInteres(BigDecimal.ZERO);
                    movimientoCtaCteProv.setSaldo(pago.getMonto());
                    movimientoCtaCteProv.setMontoTotal(pago.getMonto());
                } else {
                    movimientoCtaCteProv.setInteres(pago.getInteres());
                    if (pago.getInteres().compareTo(BigDecimal.ZERO) == 0) {
                        movimientoCtaCteProv.setMontoTotal(pago.getMonto());
                        movimientoCtaCteProv.setSaldo(pago.getMonto());
                    } else {
                        movimientoCtaCteProv.setMontoTotal(pago.getMontoConInteres());
                        movimientoCtaCteProv.setSaldo(pago.getMontoConInteres());
                    }
                }
                saldo = saldo.add(movimientoCtaCteProv.getSaldo());
                movimientoCtaCteProv.setCuentaCorriente(cta);
                pago.setCbteCompEnc(cbteCompEnc);
                cbteCompEnc.getPagos().add(pago);
                session.save(pago);
                movimientoCtaCteProv.setPagoCbteComp(pago);
                session.save(movimientoCtaCteProv);
            } else {
                pago.setCbteCompEnc(cbteCompEnc);
                cbteCompEnc.getPagos().add(pago);
                session.save(pago);
            }
            stringTipoPago = stringTipoPago + (stringTipoPago.equals("") ? "" : "-") + pago.getTipo();
        }
// verificamos si hay saldo quiere decir que la factura no esta pagada del todo
        if (saldo.compareTo(BigDecimal.ZERO) > 0) {
            cbteCompEnc.setSaldo(saldo);
            cbteCompEnc.setPagada(Boolean.FALSE);
        } else {
            cbteCompEnc.setPagada(Boolean.TRUE);
        }
        // insertamos ivas si es que lo tiene para fe
        Set<IvasAfip> ivas = cbteCompEnc.getIvas();
        if (Objects.nonNull(ivas)) {
            for (IvasAfip iva : ivas) {
                iva.setCbte(cbteCompEnc.getId());
                iva.setTipoCbte(2); // el 2 es del tipo cbte de compra
                cbteCompEnc.getIvas().add(iva);
                session.save(iva);
            }
        }
        // insertamos tributos si es que lo tiene para fe
        Set<TributosAfip> tributos = cbteCompEnc.getTributos();
        if (Objects.nonNull(tributos)) {
            for (TributosAfip tributo : tributos) {
                tributo.setCbte(cbteCompEnc.getId());
                tributo.setTipoCbte(2); // el 2 es del tipo cbte de compra
                cbteCompEnc.getTributos().add(tributo);
                session.save(tributo);
            }
        }
        return cbteCompEnc;
    }

    /**
     * Paginacion...
     *
     * @param filterFacturas
     * @param page
     * @param pageSize
     * @param fieldOrder
     * @param reverse
     * @param tipo
     * @return
     */
    @Override
    public Pagination<CbteCompEnc> getFacturasByPage(FilterFacturas filterFacturas, int page, int pageSize, String fieldOrder, boolean reverse, Integer tipo) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaFacturas = session.createCriteria(CbteCompEnc.class, "factura");
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaFacturas.setProjection(idCountProjection);
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaFacturas.add(c1);
        /*######################################################################*/
        Criteria criteriaProveedor = criteriaFacturas.createCriteria("factura.proveedor", "proveedor", JoinType.LEFT_OUTER_JOIN);
        criteriaFacturas.setFetchMode("pagos", FetchMode.JOIN);
        criteriaFacturas.setFetchMode("pagos.formaPago", FetchMode.JOIN);

//		Criteria criteriaMov = criteriaFacturas.createCriteria("factura.movimientosCtaCteProv", "movimientos", JoinType.LEFT_OUTER_JOIN);
        // filtro para el tipo de la factura
        criteriaFacturas.add(Restrictions.ge("tipo", tipo));
        // filtro si es mayor a  cierto monto
        if (Objects.nonNull(filterFacturas.getTotalMinimo())) {
            if (filterFacturas.getTotalMinimo().compareTo(BigDecimal.ZERO) > 0) {
                criteriaFacturas.add(Restrictions.ge("total", filterFacturas.getTotalMinimo()));
            }
        }
        // filtro si es menor a  cierto monto
        if (Objects.nonNull(filterFacturas.getTotalMaximo())) {
            if (filterFacturas.getTotalMaximo().compareTo(BigDecimal.ZERO) > 0) {
                criteriaFacturas.add(Restrictions.le("total", filterFacturas.getTotalMaximo()));
            }
        }
        // filtro para las fechas
        if (DateUtils.isValid(filterFacturas.getFechaInicial()) && !DateUtils.isValid(filterFacturas.getFechaFinal())) {
            criteriaFacturas.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_cbte, '%Y-%m-%d') >= STR_TO_DATE(?, '%d-%m-%Y')", filterFacturas.getFechaInicial().trim(), StandardBasicTypes.STRING));
        }
        if (DateUtils.isValid(filterFacturas.getFechaFinal()) && !DateUtils.isValid(filterFacturas.getFechaInicial())) {
            criteriaFacturas.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_cbte, '%Y-%m-%d') <= STR_TO_DATE(?, '%d-%m-%Y')", filterFacturas.getFechaFinal().trim(), StandardBasicTypes.STRING));
        }
        if (DateUtils.isValid(filterFacturas.getFechaInicial()) && DateUtils.isValid(filterFacturas.getFechaFinal())) {
            org.hibernate.type.Type[] tipos = {StandardBasicTypes.STRING, StandardBasicTypes.STRING};
            String[] values = {filterFacturas.getFechaInicial().trim(), filterFacturas.getFechaFinal().trim()};
            criteriaFacturas.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.fecha_cbte, '%Y-%m-%d') BETWEEN STR_TO_DATE(?, '%d-%m-%Y') AND  STR_TO_DATE(?, '%d-%m-%Y')", values, tipos));
        }
        // filter para el usuario
        if (Objects.nonNull(filterFacturas.getProveedor())) {
            criteriaProveedor.add(Restrictions.eq("id", filterFacturas.getProveedor().getId()));
        }
        // setup criteria, joins etc here
        Integer totalResultCount = ((Long) criteriaFacturas.uniqueResult()).intValue();
        if (reverse) {
            criteriaFacturas.addOrder(Order.asc(fieldOrder.trim()));
        } else {
            criteriaFacturas.addOrder(Order.desc(fieldOrder.trim()));
        }
        criteriaFacturas.setProjection(Projections.distinct(Projections.property("id")));
        if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
            criteriaFacturas.setFirstResult((page - 1) * pageSize);
            criteriaFacturas.setMaxResults(pageSize);
        }
        List uniqueSubList = criteriaFacturas.list();
        criteriaFacturas.setProjection(null);
        criteriaFacturas.setFirstResult(0);
        criteriaFacturas.setMaxResults(Integer.MAX_VALUE);
        criteriaFacturas.add(Restrictions.in("id", uniqueSubList));
        criteriaFacturas.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<CbteCompEnc> lista = new ArrayList<>();
        if (totalResultCount > 0) {
            lista = new ArrayList<>(criteriaFacturas.list());
        }
        Pagination<CbteCompEnc> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    @Override
    public CbteCompEnc getFactura(int id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaFactura = session.createCriteria(CbteCompEnc.class, "factura");
        criteriaFactura.setFetchMode("proveedor", FetchMode.JOIN);
        criteriaFactura.setFetchMode("impuestos", FetchMode.JOIN);
        criteriaFactura.setFetchMode("descuentos", FetchMode.JOIN);
        criteriaFactura.setFetchMode("adicionales", FetchMode.JOIN);
        criteriaFactura.setFetchMode("items", FetchMode.JOIN);
        criteriaFactura.add(Restrictions.eq("id", id));
        CbteCompEnc cbteCompEnc = (CbteCompEnc) criteriaFactura.uniqueResult();
        return cbteCompEnc;
    }

    @Override
    public MovimientosCtaCteProv getMovimientoFromFactura(int id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaMov = session.createCriteria(MovimientosCtaCteProv.class, "mov");
        Criteria criteriaFac = criteriaMov.createCriteria("mov.factura", "factura", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaPagos = criteriaMov.createCriteria("mov.pagosCtaCte", "pagos", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaPagosFP = criteriaPagos.createCriteria("pagos.formaPago", "fp", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaPagosDT = criteriaPagos.createCriteria("pagos.detalleTransaccion", "dt", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaPagosT = criteriaPagos.createCriteria("dt.transaccionCaja", "t", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaPagosC = criteriaPagos.createCriteria("t.caja", "c", JoinType.LEFT_OUTER_JOIN);
        Criterion c = Restrictions.eq("id", id);
        criteriaFac.add(c);
        MovimientosCtaCteProv movimientosCtaCteProv = (MovimientosCtaCteProv) criteriaMov.uniqueResult();
        return movimientosCtaCteProv;
    }

    @Override
    public CbteCompEnc getFacturaByNum(String q) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaFactura = session.createCriteria(CbteCompEnc.class, "factura");
        criteriaFactura.setFetchMode("proveedor", FetchMode.JOIN);
        criteriaFactura.add(Restrictions.like("numero", q, MatchMode.ANYWHERE));
        criteriaFactura.addOrder(Order.desc("id"));
        List lista = criteriaFactura.list();
        if (lista.isEmpty()) {
            return null;
        } else {
            // devuelvve soo la ultima registrrada segun la carag q se hizo
            return (CbteCompEnc) criteriaFactura.list().get(0);
        }
    }

    @Override
    public CbteCompEnc getFacturaByPago(Integer idPago) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaFactura = session.createCriteria(CbteCompEnc.class, "factura");
        criteriaFactura.setFetchMode("proveedor", FetchMode.JOIN);
        criteriaFactura.setFetchMode("impuestos", FetchMode.JOIN);
        criteriaFactura.setFetchMode("descuentos", FetchMode.JOIN);
        criteriaFactura.setFetchMode("adicionales", FetchMode.JOIN);
        criteriaFactura.setFetchMode("items", FetchMode.JOIN);
        Criteria criteriaMovimientos = criteriaFactura.createCriteria("factura.movimientosCtaCteProv", "movimiento", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaPagos = criteriaMovimientos.createCriteria("movimiento.pagosCtaCte", "pagos", JoinType.LEFT_OUTER_JOIN);
        criteriaPagos.add(Restrictions.eq("id", idPago));
        CbteCompEnc cbteCompEnc = (CbteCompEnc) criteriaFactura.uniqueResult();
        return cbteCompEnc;
//        List lista = criteriaFactura.list();
//        if (lista.isEmpty()) {
//            return null;
//        } else {
//            // devuelvve soo la ultima registrrada segun la carag q se hizo
//            return (ContFacturasEnc) criteriaFactura.list().get(0);
//        }

    }

    /**
     * se cambia el estado de cantidad agregada a los items
     *
     */
    @Override
    public int updateCantidadAgregada(Set<Integer> ids, boolean value) {
        Session session = sessionFactory.getCurrentSession();
        String updateHQL = " UPDATE CbteCompDet c SET c.cantAgregadaStock = :value"
                + " WHERE  c.id in (:ids)";
        int updatedEntities = session.createQuery(updateHQL)
                .setBoolean("value", value)
                .setParameterList("ids", ids)
                .executeUpdate();
        return updatedEntities;
    }

    /**
     * se cambia el estado de cantidad agregada a los items
     *
     */
    @Override
    public int updateFacturaCompleta(Integer id, Integer value) {
        Session session = sessionFactory.getCurrentSession();
        String updateHQL = " UPDATE CbteCompEnc c SET c.cargada = :value"
                + " WHERE  c.id = :id ";
        int updatedEntities = session.createQuery(updateHQL)
                .setInteger("value", value)
                .setInteger("id", id)
                .executeUpdate();
        return updatedEntities;
    }

    @Override
    public Pagination<CbteCompEnc> getLibroIvaByPage(FilterFacturas filterFacturas, int page, int pageSize, String fieldOrder, boolean reverse) {
//		Session session = sessionFactory.getCurrentSession();
        Projection idCountProjection = Projections.countDistinct("id");
        Criteria criteriaCompras = this.getCriteriaBaseLibroIVA(filterFacturas);
        criteriaCompras.setProjection(idCountProjection);
        // setup criteria, joins etc here
        Integer totalResultCount = ((Long) criteriaCompras.uniqueResult()).intValue();
        if (reverse) {
            criteriaCompras.addOrder(Order.asc(fieldOrder.trim()));
        } else {
            criteriaCompras.addOrder(Order.desc(fieldOrder.trim()));
        }

        criteriaCompras.setProjection(Projections.distinct(Projections.property("id")));
        if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
            criteriaCompras.setFirstResult((page - 1) * pageSize);
            criteriaCompras.setMaxResults(pageSize);
        }
        List uniqueSubList = criteriaCompras.list();
        criteriaCompras.setProjection(null);
        criteriaCompras.setFirstResult(0);
        criteriaCompras.setMaxResults(Integer.MAX_VALUE);
        criteriaCompras.add(Restrictions.in("id", uniqueSubList));
        criteriaCompras.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<CbteCompEnc> lista = new ArrayList<>();
        if (totalResultCount > 0) {
            lista = new ArrayList<>(criteriaCompras.list());
        }
        Pagination<CbteCompEnc> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    @Override
    public Map getTotalesLibroIva(FilterFacturas filterCompras) {
        Criteria criteriaVentas = this.getCriteriaBaseLibroIVA(filterCompras);
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
        criteriaVentas.setProjection(Projections.sum("totalIVAs"));
        BigDecimal totalIva = (BigDecimal) criteriaVentas.uniqueResult();
        extraData.put("totalIva", totalIva);
        // sumamos el total en tributos
        criteriaVentas.setProjection(Projections.sum("totalTrib"));
        BigDecimal totalTrib = (BigDecimal) criteriaVentas.uniqueResult();
        extraData.put("totalTrib", totalTrib);
        extraData.put("ivas", this.getIvasTotalesLibroIva(filterCompras));
        return extraData;
    }

    public ArrayNode getIvasTotalesLibroIva(FilterFacturas filterCompras) {
        // para ivas igual a 0
        ArrayNode ivas = ConstanstAfipTest.getTpoIvas();
        Map<String, Number> extraData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode temp = null;
        ArrayNode arrayNode = mapper.createArrayNode();

        for (JsonNode iva : ivas) {
            Criteria criteriaVentas = this.getCriteriaBaseLibroIVA(filterCompras);
            Criteria criteriaVentasIva = criteriaVentas.createCriteria("cbteCompEnc.ivas", "ivas", JoinType.LEFT_OUTER_JOIN);
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

    private Criteria getCriteriaBaseLibroIVA(FilterFacturas filterCompras) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaCompras = session.createCriteria(CbteCompEnc.class, "cbteCompEnc");
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaCompras.setProjection(idCountProjection);
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaCompras.add(c1);
        /*######################################################################*/
        criteriaCompras.setFetchMode("ivas", FetchMode.JOIN);
        criteriaCompras.setFetchMode("tributos", FetchMode.JOIN);
        // filtro para las fechas
        if (DateUtils.isValid(filterCompras.getFechaInicial()) && !DateUtils.isValid(filterCompras.getFechaFinal())) {
            criteriaCompras.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') >= STR_TO_DATE(?, '%d-%m-%Y')", filterCompras.getFechaInicial().trim(), StandardBasicTypes.STRING));
        }
        if (DateUtils.isValid(filterCompras.getFechaFinal()) && !DateUtils.isValid(filterCompras.getFechaInicial())) {
            criteriaCompras.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') <= STR_TO_DATE(?, '%d-%m-%Y')", filterCompras.getFechaFinal().trim(), StandardBasicTypes.STRING));
        }
        if (DateUtils.isValid(filterCompras.getFechaInicial()) && DateUtils.isValid(filterCompras.getFechaFinal())) {
            org.hibernate.type.Type[] tipos = {StandardBasicTypes.STRING, StandardBasicTypes.STRING};
            String[] values = {filterCompras.getFechaInicial().trim(), filterCompras.getFechaFinal().trim()};
            criteriaCompras.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') BETWEEN STR_TO_DATE(?, '%d-%m-%Y') AND  STR_TO_DATE(?, '%d-%m-%Y')", values, tipos));
        }
        // filter para sucursal
        if (Objects.nonNull(filterCompras.getSucursales()) && filterCompras.getSucursales().size() > 0) {
            this.addRestrictionSucursalesObj(filterCompras.getSucursales(), criteriaCompras, session);
        }
        // filtro de comprobantes
        if (Objects.nonNull(filterCompras.getComprobantes()) && filterCompras.getComprobantes().size() > 0) {
            Disjunction disjunctionCbte = Restrictions.disjunction();
            filterCompras.getComprobantes().forEach((cbte) -> {
                disjunctionCbte.add(Restrictions.eq("tipoCbte", cbte));
            });
            criteriaCompras.add(disjunctionCbte);
        }
        return criteriaCompras;
    }
}
