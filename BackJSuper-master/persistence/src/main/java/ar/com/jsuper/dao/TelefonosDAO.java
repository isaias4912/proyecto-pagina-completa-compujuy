package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.domain.Telefonos;
import java.util.Set;

public interface TelefonosDAO extends GenericDAO<Telefonos, Integer> {

    public void saveUpdateOrDelete(Entidad entidad, Set<Telefonos> telefonos, Set<Telefonos> telefonosBD) throws BussinessException;

//    public void saveUpdateOrDelete(Empresas empresa, Set<Telefonos> telefonos, Set<Telefonos> telefonosBD) throws BussinessException;

    public Telefonos getObject(Telefonos telefonoOld, Telefonos telefonoNew);

}
