package ar.com.jsuper.dao;

import ar.com.jsuper.domain.Claves;

public interface ClavesDAO extends GenericDAO<Claves, Integer> {

    public Claves getByClave(String clave);

}
