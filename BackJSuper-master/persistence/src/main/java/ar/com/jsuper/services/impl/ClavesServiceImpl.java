package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.ClavesDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Claves;
import ar.com.jsuper.dto.ClavesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.ClavesService;
import java.util.Objects;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClavesServiceImpl implements ClavesService {

    @Autowired
    private OrikaBeanMapper modelMapper;
    @Autowired
    private ClavesDAO clavesDAO;

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public ClavesDTO getByClave(String clave) {
        Claves c = clavesDAO.getByClave(clave);
        if (Objects.isNull(c)) {
            throw new DataIntegrityViolationException("Error - No se encontro esa clave.");
        } else {
            return modelMapper.map(c, ClavesDTO.class);
        }
    }

}
