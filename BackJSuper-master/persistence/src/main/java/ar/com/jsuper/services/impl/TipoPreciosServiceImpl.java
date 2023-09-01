package ar.com.jsuper.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.TipoPreciosDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.TipoPrecios;
import ar.com.jsuper.dto.TipoPreciosDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.TipoPreciosService;
import java.util.Set;

@Service
public class TipoPreciosServiceImpl implements TipoPreciosService {

    @Autowired
    private TipoPreciosDAO tipoPreciosDAO;
    @Autowired
    private OrikaBeanMapper modelMapper;

    @Transactional(rollbackFor = BussinessException.class)
    public void insert(TipoPrecios rubro) throws BussinessException {
        try {
            tipoPreciosDAO.insert(rubro);
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Transactional(rollbackFor = BussinessException.class)
    public TipoPrecios get(Integer id) throws BussinessException {
        try {
            return tipoPreciosDAO.get(id);
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Set<TipoPreciosDTO> getAllActive() throws BussinessException {
        try {
            Set<TipoPrecios> tipoPrecios = tipoPreciosDAO.getAllActive("orden");
            Set<TipoPreciosDTO> tipoPreciosDTO = modelMapper.mapAsSet(tipoPrecios, TipoPreciosDTO.class);
            return tipoPreciosDTO;
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

}
