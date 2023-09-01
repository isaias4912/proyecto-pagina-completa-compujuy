package ar.com.jsuper.services;

import java.util.Set;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Rubros;
import ar.com.jsuper.dto.RubrosDTO;

public interface RubrosService {
	
	void insertRubro(Rubros rubro) throws BussinessException;
	Rubros getRubro(Integer id) throws BussinessException;
	Set<RubrosDTO> getall() throws BussinessException;
    void insertr() throws BussinessException;
}
