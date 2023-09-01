package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.CodigoBarras;
import ar.com.jsuper.dto.CodigoBarrasDTO;
import ar.com.jsuper.dto.ProductoDTO;
import java.util.Set;

public interface CodigoBarrasService {
	void insertCodigoBarra(CodigoBarras codigoBarra) throws BussinessException;
        void crudCodigosBarraByProducto(Set<CodigoBarrasDTO> codigosBarraDTO, ProductoDTO productoDTO) throws BussinessException;

}
