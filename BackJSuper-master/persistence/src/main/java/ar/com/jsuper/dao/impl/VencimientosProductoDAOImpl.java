package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.VencimientosProductoDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.dao.utils.UnitExpression;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.VencimientosProductos;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.security.TenantContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

@Repository
public class VencimientosProductoDAOImpl extends GenericDAOImpl<VencimientosProductos, Integer> implements VencimientosProductoDAO {

    /**
     * Guardamos el vencimiento de un producto
     */
    @Override
    public VencimientosProductos saveVencimientoProducto(VencimientosProductos vencimientosProducto) {
        Session session = sessionFactory.getCurrentSession();
        if (Objects.equals(vencimientosProducto.getTipo(), 1)) {
            vencimientosProducto.setFacturaDet(null);
        }
        if (Objects.equals(vencimientosProducto.getTipo(), 2)) {
            vencimientosProducto.setProducto(null);
        }
        vencimientosProducto.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
        vencimientosProducto.setFechaCarga(new Date());
        // por defecto estar activo el venci
        vencimientosProducto.setActivo(Boolean.TRUE);
        session.save(vencimientosProducto);
        return vencimientosProducto;
    }

    /**
     * Retorna la cantidad de prod q estan en alerta de vencimiento
     *
     * @return
     */
    @Override
    public Integer getCountAlerta() {
        Session session = sessionFactory.getCurrentSession();
        Integer value;
        String sql;
        Query query = null;
        sql = "select count(*) as count from vencimientos_productos vencimient0_ "
                + "where vencimient0_.activo=1 "
                + "and TIMESTAMPDIFF(DAY, current_date, vencimient0_.fecha_vencimiento ) <= vencimient0_.dias_alerta "
                + "and vencimient0_.fecha_vencimiento >= current_date "
                + "and vencimient0_.alerta_vencimientos=1 "
                + "and vencimient0_.app_id=:app ";
        query = session.createSQLQuery(sql).addScalar("count", IntegerType.INSTANCE);
        query.setParameter("app", TenantContext.getCurrentTenant());
        //total 
        value = (Integer) query.uniqueResult();
        return value;
    }

    @Override
    public Pagination<VencimientosProductos> getVencimientosByPage(FilterGeneric filterGeneric, int page, int pageSize, String fieldOrder, boolean reverse) {
        Session session = sessionFactory.getCurrentSession();
        List<Predicate> predicates = new ArrayList<Predicate>();
        Predicate predicateJoinFactura = null;
        Predicate predicateJoinProducto = null;
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<VencimientosProductos> criteria = builder.createQuery(VencimientosProductos.class);
        Root<VencimientosProductos> booksRoot = criteria.from(VencimientosProductos.class);
        booksRoot.fetch("facturaDet", javax.persistence.criteria.JoinType.LEFT);
        booksRoot.fetch("producto", javax.persistence.criteria.JoinType.LEFT);
        // filtros
        Integer activo = filterGeneric.getActivo();
        if (activo != null && !activo.equals("") && activo != 2) {
            predicates.add(builder.equal(booksRoot.get("activo"), (activo == 1)));
        }
        // filtro para productos
        if (!Objects.isNull(filterGeneric.getProducto())) {
            if (filterGeneric.getProducto().getId() > 0) {
                predicateJoinProducto = builder.equal(booksRoot.get("producto").get("id"), filterGeneric.getProducto().getId());
                predicateJoinFactura = builder.equal(booksRoot.get("facturaDet").get("idProducto"), filterGeneric.getProducto().getId());
            }
        }
        if (!Objects.isNull(filterGeneric.getValido())) {
            if (filterGeneric.getValido() == 1) {
                Predicate tt = builder.greaterThanOrEqualTo(booksRoot.get("fechaVencimiento"), builder.currentDate());
                predicates.add(tt);
            }
            if (filterGeneric.getValido() == 0) {
                Predicate tt = builder.lessThan(booksRoot.get("fechaVencimiento"), builder.currentDate());
                predicates.add(tt);
            }
        }
        if (!Objects.isNull(predicateJoinProducto) && !Objects.isNull(predicateJoinFactura)) {
            Predicate pOrProdFact = builder.or(predicateJoinFactura, predicateJoinProducto);
            predicates.add(pOrProdFact);
        }
        // filtro para los que estan en alerta
        if (!Objects.isNull(filterGeneric.getAlerta())) {
            if (filterGeneric.getAlerta() == 1 || filterGeneric.getAlerta() == 0) {
                Expression<String> day = new UnitExpression(null, String.class, "DAY");
                Expression<Integer> timeDiff = builder.function(
                        "TIMESTAMPDIFF",
                        Integer.class,
                        day,
                        builder.currentDate(),
                        booksRoot.get("fechaVencimiento"));
                Predicate fechaValida = null;
                if (filterGeneric.getAlerta() == 0) {
                    fechaValida = builder.greaterThanOrEqualTo(timeDiff, booksRoot.get("diasAlerta"));
                }
                if (filterGeneric.getAlerta() == 1) {
                    fechaValida = builder.lessThanOrEqualTo(timeDiff, booksRoot.get("diasAlerta"));
                }
                predicates.add(fechaValida);
            }
        }
        /*###########################Control por APP############################*/
        predicates.add(builder.equal(booksRoot.get("app"), TenantContext.getCurrentTenant()));
        /*######################################################################*/

        criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        // ordenamos 
        if (reverse) {
            criteria.orderBy(builder.asc(booksRoot.get(fieldOrder.trim())));
        } else {
            criteria.orderBy(builder.desc(booksRoot.get(fieldOrder.trim())));
        }

        // This query fetches the Books as per the Page Limit
        List<VencimientosProductos> result = session.createQuery(criteria).setFirstResult(((page - 1) * pageSize)).setMaxResults(pageSize).getResultList();

        // Create Count Query
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<VencimientosProductos> booksRootCount = countQuery.from(VencimientosProductos.class);
        countQuery.select(builder.count(booksRootCount)).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

        // Fetches the count of all Books as per given criteria
        Long count = session.createQuery(countQuery).getSingleResult();

        Pagination<VencimientosProductos> pagination = new Pagination<>();
        pagination.setData(result);
        pagination.setTotal(count.intValue());
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    /**
     * habilitamos o deshabilitamos vencimientos
     *
     */
    @Override
    public int enabledOrdisabledVencimientos(Set<VencimientosProductos> vencimientos, boolean value) {
        Session session = sessionFactory.getCurrentSession();
        Set<Integer> setIds = new HashSet<>();
        for (VencimientosProductos vencimiento : vencimientos) {
            setIds.add(vencimiento.getId());
        }
        String updateHQL = " UPDATE VencimientosProductos v SET v.activo = :valueactivo"
                + " WHERE  v.id in (:ids)";
        int updatedEntities = session.createQuery(updateHQL)
                .setBoolean("valueactivo", value)
                .setParameterList("ids", setIds)
                .executeUpdate();
        return updatedEntities;
    }

    /**
     * activamos o desactivamos el alerta
     *
     */
    @Override
    public int enabledOrdisabledAlertaVencimientos(VencimientosProductos vencimiento, boolean value) {
        Session session = sessionFactory.getCurrentSession();
        String updateHQL = " UPDATE VencimientosProductos v SET v.alertaVencimientos = :valuealerta"
                + " WHERE  v.id = :id";
        int updatedEntities = session.createQuery(updateHQL)
                .setBoolean("valuealerta", value)
                .setParameter("id", vencimiento.getId())
                .executeUpdate();
        return updatedEntities;
    }

}
