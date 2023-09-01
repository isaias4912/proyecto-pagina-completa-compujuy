package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.ContImpuestosDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Impuesto;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.security.TenantContext;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.springframework.stereotype.Repository;

@Repository
public class ContImpuestosImpl extends GenericDAOImpl<Impuesto, Integer> implements ContImpuestosDAO {

    @Override
    public Pagination<Impuesto> getContImpuestosByPage(FilterGeneric filterGeneric, int page, int pageSize, String fieldOrder, boolean reverse) {
        Session session = sessionFactory.getCurrentSession();
        Criteria cirteriaContImpuestos = session.createCriteria(Impuesto.class, "contImpuesto");
        Projection idCountProjection = Projections.countDistinct("id");
        cirteriaContImpuestos.setProjection(idCountProjection);
        if (filterGeneric.getText() != null && !filterGeneric.getText().trim().equals("")) {
            Criterion c1 = Restrictions.like("contImpuesto.nombre", filterGeneric.getText(), MatchMode.ANYWHERE);
            cirteriaContImpuestos.add(c1);
        }
        /*###########################Control por APP############################*/
        cirteriaContImpuestos.setFetchMode("contImpuesto.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app", TenantContext.getCurrentTenant());
        cirteriaContImpuestos.add(c1);
        /*######################################################################*/
        // setup criteria, joins etc here
        Integer totalResultCount = ((Long) cirteriaContImpuestos.uniqueResult()).intValue();

        if (reverse) {
            cirteriaContImpuestos.addOrder(Order.asc(fieldOrder.trim()));
        } else {
            cirteriaContImpuestos.addOrder(Order.desc(fieldOrder.trim()));
        }
        cirteriaContImpuestos.setProjection(Projections.distinct(Projections.property("id")));
        cirteriaContImpuestos.setFirstResult((page - 1) * pageSize);
        cirteriaContImpuestos.setMaxResults(pageSize);
        List uniqueSubList = cirteriaContImpuestos.list();
        cirteriaContImpuestos.setProjection(null);
        cirteriaContImpuestos.setFirstResult(0);
        cirteriaContImpuestos.setMaxResults(Integer.MAX_VALUE);
        cirteriaContImpuestos.add(Restrictions.in("id", uniqueSubList));
        cirteriaContImpuestos.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<Impuesto> lista = new ArrayList<>();
        if (totalResultCount > 0) {
            lista = new ArrayList<>(cirteriaContImpuestos.list());
        }
        Pagination<Impuesto> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    @Override
    public Impuesto update(Impuesto entityOld, Impuesto entityNew) {
        Session session = sessionFactory.getCurrentSession();
        entityOld = this.getObject(entityOld, entityNew);
        session.update(entityOld);
        return entityOld;
    }

    @Override
    public Impuesto getObject(Impuesto impOld, Impuesto impNew) {
        if (Objects.isNull(impOld)) {
            impOld = new Impuesto();
        }
        impOld.setActivo(impNew.getActivo());
        impOld.setDetalle(impNew.getDetalle());
        impOld.setTipo(impNew.getTipo());
        impOld.setPorcentaje(impNew.getPorcentaje());
        impOld.setNombre(impNew.getNombre());
        return impOld;
    }

    /**
     * habilitamos o deshabilitamos un producto por oferta
     *
     */
    @Override
    public int enabledOrdisabledOfertasProductos(Set<Impuesto> impuestos, boolean value) {
        Session session = sessionFactory.getCurrentSession();
        Set<Integer> setIds = new HashSet<>();
        for (Impuesto contImpuestos : impuestos) {
            setIds.add(contImpuestos.getId());
        }
        String updateHQL = " UPDATE ContImpuestos c SET c.activo = :valueactivo"
                + " WHERE  c.id in (:setIds)";
        int updatedEntities = session.createQuery(updateHQL)
                .setBoolean("valueactivo", value)
                .setParameterList("setIds", setIds)
                .executeUpdate();
        return updatedEntities;
    }
}
