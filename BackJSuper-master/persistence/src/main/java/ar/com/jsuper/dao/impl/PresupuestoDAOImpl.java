package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.CbteDao;
import ar.com.jsuper.dao.PresupuestoDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.*;
import ar.com.jsuper.domain.utils.*;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.utils.DateUtils;
import ar.com.jsuper.utils.EstadoCbte;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class PresupuestoDAOImpl extends GenericDAOImpl<CbteEncPresupuesto, Integer> implements PresupuestoDAO, CbteDao {

    private Logger logger = Logger.getLogger(PresupuestoDAOImpl.class);

    /**
     * @throws BussinessException
     */
    @Override
    public Pagination<CbteEncPresupuesto> getPresupuestoByPage(FilterCbte filterCbte, PaginationRequestDTO paginationRequestDTO) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaPresupuesto = session.createCriteria(CbteEncPresupuesto.class, "cbteEnc");
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaPresupuesto.setProjection(idCountProjection);
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaPresupuesto.add(c1);
        /*######################################################################*/
        Criteria criteriaUsuario = criteriaPresupuesto.createCriteria("cbteEnc.usuario", "usuario", JoinType.LEFT_OUTER_JOIN);
        // filter for estado
        if (Objects.nonNull(filterCbte.getEstadoCbte()) && filterCbte.getEstadoCbte().compareTo(0) > 0) {
            criteriaPresupuesto.add(Restrictions.eq("estadoCbte", EstadoCbte.value(filterCbte.getEstadoCbte())));
        }
        // filtro si es mayor a  cierto monto
        if (Objects.nonNull(filterCbte.getTotalMinimo()) && filterCbte.getTotalMinimo().compareTo(BigDecimal.ZERO) > 0) {
            criteriaPresupuesto.add(Restrictions.ge("total", filterCbte.getTotalMinimo()));
        }
        // filtro si es menor a  cierto monto
        if (Objects.nonNull(filterCbte.getTotalMaximo()) &&  filterCbte.getTotalMaximo().compareTo(BigDecimal.ZERO) > 0) {
            criteriaPresupuesto.add(Restrictions.le("total", filterCbte.getTotalMaximo()));
        }
        // filtro para las fechas
        if (DateUtils.isValid(filterCbte.getFechaInicial()) && !DateUtils.isValid(filterCbte.getFechaFinal())) {
            criteriaPresupuesto.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') >= STR_TO_DATE(?, '%d-%m-%Y')", filterCbte.getFechaInicial().trim(), StandardBasicTypes.STRING));
        }
        if (DateUtils.isValid(filterCbte.getFechaFinal()) && !DateUtils.isValid(filterCbte.getFechaInicial())) {
            criteriaPresupuesto.add(Restrictions.sqlRestriction(" DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') <= STR_TO_DATE(?, '%d-%m-%Y')", filterCbte.getFechaFinal().trim(), StandardBasicTypes.STRING));
        }
        if (DateUtils.isValid(filterCbte.getFechaInicial()) && DateUtils.isValid(filterCbte.getFechaFinal())) {
            org.hibernate.type.Type[] tipos = {StandardBasicTypes.STRING, StandardBasicTypes.STRING};
            String[] values = {filterCbte.getFechaInicial().trim(), filterCbte.getFechaFinal().trim()};
            criteriaPresupuesto.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.fecha_carga, '%Y-%m-%d') BETWEEN STR_TO_DATE(?, '%d-%m-%Y') AND  STR_TO_DATE(?, '%d-%m-%Y')", values, tipos));
        }
        // filter para el usuario
        if (Objects.nonNull(filterCbte.getUsuario()) && Objects.nonNull(filterCbte.getUsuario().getId())) {
            criteriaUsuario.add(Restrictions.eq("id", filterCbte.getUsuario().getId()));
        }
        // filter para sucursal
        if (Objects.nonNull(filterCbte.getSucursales())) {
            this.addRestrictionSucursalesObj(filterCbte.getSucursales(), criteriaPresupuesto, session);
        }
        //filter de ventas por productos
        if (Objects.nonNull(filterCbte.getProductos())) {
            if (filterCbte.getProductos().size() > 0) {
                Disjunction disjunctionProducto = Restrictions.disjunction();
                Criteria criteriaDetalle = criteriaPresupuesto.createCriteria("cbteEnc.detalleVentas", "detalleVentas", JoinType.LEFT_OUTER_JOIN);
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
        Integer totalResultCount = ((Long) criteriaPresupuesto.uniqueResult()).intValue();
        if (paginationRequestDTO.getReverse()) {
            criteriaPresupuesto.addOrder(Order.asc(paginationRequestDTO.getOrder()));
        } else {
            criteriaPresupuesto.addOrder(Order.desc(paginationRequestDTO.getOrder()));
        }
        criteriaPresupuesto.setProjection(Projections.distinct(Projections.property("id")));
        if (paginationRequestDTO.getPage() != 0 && paginationRequestDTO.getPageSize() != 0) {// si viene en 0 los dos param devuelve todo sin paginar
            criteriaPresupuesto.setFirstResult((paginationRequestDTO.getPage() - 1) * paginationRequestDTO.getPageSize());
            criteriaPresupuesto.setMaxResults(paginationRequestDTO.getPageSize());
        }
        List uniqueSubList = criteriaPresupuesto.list();
        criteriaPresupuesto.setProjection(null);
        criteriaPresupuesto.setFirstResult(0);
        criteriaPresupuesto.setMaxResults(Integer.MAX_VALUE);
        criteriaPresupuesto.add(Restrictions.in("id", uniqueSubList));
        criteriaPresupuesto.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<CbteEncPresupuesto> lista = new ArrayList<>();
        if (totalResultCount > 0) {
            lista = new ArrayList<>(criteriaPresupuesto.list());
        }
        Pagination<CbteEncPresupuesto> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(paginationRequestDTO.getPage());
        pagination.setPageSize(paginationRequestDTO.getPageSize());
        return pagination;
    }

    @Override
    public CbteEncPresupuesto updateEstadoCbte(CbteEncPresupuesto cbteEncPresupuesto, EstadoCbte estadoCbte) {
        Session session = sessionFactory.getCurrentSession();
        cbteEncPresupuesto.setEstadoCbte(estadoCbte);
        session.save(cbteEncPresupuesto);
        return cbteEncPresupuesto;
    }

    @Override
    public CbteEncPresupuesto insertPresupuesto(CbteEncPresupuesto cbteEncPresupuesto) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        /*###########################Control por APP############################*/
        App app = session.get(App.class, TenantContext.getCurrentTenant());
        cbteEncPresupuesto.setApp(app);
        /*######################################################################*/
        // seteamos el id del usuario segun el token
        Usuarios usuario = new Usuarios();
        usuario.setId(TenantContext.getCurrentIdUser());
        cbteEncPresupuesto.setUsuario(usuario);
        cbteEncPresupuesto.setEstado(Boolean.TRUE);
        cbteEncPresupuesto.setPagada(null);
        cbteEncPresupuesto.setSaldo(null);
        cbteEncPresupuesto.setFechaCarga(new Date());
        cbteEncPresupuesto.setEstadoCbte(EstadoCbte.PRES_PENDIENTE);
        session.save(cbteEncPresupuesto);
        Set<CbteDet> cbteDets = cbteEncPresupuesto.getDetalleVentas();
        for (CbteDet cbteDet : cbteDets) {
            cbteDet.setCbteEnc(cbteEncPresupuesto);
            cbteEncPresupuesto.getDetalleVentas().add(cbteDet);
            session.save(cbteDet);
        }
//        Set<PagoCbteVen> pagoCbtes = cbteEncPresupuesto.getPagosCbte();
//        BigDecimal saldo = BigDecimal.ZERO;
//        for (PagoCbteVen pagoCbte : pagoCbtes) {
//            pagoCbte.setCbteEnc(cbteEncPresupuesto);
//            cbteEncPresupuesto.getPagosCbte().add(pagoCbte);
//            session.save(pagoCbte);
//        }
        // controlamos el cliente id
        if (Objects.nonNull(cbteEncPresupuesto.getCliente())) {
            if (Objects.nonNull(cbteEncPresupuesto.getCliente().getId())) {
                if (cbteEncPresupuesto.getCliente().getId() > 0) {
                    cbteEncPresupuesto.setIdCliente(cbteEncPresupuesto.getCliente().getId());
                }
            }
        }
        return cbteEncPresupuesto;
    }
}
