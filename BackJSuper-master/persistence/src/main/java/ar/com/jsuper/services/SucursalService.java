package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.ProductoDTO;
import ar.com.jsuper.dto.StockSucursalDTO;
import ar.com.jsuper.dto.SucursalesDTO;
import java.util.List;
import java.util.Set;

public interface SucursalService {
        public List<SucursalesDTO> getAllActive() throws BussinessException;
        void crudStockSucursalByProducto(Set<StockSucursalDTO> stockSucursalDTO, ProductoDTO productoDTO) throws BussinessException;

}
