package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.OfertasDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Ofertas;
import ar.com.jsuper.domain.OfertasProducto;
import ar.com.jsuper.domain.utils.FilterOferta;
import ar.com.jsuper.domain.utils.FilterOfertaProductos;
import ar.com.jsuper.security.TenantContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

@Repository
public class OfertasDAOImpl extends GenericDAOImpl<Ofertas, Integer> implements OfertasDAO {

    @Override
    public Ofertas insert(Ofertas oferta){
        Session session = sessionFactory.getCurrentSession();
        /*###########################Control por APP############################*/
        oferta.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
        /*######################################################################*/
        session.save(oferta);
        return oferta;
    }

    @Override
    public Set<OfertasProducto> insertOfertaProductos(Ofertas oferta) {
        Session session = sessionFactory.getCurrentSession();
        Set<OfertasProducto> ofertasProducto = oferta.getOfertaProductos();
        Set<OfertasProducto> ofertasProductoResponse = new HashSet<>();
        Ofertas o = new Ofertas();
        o.setId(oferta.getId());
        // primero verificamos que exista la oferta,este vigente  y  no agregar prod que ya esten agregados
        Ofertas ofertaDB = this.getOfertaAndProductos(oferta.getId());
        Set<OfertasProducto> ofertasProductoBD = ofertaDB.getOfertaProductos();
        for (OfertasProducto op : ofertasProducto) {
            op.setOferta(o);
            if (!ofertasProductoBD.isEmpty()) {
                for (OfertasProducto opBD : ofertasProductoBD) {
                    if (!Objects.equals(op.getProducto().getId(), opBD.getProducto().getId())) {
                        session.save(op);
                        ofertasProductoResponse.add(op);
                    }
                }
            } else {
                session.save(op);
                ofertasProductoResponse.add(op);
            }
        }
        return ofertasProductoResponse;
    }

    @Override
    public List<Ofertas> getAllVigentes() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaOferta = session.createCriteria(Ofertas.class, "oferta");
        criteriaOferta.setFetchMode("oferta.ofertaProductos", FetchMode.JOIN);
        Criteria criteriaOfertaProductos = criteriaOferta.createCriteria("oferta.ofertaProductos", "ofertaProductos", JoinType.LEFT_OUTER_JOIN);

        criteriaOfertaProductos.setFetchMode("producto", FetchMode.JOIN);
        // restriccion para activos o no activos
        Criterion cActivo = Restrictions.eq("activo", true);
        criteriaOferta.add(cActivo);

        Criterion cProductoActivo = Restrictions.eq("estado", true);
        criteriaOfertaProductos.add(cProductoActivo);

        // restriccion para las fechas, solo se mostraran oferas validas por el momento
        criteriaOferta.add(Restrictions.sqlRestriction(" NOW() between {alias}.fecha_desde and {alias}.fecha_hasta"));
        /*###########################Control por APP############################*/
        criteriaOferta.setFetchMode("oferta.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaOferta.add(c1);
        /*######################################################################*/
        ArrayList<Ofertas> lista = null;
        lista = (ArrayList<Ofertas>) criteriaOferta.list();
        return lista;
    }

    @Override
    public Pagination<Ofertas> getOfertasBypage(FilterOferta filterOferta, int page, int pageSize, String fieldOrder, boolean reverse) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaOferta = session.createCriteria(Ofertas.class, "oferta");
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaOferta.setProjection(idCountProjection);
        Integer activo = filterOferta.getActivo();
        Integer vigente = filterOferta.getVigente();

