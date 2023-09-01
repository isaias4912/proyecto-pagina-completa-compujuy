package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.ClavesDTO;

public interface ClavesService {


    public ClavesDTO getByClave(String clave) throws BussinessException;

}
