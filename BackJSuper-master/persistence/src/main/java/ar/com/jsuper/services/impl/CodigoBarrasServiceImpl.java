package ar.com.jsuper.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.CodigoBarrasDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.CodigoBarras;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.dto.CodigoBarrasDTO;
import ar.com.jsuper.dto.ProductoDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.CodigoBarrasService;
import java.util.Set;

@Service
public class CodigoBarrasServiceImpl implements CodigoBarrasService {

    @Autowired
    private CodigoBarrasDAO codigoBarrasDAO;
    @Autowired
    private OrikaBeanMapper modelMapper;

    @Transactional(rollbackFor = BussinessException.class)
    public void insertCodigoBarra(CodigoBarras codigoBarras) throws BussinessException {
        try {
            codigoBarrasDAO.insert(codigoBarras);
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Transactional(rollbackFor = BussinessException.class)
    public void crudCodigosBarraByProducto(Set<CodigoBarrasDTO> codigosBarrasDTO, ProductoDTO productoDTO) throws BussinessException {
        Producto producto = modelMapper.map(productoDTO, Producto.class);
        for (CodigoBarrasDTO codigoBarraDTO : codigosBarrasDTO) {
            if (codigoBarraDTO.getAccion() != null) {
                CodigoBarras codigoBarra = modelMapper.map(codigoBarraDTO, CodigoBarras.class);
                if (codigoBarraDTO.getAccion() == 0) {//se crea una nueva imagen
                    codigoBarra.setProducto(producto);
                    codigoBarrasDAO.insert(codigoBarra);
                }
                if (codigoBarraDTO.getAccion() == 1) {//update
                    codigoBarra.setProducto(producto);
                    codigoBarrasDAO.update(codigoBarra);
                }
                if (codigoBarraDTO.getAccion() == 2) {//eliminacion
                    codigoBarrasDAO.delete(codigoBarra);
                }
            }
        }
    }

}
