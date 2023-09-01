package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.ProductoDAO;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.ProductoPadreDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.FamiliasUtil;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Familias;
import ar.com.jsuper.domain.ProductoPadre;
import ar.com.jsuper.domain.utils.FilterProductoPadre;
import ar.com.jsuper.security.TenantContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class ProductoPadreDAOImpl extends GenericDAOImpl<ProductoPadre, Integer> implements ProductoPadreDAO {

	@Autowired
	ProductoDAO productoDAO;

	@Override
	public Set<ProductoPadre> getByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(ProductoPadre.class);
		Criterion c1 = Restrictions.like("nombre", name, MatchMode.ANYWHERE);
		c.add(c1);
		/*###########################Control por APP############################*/
		c.setFetchMode("productoPadre.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		Set<ProductoPadre> lista = new HashSet<ProductoPadre>(c.list());
		return lista;
	}

	@Override
	public ProductoPadre getMin(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(ProductoPadre.class, "pp");
		c.setFetchMode("pp.familia", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("id", id);
		c.add(c1);
		/*###########################Control por APP############################*/
		c.setFetchMode("pp.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		return (ProductoPadre) c.uniqueResult();
	}

	@Override
	public Pagination<ProductoPadre> getProductoPadreByPage(FilterProductoPadre filterProductoPadre, int page, int pageSize, String fieldOrder,
			boolean reverse) throws BussinessException {
		ProductoPadre productoPadreFilter = filterProductoPadre.getProductoPadre();
		Set<Familias> familiasFilter = filterProductoPadre.getFamilias();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteriaProductosPadre = session.createCriteria(ProductoPadre.class, "productoPadre");
		Projection idCountProjection = Projections.countDistinct("id");
		criteriaProductosPadre.setProjection(idCountProjection);
		if (productoPadreFilter != null) {
			String nombreFinal = "";
			if (productoPadreFilter.getNombre() != null) {
				nombreFinal = productoPadreFilter.getNombre().trim();
			}
			// restriccion para el nombre
			if (nombreFinal != null && !nombreFinal.equals("")) {
				String[] nombresArray = nombreFinal.split("\\s+");
				Conjunction disjunctionNombres = Restrictions.conjunction();
				for (String nombre : nombresArray) {
					if (!nombre.equals("")) {
						disjunctionNombres.add(Restrictions.sqlRestriction("concat({alias}.nombre, this_.nombre) like (?)", "%" + nombre + "%", StandardBasicTypes.STRING));
					}
				}
				criteriaProductosPadre.add(disjunctionNombres);
			}
			// nos fijamos el id del producto padre
			if (!Objects.isNull(productoPadreFilter.getId())) {
				if (productoPadreFilter.getId() > 0) {
					criteriaProductosPadre.add(Restrictions.eq("id", productoPadreFilter.getId()));
				}
			}
		}
//        if (productoPadreFilter != null) {
//            if (productoPadreFilter.getRubro() != null) {
//                criteriaProductosPadre.createAlias("rubro", "rubro");
//                Criterion c1 = Restrictions.eq("rubro.id", productoPadreFilter.getRubro().getId());
//                criteriaProductosPadre.add(c1);
//            }
//        }
		if (familiasFilter != null) {
			criteriaProductosPadre.createAlias("familia", "familia");
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
				Disjunction disjunction = Restrictions.disjunction();
				for (String id : mySetIdChilds) {
					disjunction.add(Restrictions.eq("familia.id", Integer.parseInt(id)));
				}
				criteriaProductosPadre.add(disjunction);
			}
		}
		/*###########################Control por APP############################*/
		criteriaProductosPadre.setFetchMode("productoPadre.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		criteriaProductosPadre.add(c1);
		/*######################################################################*/
		// setup criteria, joins etc here
		Integer totalResultCount = ((Long) criteriaProductosPadre.uniqueResult()).intValue();

		if (reverse) {
			criteriaProductosPadre.addOrder(Order.asc(fieldOrder.trim()));
		} else {
			criteriaProductosPadre.addOrder(Order.desc(fieldOrder.trim()));
		}
		criteriaProductosPadre.setProjection(Projections.distinct(Projections.property("id")));
		criteriaProductosPadre.setFirstResult((page - 1) * pageSize);
		criteriaProductosPadre.setMaxResults(pageSize);
		List uniqueSubList = criteriaProductosPadre.list();
		criteriaProductosPadre.setProjection(null);
		criteriaProductosPadre.setFirstResult(0);
		criteriaProductosPadre.setMaxResults(Integer.MAX_VALUE);
		criteriaProductosPadre.add(Restrictions.in("id", uniqueSubList));
		criteriaProductosPadre.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		ArrayList<ProductoPadre> lista = new ArrayList<ProductoPadre>();
		if (totalResultCount > 0) {
			lista = new ArrayList<ProductoPadre>(criteriaProductosPadre.list());
		}
		Pagination<ProductoPadre> pagination = new Pagination<ProductoPadre>();
		pagination.setData(lista);
		pagination.setTotal(totalResultCount);
		pagination.setPage(page);
		pagination.setPageSize(pageSize);
		return pagination;
	}

	@Override
	public ProductoPadre insert(ProductoPadre productoPadre) {
		productoPadre.setFechaCreacion(new Date());
		Session session = sessionFactory.getCurrentSession();
		/*###########################Control por APP############################*/
		productoPadre.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
		/*######################################################################*/
		session.save(productoPadre);
		return productoPadre;
	}

	@Override
	public ProductoPadre update(ProductoPadre productoPadre) {
		Session session = sessionFactory.getCurrentSession();
		ProductoPadre productoPadreDB = session.get(ProductoPadre.class, productoPadre.getId());
		Integer idFamiliaOld = productoPadreDB.getFamilia().getId();
		productoPadreDB.setFamilia(productoPadre.getFamilia());
		productoPadreDB.setNombreCorto(productoPadre.getNombreCorto());
		productoPadreDB.setDetalle(productoPadre.getDetalle());
		productoPadreDB.setNombre(productoPadre.getNombre());
		productoPadreDB.setFechaCreacion(productoPadre.getFechaCreacion());
		session.update(productoPadreDB);
		// si cambia la familia se debe modificar los codigos especiales de los productos
		if (!Objects.equals(idFamiliaOld, productoPadre.getFamilia().getId())) {
			// modificamos los codigos especiales
			productoDAO.updateCodigosEspecialFromProdPadre(productoPadre.getId(), productoPadre.getFamilia());
		}
		return productoPadre;
	}

}