        if (filterOferta.getNombre() != null && !filterOferta.getNombre().trim().equals("")) {
            Criterion c1 = Restrictions.like("nombre", filterOferta.getNombre(), MatchMode.ANYWHERE);
            criteriaOferta.add(c1);
        }
        // restriccion para activos o no activos
        if (activo != null && !activo.equals("") && activo != 2) {
            Criterion cActivo = Restrictions.eq("activo", (activo == 1));
            criteriaOferta.add(cActivo);
        }
        if (vigente != null && !vigente.equals("")) {
            if (vigente == 1) {
                // restriccion para las fechas, solo se mostraran oferas validas por el momento
                criteriaOferta.add(Restrictions.sqlRestriction(" NOW() between {alias}.fecha_desde and {alias}.fecha_hasta"));
            }
            if (vigente == 0) {
                // restriccion para las fechas, solo se mostraran oferas validas por el momento
                criteriaOferta.add(Restrictions.sqlRestriction("{alias}.fecha_hasta < NOW() OR {alias}.fecha_desde > NOW()"));
            }
        }
        /*###########################Control por APP############################*/
        criteriaOferta.setFetchMode("oferta.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaOferta.add(c1);
        /*######################################################################*/
        Integer totalResultCount = ((Long) criteriaOferta.uniqueResult()).intValue();

        if (reverse) {
            criteriaOferta.addOrder(Order.asc(fieldOrder.trim()));
        } else {
            criteriaOferta.addOrder(Order.desc(fieldOrder.trim()));
        }
        criteriaOferta.setProjection(Projections.distinct(Projections.property("id")));
        criteriaOferta.setFirstResult((page - 1) * pageSize);
        criteriaOferta.setMaxResults(pageSize);
        List uniqueSubList = criteriaOferta.list();
        criteriaOferta.setProjection(null);
        criteriaOferta.setFirstResult(0);
        criteriaOferta.setMaxResults(Integer.MAX_VALUE);
        criteriaOferta.add(Restrictions.in("id", uniqueSubList));
        criteriaOferta.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<Ofertas> lista = null;
        if (totalResultCount > 0) {
            lista = new ArrayList<>(criteriaOferta.list());
        }
        Pagination<Ofertas> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    /**
     * habilitamos o deshabilitamos
     *
     */
    @Override
    public int enabledOrdisabled(Set<Ofertas> ofertas, boolean value) {
        Session session = sessionFactory.getCurrentSession();
        Set<Integer> setIds = new HashSet<>();
        for (Ofertas oferta : ofertas) {
            setIds.add(oferta.getId());
        }
        String updateHQL = " UPDATE Ofertas o SET o.activo = :valueactivo"
                + " WHERE  o.id in (:idsOfertas)";
        int updatedEntities = session.createQuery(updateHQL)
                .setBoolean("valueactivo", value)
                .setParameterList("idsOfertas", setIds)
                .executeUpdate();
        return updatedEntities;
    }

    /**
     * habilitamos o deshabilitamos un producto por oferta
     *
     */
    @Override
    public int enabledOrdisabledOfertasProductos(Set<OfertasProducto> ofertasProductos, boolean value) {
        Session session = sessionFactory.getCurrentSession();
        Set<Integer> setIds = new HashSet<>();
        for (OfertasProducto ofertasProducto : ofertasProductos) {
            setIds.add(ofertasProducto.getId());
        }
        String updateHQL = " UPDATE OfertasProducto o SET o.estado = :valueactivo"
                + " WHERE  o.id in (:idsOfertas)";
        int updatedEntities = session.createQuery(updateHQL)
                .setBoolean("valueactivo", value)
                .setParameterList("idsOfertas", setIds)
                .executeUpdate();
        return updatedEntities;
    }

    /**
     * cambiamos la prioridad
     *
     */
    @Override
    public int updatePriridad(Integer idOferta, Integer prioridad) {
        Session session = sessionFactory.getCurrentSession();
        String updateHQL = " UPDATE Ofertas o SET o.prioridad = :prioridad"
                + " WHERE  o.id =:idOferta and app.id =:app";
        int updatedEntities = session.createQuery(updateHQL)
                .setInteger("prioridad", prioridad)
                .setInteger("idOferta", idOferta)
                .setInteger("app", TenantContext.getCurrentTenant())
                .executeUpdate();
        return updatedEntities;
    }

    /**
     * cambiamos la fecha hasta de una oferta
     *
     */
    @Override
    public Ofertas updateFechaHasta(Integer idOferta, Date fechaHasta) {
        Session session = sessionFactory.getCurrentSession();
        Ofertas oferta = session.load(Ofertas.class, idOferta);
        oferta.setFechaHasta(fechaHasta);
        session.update(oferta);
        return oferta;
    }

    @Override
    public Ofertas get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Ofertas.class, "oferta");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        c.add(Restrictions.sqlRestriction(" NOW() between {alias}.fecha_desde and {alias}.fecha_hasta"));
        Criterion cActivo = Restrictions.eq("activo", true);
        c.add(cActivo);

