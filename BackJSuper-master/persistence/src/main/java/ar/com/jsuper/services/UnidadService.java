package ar.com.jsuper.services;

import java.util.Set;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.UnidadDTO;

public interface UnidadService {

    void insert(UnidadDTO unidad) throws BussinessException;
    UnidadDTO get(Integer id) throws BussinessException;
    Set<UnidadDTO> getAll() throws BussinessException;
}
