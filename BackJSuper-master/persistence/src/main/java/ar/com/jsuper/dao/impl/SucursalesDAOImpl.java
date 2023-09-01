package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.SucursalDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Caja;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.security.TenantContext;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class SucursalesDAOImpl extends GenericDAOImpl<Sucursales, Integer> implements SucursalDAO {

	@Override
	public List<Sucursales> getListAllActive() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Sucursales.class, "s");
		Criterion c1 = Restrictions.eq("s.activo", true);
		c.add(c1);
		/*###########################Control por APP############################*/
		c.setFetchMode("s.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		c.addOrder(Order.asc("id"));
		return c.list();
	}

	@Override
	public List<Sucursales> getListAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Sucursales.class, "s");
		/*###########################Control por APP############################*/
		c.setFetchMode("s.app", FetchMode.JOIN);
		Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		c.addOrder(Order.asc("id"));
		return c.list();
	}

	@Override
	public Sucursales getPrincipal() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Sucursales.class, "s");
		Criterion c1 = Restrictions.eq("s.activo", true);
		c.add(c1);
		/*###########################Control por APP############################*/
		c.setFetchMode("s.app", FetchMode.JOIN);
		c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
		c.add(c1);
		/*######################################################################*/
		c1 = Restrictions.eq("principal", true);
		c.add(c1);
		if (c.list().size() > 0) {
			return (Sucursales) c.list().get(0);
		} else {
			return null;
		}
	}

	@Override
	public List getSucursalesByUserLogged() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Sucursales.class, "s");
		Criteria criteriaUsers = c.createCriteria("s.usuarios", JoinType.LEFT_OUTER_JOIN);
		Criterion c1 = Restrictions.eq("s.activo", true);
		c.add(c1);
		/*###########################Control por Usuario############################*/
		c1 = Restrictions.eq("id", TenantContext.getCurrentIdUser());
		criteriaUsers.add(c1);
		/*######################################################################*/
		return c.list();
	}

	@Override
	public Sucursales savePrincipal(Sucursales sucursalPrincipal) {
		if (this.getPrincipal().getId() == sucursalPrincipal.getId()) {
			Session session = sessionFactory.getCurrentSession();
			Sucursales sucursalDB = session.load(Sucursales.class, sucursalPrincipal.getId());
			sucursalDB = this.getObject(sucursalDB, sucursalPrincipal);
			session.update(sucursalDB);
			return sucursalDB;
		} else {
			throw new DataIntegrityViolationException("Error - La sucursal indicada no es la principal");
		}
	}

	/**
	 * verifica si la sucursal es de la app, una validacion necesaria para algunos casos y para evitar futuros probl
	 *
	 * @param sucursalPrincipal
	 * @return
	 */
	@Override
	public Boolean isSucursalOfApp(Integer idSucursal) {
		Session session = sessionFactory.getCurrentSession();
		List sucursal = session.createSQLQuery("SELECT * FROM sucursales where app_id= :idApp AND id= :idSucursal")
				.setParameter("idApp", TenantContext.getCurrentTenant())
				.setParameter("idSucursal", idSucursal)
				.list();
		return (!Objects.isNull(sucursal) && sucursal.size() > 0);
	}

	@Override
	public Sucursales getObject(Sucursales sucursalOld, Sucursales sucursalNew) {
		if (Objects.isNull(sucursalOld)) {
			sucursalOld = new Sucursales();
		}
		sucursalOld.setDetalle(sucursalNew.getDetalle());
		sucursalOld.setDireccion(sucursalNew.getDireccion());
		sucursalOld.setNombre(sucursalNew.getNombre());
		sucursalOld.setPrefijoTelCel(sucursalNew.getPrefijoTelCel());
		sucursalOld.setNumeroTelCel(sucursalNew.getNumeroTelCel());
		sucursalOld.setPrefijoTelFijo(sucursalNew.getPrefijoTelFijo());
		sucursalOld.setNumeroTelFijo(sucursalNew.getNumeroTelFijo());
		return sucursalOld;
	}

	@Override
	public void saveUpdateOrDelete(Caja caja, Set<Sucursales> sucursales, Set<Sucursales> sucursalesBD) throws BussinessException {
		Boolean encontro = false;
		for (Sucursales prov : sucursales) {
			encontro = false;
			for (Sucursales provBD : sucursalesBD) {
				if (Objects.equals(prov.getId(), provBD.getId())) {
					encontro = true;
				}
			}
			if (!encontro) {
				caja.getSucursales().add(this.load(prov.getId()));
			}
		}
		for (Sucursales provBD : sucursalesBD) {
			encontro = false;
			for (Sucursales prov : sucursales) {
				if (Objects.equals(prov.getId(), provBD.getId())) {
					encontro = true;
				}
			}
			if (!encontro) {
				caja.getSucursales().remove(provBD);
			}
		}
	}

	@Override
	public void saveUpdateOrDelete(Usuarios usuario, Set<Sucursales> sucursales, Set<Sucursales> sucursalesBD) {
		Boolean encontro = false;
		for (Sucursales rol : sucursales) {
			encontro = false;
			for (Sucursales rolBD : sucursalesBD) {
				if (Objects.equals(rol.getId(), rolBD.getId())) {
					encontro = true;
				}
			}
			if (!encontro) {
				usuario.getSucursales().add(this.load(rol.getId()));
			}
		}
		for (Sucursales roleBD : sucursalesBD) {
			encontro = false;
			for (Sucursales rol : sucursales) {
				if (Objects.equals(rol.getId(), roleBD.getId())) {
					encontro = true;
				}
			}
			if (!encontro) {
				usuario.getSucursales().remove(roleBD);
			}
		}
	}
}
