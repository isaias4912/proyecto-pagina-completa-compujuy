package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Caja;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.domain.Usuarios;
import java.util.List;
import java.util.Set;

public interface SucursalDAO extends GenericDAO<Sucursales, Integer> {

	public List<Sucursales> getListAllActive();

	public List<Sucursales> getListAll();

	public Sucursales getPrincipal();

	public List getSucursalesByUserLogged();

	public Sucursales savePrincipal(Sucursales sucursalPrincipal);

	public void saveUpdateOrDelete(Caja caja, Set<Sucursales> sucursales, Set<Sucursales> sucursalesBD) throws BussinessException;

	public void saveUpdateOrDelete(Usuarios usuario, Set<Sucursales> sucursales, Set<Sucursales> sucursalesBD);

	public Boolean isSucursalOfApp(Integer idSucursal);
}
