package ar.com.jsuper.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.jsuper.dao.MovimientosDAO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.MovimientosService;

@Service
public class MovimientosServiceImpl implements MovimientosService {

    @Autowired
    private MovimientosDAO movimientosDAO;
    @Autowired
    private OrikaBeanMapper modelMapper;

}
