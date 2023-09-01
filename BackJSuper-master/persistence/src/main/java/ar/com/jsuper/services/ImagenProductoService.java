package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.ImagenProductoDTO;
import ar.com.jsuper.dto.ProductoDTO;
import java.util.Set;

public interface ImagenProductoService {
	void crudImagenesByProducto(Set<ImagenProductoDTO> imagenesProductoDTOs, ProductoDTO productoDTO) throws BussinessException;
}
