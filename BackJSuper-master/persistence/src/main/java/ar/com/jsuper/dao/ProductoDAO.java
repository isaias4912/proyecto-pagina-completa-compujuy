package ar.com.jsuper.dao;

import java.util.Set;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.*;
import ar.com.jsuper.domain.utils.FilterProducto;
import ar.com.jsuper.domain.utils.Historial;
import ar.com.jsuper.domain.utils.PrecioFamiliasProducto;
import ar.com.jsuper.domain.utils.PrecioProducto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProductoDAO extends GenericDAO<Producto, Integer> {

    public Pagination<Producto> getDetalleProductoByPage(FilterProducto productoFilter, int numeroPagina, int pagina, String fieldOrder, boolean reverse, String type);

    public List<Producto> getProductosMin();

    public List<Producto> getProductosMin(Set<Integer> ids);

    public List<Producto> getProductosMinByFamilia(Integer idFamilia);

    public List<Producto> getProductosMinByProdPadre(Integer idProductoPadre);

    public Producto getProductoMin(Integer idProducto);

    public Pagination<Producto> getProductoByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException;

    public Pagination<Producto> getWithouFilterPagination(int page, int pageSize);

    public Producto getProducto(Integer id);

    public Set<PrecioProductos> getPreciosActivos(ProductoPadre id) throws BussinessException;

    public List<Producto> getProductoByName(String query, Integer activo) throws BussinessException;

    public List<Producto> getProductoByBarCode(String query, Integer activo, Integer like, Integer tipoBarCode) throws BussinessException;

    public List<Producto> getProductoByNameOrCodEspecial(String query, Integer activo);

    Map<String, Object> isExistCodigoBarra(String code) throws BussinessException;

    public Producto getObject(Producto productoOld, Producto productoNew);

    public Producto updatePersona(Producto productoOld, Producto productoNew);

    public Long getCantProdBajoPuntoRepo() throws BussinessException;

    public Long getCantProdBajoStockMin() throws BussinessException;

    public void prueba();

    public void resetCompujuy();

    public int updatePrecio(Integer idProducto, PrecioProducto precioProducto, UserLogged user);

    public int updatePrecioByFamilia(PrecioFamiliasProducto precioFamiliasProducto);

    public List<StockSucursal> getStocksByProducto(Integer idProducto);

    List<StockSucursal> getStockSucursal(Integer idProducto);

    List<StockSucursal> getStockSucursal(Producto producto);

    public BigDecimal getStockSucursal(Producto producto, Sucursales suc);

    public void buildAllCodigosEspeciales();

    public String getCodigoEspecialFromIdFamilia(Integer idFamilia);

    public int updateCodigosEspecialFromFamilia(Integer idFamilia, String oldNombreCorto, String newNombreCorto);

    public int updateCodigosEspecialFromProdPadre(Integer idProductoPadre, Familias newFamilia);

    public List<Historial> getHistorialProducto(Integer idProducto, Integer page, Integer size);

    public List<Producto> getAllProductosRelacionados(Producto producto);

    public List<Producto> getAllParents(Producto producto);

    public List<Producto> getAllChilds(Producto producto);

    public void saveMovimientoProducto(DetMovimientos detMovimiento, Date fecha);

    public void saveMovimientoProducto(CbteDet cbteDet, Date fecha);

    public Producto getProductoFromIdForPV(Integer id);

}
