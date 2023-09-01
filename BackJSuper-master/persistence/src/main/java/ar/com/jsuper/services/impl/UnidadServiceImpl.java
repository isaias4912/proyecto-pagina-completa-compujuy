package ar.com.jsuper.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.UnidadDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Unidad;
import ar.com.jsuper.dto.UnidadDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.UnidadService;

@Service
public class UnidadServiceImpl implements UnidadService {

    @Autowired
    private UnidadDAO unidadDAO;
    @Autowired
    private OrikaBeanMapper modelMapper;

    @Transactional(rollbackFor = BussinessException.class)
    public void insert(UnidadDTO rubroDTO) throws BussinessException {
        try {
            Unidad rubro = modelMapper.map(rubroDTO, Unidad.class);
            unidadDAO.insert(rubro);
        } catch (Exception e) {
            throw new BussinessException(e);
        }   
    }

    @Transactional(rollbackFor = BussinessException.class)
    public UnidadDTO get(Integer id) throws BussinessException {
        try {
            Unidad unidad = unidadDAO.get(id);
            UnidadDTO unidadDTO = modelMapper.map(unidad, UnidadDTO.class);
            return unidadDTO;
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Set<UnidadDTO> getAll() throws BussinessException {
        try {
            Set<UnidadDTO> unidadesDTO = modelMapper.mapAsSet(unidadDAO.getAll(), UnidadDTO.class);
            return unidadesDTO;
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

}
