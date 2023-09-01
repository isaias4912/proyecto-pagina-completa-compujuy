package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.StockSucursalDAO;
import ar.com.jsuper.dao.SucursalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.StockSucursal;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.dto.ProductoDTO;
import ar.com.jsuper.dto.StockSucursalDTO;
import ar.com.jsuper.dto.SucursalesDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.SucursalService;
import java.util.List;
import java.util.Set;

@Service
public class SucursalServiceImpl implements SucursalService {

    @Autowired
    private SucursalDAO sucursalDAO;
    @Autowired
    private StockSucursalDAO stockSucursalDAO;
    @Autowired
    private OrikaBeanMapper modelMapper;

    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<SucursalesDTO> getAllActive() throws BussinessException {
        List<Sucursales> sucursales = sucursalDAO.getListAllActive();
        List<SucursalesDTO> sucursalesDTO = modelMapper.mapAsList(sucursales, SucursalesDTO.class);
        return sucursalesDTO;
    }

    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<SucursalesDTO> getAll() {
        List<Sucursales> sucursales = sucursalDAO.getListAll();
        List<SucursalesDTO> sucursalesDTO = modelMapper.mapAsList(sucursales, SucursalesDTO.class);
        return sucursalesDTO;
    }

    @Override
    public void crudStockSucursalByProducto(Set<StockSucursalDTO> stocksSucursalDTO, ProductoDTO productoDTO) throws BussinessException {
        Producto producto = modelMapper.map(productoDTO, Producto.class);
        for (StockSucursalDTO stockSucursalDTO : stocksSucursalDTO) {
            if (stockSucursalDTO.getAccion() != null) {
                StockSucursal stockSucursal = modelMapper.map(stockSucursalDTO, StockSucursal.class);
                if (stockSucursalDTO.getAccion() == 0) {//se crea una nueva imagen
                    stockSucursal.setProducto(producto);
                    stockSucursalDAO.insert(stockSucursal);
                }
                if (stockSucursalDTO.getAccion() == 1) {//se crea una nueva imagen
                    stockSucursal.setProducto(producto);
                    stockSucursalDAO.update(stockSucursal);
                }
                if (stockSucursalDTO.getAccion() == 2) {//eliminacion
                    stockSucursalDAO.delete(stockSucursal);
                }
            }
        }
    }

}
