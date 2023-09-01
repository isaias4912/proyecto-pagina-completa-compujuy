package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Contactos;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Personas;
import java.util.Set;

public interface ContactosDAO extends GenericDAO<Contactos, Integer> {

    public void saveUpdateOrDelete(Entidad persona, Set<Contactos> domicilios, Set<Contactos> domiciliosBD) throws BussinessException;

//    public void saveUpdateOrDelete(Empresas empresa, Set<Contactos> contactos, Set<Contactos> contactosBD) throws BussinessException;

    public Contactos getObject(Contactos domicilioOld, Contactos omiciliosNew);

}
