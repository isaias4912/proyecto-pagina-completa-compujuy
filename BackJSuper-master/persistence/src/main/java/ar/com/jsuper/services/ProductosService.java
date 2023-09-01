package ar.com.jsuper.services;

import java.util.Set;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.dto.*;
import ar.com.jsuper.dto.reportes.ProductoBarCodeDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductosService {

    ProductoDTO insert(ProductoDTO producto) throws BussinessException;

    ProductoDTO update(Integer id, ProductoDTO producto) throws BussinessException;

    Set<ProductoDTO> getall() throws BussinessException;

    List<ProductoPVDTO> getProductoByBarCodeOrName(String query, Integer idSucursal, Integer activo, Integer like, Integer tipoBarCode, Boolean precios) throws BussinessException;

    List<ProductoListDTO> getProductoByBarCodeOrNameWithOutStock(String query, Integer activo, Integer like, Integer tipoBarCode) throws BussinessException;

    List<ProductoPVDTO> getProductosFromIds(Integer idSucursal, Set<SimpleObjectDTO> productosIds, Boolean precios);

    List<ProductoDTO> getProductoByBarCode(String query, Integer activo, Integer like, Integer tipoBarCode) throws BussinessException;

    public Pagination<ProductoPVDTO> getWithouFilterPagination(int page, int pageSize);

    ProductoBarCodeDTO getProductoMinBarCode(Integer id);

    ProductoDTO getProducto(int id, Boolean precios);

    void setPrecios(List<? extends IProductoPrecio> productos);

    void setPrecios(IProductoPrecio productoDTO);

    ProductoDTO getProductoAndStock(Integer id, Boolean precios);

    ProductoMinDTO getProductoMin(int id) throws BussinessException;

    List<ProductoNanoDTO> getProductosMin(Set<Integer> ids);

    List<ProductoBarCodeDTO> getProductosMinBarCode(Set<Integer> ids);

    ProductoPreviewDTO getProductoPreview(int id);

    ProductoSubMinDTO getProductoSubMin(int id) throws BussinessException;

    Pagination<ProductoListDTO> getProductoByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse, Boolean precios) throws BussinessException;

    Pagination<ProductoListDTO> getDetalleProductoByPage(FilterProductoDTO productoFilter, int numeroPagina, int pagina, String fieldOrder, boolean reverse, String type);

    List<ProductoListDTO> getDetalleProducto(FilterProductoDTO productoFilter, String fieldOrder, boolean reverse, String type) throws BussinessException;

    void enabledOrdisabled(Set<ProductoDTO> productosDTO, boolean value) throws BussinessException;

    Map<String, Object> isExistCodigoBarra(String code) throws BussinessException;

    void deleteProducto(ProductoMinDTO productoDTO) throws BussinessException;

    public int updatePrecio(Integer idProducto, PrecioProductoDTO precioProductoDTO);

    public int updatePrecioByFamilia(PrecioFamiliasProductoDTO precioFamiliasProductoDTO);

    public List<StockSucursalMinDTO> getStocksByProducto(Integer idProducto);

    public ProductoRecursiveDTO getParentsFromId(Integer idProducto);

    public Map getParentsAndChildsFromId(Integer idProducto);

    public ProductoRecursiveDTO getChildsFromId(Integer idProducto);

    public ProductoRecursiveDTO getParents(Producto producto, BigDecimal cantidad);

    public ProductoRecursiveDTO getChilds(Producto producto, BigDecimal cantidad);

    public void buildAllCodigosEspeciales();

    public String getCodigoEspecialFromIdFamilia(Integer idFamilia);

    public List<HistorialDTO> getHistorialProducto(Integer idProducto, Integer page, Integer size);

    public void resetCompujuy();
}
