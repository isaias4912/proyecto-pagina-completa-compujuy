package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;

public interface SecurityService<T> {

    void checkIfIdBelognsApp(Integer id,  Class<T> clazz) throws BussinessException;
    void checkIfIdBelognsAppForProducto(Integer id, Class<T> clazz) throws BussinessException;

}
