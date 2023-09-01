package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Menu;
import ar.com.jsuper.domain.Roles;
import ar.com.jsuper.domain.Usuarios;
import java.util.List;
import java.util.Set;

public interface RolesDAO extends GenericDAO<Roles, Integer> {

    void saveUpdateOrDelete(Usuarios usuario, Set<Roles> roles, Set<Roles> rolesBD) throws BussinessException;

    List<Menu> getMenuesFromRol(Roles rol);

    public Menu getMenuesPrincipalFromNameRol(String nombre);

}
