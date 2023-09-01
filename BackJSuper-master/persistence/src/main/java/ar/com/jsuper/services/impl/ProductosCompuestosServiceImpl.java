package ar.com.jsuper.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.jsuper.dao.ProductosCompuestoDAO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.ProductosCompuestosService;

@Service
public class ProductosCompuestosServiceImpl implements ProductosCompuestosService {

    @Autowired
    private ProductosCompuestoDAO productosCompuestoDAO;
    @Autowired
    private OrikaBeanMapper modelMapper;

//    public List<Producto> getParents(Producto producto) {
//        if (producto.getTipo() == 1) {
//            return null;
//        } else {
//            Set<ProductosCompuestos> pc = productosCompuestoDAO.getProdCompuestosFromProducto(producto);
//        }
//
//    }
}
