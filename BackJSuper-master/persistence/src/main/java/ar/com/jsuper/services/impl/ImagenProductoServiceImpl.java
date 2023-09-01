package ar.com.jsuper.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.ImagenProductoDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.ImagenProducto;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.dto.ImagenProductoDTO;
import ar.com.jsuper.dto.ProductoDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.ImagenProductoService;
import java.util.Set;

@Service
public class ImagenProductoServiceImpl implements ImagenProductoService {

    @Autowired
    private ImagenProductoDAO imagenProductoDAO;
    @Autowired
    private OrikaBeanMapper modelMapper;

    @Transactional(rollbackFor = BussinessException.class)
    public void crudImagenesByProducto(Set<ImagenProductoDTO> imagenesProductoDTO, ProductoDTO productoDTO) throws BussinessException {
        Producto producto = modelMapper.map(productoDTO, Producto.class);
        for (ImagenProductoDTO imagenProductoDTO : imagenesProductoDTO) {
            if (imagenProductoDTO.getAccion() != null) {
                ImagenProducto imagenProducto = modelMapper.map(imagenProductoDTO, ImagenProducto.class);
                if (imagenProductoDTO.getAccion() == 0) {//se crea una nueva imagen
                    imagenProducto.setProducto(producto);
                    imagenProductoDAO.insert(imagenProducto);
                }
                if (imagenProductoDTO.getAccion() == 1) {//se crea una nueva imagen
                    imagenProducto.setProducto(producto);
                    imagenProductoDAO.update(imagenProducto);
                }
                if (imagenProductoDTO.getAccion() == 2) {//eliminacion
                    imagenProductoDAO.delete(imagenProducto);
                }
            }
        }
    }

}
