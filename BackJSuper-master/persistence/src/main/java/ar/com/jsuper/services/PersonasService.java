package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.utils.FilterEntidad;
import ar.com.jsuper.dto.PersonasDTO;
import ar.com.jsuper.dto.PersonasListDTO;
import ar.com.jsuper.dto.PersonasMinDTO;
import ar.com.jsuper.dto.PersonasSinClienteDTO;
import java.util.List;

public interface PersonasService {

    Pagination<PersonasListDTO> getPersonasBypage(FilterEntidad personaFilter, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException;

    Pagination<PersonasMinDTO> getPersonasByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse);

    List<PersonasMinDTO> getAllPersAndCliByMultipleFilter(String query, String fieldOrder, boolean reverse, boolean  saldo);

    PersonasDTO insertPersona(PersonasDTO personasDTO) throws BussinessException;

    PersonasDTO updatePersona(Integer id, PersonasDTO personaDTO) throws BussinessException;

    PersonasSinClienteDTO get(Integer id) throws BussinessException;

    PersonasMinDTO getByDni(String dni);

    void delete(Integer id) throws BussinessException;

}