        c1 = Restrictions.eq("id", id);
        c.add(c1);
        Ofertas oferta = (Ofertas) c.uniqueResult();
        return oferta;
    }

    @Override
    public Ofertas getOfertaAndProductos(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Ofertas.class, "oferta");
        c.setFetchMode("oferta.ofertaProductos", FetchMode.JOIN);
        c.setFetchMode("oferta.ofertaProductos.producto", FetchMode.JOIN);

        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        c.add(Restrictions.sqlRestriction(" NOW() between {alias}.fecha_desde and {alias}.fecha_hasta"));
        Criterion cActivo = Restrictions.eq("activo", true);
        c.add(cActivo);

        c1 = Restrictions.eq("id", id);
        c.add(c1);
        Ofertas oferta = (Ofertas) c.uniqueResult();
        return oferta;
    }

    @Override
    public Pagination<OfertasProducto> getOfertasProductosBypage(FilterOfertaProductos filterOferta, int page, int pageSize, String fieldOrder, boolean reverse) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaOfertaProductos = session.createCriteria(OfertasProducto.class, "ofertaProducto");
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaOfertaProductos.setProjection(idCountProjection);
        Integer activo = filterOferta.getActivo();
        Criterion c1;
        Criteria criteriaProducto = criteriaOfertaProductos.createCriteria("ofertaProducto.producto", "producto", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaOferta = criteriaOfertaProductos.createCriteria("ofertaProducto.oferta", "oferta", JoinType.LEFT_OUTER_JOIN);
//        criteriaOfertaProductos.setFetchMode("oferta", FetchMode.JOIN);
        if (!Objects.isNull(filterOferta.getIdOferta())) {
            if (filterOferta.getIdOferta() > 0) {
                Criterion cId = Restrictions.eq("id", filterOferta.getIdOferta());
                criteriaOferta.add(cId);
            }
        }
        if (filterOferta.getNombre() != null && !filterOferta.getNombre().trim().equals("")) {
            String nombreFinal = filterOferta.getNombre();
            if (nombreFinal != null && !nombreFinal.trim().equals("")) {// este trim se le agrega al final
                String[] nombresArray = nombreFinal.split("\\s+");
                Conjunction conjunctionNombresFinal = Restrictions.conjunction();
                for (String nombre : nombresArray) {
                    if (!nombre.equals("")) {
                        conjunctionNombresFinal.add(Restrictions.sqlRestriction("{alias}.nombre_final like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
                    }
                }
                criteriaProducto.add(conjunctionNombresFinal);
            }
//            c1 = Restrictions.like("nombre", filterOferta.getNombreProducto(), MatchMode.ANYWHERE);
//            criteriaProducto.add(c1);
        }
        // restriccion para activos o no activos
        if (activo != null && !activo.equals("") && activo != 2) {
            Criterion cActivo = Restrictions.eq("estado", (activo == 1));
            criteriaOfertaProductos.add(cActivo);
        }
        /*###########################Control por APP############################*/
        criteriaOferta.setFetchMode("oferta.app", FetchMode.JOIN);
        c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaOferta.add(c1);
        /*######################################################################*/
        Integer totalResultCount = ((Long) criteriaOfertaProductos.uniqueResult()).intValue();

        if (reverse) {
            criteriaOfertaProductos.addOrder(Order.asc(fieldOrder.trim()));
        } else {
            criteriaOfertaProductos.addOrder(Order.desc(fieldOrder.trim()));
        }
        criteriaOfertaProductos.setProjection(Projections.distinct(Projections.property("id")));
        criteriaOfertaProductos.setFirstResult((page - 1) * pageSize);
        criteriaOfertaProductos.setMaxResults(pageSize);
        List uniqueSubList = criteriaOfertaProductos.list();
        criteriaOfertaProductos.setProjection(null);
        criteriaOfertaProductos.setFirstResult(0);
        criteriaOfertaProductos.setMaxResults(Integer.MAX_VALUE);
        criteriaOfertaProductos.add(Restrictions.in("id", uniqueSubList));
        criteriaOfertaProductos.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<OfertasProducto> lista = null;
        if (totalResultCount > 0) {
            lista = new ArrayList<>(criteriaOfertaProductos.list());
        }
        Pagination<OfertasProducto> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }
}
