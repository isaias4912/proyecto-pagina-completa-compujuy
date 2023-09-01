package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.RolesDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Menu;
import ar.com.jsuper.domain.Roles;
import ar.com.jsuper.domain.Usuarios;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

@Repository
public class RolesDAOImpl extends GenericDAOImpl<Roles, Integer> implements RolesDAO {

    @Override
    public void saveUpdateOrDelete(Usuarios usuario, Set<Roles> roles, Set<Roles> rolesBD) throws BussinessException {
        Boolean encontro = false;
        for (Roles rol : roles) {
            encontro = false;
            for (Roles rolBD : rolesBD) {
                if (Objects.equals(rol.getId(), rolBD.getId())) {
                    encontro = true;
                }
            }
            if (!encontro) {
                usuario.getRoles().add(this.load(rol.getId()));
            }
        }
        for (Roles roleBD : rolesBD) {
            encontro = false;
            for (Roles rol : roles) {
                if (Objects.equals(rol.getId(), roleBD.getId())) {
                    encontro = true;
                }
            }
            if (!encontro) {
                usuario.getRoles().remove(roleBD);
            }
        }
    }

    @Override
    public List<Menu> getMenuesFromRol(Roles rol) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Menu.class, "menu");
        Criterion c1 = Restrictions.eq("rol", rol);
        c.add(c1);
        return c.list();
    }

    @Override
    public Menu getMenuesPrincipalFromNameRol(String nombre) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Menu.class, "menu");
        Criteria criteriaRol = c.createCriteria("menu.rol", "rol", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaMenuItems = c.createAlias("menu.menuItems", "menuItems", JoinType.LEFT_OUTER_JOIN);
//        criteriaMenuItems.createAlias("menuItems.menuItemData", "mmenuItemData", JoinType.LEFT_OUTER_JOIN);
        criteriaMenuItems.createAlias("menuItems.menuItem", "mmenuItem", JoinType.LEFT_OUTER_JOIN);
        criteriaMenuItems.createAlias("menuItems.menuItems", "mmenuItems", JoinType.LEFT_OUTER_JOIN);
        Criterion c1 = Restrictions.eq("nombre", nombre);
        criteriaRol.add(c1);
        c1 = Restrictions.eq("tipoMenu", 1);// el tipo 1 es el de principal
        c.add(c1);
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        try {
            return (Menu) c.uniqueResult();
        } catch (HibernateException exception) {
            return null;
        }
    }
}
