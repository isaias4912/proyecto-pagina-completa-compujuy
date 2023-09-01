package ar.com.jsuper.services.param.impl;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.param.ParamEntradasCajasDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.jsuper.services.param.ParamEntradasCajasService;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParamEntradasCajasServiceImpl implements ParamEntradasCajasService {

   
    @Autowired
    private ParamEntradasCajasDAO paramEntradasCajasDAO;

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public List getAllActive(){
        return paramEntradasCajasDAO.getAllParamListActive();
    }

   
}
