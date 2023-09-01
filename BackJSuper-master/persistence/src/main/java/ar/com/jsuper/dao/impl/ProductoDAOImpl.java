package ar.com.jsuper.dao.impl;

//import ar.com.jsuper.dao.FamiliasDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.jsuper.domain.*;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.FamiliasUtil;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.utils.FilterProducto;

import java.util.Arrays;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StandardBasicTypes;
import ar.com.jsuper.dao.ProductoDAO;
import ar.com.jsuper.domain.utils.Historial;
import ar.com.jsuper.domain.utils.PrecioFamiliasProducto;
import ar.com.jsuper.domain.utils.PrecioProducto;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.utils.NumbersUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.springframework.dao.DataIntegrityViolationException;

@Repository
public class ProductoDAOImpl extends GenericDAOImpl<Producto, Integer> implements ProductoDAO {

    @Override
    public Pagination<Producto> getDetalleProductoByPage(FilterProducto filterProducto, int page, int pageSize, String fieldOrder,
                                                         boolean reverse, String type) {
        Integer activo = filterProducto.getActivo();
        Integer tipo = filterProducto.getTipo();
        String codigoEspecial = filterProducto.getProducto().getCodigoEspecial();
        Producto productoFilter = filterProducto.getProducto();
        Set<Familias> familiasFilter = filterProducto.getFamilias();
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "producto");
        Criteria criteriaProductos = null;
        Criteria criteriaCodigoBarras = null;
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaDetalleProductos.setProjection(idCountProjection);
        //creamos un alias, tiene que ser alias porque sse tiene que ejecutar sobre el el mismo biuld de criteria
        // sino mappee al pasar a dto vuelve a ejecutar todo, luego de  cambiar noseque funciona con createcriteria        
        criteriaProductos = criteriaDetalleProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        criteriaProductos.setFetchMode("productoPadre.familia", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("productosCompuestos", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("productosCompuestos.producto", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("unidad", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("imagenProducto", FetchMode.JOIN);
        type = type.trim();
        Conjunction conjunctionGral = Restrictions.conjunction();
        Disjunction disjunctionGral = Restrictions.disjunction();
        Disjunction disjunctionNombre = Restrictions.disjunction();
        if (productoFilter != null) {
            String nombreFinal = "";
            if (productoFilter.getProductoPadre() != null) {
                nombreFinal = productoFilter.getProductoPadre().getNombre().trim() + " " + productoFilter.getNombre().trim();
            }
            // restriccion para el nombre
            if (nombreFinal != null && !nombreFinal.trim().equals("")) {// este trim se le agrega al final
                String[] nombresArray = nombreFinal.split("\\s+");
                Conjunction conjunctionNombres = Restrictions.conjunction();
                Conjunction conjunctionNombresFinal = Restrictions.conjunction();
                Disjunction disjunctionNombres = Restrictions.disjunction();
                for (String nombre : nombresArray) {
                    if (!nombre.equals("")) {
                        conjunctionNombres.add(Restrictions.sqlRestriction("concat({alias}.nombre, this_.nombre) like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
                    }
                }
                for (String nombre : nombresArray) {
                    if (!nombre.equals("")) {
                        conjunctionNombresFinal.add(Restrictions.sqlRestriction("this_.nombre_final like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
                    }
                }
                disjunctionNombres.add(conjunctionNombres);
                disjunctionNombres.add(conjunctionNombresFinal);
                if (type.equals("OR")) {
                    disjunctionGral.add(disjunctionNombres);
                } else {
                    conjunctionGral.add(disjunctionNombres);
                }
            }
            // restriccion para activos o no activos
            if (activo != null && !activo.equals("") && activo != 2) {
                Criterion cActivo = Restrictions.eq("activo", (activo == 1));
                criteriaDetalleProductos.add(cActivo);
            }
            // restriccion para el tipo de prod si es simple, compuesto o todos
            if (tipo != null && !tipo.equals("") && tipo != 0) {
                if (tipo == 1 || tipo == 2) {
                    Criterion cTipo = Restrictions.eq("tipo", tipo);
                    criteriaDetalleProductos.add(cTipo);
                }
            }
            //restriccion para el codigo especial de producto
            if (!Objects.isNull(codigoEspecial)) {
                if (!codigoEspecial.trim().equals("")) {
                    Criterion cEspecial = Restrictions.eq("codigoEspecial", codigoEspecial.trim());
                    criteriaDetalleProductos.add(cEspecial);
                }
            }

        }
        if (familiasFilter != null) {
            criteriaProductos.createAlias("productoPadre.familia", "familia");
            if (!familiasFilter.isEmpty()) {
                // primero traemos  todos los hijos de la familia
                String idChildsFamilia = "";
                Integer i = 0;
                for (Familias fam : familiasFilter) {
                    Familias familia = session.get(Familias.class, fam.getId());
                    if (i == 0) {
                        idChildsFamilia += FamiliasUtil.getIdOfChildsByFamilia(familia);
                    } else {
                        idChildsFamilia += "-" + FamiliasUtil.getIdOfChildsByFamilia(familia);
                    }
                    i++;
                }
                //a l string lo pasamos a array, que contiene los ids
                String[] arrayIdsChildsFamilia = idChildsFamilia.split("-");
                // para que no nos haga consulta doble, con set eliminamos los duplicados del array
                Set<String> mySetIdChilds = new HashSet<String>(Arrays.asList(arrayIdsChildsFamilia));
                Disjunction disjunctionFamilias = Restrictions.disjunction();
                for (String id : mySetIdChilds) {
                    disjunctionFamilias.add(Restrictions.eq("familia.id", Integer.parseInt(id)));
                }
                if (type.equals("OR")) {
                    disjunctionGral.add(disjunctionFamilias);
                } else {
                    conjunctionGral.add(disjunctionFamilias);
                }
            }
        }
        // not null
        // restriccion para los codigos de barra
        criteriaCodigoBarras = criteriaDetalleProductos.createAlias("producto.codigoBarras", "codigoBarras", JoinType.LEFT_OUTER_JOIN);
        if (productoFilter != null) {
            if (productoFilter.getCodigoBarras() != null) {
                for (CodigoBarras codigoBarra : productoFilter.getCodigoBarras()) {
                    if (codigoBarra != null) {
                        if (!"".equals(codigoBarra.getCodigo().trim())) {
                            String[] codigosArray = codigoBarra.getCodigo().trim().split("\\s+");
                            Conjunction conjunctionCodigos = Restrictions.conjunction();
                            for (String codigo : codigosArray) {
                                conjunctionCodigos.add(Restrictions.like("codigoBarras.codigo", "%" + codigo + "%"));
                            }
                            if (type.equals("OR")) {
                                disjunctionGral.add(conjunctionCodigos);
                            } else {
                                conjunctionGral.add(conjunctionCodigos);
                            }
                        }
                    }
                }
            }
        }
        if (type.trim().equals("OR")) {
            criteriaProductos.add(disjunctionGral);
        } else {
            criteriaProductos.add(conjunctionGral);
        }
        //--------------------filtros especiales para otras paginas----------------------------------------------

        Boolean joinSucursal = false;
        Criteria criteriaStock = null;
        if (!Objects.isNull(filterProducto.getSucursales())) {
            if (!filterProducto.getSucursales().isEmpty()) {
                joinSucursal = true;
                // secrea alias porque por alg razon el create criteria no me hace el join 
                Disjunction disjunctionSucursal = Restrictions.disjunction();
                criteriaStock = criteriaDetalleProductos.createAlias("producto.stockSucursales", "ss", JoinType.LEFT_OUTER_JOIN);
                criteriaStock.createAlias("ss.sucursal", "sss", JoinType.LEFT_OUTER_JOIN);
                filterProducto.getSucursales().forEach((sucursal) -> {
                    disjunctionSucursal.add(Restrictions.eq("ss.sucursal", sucursal));
                });
                criteriaStock.add(disjunctionSucursal);
            }
        }
        if (!Objects.isNull(filterProducto.getShowStock())) {
            if (filterProducto.getShowStock()) {
                if (!joinSucursal) {
                    joinSucursal = true;
                    criteriaStock = criteriaDetalleProductos.createAlias("producto.stockSucursales", "ss", JoinType.LEFT_OUTER_JOIN);
                    criteriaStock.createAlias("ss.sucursal", "sss", JoinType.LEFT_OUTER_JOIN);
                }
            }
        }
        if (!Objects.isNull(filterProducto.getFilterStock())) {
            if (filterProducto.getFilterStock() == 1 || filterProducto.getFilterStock() == 2) {
                if (!joinSucursal) {
                    criteriaStock = criteriaDetalleProductos.createAlias("producto.stockSucursales", "ss", JoinType.LEFT_OUTER_JOIN);
                    criteriaStock.createAlias("ss.sucursal", "sss", JoinType.LEFT_OUTER_JOIN);
                }
            }
            if (filterProducto.getFilterStock() == 1) {
                criteriaStock.add(Restrictions.leProperty("ss.stock", "ss.stockMinimo"));
            }
            if (filterProducto.getFilterStock() == 2) {
                criteriaStock.add(Restrictions.leProperty("ss.stock", "ss.puntoReposicion"));
            }
            if (filterProducto.getFilterStock() == 3) {
                criteriaStock.add(Restrictions.or(Restrictions.le("ss.stock", BigDecimal.ZERO), Restrictions.isNull("ss.stock")));
            }
        }
        //------------------------------------------------------------------------------------------------------
        /*###########################Control por APP############################*/
        criteriaProductos.setFetchMode("productoPadre.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductos.add(c1);
        /*######################################################################*/
        // setup criteria, joins etc here
        Integer totalResultCount = ((Long) criteriaDetalleProductos.uniqueResult()).intValue();

        if (reverse) {
            criteriaDetalleProductos.addOrder(Order.asc(fieldOrder.trim()));
        } else {
            criteriaDetalleProductos.addOrder(Order.desc(fieldOrder.trim()));
        }
        criteriaDetalleProductos.setProjection(Projections.distinct(Projections.property("id")));
        if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
            if (totalResultCount > ((page - 1) * pageSize)) {
                criteriaDetalleProductos.setFirstResult((page - 1) * pageSize);
            } else {
                criteriaDetalleProductos.setFirstResult(0);
            }
            criteriaDetalleProductos.setMaxResults(pageSize);
        }
        List uniqueSubList = criteriaDetalleProductos.list();
        criteriaDetalleProductos.setProjection(null);
        criteriaDetalleProductos.setFirstResult(0);
        criteriaDetalleProductos.setMaxResults(Integer.MAX_VALUE);
        criteriaDetalleProductos.add(Restrictions.in("id", uniqueSubList));
        criteriaDetalleProductos.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<Producto> lista;
        if (totalResultCount <= 0) {
            lista = null;
        } else {
            lista = new ArrayList<>(criteriaDetalleProductos.list());
        }
        Pagination<Producto> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    @Override
    public Producto getProducto(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "detalleProducto");
        criteriaDetalleProductos.setFetchMode("codigoBarras", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("productoPadre", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("imagenProducto", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("precios", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("precios.tipoPrecio", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales.sucursales", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("stockSucursales.ubicacionStocks", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("productosCompuestos", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("proveedores", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("unidad", FetchMode.JOIN);
        criteriaDetalleProductos.add(Restrictions.eq("id", id));
        Producto detalleProductos = (Producto) criteriaDetalleProductos.uniqueResult();
        return detalleProductos;
    }

    @Override
    public List<Producto> getProductosMin() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "detalleProducto");
        criteriaDetalleProductos.setFetchMode("productoPadre", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("productoPadre.familia", FetchMode.JOIN);
        return criteriaDetalleProductos.list();
    }

    @Override
    public List<Producto> getProductosMin(Set<Integer> ids) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "detalleProducto");
        criteriaDetalleProductos.setFetchMode("codigoBarras", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("unidad", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("productoPadre", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("productoPadre.familia", FetchMode.JOIN);
        Disjunction disjunctionId = Restrictions.disjunction();
        for (Integer id : ids) {
            disjunctionId.add(Restrictions.eq("id", id));
        }
        criteriaDetalleProductos.add(disjunctionId);
        return criteriaDetalleProductos.list();
    }

    @Override
    public Producto getProductoMin(Integer idProducto) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaProductos = session.createCriteria(Producto.class, "detalleProducto");
        criteriaProductos.setFetchMode("codigoBarras", FetchMode.JOIN);
        criteriaProductos.setFetchMode("productoPadre", FetchMode.JOIN);
        criteriaProductos.setFetchMode("productoPadre.familia", FetchMode.JOIN);
        criteriaProductos.add(Restrictions.eq("id", idProducto));
        return (Producto) criteriaProductos.uniqueResult();
    }

    @Override
    public List<Producto> getProductosMinByFamilia(Integer idFamilia) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "detalleProducto");
        Criteria criteriaProductos = criteriaDetalleProductos.createCriteria("detalleProducto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        criteriaProductos.createAlias("productoPadre.familia", "familia");
        criteriaProductos.add(Restrictions.eq("familia.id", idFamilia));
        return criteriaDetalleProductos.list();
    }

    @Override
    public List<Producto> getProductosMinByProdPadre(Integer idProductoPadre) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "detalleProducto");
        Criteria criteriaProductos = criteriaDetalleProductos.createCriteria("detalleProducto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        criteriaProductos.add(Restrictions.eq("id", idProductoPadre));
        return criteriaDetalleProductos.list();
    }

    @Override
    public List<Producto> getProductoByNameOrCodEspecial(String query, Integer activo) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "producto");
        Criteria criteriaProductos = null;
        Criteria criteriaCodigoBarras = null;
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaDetalleProductos.setProjection(idCountProjection);
        //creamos un alias, tiene que ser alias porque sse tiene que ejecutar sobre el el mismo biuld de criteria
        // sino mappee al pasar a dto vuelve a ejecutar todo, luego de  cambiar noseque funciona con createcriteria        
        criteriaProductos = criteriaDetalleProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales.sucursales", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("unidad", FetchMode.JOIN);

        /*###########################Control por APP############################*/
        criteriaProductos.setFetchMode("productoPadre.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductos.add(c1);
        /*######################################################################*/

        criteriaCodigoBarras = criteriaDetalleProductos.createAlias("producto.codigoBarras", "codigoBarras", JoinType.LEFT_OUTER_JOIN);

        criteriaDetalleProductos.setProjection(null);
        criteriaDetalleProductos.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//        ArrayList<Producto> lista;
        // restriccion para el nombre
        String nombreFinal = query;
        if (nombreFinal != null && !nombreFinal.equals("")) {
            String[] nombresArray = nombreFinal.split("\\s+");
            Conjunction disjunctionNombres = Restrictions.conjunction();
            for (String nombre : nombresArray) {
                if (!nombre.equals("")) {
                    disjunctionNombres.add(Restrictions.sqlRestriction("concat({alias}.nombre, this_.nombre) like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
                }
            }
//            criteriaProductos.add(disjunctionNombres);
            // restriccion para codigos  especiales 
            Criterion cCodEspecial = Restrictions.or(Restrictions.like("codigoEspecial", query, MatchMode.START), disjunctionNombres);
            criteriaDetalleProductos.add(cCodEspecial);
        }

        // restriccion para activos o no activos
        if (activo != null && activo != 2) {
            Criterion cActivo = Restrictions.eq("activo", (activo == 1));
            criteriaDetalleProductos.add(cActivo);
        }
        criteriaDetalleProductos.setProjection(null);
        criteriaDetalleProductos.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//        lista = new ArrayList<>(criteriaDetalleProductos.list());
//        return lista;
        return criteriaDetalleProductos.list();
    }

    @Override
    public Pagination<Producto> getWithouFilterPagination(int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "producto");
        Criteria criteriaProductos = null;
        Criteria criteriaCodigoBarras = null;
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaDetalleProductos.setProjection(idCountProjection);
        //creamos un alias, tiene que ser alias porque sse tiene que ejecutar sobre el el mismo biuld de criteria
        // sino mappee al pasar a dto vuelve a ejecutar todo, luego de  cambiar noseque funciona con createcriteria        
        criteriaProductos = criteriaDetalleProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales.sucursales", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("unidad", FetchMode.JOIN);
        Criterion cActivo = Restrictions.eq("activo", true);
        criteriaDetalleProductos.add(cActivo);
        /*###########################Control por APP############################*/
        criteriaProductos.setFetchMode("productoPadre.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductos.add(c1);
        /*######################################################################*/
        Integer totalResultCount = ((Long) criteriaDetalleProductos.uniqueResult()).intValue();
        // setup criteria, joins etc here
        criteriaDetalleProductos.addOrder(Order.desc("id"));
        criteriaDetalleProductos.setProjection(Projections.distinct(Projections.property("id")));
        if (page != 0 && pageSize != 0) {// si viene en 0 los dos param devuelve todo sin paginar
            if (totalResultCount > ((page - 1) * pageSize)) {
                criteriaDetalleProductos.setFirstResult((page - 1) * pageSize);
            } else {
                criteriaDetalleProductos.setFirstResult(0);
            }
            criteriaDetalleProductos.setMaxResults(pageSize);
        }
        List uniqueSubList = criteriaDetalleProductos.list();
        criteriaDetalleProductos.setProjection(null);
        criteriaDetalleProductos.setFirstResult(0);
        criteriaDetalleProductos.setMaxResults(Integer.MAX_VALUE);
        criteriaDetalleProductos.add(Restrictions.in("id", uniqueSubList));
        criteriaDetalleProductos.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<Producto> lista;
        if (totalResultCount <= 0) {
            lista = null;
        } else {
            lista = new ArrayList<>(criteriaDetalleProductos.list());
        }
        Pagination<Producto> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    @Override
    public List<Producto> getProductoByName(String query, Integer activo) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "producto");
        Criteria criteriaProductos = null;
        Criteria criteriaCodigoBarras = null;
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaDetalleProductos.setProjection(idCountProjection);
        //creamos un alias, tiene que ser alias porque sse tiene que ejecutar sobre el el mismo biuld de criteria
        // sino mappee al pasar a dto vuelve a ejecutar todo, luego de  cambiar noseque funciona con createcriteria        
        criteriaProductos = criteriaDetalleProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales.sucursales", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("unidad", FetchMode.JOIN);

        /*###########################Control por APP############################*/
        criteriaProductos.setFetchMode("productoPadre.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductos.add(c1);
        /*######################################################################*/

        criteriaCodigoBarras = criteriaDetalleProductos.createAlias("producto.codigoBarras", "codigoBarras", JoinType.LEFT_OUTER_JOIN);

        criteriaDetalleProductos.setProjection(null);
        criteriaDetalleProductos.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        // restriccion para el nombre
        String nombreFinal = query;
        if (nombreFinal != null && !nombreFinal.equals("")) {
            String[] nombresArray = nombreFinal.split("\\s+");
            Conjunction disjunctionNombres = Restrictions.conjunction();
            for (String nombre : nombresArray) {
                if (!nombre.equals("")) {
                    disjunctionNombres.add(Restrictions.sqlRestriction("concat({alias}.nombre, this_.nombre) like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
                }
            }
            criteriaProductos.add(disjunctionNombres);
        }
        // restriccion para activos o no activos
        if (activo != null && activo != 2) {
            Criterion cActivo = Restrictions.eq("activo", (activo == 1));
            criteriaDetalleProductos.add(cActivo);
        }
        criteriaDetalleProductos.setProjection(null);
        criteriaDetalleProductos.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//        lista = new ArrayList<>(criteriaDetalleProductos.list());
//        return lista;
        return criteriaDetalleProductos.list();
    }

    @Override
    public List<Producto> getProductoByBarCode(String query, Integer activo, Integer like, Integer tipoBarCode) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "producto");
        Criteria criteriaProductos = null;
        Criteria criteriaCodigoBarras = null;
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaDetalleProductos.setProjection(idCountProjection);
        //creamos un alias, tiene que ser alias porque sse tiene que ejecutar sobre el el mismo biuld de criteria
        // sino mappee al pasar a dto vuelve a ejecutar todo, luego de  cambiar noseque funciona con createcriteria        
        criteriaProductos = criteriaDetalleProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
//        criteriaProductos = criteriaDetalleProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
//        criteriaProductos.setFetchMode("productoPadre.familia", FetchMode.JOIN);
//        criteriaProductos.setFetchMode("productoPadre.rubro", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("imagenProducto", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("productosCompuestos", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("productosCompuestos.producto", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("precios", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("precios.tipoPrecio", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales.sucursales", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("stockSucursales.ubicacionStocks", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("unidad", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("proveedores", FetchMode.JOIN);

        criteriaCodigoBarras = criteriaDetalleProductos.createAlias("producto.codigoBarras", "codigoBarras", JoinType.LEFT_OUTER_JOIN);
        if (tipoBarCode == 1) {
            if (like == 0) {
                criteriaCodigoBarras.add(Restrictions.eq("codigoBarras.codigo", query));
            }
            if (like == 1) {
                criteriaCodigoBarras.add(Restrictions.like("codigoBarras.codigo", "%" + query + "%"));
            }
            criteriaCodigoBarras.add(Restrictions.eq("codigoBarras.tipo", 1));
        }
        if (tipoBarCode == 2) {
            criteriaCodigoBarras.add(Restrictions.eq("codigoBarras.codigo", query));
            criteriaCodigoBarras.add(Restrictions.eq("codigoBarras.tipo", 2));
        }

// restriccion para activos o no activos
        if (activo != null && activo != 2) {
            Criterion cActivo = Restrictions.eq("activo", (activo == 1));
            criteriaDetalleProductos.add(cActivo);
        }
        /*###########################Control por APP############################*/
        criteriaProductos.setFetchMode("productoPadre.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductos.add(c1);
        /*######################################################################*/
        criteriaDetalleProductos.setProjection(Projections.distinct(Projections.property("id")));
        criteriaDetalleProductos.setProjection(null);
        criteriaDetalleProductos.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//        ArrayList<Producto> lista;
//        lista = new ArrayList<Producto>(criteriaDetalleProductos.list());
//        lista = criteriaDetalleProductos.list();
//        return lista;
        return criteriaDetalleProductos.list();
    }

    @Override
    public Pagination<Producto> getProductoByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "producto");
        Criteria criteriaProductos = null;
        Criteria criteriaCodigoBarras = null;
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaDetalleProductos.setProjection(idCountProjection);
        //creamos un alias, tiene que ser alias porque sse tiene que ejecutar sobre el el mismo biuld de criteria
        // sino mappee al pasar a dto vuelve a ejecutar todo, luego de  cambiar noseque funciona con createcriteria        
        criteriaProductos = criteriaDetalleProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        criteriaProductos.setFetchMode("productoPadre.familia", FetchMode.JOIN);
//        criteriaProductos.setFetchMode("productoPadre.rubro", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("imagenProducto", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("productosCompuestos", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("productosCompuestos.producto", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("precios", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("precios.tipoPrecio", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("stockSucursales", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("stockSucursales.sucursales", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("stockSucursales.ubicacionStocks", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("unidad", FetchMode.JOIN);
//        criteriaDetalleProductos.setFetchMode("proveedores", FetchMode.JOIN);

        criteriaCodigoBarras = criteriaDetalleProductos.createAlias("producto.codigoBarras", "codigoBarras", JoinType.LEFT_OUTER_JOIN);

        // verificamos si el codigo de barar comienza con asterisco
        Conjunction conjunctionBarCode = Restrictions.conjunction();
        Criterion criterionCodBarra = null;
        Criterion criterionCodBarraTipo = null;
        Boolean codeEspecial = false;
        query = query.trim();
        if (query.startsWith("*")) {
            codeEspecial = true;
            query = query.substring(1);
            criterionCodBarraTipo = Restrictions.eq("codigoBarras.tipo", 2);
        } else {
            criterionCodBarraTipo = Restrictions.eq("codigoBarras.tipo", 1);
        }
//        criterionCodBarra = Restrictions.eq("codigoBarras.codigo", query);
        criterionCodBarra = Restrictions.like("codigoBarras.codigo", "%" + query + "%");
        conjunctionBarCode.add(criterionCodBarra);
        conjunctionBarCode.add(criterionCodBarraTipo);
        Conjunction disjunctionNombres = new Conjunction();
        if (!codeEspecial) {
            String nombreFinal = query;
            if (nombreFinal != null && !nombreFinal.equals("")) {
                String[] nombresArray = nombreFinal.split("\\s+");
                disjunctionNombres = Restrictions.conjunction();
                for (String nombre : nombresArray) {
                    if (!nombre.equals("")) {
                        disjunctionNombres.add(Restrictions.sqlRestriction("concat({alias}.nombre, this_.nombre) like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
                    }
                }
            }
        }
        if (codeEspecial) {
            criteriaProductos.add(Restrictions.or(conjunctionBarCode));
        } else {
            criteriaProductos.add(Restrictions.or(conjunctionBarCode, disjunctionNombres));
        }
        // solamente para productos activos
        Criterion cActivo = Restrictions.eq("activo", true);
        criteriaDetalleProductos.add(cActivo);
        /*###########################Control por APP############################*/
        criteriaProductos.setFetchMode("productoPadre.app", FetchMode.JOIN);
        Criterion c2 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductos.add(c2);
        /*######################################################################*/
        // setup criteria, joins etc here
        Integer totalResultCount = ((Long) criteriaDetalleProductos.uniqueResult()).intValue();
        if (reverse) {
            criteriaDetalleProductos.addOrder(Order.asc(fieldOrder.trim()));
        } else {
            criteriaDetalleProductos.addOrder(Order.desc(fieldOrder.trim()));
        }
        criteriaDetalleProductos.setProjection(Projections.distinct(Projections.property("id")));
        criteriaDetalleProductos.setFirstResult((page - 1) * pageSize);
        criteriaDetalleProductos.setMaxResults(pageSize);
        List uniqueSubList = criteriaDetalleProductos.list();
        criteriaDetalleProductos.setProjection(null);
        criteriaDetalleProductos.setFirstResult(0);
        criteriaDetalleProductos.setMaxResults(Integer.MAX_VALUE);
        criteriaDetalleProductos.add(Restrictions.in("id", uniqueSubList));
        criteriaDetalleProductos.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<Producto> lista;
        if (totalResultCount <= 0) {
            lista = null;
        } else {
            lista = new ArrayList<>(criteriaDetalleProductos.list());
        }
        Pagination<Producto> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    @Override
    public Set<PrecioProductos> getPreciosActivos(ProductoPadre p) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PrecioProductos.class, "pp");
        Criterion c1 = Restrictions.eq("pp.activo", Boolean.TRUE);
        c.add(c1);
        c1 = Restrictions.eq("pp.producto", p);
        c.add(c1);
        Set<PrecioProductos> listaSet = new HashSet<PrecioProductos>(c.list());
        return listaSet;
    }

    @Override
    public Producto insert(Producto producto) {
        Session session = sessionFactory.getCurrentSession();
        ProductoPadre productoPadre = null;
        if (producto.getProductoPadre().getId() != 0 && producto.getProductoPadre().getId() != null) {
            productoPadre = producto.getProductoPadre();
        } else {
            productoPadre = new ProductoPadre();
            productoPadre.setActivo(true);
            productoPadre.setNombre(producto.getProductoPadre().getNombre());
            productoPadre.setNombreCorto(producto.getProductoPadre().getNombre());
            productoPadre.setDetalle(producto.getProductoPadre().getDetalle());
            productoPadre.setFamilia(producto.getProductoPadre().getFamilia());
//            productoPadre.setRubro(producto.getProductoPadre().getRubro());
            /*###########################Control por APP############################*/
            productoPadre.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
            /*######################################################################*/
            session.save(productoPadre);
        }
        producto.setProductoPadre(productoPadre);
        producto.setActivo(true);
        producto.setFechaCreacion(new Date());
        productoPadre.setProductos(new HashSet<Producto>());
        productoPadre.getProductos().add(producto);
        session.save(producto);
        //agregamos los iamgenes
        Set<ImagenProducto> imagenProductos = producto.getImagenProducto();
        if (!Objects.isNull(imagenProductos)) {
            for (ImagenProducto imagenProducto : imagenProductos) {
                imagenProducto.setProducto(producto);
                producto.getImagenProducto().add(imagenProducto);
                session.save(imagenProducto);
            }
        }
        //agregamos los cododigos de barra que tenga
        Set<CodigoBarras> codigoBarras = producto.getCodigoBarras();
        if (!Objects.isNull(codigoBarras)) {
            for (CodigoBarras codigoBarra : codigoBarras) {
                if (codigoBarra.getCodigo() != null) {
                    if (!codigoBarra.getCodigo().trim().equals("")) {
                        codigoBarra.setProducto(producto);
                        producto.getCodigoBarras().add(codigoBarra);
                        session.save(codigoBarra);
                    }
                }
            }
        }

        //agregamos los Proveedores que tenga
        Set<Proveedores> proveedores = producto.getProveedores();
        if (!Objects.isNull(proveedores)) {
            for (Proveedores proveedor : proveedores) {
                producto.getProveedores().add(proveedor);
            }
        }
        //agregamos los precios
//		Set<PrecioProductos> precioProductos = producto.getPrecios();
//		if (!Objects.isNull(precioProductos)) {
//			for (PrecioProductos precioProducto : precioProductos) {
//				precioProducto.setProducto(producto);
//				producto.getPrecios().add(precioProducto);
//				session.save(precioProducto);
//			}
//		}
        //agregamos el stock por  sucursal
        Set<StockSucursal> stockSucursales = producto.getStockSucursales();
        if (!Objects.isNull(stockSucursales)) {
            for (StockSucursal stockSucursal : stockSucursales) {
                stockSucursal.setProducto(producto);
                stockSucursal.setStock(BigDecimal.ZERO);
                producto.getStockSucursales().add(stockSucursal);
                session.save(stockSucursal);
                //agreregamos las ubicaciones por stock
//                Set<UbicacionStock> ubicacionStocks = stockSucursal.getUbicacionStocks();
//                if (!Objects.isNull(ubicacionStocks)) {
//                    for (UbicacionStock ubicacionStock : ubicacionStocks) {
//                        ubicacionStock.setStockSucursal(stockSucursal);
//                        stockSucursal.getUbicacionStocks().add(ubicacionStock);
//                        session.save(ubicacionStock);
//                    }
//                }
            }
        }
        //agregamos los productos compuestos
        Set<ProductosCompuestos> productosCompuestos = producto.getProductosCompuestos();
        if (!Objects.isNull(productosCompuestos)) {
            if (!productosCompuestos.isEmpty()) {
                for (ProductosCompuestos productoCompuestos : productosCompuestos) {
                    productoCompuestos.setProductoPrincipal(producto);
                    producto.getProductosCompuestos().add(productoCompuestos);
                    session.save(productoCompuestos);
                }
                producto.setTipo(2);// producto compuesto
            } else {
                producto.setTipo(1);// producto simple
            }
        } else {
            producto.setTipo(1);// producto simple
        }
        return producto;
    }

    @Override
    public Map<String, Object> isExistCodigoBarra(String code) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "producto");
        Criteria criteriaCodigoBarras = null;
        // restriccion para los codigos de barra
        criteriaCodigoBarras = criteriaDetalleProductos.createAlias("producto.codigoBarras", "codigoBarras", JoinType.LEFT_OUTER_JOIN);
        if (code != null) {
            if (!"".equals(code.trim())) {
//                criteriaCodigoBarras.add(Restrictions.like("codigoBarras.codigo", "%" + code.trim() + "%"));
                criteriaCodigoBarras.add(Restrictions.eq("codigoBarras.codigo", code.trim()));
            }
        }
        /*###########################Control por APP############################*/
        Criteria criteriaProductos = null;
        criteriaProductos = criteriaDetalleProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        criteriaProductos.setFetchMode("productoPadre.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductos.add(c1);
        /*######################################################################*/
        List<Producto> lista = null;
        Map<String, Object> temp = new HashMap<>();
        lista = criteriaDetalleProductos.list();
        if (lista != null) {
            if (lista.size() <= 0) {
                temp.put("existe", Boolean.FALSE);
            } else {
                temp.put("existe", Boolean.TRUE);
                temp.put("id", lista.get(0).getId().toString());
                temp.put("nombre", lista.get(0).getProductoPadre().getNombre() + " " + lista.get(0).getNombre());
            }
        } else {
            temp.put("existe", Boolean.FALSE);
        }
        return temp;
    }

    @Override
    public Long getTotalRows() throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "producto");
        criteriaDetalleProductos.add(Restrictions.eq("activo", true)); // solo para los activos
        /*###########################Control por APP############################*/
        Criteria criteriaProductos = null;
        criteriaProductos = criteriaDetalleProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        criteriaProductos.setFetchMode("productoPadre.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductos.add(c1);
        /*######################################################################*/
        criteriaDetalleProductos.setProjection(Projections.rowCount());
        return (Long) criteriaDetalleProductos.uniqueResult();
    }

    @Override
    public Producto updatePersona(Producto productoOld, Producto productoNew) {
        Session session = sessionFactory.getCurrentSession();
        if (productoNew.getProductoPadre().getId() == 0 || productoNew.getProductoPadre().getId() == null) {
            ProductoPadre productoPadre = new ProductoPadre();
            productoPadre.setActivo(true);
            productoPadre.setNombre(productoNew.getProductoPadre().getNombre());
            productoPadre.setNombreCorto(productoNew.getProductoPadre().getNombreCorto());
            productoPadre.setDetalle(productoNew.getProductoPadre().getDetalle());
            productoPadre.setFamilia(productoNew.getProductoPadre().getFamilia());
            /*###########################Control por APP############################*/
            productoPadre.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
            /*######################################################################*/
            session.save(productoPadre);
            productoNew.setProductoPadre(productoPadre);
        }
        productoOld = this.getObject(productoOld, productoNew);
//        Proveedores proveedor = session.get(Proveedores.class, 5);
//        productoOld.getProveedores().add(proveedor);
        session.update(productoOld);
        return productoOld;
    }

    @Override
    public Producto getObject(Producto productoOld, Producto productoNew) {
        if (Objects.isNull(productoOld)) {
            productoOld = new Producto();
        }
        productoOld.setActivo(productoNew.isActivo());
        productoOld.setCodigoBarra(productoNew.getCodigoBarra());
        productoOld.setContenidoNeto(productoNew.getContenidoNeto());
        productoOld.setDetalle(productoNew.getDetalle());
        productoOld.setIva(productoNew.getIva());
        productoOld.setIdIva(productoNew.getIdIva());
        productoOld.setNombre(productoNew.getNombre());
        productoOld.setNombreFinal(productoNew.getNombreFinal());
        productoOld.setPrecioCosto(productoNew.getPrecioCosto());
        productoOld.setPrecioVenta(productoNew.getPrecioVenta());
        productoOld.setTags(productoNew.getTags());
        productoOld.setUnidad(productoNew.getUnidad());
        productoOld.setProductoPadre(productoNew.getProductoPadre());
        productoOld.setPesable(productoNew.getPesable());
        productoOld.setIngresoCantidadManual(productoNew.getIngresoCantidadManual());
        return productoOld;
    }
//#####################################################Consultas secundarias################################################

    /**
     * devuleve la cantidad de prod debajo del punto de reposicion
     *
     * @throws BussinessException
     */
    @Override
    public Long getCantProdBajoPuntoRepo() throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaStock = session.createCriteria(StockSucursal.class, "ss");
        Criteria criteriaProductos = null;
        Criteria criteriaProductosPadre = null;
        /*###########################Control por APP############################*/
        criteriaProductos = criteriaStock.createCriteria("ss.producto", "p", JoinType.LEFT_OUTER_JOIN);
        criteriaProductos.add(Restrictions.eq("activo", true)); // solo para los activos
        criteriaProductosPadre = criteriaProductos.createCriteria("p.productoPadre", "pp", JoinType.LEFT_OUTER_JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductosPadre.add(c1);
        /*######################################################################*/
        c1 = Restrictions.leProperty("ss.stock", "ss.puntoReposicion");
        criteriaStock.add(c1);
        criteriaStock.setProjection(Projections.countDistinct("producto"));
        return (Long) criteriaStock.uniqueResult();
    }

    /**
     * devuleve la cantidad de prod debajo del stock minimo
     *
     * @throws BussinessException
     */
    @Override
    public Long getCantProdBajoStockMin() throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaStock = session.createCriteria(StockSucursal.class, "ss");
        Criteria criteriaProductos = null;
        Criteria criteriaProductosPadre = null;
        /*###########################Control por APP############################*/
        criteriaProductos = criteriaStock.createCriteria("ss.producto", "p", JoinType.LEFT_OUTER_JOIN);
        criteriaProductos.add(Restrictions.eq("activo", true)); // solo para los activos
        criteriaProductosPadre = criteriaProductos.createCriteria("p.productoPadre", "pp", JoinType.LEFT_OUTER_JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductosPadre.add(c1);
        /*######################################################################*/
        c1 = Restrictions.leProperty("ss.stock", "ss.stockMinimo");
        criteriaStock.setProjection(Projections.countDistinct("producto"));
        criteriaStock.add(c1);
//        criteriaStock.setProjection(Projections.rowCount());
//        Integer totalResultCount = ((Long) criteriaStock.uniqueResult()).longValue();
        return (Long) criteriaStock.uniqueResult();
    }

    /**
     * modificamos solo el precio del producto
     */
    @Override
    public int updatePrecio(Integer idProducto, PrecioProducto precioProducto, UserLogged user) {
        Session session = sessionFactory.getCurrentSession();
        String hqlUpdate = "update Producto p set p.precioVenta = :precioVenta,  p.precioCosto = :precioCosto  where p.id = :id";
        int updatedEntities = session.createQuery(hqlUpdate)
                .setBigDecimal("precioVenta", precioProducto.getPrecioVenta())
                .setBigDecimal("precioCosto", precioProducto.getPrecioCosto())
                .setInteger("id", idProducto)
                .executeUpdate();
        // agregamos al historial el cambio de precios
        // verificamos q el precio de costo sea seigual al inicial  y lo metemos en el historial,  y si el precio de venta idem
        if (precioProducto.getPrecioCostoInicial().compareTo(precioProducto.getPrecioCosto()) != 0) {
            String hqlInsert = "INSERT INTO hist_precios(fecha, precio_inicial, precio_final, detalle, producto_id, tipo_precios_id, user_id, user_name) VALUES (NOW(), :precioInicial, :precioFinal, :detalle, :idProducto, 1, :idUser, :userName);";
            int insertEntities = session.createSQLQuery(hqlInsert)
                    .setBigDecimal("precioInicial", precioProducto.getPrecioCostoInicial())
                    .setBigDecimal("precioFinal", precioProducto.getPrecioCosto())
                    .setString("detalle", "")
                    .setInteger("idProducto", idProducto)
                    .setInteger("idUser", user.getIdUser())
                    .setString("userName", user.getNameUser())
                    .executeUpdate();
        }
        if (precioProducto.getPrecioVentaInicial().compareTo(precioProducto.getPrecioVenta()) != 0) {
            String hqlInsert = "INSERT INTO hist_precios(fecha, precio_inicial, precio_final, detalle, producto_id, tipo_precios_id, user_id, user_name) VALUES (NOW(), :precioInicial, :precioFinal, :detalle, :idProducto, 2, :idUser, :userName);";
            int insertEntities = session.createSQLQuery(hqlInsert)
                    .setBigDecimal("precioInicial", precioProducto.getPrecioVentaInicial())
                    .setBigDecimal("precioFinal", precioProducto.getPrecioVenta())
                    .setString("detalle", "")
                    .setInteger("idProducto", idProducto)
                    .setInteger("idUser", user.getIdUser())
                    .setString("userName", user.getNameUser())
                    .executeUpdate();
        }
        return updatedEntities;
    }

    /**
     * modificamos de una flia o varias familias de producto
     */
    @Override
    public int updatePrecioByFamilia(PrecioFamiliasProducto precioFamiliasProducto) {
        Session session = sessionFactory.getCurrentSession();
        // para que no nos haga consulta doble, con set eliminamos los duplicados del array
        Set<Integer> mySetIdChilds = this.getChildsFamilias(precioFamiliasProducto.getFamilias());

        String productosHQL = "SELECT p.id from Producto p INNER JOIN  p.productoPadre pp"
                + " INNER JOIN pp.familia f where f.id in (:ids)";
        Query query = session.createQuery(productosHQL)
                .setParameterList("ids", mySetIdChilds);
        List results = query.list();
        String updateHQL = "";
        //modificaion por porcentaje
        if (precioFamiliasProducto.getTipoModificacion() == 1) {
            updateHQL = " UPDATE Producto p SET p.precioVenta =p.precioVenta + ((:cantidad * p.precioVenta)/100)"
                    + " WHERE  p.id in (:idsProductos)";
        }
        //modificaion por suma cantidad al precio
        if (precioFamiliasProducto.getTipoModificacion() == 2) {
            updateHQL = " update Producto p set p.precioVenta = p.precioVenta + :cantidad "
                    + " WHERE  p.id in (:idsProductos)";
        }
        int updatedEntities = session.createQuery(updateHQL)
                .setBigDecimal("cantidad", precioFamiliasProducto.getCantidad())
                .setParameterList("idsProductos", results)
                .executeUpdate();
        return updatedEntities;
    }

    /**
     * Devuelve los stocks de un producto
     *
     * @return
     */
    @Override
    public List<StockSucursal> getStocksByProducto(Integer idProducto) {
        Session session = sessionFactory.getCurrentSession();
        Producto producto = session.load(Producto.class, idProducto);
        Criteria criteriaStock = session.createCriteria(StockSucursal.class, "ss");
        Criteria criteriaProductos = null;
        Criteria criteriaProductosPadre = null;
        criteriaProductos = criteriaStock.createCriteria("ss.producto", "p", JoinType.LEFT_OUTER_JOIN);
        criteriaProductosPadre = criteriaProductos.createCriteria("p.productoPadre", "pp", JoinType.LEFT_OUTER_JOIN);
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductosPadre.add(c1);
        /*######################################################################*/
        c1 = Restrictions.eq("ss.producto", producto);
        criteriaStock.add(c1);
        return criteriaStock.list();
    }

    @Override
    public void prueba() {
        Session session = sessionFactory.getCurrentSession();
        Producto producto = session.get(Producto.class, 99);
        Proveedores proveedor = session.get(Proveedores.class, 5);
        producto.getProveedores().add(proveedor);
        session.update(producto);
    }

    /**
     * Borramos todos los datos ingresados de compujuy con respecto a app1
     */
    @Override
    public void resetCompujuy() {
        Session session = sessionFactory.getCurrentSession();
//        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "producto");
        Criteria criteriaCbteVenDet = session.createCriteria(CbteDet.class, "det");
        Criteria criteriaPagoVentas = session.createCriteria(PagoCbteVen.class, "pag");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String dateString = format.format(new Date());
//        try {
//            Date date = format.parse("2018-02-23");
//            criteriaDetalleProductos.add(Restrictions.ge("fechaCreacion", date));
        List<Integer> listaProductos = session.createSQLQuery("select id from producto p where DATE_FORMAT(p.fecha_creacion, '%Y-%m-%d') > STR_TO_DATE('22-02-2018', '%d-%m-%Y')").list();
        for (Integer producto : listaProductos) {
            Integer idProductoPadre = (Integer) session.createSQLQuery("select producto_padre_id from producto p where p.id = :idProducto")
                    .setParameter("idProducto", producto)
                    .uniqueResult();
            Integer idApp = (Integer) session.createSQLQuery("select app_id from producto_padre where id = :idProductoPadre")
                    .setInteger("idProductoPadre", idProductoPadre)
                    .uniqueResult();
            System.out.println("app:" + idApp);
//			if (idApp == 1) {
            System.out.println("producto:" + producto);
            //primero eliminamos la constrainst de producto compuesto

            String updateHQL = "DELETE FROM codigos_barras WHERE producto_id=:producto";
            session.createSQLQuery(updateHQL)
                    .setParameter("producto", producto)
                    .executeUpdate();
            updateHQL = "DELETE FROM pase_movimientos WHERE producto_id=:producto";
            session.createSQLQuery(updateHQL)
                    .setParameter("producto", producto)
                    .executeUpdate();
            session.createSQLQuery("DELETE FROM producto_proveedores where producto_id=:idProducto")
                    .setParameter("idProducto", producto)
                    .executeUpdate();
            updateHQL = "DELETE FROM imagen_producto WHERE imagen_producto.producto_id = :producto";
            session.createSQLQuery(updateHQL)
                    .setParameter("producto", producto)
                    .executeUpdate();
            updateHQL = "DELETE FROM precio_producto WHERE producto_id = :producto";
            session.createSQLQuery(updateHQL)
                    .setParameter("producto", producto)
                    .executeUpdate();
            updateHQL = "DELETE FROM hist_precios WHERE producto_id= :producto";
            session.createSQLQuery(updateHQL)
                    .setParameter("producto", producto)
                    .executeUpdate();
            updateHQL = "DELETE FROM stock_sucursal WHERE stock_sucursal.producto_id = :producto";
            session.createSQLQuery(updateHQL)
                    .setParameter("producto", producto)
                    .executeUpdate();
            updateHQL = "DELETE FROM vencimientos_productos WHERE vencimientos_productos.producto_id = :producto";
            session.createSQLQuery(updateHQL)
                    .setParameter("producto", producto)
                    .executeUpdate();
            updateHQL = "DELETE FROM vencimientos_productos WHERE vencimientos_productos.producto_id = :producto";
            session.createSQLQuery(updateHQL)
                    .setParameter("producto", producto)
                    .executeUpdate();
            List<Integer> listaDetaVentas = session.createSQLQuery("SELECT cbte_enc_id FROM cbte_det WHERE productos_id = :producto")
                    .setParameter("producto", producto)
                    .list();
            for (Integer encventa : listaDetaVentas) {
                List<Integer> listaPagoVentas = session.createSQLQuery("SELECT id FROM pagoventas WHERE cbte_enc_id = :cbteVenEnc")
                        .setParameter("cbteVenEnc", encventa)
                        .list();
                for (Integer pagventa : listaPagoVentas) {
                    List<Integer> listaMovCtaCte = session.createSQLQuery("SELECT id FROM movimientos_cta_cte WHERE pagoventas_id = :pagventa")
                            .setParameter("pagventa", pagventa)
                            .list();
                    for (Integer movctacte : listaMovCtaCte) {
                        updateHQL = "DELETE FROM movimientos_cta_cte_pagos_cta_cte WHERE movimientos_cta_cte_id= :mtoctacte";
                        session.createSQLQuery(updateHQL)
                                .setParameter("mtoctacte", movctacte)
                                .executeUpdate();
                    }
                    updateHQL = "DELETE FROM movimientos_cta_cte WHERE pagoventas_id= :pagventa";
                    session.createSQLQuery(updateHQL)
                            .setParameter("pagventa", pagventa)
                            .executeUpdate();
                }
                updateHQL = "DELETE FROM cbte_det WHERE cbte_enc_id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
                updateHQL = "DELETE FROM eventos_ventas WHERE cbte_enc_id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
                updateHQL = "DELETE FROM pagoventas WHERE cbte_enc_id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
                updateHQL = "DELETE FROM cbte_enc WHERE id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
            }

            List<Integer> listaEncMov = session.createSQLQuery("SELECT enc_movimientos_id FROM det_movimientos WHERE producto_id = :producto")
                    .setParameter("producto", producto)
                    .list();
//				for (Integer encmov : listaEncMov) {
//					List<Integer> listaDetMov = session.createSQLQuery("SELECT id FROM det_movimientos WHERE enc_movimientos_id = :encmov")
//							.setParameter("encmov", encmov)
//							.list();
//					for (Integer detmov : listaDetMov) {
            updateHQL = "DELETE FROM movimientos_producto";
            session.createSQLQuery(updateHQL)
                    .executeUpdate();
//					}
//				}
            for (Integer encmov : listaEncMov) {
                updateHQL = "DELETE FROM det_movimientos WHERE enc_movimientos_id = :encmov";
                session.createSQLQuery(updateHQL)
                        .setParameter("encmov", encmov)
                        .executeUpdate();
                updateHQL = "DELETE FROM enc_movimientos WHERE id = :encmov";
                session.createSQLQuery(updateHQL)
                        .setParameter("encmov", encmov)
                        .executeUpdate();
            }
//				updateHQL = " DELETE FROM Producto p where p.id = :idProducto";
//				session.createQuery(updateHQL)
//						.setParameter("idProducto", producto)
//						.executeUpdate();
//			}
        }
        //eliminamos las personas agregadas
//		List<Integer> listaPersonas = session.createSQLQuery("SELECT id FROM personas where DATE_FORMAT(fecha_alta, '%Y-%m-%d') > STR_TO_DATE('01-03-2019', '%d-%m-%Y') AND app_id=1")
        List<Integer> listaPersonas = session.createSQLQuery("SELECT id FROM personas")
                .list();
        String updateHQL = "";
        for (Integer persona : listaPersonas) {

            updateHQL = "UPDATE proveedores set personas_id=null where  personas_id= :persona";
            session.createSQLQuery(updateHQL)
                    .setInteger("persona", persona)
                    .executeUpdate();

            updateHQL = "DELETE FROM contactos where personas_id= :persona";
            session.createSQLQuery(updateHQL)
                    .setInteger("persona", persona)
                    .executeUpdate();
            updateHQL = "DELETE FROM domicilios where personas_id= :persona";
            session.createSQLQuery(updateHQL)
                    .setInteger("persona", persona)
                    .executeUpdate();
            updateHQL = "DELETE FROM telefonos where personas_id= :persona";
            session.createSQLQuery(updateHQL)
                    .setInteger("persona", persona)
                    .executeUpdate();

            List<Integer> listaEncVentas = session.createSQLQuery("SELECT id FROM cbte_enc WHERE clientes_id = :cliente")
                    .setParameter("cliente", new Integer(persona.toString()))
                    .list();
            for (Integer encventa : listaEncVentas) {
                List<Integer> listaPagoVentas = session.createSQLQuery("SELECT id FROM pagoventas WHERE cbte_enc_id = :cbteVenEnc")
                        .setParameter("cbteVenEnc", encventa)
                        .list();
                for (Integer pagventa : listaPagoVentas) {
                    List<Integer> listaMovCtaCte = session.createSQLQuery("SELECT id FROM movimientos_cta_cte WHERE pagoventas_id = :pagventa")
                            .setParameter("pagventa", pagventa)
                            .list();
                    for (Integer movctacte : listaMovCtaCte) {
                        updateHQL = "DELETE FROM movimientos_cta_cte_pagos_cta_cte WHERE movimientos_cta_cte_id= :mtoctacte";
                        session.createSQLQuery(updateHQL)
                                .setParameter("mtoctacte", movctacte)
                                .executeUpdate();
                    }
                    updateHQL = "DELETE FROM movimientos_cta_cte WHERE pagoventas_id= :pagventa";
                    session.createSQLQuery(updateHQL)
                            .setParameter("pagventa", pagventa)
                            .executeUpdate();
                }
                updateHQL = "DELETE FROM cbte_det WHERE cbte_enc_id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
                updateHQL = "DELETE FROM eventos_ventas WHERE cbte_enc_id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
                updateHQL = "DELETE FROM pagoventas WHERE cbte_enc_id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
                updateHQL = "DELETE FROM cbte_enc WHERE id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
            }

            updateHQL = "DELETE FROM clientes where personas_id= :persona";
            session.createSQLQuery(updateHQL)
                    .setInteger("persona", persona)
                    .executeUpdate();
            //usuario
            Integer idUsuario = (Integer) session.createSQLQuery("select id from usuarios p where p.personas_id = :persona")
                    .setInteger("persona", persona)
                    .uniqueResult();

            updateHQL = "DELETE FROM detalle_transaccion_caja where usuarios_id= :usuario";
            session.createSQLQuery(updateHQL)
                    .setInteger("usuario", idUsuario)
                    .executeUpdate();

            List<Integer> listaEncVentasUsuarios = session.createSQLQuery("SELECT id FROM cbte_enc WHERE usuarios_id = :usuario")
                    .setInteger("usuario", idUsuario)
                    .list();
            for (Integer encventa : listaEncVentasUsuarios) {
                List<Integer> listaPagoVentas = session.createSQLQuery("SELECT id FROM pagoventas WHERE cbte_enc_id = :cbteVenEnc")
                        .setParameter("cbteVenEnc", encventa)
                        .list();
                for (Integer pagventa : listaPagoVentas) {
                    List<Integer> listaMovCtaCte = session.createSQLQuery("SELECT id FROM movimientos_cta_cte WHERE pagoventas_id = :pagventa")
                            .setParameter("pagventa", pagventa)
                            .list();
                    for (Integer movctacte : listaMovCtaCte) {
                        updateHQL = "DELETE FROM movimientos_cta_cte_pagos_cta_cte WHERE movimientos_cta_cte_id= :mtoctacte";
                        session.createSQLQuery(updateHQL)
                                .setParameter("mtoctacte", movctacte)
                                .executeUpdate();
                    }
                    updateHQL = "DELETE FROM movimientos_cta_cte WHERE pagoventas_id= :pagventa";
                    session.createSQLQuery(updateHQL)
                            .setParameter("pagventa", pagventa)
                            .executeUpdate();
                }
                updateHQL = "DELETE FROM cbte_det WHERE cbte_enc_id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
                updateHQL = "DELETE FROM eventos_ventas WHERE cbte_enc_id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
                updateHQL = "DELETE FROM pagoventas WHERE cbte_enc_id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
                updateHQL = "DELETE FROM cbte_enc WHERE id= :cbteVenEnc";
                session.createSQLQuery(updateHQL)
                        .setParameter("cbteVenEnc", encventa)
                        .executeUpdate();
            }

            updateHQL = "DELETE FROM usuarios_sucursales where usuarios_id= :usuario";
            session.createSQLQuery(updateHQL)
                    .setInteger("usuario", idUsuario)
                    .executeUpdate();

            updateHQL = "DELETE FROM usuarios_roles where usuarios_id= :usuario";
            session.createSQLQuery(updateHQL)
                    .setInteger("usuario", idUsuario)
                    .executeUpdate();

            updateHQL = "DELETE FROM log_users where usuarios_id= :usuario";
            session.createSQLQuery(updateHQL)
                    .setInteger("usuario", idUsuario)
                    .executeUpdate();

            updateHQL = "DELETE FROM usuarios where id= :usuario";
            session.createSQLQuery(updateHQL)
                    .setInteger("usuario", idUsuario)
                    .executeUpdate();
            //persona
            updateHQL = "DELETE FROM personas where id= :persona";
            session.createSQLQuery(updateHQL)
                    .setInteger("persona", persona)
                    .executeUpdate();

        }
    }

    private Set<Integer> getChildsFamilias(Set<Familias> familias) {
        Session session = sessionFactory.getCurrentSession();
        String idChildsFamilia = "";
        Integer i = 0;
        for (Familias fam : familias) {
            Familias familia = session.get(Familias.class, fam.getId());
            if (i == 0) {
                idChildsFamilia += FamiliasUtil.getIdOfChildsByFamilia(familia);
            } else {
                idChildsFamilia += "-" + FamiliasUtil.getIdOfChildsByFamilia(familia);
            }
            i++;
        }
        //a l string lo pasamos a array, que contiene los ids
        String[] arrayIdsChildsFamilia = idChildsFamilia.split("-");
        // para que no nos haga consulta doble, con set eliminamos los duplicados del array
        Set<String> mySetIdChilds = new HashSet<>(Arrays.asList(arrayIdsChildsFamilia));
        Set<Integer> mySetIdChildsInt = new HashSet<>();
        for (String mySetIdChild : mySetIdChilds) {
            mySetIdChildsInt.add(Integer.parseInt(mySetIdChild));
        }
        return mySetIdChildsInt;
    }

    /**
     * Elimina un producto
     *
     * @param entity
     */
    @Override
    public void delete(Producto entity) {
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery("DELETE FROM producto_proveedores where producto_id=:idProducto")
                .setInteger("idProducto", entity.getId())
                .executeUpdate();
        String updateHQL = " DELETE FROM CodigoBarras c where c.producto = :producto";
        session.createQuery(updateHQL)
                .setEntity("producto", entity)
                .executeUpdate();
        updateHQL = " DELETE FROM ImagenProducto i where i.producto = :producto";
        session.createQuery(updateHQL)
                .setEntity("producto", entity)
                .executeUpdate();
        updateHQL = " DELETE FROM PrecioProductos p where p.producto = :producto";
        session.createQuery(updateHQL)
                .setEntity("producto", entity)
                .executeUpdate();
        updateHQL = " DELETE FROM StockSucursal s where s.producto = :producto";
        session.createQuery(updateHQL)
                .setEntity("producto", entity)
                .executeUpdate();
        updateHQL = " DELETE FROM ProductosCompuestos p where p.producto = :producto";
        session.createQuery(updateHQL)
                .setEntity("producto", entity)
                .executeUpdate();
        updateHQL = " DELETE FROM Producto p where p.id = :idProducto";
        session.createQuery(updateHQL)
                .setInteger("idProducto", entity.getId())
                .executeUpdate();

    }

    /**
     * Return stock from id prod
     * @param idProducto
     * @return
     */
    public List<StockSucursal> getStockSucursal(Integer idProducto) {
        Session session = sessionFactory.getCurrentSession();
        Producto producto = session.get(Producto.class, idProducto);
        if (Objects.isNull(producto)) {
            throw new DataIntegrityViolationException("No existe el producto con el id: " + idProducto);
        }
        return this.getStockSucursal(producto);
    }

    /**
     * Devuelve los estocks por sucursal, de todas las sucursales, un array
     * @return
     */
    @Override
    public List<StockSucursal> getStockSucursal(Producto producto) {
        Session session = sessionFactory.getCurrentSession();
        List<Sucursales> sucursales = this.getSucursalesByUser(session);
        List<StockSucursal> stockSucursales = new ArrayList<>();
        for (Sucursales sucursal : sucursales) {
            BigDecimal stock = this.getStockSucursal(producto, sucursal);
            if (!Objects.isNull(stock)) {
                StockSucursal ss = new StockSucursal();
                ss.setStock(stock);
                ss.setSucursal(sucursal);
                stockSucursales.add(ss);
            }
        }
        // realizamos un recorrido para sacar el stock minimo y stock de repo
        if (!stockSucursales.isEmpty()) {
            Set<StockSucursal> stockSucursalesBD = producto.getStockSucursales();
            if (!stockSucursalesBD.isEmpty()) {
                for (StockSucursal stockSucursalBD : stockSucursalesBD) {
                    for (StockSucursal stockSucursal : stockSucursales) {
                        if (Objects.equals(stockSucursal.getSucursal().getId(), stockSucursalBD.getSucursal().getId())) {
                            stockSucursal.setStockMinimo(stockSucursalBD.getStockMinimo());
                            stockSucursal.setPuntoReposicion(stockSucursalBD.getPuntoReposicion());
                        }
                    }
                }
            }
        }
        return stockSucursales;
    }

    /**
     * Devuelve solo el stock por sucursal
     *
     * @param producto
     * @param suc
     * @return
     */
    @Override
    public BigDecimal getStockSucursal(Producto producto, Sucursales suc) {
        BigDecimal stockTemp = BigDecimal.ZERO;
        List<BigDecimal> listTemp = new ArrayList<>();
        if (producto.getTipo() == 1) {// es un  producto simple
            Set<StockSucursal> stockSucursales = producto.getStockSucursales();
            if (!stockSucursales.isEmpty()) {
                for (StockSucursal stockSucursal : stockSucursales) {
                    if (Objects.equals(stockSucursal.getSucursal().getId(), suc.getId())) { // en definitiva siempre el llist de stock tendra un item
                        stockTemp = stockSucursal.getStock();
                        if (Objects.isNull(stockTemp)) {
                            listTemp.add(BigDecimal.ZERO);
                        } else {
                            listTemp.add(stockTemp);
                        }
                    }
                }
            }
        } else {
            if (!producto.getProductosCompuestos().isEmpty()) {
                for (ProductosCompuestos productoCompuesto : producto.getProductosCompuestos()) {
                    BigDecimal s = this.getStockSucursal(productoCompuesto.getProducto(), suc);
                    if (!Objects.isNull(s)) {
                        stockTemp = this.getStockSucursal(productoCompuesto.getProducto(), suc).divide(productoCompuesto.getCantidad(), 2, RoundingMode.HALF_DOWN);
                        listTemp.add(stockTemp);
                    }
                }
            }
        }
        // controlamos el array
        if (listTemp.isEmpty()) {
            return null;
        }
        if (listTemp.size() == 1) {
            return listTemp.get(0);
        } else {
            return Collections.min(listTemp);
        }
    }

    /**
     * Reconstruye todos los codigos especiales de los productos
     */
    @Override
    public void buildAllCodigosEspeciales() {
        Session session = sessionFactory.getCurrentSession();
        Integer updatedEntitie = 0;
        String updateHQL = "update producto set codigo_especial=null "
                + "where id in ( "
                + "select idProducto from "
                + "(select pr.id as idProducto from producto pr "
                + "inner join producto_padre pp on pr.producto_padre_id=pp.id "
                + "inner join app ap on pp.app_id= ap.id "
                + "where ap.id=:app) as t1) ";
        updatedEntitie = session.createSQLQuery(updateHQL)
                .setParameter("app", TenantContext.getCurrentTenant())
                .executeUpdate();

        List<Producto> productos = this.getProductosMin();
        Set<Familias> familias = this.getAllMayorLevelMin();
        Integer contador = 0;
        for (Familias familia : familias) {
            contador = 0;
            for (Producto producto : productos) {
                if (Objects.equals(producto.getProductoPadre().getFamilia().getId(), familia.getId())) {
                    updateHQL = " UPDATE Producto p SET p.codigoEspecial = :valueCodigo"
                            + " WHERE  p.id = :idProducto";
                    updatedEntitie = session.createQuery(updateHQL)
                            .setString("valueCodigo", familia.getNombreCorto() + contador)
                            .setInteger("idProducto", producto.getId())
                            .executeUpdate();
                    contador++;
                }
            }

        }

    }

    /**
     * Reconstruye todos los codigos especiales de los productos
     */
    @Override
    public String getCodigoEspecialFromIdFamilia(Integer idFamilia) {
        return this.getPrefijoId(idFamilia) + this.getLastSufijoId(idFamilia);
    }

    /**
     * Cuando se modifica una familia tambien se modifica el codigo especial
     */
    @Override
    public int updateCodigosEspecialFromFamilia(Integer idFamilia, String oldNombreCorto, String newNombreCorto) {
        Session session = sessionFactory.getCurrentSession();
        Integer randomNum = 100 + (int) (Math.random() * 999);
        String tempPrefijo = "_" + randomNum + "_";
        // primero pasamos a  un temporal para borrar inicialmente lo que tenia porque sino surge conflicto si al nobre original solo se le agrega un uno por ejemplo
        String updateSQL = " update producto set codigo_especial= REPLACE(codigo_especial, :oldname, :newName) "
                + "where id in ( "
                + "select idProducto from "
                + "(select pr.id as idProducto from producto pr "
                + "inner join producto_padre pp on pr.producto_padre_id=pp.id "
                + "inner join app ap on pp.app_id= ap.id "
                + "where ap.id=:app "
                + "and pp.familias_id=:idFamilia"
                + ") as t1) ";
        int updatedEntities = session.createSQLQuery(updateSQL)
                .setString("oldname", oldNombreCorto)
                .setString("newName", tempPrefijo)
                .setParameter("app", TenantContext.getCurrentTenant())
                .setParameter("idFamilia", idFamilia)
                .executeUpdate();
        // ahora si pasamos al nombre original
        updateSQL = " update producto set codigo_especial= REPLACE(codigo_especial, :oldname, :newName) "
                + "where id in ( "
                + "select idProducto from "
                + "(select pr.id as idProducto from producto pr "
                + "inner join producto_padre pp on pr.producto_padre_id=pp.id "
                + "inner join app ap on pp.app_id= ap.id "
                + "where ap.id=:app "
                + "and pp.familias_id=:idFamilia"
                + ") as t1) ";
        updatedEntities = session.createSQLQuery(updateSQL)
                .setString("oldname", tempPrefijo)
                .setString("newName", newNombreCorto)
                .setParameter("app", TenantContext.getCurrentTenant())
                .setParameter("idFamilia", idFamilia)
                .executeUpdate();
        return updatedEntities;
    }

    /**
     * Cuando se modifica una familia tambien se modifica el codigo especial
     */
    @Override
    public int updateCodigosEspecialFromProdPadre(Integer idProductoPadre, Familias newFamilia) {
        Session session = sessionFactory.getCurrentSession();
        String valuePrefijo = null;
        int updatedEntitie = 0;

        Integer contador = this.getLastSufijoId(newFamilia.getId());
        valuePrefijo = this.getPrefijoId(newFamilia.getId());
        // traemos todos productos de la vieja familia
        List<Producto> productos = this.getProductosMinByProdPadre(idProductoPadre);
        // traemos el nombre corto de la nueva familia

        // recorremos todos los productos de la vieja flia para modificar
        for (Producto producto : productos) {
            String updateHQL = " UPDATE Producto p SET p.codigoEspecial = :valueCodigo"
                    + " WHERE  p.id = :idProducto";
            updatedEntitie = session.createQuery(updateHQL)
                    .setString("valueCodigo", valuePrefijo + contador)
                    .setInteger("idProducto", producto.getId())
                    .executeUpdate();
            contador++;
        }
        return updatedEntitie;
    }

    @Override
    public List<Historial> getHistorialProducto(Integer idProducto, Integer page, Integer size) {
        Session session = sessionFactory.getCurrentSession();
        List<Historial> lista = new ArrayList<>();
        Historial historial;
        Integer id = new Integer("0");
        Date fecha = new Date();
        String subtipo = "";
        String tipo = "";
        String dia = "";
        String hora = "";
        String sql = "SELECT en.id as id, en.fecha_carga as fecha, ('Venta') as tipo, DATE_FORMAT(en.fecha_carga, '%d-%m-%Y') as dia, DATE_FORMAT(en.fecha_carga, '%H:%i') as hora, en.tipo_cbte as tipoVenta  FROM cbte_det de "
                + "left outer join cbte_enc en on de.cbte_enc_id= en.id "
                + "where de.productos_id=:idProducto AND en.tipo=1 "
                + "UNION ALL "
                + "SELECT em.id as idMov, em.fecha_carga as fechaMov, IF(em.tipo=2,'Ingreso', IF(em.tipo=3,'Egreso',IF(em.tipo=4,'Pase',''))) as tipoMov, DATE_FORMAT(em.fecha_carga, '%d-%m-%Y') as diaMov, DATE_FORMAT(em.fecha_carga, '%H:%i') as horaMov,  IF(em.subtipo=1,'Ingreso', IF(em.subtipo=2,'Egreso','')) as subtipoMov  FROM det_movimientos dm "
                + "left outer join enc_movimientos em on dm.enc_movimientos_id=em.id "
                + "where dm.producto_id=:idProducto order by fecha desc LIMIT :page, :size";
        Query query = session.createSQLQuery(sql)
                .setParameter("idProducto", idProducto)
                .setParameter("page", ((page - 1) * size))
                .setParameter("size", size);
        for (Object rows : query.list()) {
            historial = new Historial();
            Object[] row = (Object[]) rows;
            id = (Integer) row[0];
            fecha = (Date) row[1];
            tipo = (String) row[2];
            dia = (String) row[3];
            hora = (String) row[4];
            subtipo = (String) row[5];
            historial.setId(id);
            historial.setFecha(fecha);
            historial.setTipo(tipo);
            historial.setDia(dia);
            historial.setHora(hora);
            historial.setSubtipo(subtipo);
            lista.add(historial);
        }
        return lista;
    }

    /**
     * Devuelve el ultimo id a generar segun la familia
     *
     * @param idFamilia
     * @return
     */
    private Integer getLastSufijoId(Integer idFamilia) {
        Session session = sessionFactory.getCurrentSession();
        Query query = null;
        String valueActual = null;
        StringBuilder sql = new StringBuilder();
        Integer contador = 0;
        // sacamos el ultimo id de la nueva familia
        sql.append("SELECT REPLACE(pr.codigo_especial,fa.nombreCorto COLLATE utf8_unicode_ci ,'') as codigoEspecial FROM producto pr ");
        sql.append("inner join producto_padre pp on pr.producto_padre_id=pp.id ");
        sql.append("inner join familias fa on pp.familias_id=fa.id ");
        sql.append("inner join app ap on pp.app_id= ap.id ");
        sql.append("where ap.id=:app ");
        sql.append("and pp.familias_id=:idFamilia ");
        sql.append("ORDER BY ABS(codigoEspecial) DESC LIMIT 1");
        query = session.createSQLQuery(sql.toString())
                .setParameter("app", TenantContext.getCurrentTenant())
                .setParameter("idFamilia", idFamilia);
        valueActual = (String) query.uniqueResult();
        try {
            if (Objects.isNull(valueActual) || valueActual.trim().equals(equals(""))) {
                valueActual = "0";
            }
            contador = Integer.parseInt(valueActual.trim()) + 1;
        } catch (NumberFormatException exception) {

        }
        return contador;
    }

    private String getPrefijoId(Integer idFamilia) {
        Session session = sessionFactory.getCurrentSession();
        Query query = null;
        String valuePrefijo = null;
        String sql = "";
        sql = "SELECT nombreCorto FROM familias  where id=:idFamilia ";
        query = session.createSQLQuery(sql)
                .setParameter("idFamilia", idFamilia);
        valuePrefijo = (String) query.uniqueResult();
        valuePrefijo = Objects.isNull(valuePrefijo) ? "XXXXXX" : valuePrefijo;
        return valuePrefijo;
    }

    public Set<Familias> getAllMayorLevelMin() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Familias.class, "familia");
        /*###########################Control por APP############################*/
        c.setFetchMode("familia.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        Set<Familias> lista = new HashSet<>(c.list());
        return lista;
    }

    @Override
    public List<Producto> getAllProductosRelacionados(Producto producto) {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteriaProductos = session.createCriteria(Producto.class, "producto");
        Criteria criteriaProductosComp = criteriaProductos.createCriteria("producto.productosCompuestos", "productoComp", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaProductoPadre = criteriaProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);

//		criteriaProductosComp.add(Restrictions.or(Restrictions.eq("productoPrincipal", producto), Restrictions.eq("producto", producto)));
        // si es simple vamos hacia arriba nomas
        if (producto.getTipo() == 1) {
        }
        criteriaProductosComp.add(Restrictions.or(Restrictions.eq("productoPrincipal", producto), Restrictions.eq("producto", producto)));
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductoPadre.add(c1);
        /*######################################################################*/
        return criteriaProductos.list();
    }

    @Override
    public List<Producto> getAllParents(Producto producto) {
        List<Producto> productos = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaProductosComp = session.createCriteria(ProductosCompuestos.class, "productoCompuesto");
        Criteria criteriaProductosCompProd = criteriaProductosComp.createCriteria("productoCompuesto.producto", "producto", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaProductosCompProdPrin = criteriaProductosComp.createCriteria("productoCompuesto.productoPrincipal", "productoPrincipal", JoinType.LEFT_OUTER_JOIN);
//		Criteria criteriaProductoPadre = criteriaProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        // si es simple vamos hacia arriba nomas
        criteriaProductosComp.add(Restrictions.eq("producto", producto));
//		/*###########################Control por APP############################*/
//		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
//		criteriaProductoPadre.add(c1);
//		/*######################################################################*/

        List<ProductosCompuestos> ps = criteriaProductosComp.list();
        if (Objects.isNull(ps) || ps.size() == 0) {
            return null;
        } else {
            for (ProductosCompuestos p : ps) {
                productos.add(p.getProductoPrincipal());
                p.getProductoPrincipal().setNombreFinal(p.getProductoPrincipal().getNombreFinal() + "->" + NumbersUtils.roundCutTwoDec(p.getCantidad()) + ": " + producto.getNombreFinal());
                List<Producto> psa = this.getAllParents(p.getProductoPrincipal());
                if (!Objects.isNull(psa) && psa.size() > 0) {
                    for (Producto p1 : psa) {
                        productos.add(p1);
                    }
                }
            }
        }
        return productos;
    }

    @Override
    public List<Producto> getAllChilds(Producto producto) {
        List<Producto> productos = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaProductosComp = session.createCriteria(ProductosCompuestos.class, "productoCompuesto");
        Criteria criteriaProductosCompProd = criteriaProductosComp.createCriteria("productoCompuesto.producto", "producto", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaProductosCompProdPrin = criteriaProductosComp.createCriteria("productoCompuesto.productoPrincipal", "productoPrincipal", JoinType.LEFT_OUTER_JOIN);
//		Criteria criteriaProductoPadre = criteriaProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        // si es simple vamos hacia arriba nomas
        criteriaProductosComp.add(Restrictions.eq("productoPrincipal", producto));
//		/*###########################Control por APP############################*/
//		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
//		criteriaProductoPadre.add(c1);
//		/*######################################################################*/

        List<ProductosCompuestos> ps = criteriaProductosComp.list();
        if (Objects.isNull(ps) || ps.size() == 0) {
            return null;
        } else {
            for (ProductosCompuestos p : ps) {
                productos.add(p.getProducto());
//				p.getProducto().setNombreFinal(p.getProducto().getNombreFinal() + p.getCantidad() + " <- "  + producto.getNombreFinal());
                p.getProducto().setNombreFinal(p.getProductoPrincipal().getNombreFinal() + "->" + NumbersUtils.roundCutTwoDec(p.getCantidad()) + ": " + p.getProducto().getNombreFinal());
                List<Producto> psa = this.getAllChilds(p.getProducto());
                if (!Objects.isNull(psa) && psa.size() > 0) {
                    for (Producto p1 : psa) {
                        productos.add(p1);
                    }
                }
            }
        }
        return productos;
    }

    @Override
    public void saveMovimientoProducto(DetMovimientos detMovimiento, Date fecha) {
        Session session = sessionFactory.getCurrentSession();
        MovimientosProducto movimientosProducto = new MovimientosProducto();
        movimientosProducto.setCantidad(detMovimiento.getCantidad());
        movimientosProducto.setMovimiento(detMovimiento);
        movimientosProducto.setTipo(1); //ingreso - egreso
        movimientosProducto.setValorFinal(detMovimiento.getValorFinal());
        movimientosProducto.setValorInicial(detMovimiento.getValorInicial());
        movimientosProducto.setVenta(null);
        movimientosProducto.setFecha(fecha);
        session.save(movimientosProducto);
    }

    @Override
    public void saveMovimientoProducto(CbteDet cbteDet, Date fecha) {
        Session session = sessionFactory.getCurrentSession();
        MovimientosProducto movimientosProducto = new MovimientosProducto();
        movimientosProducto.setCantidad(cbteDet.getCantidad());
        movimientosProducto.setMovimiento(null);
        movimientosProducto.setTipo(2); //venta-detalle de venta
        movimientosProducto.setValorFinal(null);
        movimientosProducto.setValorInicial(null);
        movimientosProducto.setVenta(cbteDet);
        movimientosProducto.setFecha(fecha);
        session.save(movimientosProducto);
    }

    @Override
    public Producto getProductoFromIdForPV(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaDetalleProductos = session.createCriteria(Producto.class, "producto");
        Criteria criteriaProductos = null;
        criteriaDetalleProductos.setFetchMode("codigoBarras", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("stockSucursales.sucursales", FetchMode.JOIN);
        criteriaDetalleProductos.setFetchMode("unidad", FetchMode.JOIN);
        criteriaProductos = criteriaDetalleProductos.createCriteria("producto.productoPadre", "productoPadre", JoinType.LEFT_OUTER_JOIN);
        /*###########################Control por APP############################*/
        criteriaProductos.setFetchMode("productoPadre.app", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaProductos.add(c1);
        /*######################################################################*/
        criteriaDetalleProductos.add(Restrictions.eq("id", id));
        Producto detalleProductos = (Producto) criteriaDetalleProductos.uniqueResult();
        return detalleProductos;
    }

}
