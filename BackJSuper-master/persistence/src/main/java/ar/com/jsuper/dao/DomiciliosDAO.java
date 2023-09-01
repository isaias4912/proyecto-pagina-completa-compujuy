package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Domicilios;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.domain.Proveedores;
import java.util.Set;

public interface DomiciliosDAO extends GenericDAO<Domicilios, Integer> {

    public void saveUpdateOrDelete(Entidad entidad, Set<Domicilios> domicilios, Set<Domicilios> domiciliosBD) throws BussinessException;

//    public void saveUpdateOrDelete(Empresas empresa, Set<Domicilios> domicilios, Set<Domicilios> domiciliosBD) throws BussinessException;

    public Domicilios getObject(Domicilios domicilioOld, Domicilios omiciliosNew);

}
