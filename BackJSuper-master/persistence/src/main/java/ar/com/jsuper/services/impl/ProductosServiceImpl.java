package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.CodigoBarrasDAO;
import ar.com.jsuper.dao.ImagenProductoDAO;
import ar.com.jsuper.dao.ListaPreciosDAO;

import java.util.ArrayList;
import java.util.Set;
import javax.annotation.PostConstruct;

import ar.com.jsuper.mapper.ProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.utils.FilterProducto;
import ar.com.jsuper.dto.ProductoDTO;
import ar.com.jsuper.dto.FilterProductoDTO;
import ar.com.jsuper.dto.ListaPreciosDTO;
import ar.com.jsuper.services.ProductosService;
import ar.com.jsuper.dao.ProductoDAO;
import ar.com.jsuper.dao.ProductosCompuestoDAO;
import ar.com.jsuper.dao.ProveedoresDAO;
import ar.com.jsuper.dao.StockSucursalDAO;
import ar.com.jsuper.dao.SucursalDAO;
import ar.com.jsuper.domain.ImagenProducto;
import ar.com.jsuper.domain.ListaPrecios;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.ProductosCompuestos;
import ar.com.jsuper.domain.StockSucursal;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.domain.utils.PrecioFamiliasProducto;
import ar.com.jsuper.domain.utils.PrecioProducto;
import ar.com.jsuper.dto.HistorialDTO;
import ar.com.jsuper.dto.IProductoPrecio;
import ar.com.jsuper.dto.ImagenProductoDTO;
import ar.com.jsuper.dto.PrecioFamiliasProductoDTO;
import ar.com.jsuper.dto.PrecioProductoDTO;
import ar.com.jsuper.dto.ProductoListDTO;
import ar.com.jsuper.dto.ProductoListStockDTO;
import ar.com.jsuper.dto.ProductoMinDTO;
import ar.com.jsuper.dto.ProductoNanoDTO;
import ar.com.jsuper.dto.ProductoPVDTO;
import ar.com.jsuper.dto.ProductoPreviewDTO;
import ar.com.jsuper.dto.ProductoRecursiveDTO;
import ar.com.jsuper.dto.ProductoSubMinDTO;
import ar.com.jsuper.dto.ProductoUpdateDTO;
import ar.com.jsuper.dto.SimpleObjectDTO;
import ar.com.jsuper.dto.StockSucursalMinDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.dto.reportes.ProductoBarCodeDTO;
import ar.com.jsuper.utils.PrecioUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class ProductosServiceImpl extends BaseService implements ProductosService {

    @Autowired
    private ProductoDAO productosDAO;
    @Autowired
    private ProductosCompuestoDAO productosCompuestoDAO;
    @Autowired
    private ImagenProductoDAO imagenProductoDAO;
    @Autowired
    private CodigoBarrasDAO codigoBarrasDAO;
    @Autowired
    private StockSucursalDAO stockSucursalDAO;
    @Autowired
    private SucursalDAO sucursalDAO;
    @Autowired
    private ProveedoresDAO proveedoresDAO;
    @Autowired
    private OrikaBeanMapper modelMappero;
    @Autowired
    private ListaPreciosDAO listaPreciosDAO;
    @Autowired
    ProductoMapper productoMapper;

    @PostConstruct
    private void init() {
    }

    @Transactional(rollbackFor = BussinessException.class)
    public ProductoDTO insert(ProductoDTO productoDTO) throws BussinessException {
        Producto producto = modelMappero.map(productoDTO, Producto.class);
        this.validateProducto(producto);
        try {
            producto = productosDAO.insert(producto);
            productoDTO = modelMappero.map(producto, ProductoDTO.class);
            return productoDTO;
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public ProductoDTO update(Integer id, ProductoDTO productoUpdateDTO) throws BussinessException {
//		productoUpdateDTO.getProducto().setId(id);
        Producto producto = modelMappero.map(productoUpdateDTO, Producto.class);
        this.validateProducto(producto);
        Producto productoBD = productosDAO.get(producto.getId());
        productosCompuestoDAO.saveUpdateOrDelete(producto, producto.getProductosCompuestos(), productoBD.getProductosCompuestos());
        imagenProductoDAO.saveUpdateOrDelete(producto, producto.getImagenProducto(), productoBD.getImagenProducto());
        codigoBarrasDAO.saveUpdateOrDelete(producto, producto.getCodigoBarras(), productoBD.getCodigoBarras());
        stockSucursalDAO.saveUpdateOrDelete(producto, producto.getStockSucursales(), productoBD.getStockSucursales());
        proveedoresDAO.saveUpdateOrDelete(productoBD, producto.getProveedores(), new HashSet<>(productoBD.getProveedores()));
        productosDAO.updatePersona(productoBD, producto);
        ProductoDTO productoDTO = modelMappero.map(producto, ProductoDTO.class);
        return productoDTO;
    }

    private void validateProducto(Producto producto) {
        if (producto.getNombre().trim().equals("")) {
            throw new DataIntegrityViolationException("El nombre del producto no puede ser vacio.");
        }
        if (producto.getNombreFinal().trim().equals("")) {
            throw new DataIntegrityViolationException("El nombre final del producto no puede ser vacio.");
        }
        if (Objects.isNull(producto.getProductoPadre())) {
            throw new DataIntegrityViolationException("El producto debe tener un producto padre");
        }
        if (Objects.isNull(producto.getContenidoNeto())) {
            throw new DataIntegrityViolationException("El producto debe tener un contenido neto");
        }
        if (Objects.isNull(producto.getUnidad())) {
            throw new DataIntegrityViolationException("El producto debe tener una unidad");
        }
        List<StockSucursal> stockSucursales = new ArrayList<>(producto.getStockSucursales());
        for (int i = 0; i < stockSucursales.size(); i++) {
            for (int j = 0; j < stockSucursales.size(); j++) {
                if (i != j) {
                    if (stockSucursales.get(i).getSucursal().getId() == stockSucursales.get(j).getSucursal().getId()) {
                        throw new DataIntegrityViolationException("El producto no puede tener dos sucursales iguales.");
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Set<ProductoDTO> getall() throws BussinessException {
        Set<Producto> productos = productosDAO.getAll();
        Set<ProductoDTO> productosDTO = null;
        if (productos != null) {
            productosDTO = modelMappero.mapAsSet(productos, ProductoDTO.class);
        }
        return productosDTO;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public ProductoDTO getProducto(int id, Boolean precios) {
        Producto detalleProducto = productosDAO.getProducto(id);
        ProductoDTO detalleProductosDTO = modelMappero.map(detalleProducto, ProductoDTO.class);
        if (precios) {
            this.setPrecios(detalleProductosDTO); // seteamos los precios si asi lo requiere
        }
        if (detalleProductosDTO != null) {
            return detalleProductosDTO;
        } else {
            return null;
        }
    }

    /**
     * Returns dto dinamyc as generic
     *
     * @param id
     * @param objectClass
     * @param <G>
     * @return
     */
    protected <G> G getProducto(Integer id, Class<G> objectClass) {
        return this.modelMappero.map(productosDAO.getProducto(id), objectClass);
    }

    /**
     * agregamos el precio normal como primer item y luego los demas precios de la lista hacemos aqui el unico calculo del precio, no se lo hace en los clientes
     */
    public void setPrecios(List<? extends IProductoPrecio> productos) {
        List<ListaPreciosDTO> tempDTO = new ArrayList<>();
        List<ListaPrecios> listaPrecios = listaPreciosDAO.getAllListActiveId();
        ListaPreciosDTO listaPrecio = null;
        tempDTO = modelMappero.mapAsList(listaPrecios, ListaPreciosDTO.class);
        for (IProductoPrecio producto : productos) {
            List<ListaPreciosDTO> temp = new ArrayList<>();
            listaPrecio = new ListaPreciosDTO();
            listaPrecio.setActivo(Boolean.TRUE);
            listaPrecio.setDetalle("");
            listaPrecio.setId(0);
            listaPrecio.setNombre("Normal");
            listaPrecio.setTipo(0);
            listaPrecio.setValor(BigDecimal.ZERO);
            listaPrecio.setPrecio(producto.getPrecioVenta());
            temp.add(listaPrecio);
            for (ListaPreciosDTO lp : tempDTO) {
                ListaPreciosDTO tempListaDTO = new ListaPreciosDTO();
                tempListaDTO.setActivo(lp.isActivo());
                tempListaDTO.setDetalle(lp.getDetalle());
                tempListaDTO.setFechaCreacion(lp.getFechaCreacion());
                tempListaDTO.setId(lp.getId());
                tempListaDTO.setNombre(lp.getNombre());
                tempListaDTO.setTipo(lp.getTipo());
                tempListaDTO.setValor(lp.getValor());
                if (Objects.isNull(producto.getPrecioCosto()) || Objects.isNull(producto.getPrecioCosto())) {
                    tempListaDTO.setPrecio(BigDecimal.ZERO);
                } else {
                    tempListaDTO.setPrecio(PrecioUtils.addPorcentaje(producto.getPrecioCosto(), lp.getValor(), lp.getTipo()));
                }
                temp.add(tempListaDTO);
            }
            producto.setPrecios(temp);
        }
    }

    public void setPrecios(IProductoPrecio productoDTO) {
        List<ListaPreciosDTO> temp = new ArrayList<>();
        List<ListaPreciosDTO> tempDTO = new ArrayList<>();
        List<ListaPrecios> listaPrecios = listaPreciosDAO.getAllListActiveId();
        tempDTO = modelMappero.mapAsList(listaPrecios, ListaPreciosDTO.class);
        ListaPreciosDTO listaPrecio = new ListaPreciosDTO();
        listaPrecio.setActivo(Boolean.TRUE);
        listaPrecio.setDetalle("");
        listaPrecio.setId(0);
        listaPrecio.setNombre("Normal");
        listaPrecio.setTipo(0);
        listaPrecio.setValor(BigDecimal.ZERO);
        listaPrecio.setPrecio(productoDTO.getPrecioVenta());
        temp.add(listaPrecio);
        for (ListaPreciosDTO lp : tempDTO) {
            lp.setPrecio(PrecioUtils.addPorcentaje(productoDTO.getPrecioCosto(), lp.getValor(), lp.getTipo()));
            temp.add(lp);
        }
        productoDTO.setPrecios(temp);
    }

    /**
     * Devuelve el producto mas todos los stocks que pueda tener en las sucursales
     *
     * @param id
     * @return
     * @throws BussinessException
     */
    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public ProductoDTO getProductoAndStock(Integer id, Boolean precios) {
        Producto detalleProducto = productosDAO.getProducto(id);
        List<Sucursales> sucursales = sucursalDAO.getListAllActive();
        Set<StockSucursal> stocks = detalleProducto.getStockSucursales();
        Set<StockSucursal> tempss = null;
        if (Objects.isNull(stocks)) {
            detalleProducto.setStockSucursales(new HashSet<>());
        }
        for (Sucursales sucursal : sucursales) {
            BigDecimal cantidadStock = productosDAO.getStockSucursal(detalleProducto, sucursal);
            Boolean entro = false;
            if (!Objects.isNull(stocks)) {
                for (StockSucursal stock : stocks) {
                    if (stock.getSucursal().getId() == sucursal.getId()) {
                        stock.setStock(cantidadStock);
                        entro = true;
                    }
                }
            }
            if (!entro) { // esto hacemos porque queremos mostrar todos los stocks que existen, no solo de lo que tenga el producto
                if (Objects.isNull(tempss)) {
                    tempss = new HashSet<>();
                }
                StockSucursal ss = new StockSucursal();
                ss.setStock(cantidadStock);
                ss.setSucursal(sucursal);
                tempss.add(ss);
            }
        }
        if (!Objects.isNull(tempss)) {
            for (StockSucursal temp : tempss) {
                detalleProducto.getStockSucursales().add(temp);
            }
        }
//        List<StockSucursal> stockSucursales = productosDAO.getStockSucursal(detalleProducto, sucursales);
        ProductoDTO detalleProductosDTO = modelMappero.map(detalleProducto, ProductoDTO.class);
        if (precios) {// si se requiere precio se pasa esto
            this.setPrecios(detalleProductosDTO);
        }
        if (detalleProductosDTO != null) {
            return detalleProductosDTO;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public ProductoMinDTO getProductoMin(int id) throws BussinessException {
        Producto detalleProducto = productosDAO.get(id);
        ProductoMinDTO detalleProductosDTO = modelMappero.map(detalleProducto, ProductoMinDTO.class);
        if (detalleProductosDTO != null) {
            return detalleProductosDTO;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public ProductoSubMinDTO getProductoSubMin(int id) throws BussinessException {
        Producto detalleProducto = productosDAO.get(id);
        ProductoSubMinDTO detalleProductosDTO = modelMappero.map(detalleProducto, ProductoSubMinDTO.class);
        if (detalleProductosDTO != null) {
            return detalleProductosDTO;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public ProductoPreviewDTO getProductoPreview(int id) {
        Producto detalleProducto = productosDAO.getProductoMin(id);
        ProductoPreviewDTO previewProducto = modelMappero.map(detalleProducto, ProductoPreviewDTO.class);
        ImagenProducto imagenProducto = imagenProductoDAO.getFirtsImageFromIdProducto(previewProducto.getId());
        ImagenProductoDTO imagenProductoDTO = modelMappero.map(imagenProducto, ImagenProductoDTO.class);
        previewProducto.setImagenProducto(imagenProductoDTO);
        return previewProducto;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<ProductoDTO> getProductoByBarCode(String query, Integer activo, Integer like, Integer tipoBarCode) throws BussinessException {
        List<Producto> productos = productosDAO.getProductoByBarCode(query, activo, like, tipoBarCode);
        List<ProductoDTO> productosDTO = null;
        if (productos != null) {
            productosDTO = modelMappero.mapAsList(productos, ProductoDTO.class);
        }
        return productosDTO;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Pagination<ProductoListDTO> getProductoByMultipleFilter(String query, int page, int pageSize, String fieldOrder, boolean reverse, Boolean precios) throws BussinessException {
        Pagination<Producto> paginacionProductos = productosDAO.getProductoByMultipleFilter(query, page, pageSize, fieldOrder, reverse);
        List<Producto> detalleProductos = null;
        if (paginacionProductos.getData() != null) {
            detalleProductos = paginacionProductos.getData();
        } else {
            detalleProductos = new ArrayList<>();
        }
        List<ProductoListDTO> detalleProductosDTO = new ArrayList<>();
        if (detalleProductos != null) {
            detalleProductosDTO = modelMappero.mapAsList(detalleProductos, ProductoListDTO.class);
        }
        if (precios) {
            this.setPrecios(detalleProductosDTO);
        }
        Pagination<ProductoListDTO> pag = new Pagination<>();
        pag.setData(detalleProductosDTO);
        pag.setTotal(paginacionProductos.getTotal());
        pag.setPageSize(paginacionProductos.getPageSize());
        pag.setPage(paginacionProductos.getPage());
        return pag;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Pagination<ProductoPVDTO> getWithouFilterPagination(int page, int pageSize) {
        Pagination<Producto> paginacionProductos = productosDAO.getWithouFilterPagination(page, pageSize);
        List<Producto> detalleProductos = null;
        if (paginacionProductos.getData() != null) {
            detalleProductos = paginacionProductos.getData();
        } else {
            detalleProductos = new ArrayList<>();
        }
        List<ProductoPVDTO> detalleProductosDTO = new ArrayList<>();
        if (detalleProductos != null) {
            detalleProductosDTO = modelMappero.mapAsList(detalleProductos, ProductoPVDTO.class);
        }
        this.setPrecios(detalleProductosDTO);
        Pagination<ProductoPVDTO> pag = new Pagination<>();
        pag.setData(detalleProductosDTO);
        pag.setTotal(paginacionProductos.getTotal());
        pag.setPageSize(paginacionProductos.getPageSize());
        pag.setPage(paginacionProductos.getPage());
        return pag;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<ProductoPVDTO> getProductoByBarCodeOrName(String query, Integer idSucursal, Integer activo, Integer like, Integer tipoBarCode, Boolean precios) throws BussinessException {
        List<Producto> productos = null;
        Sucursales sucursal = new Sucursales();
        sucursal.setId(idSucursal);
        if (query.contains(" ")) {// quiere decir que contiene espacios entonces no es un bar code
            productos = productosDAO.getProductoByName(query, activo);
        } else {
            productos = productosDAO.getProductoByBarCode(query, activo, like, tipoBarCode);
            if (productos.size() <= 0) {
                productos = productosDAO.getProductoByName(query, activo);
            }
        }
//		IProductoPrecio sdfd= new ProductoListDTO();
        List<ProductoPVDTO> productosDTO = null;
        if (productos != null) {
            productosDTO = new ArrayList<>();
            for (Producto producto : productos) {
                BigDecimal stock = productosDAO.getStockSucursal(producto, sucursal);
                ProductoPVDTO ppvdto = modelMappero.map(producto, ProductoPVDTO.class);
                ppvdto.setStock(stock);
                productosDTO.add(ppvdto);
            }

        }
        if (precios) {
            this.setPrecios(productosDTO);
        }
        return productosDTO;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<ProductoListDTO> getProductoByBarCodeOrNameWithOutStock(String query, Integer activo, Integer like, Integer tipoBarCode) throws BussinessException {
        List<Producto> productos = null;
        if (query.contains(" ")) {// quiere decir que contiene espacios entonces no es un bar code
            productos = productosDAO.getProductoByName(query, activo);
        } else {
            productos = productosDAO.getProductoByBarCode(query, activo, like, tipoBarCode);
            if (productos.size() <= 0) {
                productos = productosDAO.getProductoByName(query, activo);
            }
        }
        List<ProductoListDTO> ppvdto = modelMappero.mapAsList(productos, ProductoListDTO.class);
        return ppvdto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoNanoDTO> getProductosMin(Set<Integer> ids) {
        List<Producto> productos = this.productosDAO.getProductosMin(ids);
        return modelMappero.mapAsList(productos, ProductoNanoDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoBarCodeDTO> getProductosMinBarCode(Set<Integer> ids) {
        List<Producto> productos = this.productosDAO.getProductosMin(ids);
        return modelMappero.mapAsList(productos, ProductoBarCodeDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoBarCodeDTO getProductoMinBarCode(Integer id) {
        Producto producto = this.productosDAO.getProductoMin(id);
        return modelMappero.map(producto, ProductoBarCodeDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoPVDTO> getProductosFromIds(Integer idSucursal, Set<SimpleObjectDTO> productosIds, Boolean precios) {
        List<Producto> productos = new ArrayList<>();
        Sucursales sucursal = new Sucursales();
        sucursal.setId(idSucursal);
        for (SimpleObjectDTO objectSimple : productosIds) {
            Producto producto = productosDAO.getProductoFromIdForPV(objectSimple.getId());
            productos.add(producto);
        }
//		productos = productosDAO.getProductoFromIdForPV(productosIds);
        List<ProductoPVDTO> productosDTO = null;
        if (productos != null) {
            productosDTO = new ArrayList<>();
            for (Producto producto : productos) {
                BigDecimal stock = productosDAO.getStockSucursal(producto, sucursal);
                ProductoPVDTO ppvdto = modelMappero.map(producto, ProductoPVDTO.class);
                ppvdto.setStock(stock);
                productosDTO.add(ppvdto);
            }

        }
        if (precios) {
            this.setPrecios(productosDTO);
        }
        return productosDTO;
    }


    /*
     *Metodo que nos devuelve un object Pagination del tipo DetalleProductosDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Pagination<ProductoListDTO> getDetalleProductoByPage(FilterProductoDTO productoFilter, int page, int pageSize, String fieldOrder,
                                                                boolean reverse, String type) {
        FilterProducto filterProducto = modelMappero.map(productoFilter, FilterProducto.class);
        // armamos los filtros extras para los daos
        Pagination<Producto> paginacionProductos = productosDAO.getDetalleProductoByPage(filterProducto, page, pageSize,
                fieldOrder, reverse, type);
        List<Producto> detalleProductos = null;
        if (paginacionProductos.getData() != null) {
            detalleProductos = paginacionProductos.getData();
        } else {
            detalleProductos = new ArrayList<>();
        }
        List<? extends ProductoListDTO> detalleProductosDTO = new ArrayList<>();
        if (detalleProductos != null) {
            if ((!Objects.isNull(filterProducto.getSucursales()) && !filterProducto.getSucursales().isEmpty())
                    || (filterProducto.getShowStock() && !Objects.isNull(filterProducto.getShowStock()))
                    || (!Objects.isNull(filterProducto.getFilterStock()) && filterProducto.getFilterStock() > 0)) {
                detalleProductosDTO = modelMappero.mapAsList(detalleProductos, ProductoListStockDTO.class);
            } else {
//                detalleProductosDTO = modelMappero.mapAsList(detalleProductos, ProductoListDTO.class);
                detalleProductosDTO = this.productoMapper.productoListToProductListDto(detalleProductos);
            }
        }
        List<ProductoListDTO> variable = (List<ProductoListDTO>) (List<?>) detalleProductosDTO;
        Pagination<ProductoListDTO> pag = new Pagination<>();
        pag.setData(variable);
        pag.setTotal(paginacionProductos.getTotal());
        pag.setPageSize(paginacionProductos.getPageSize());
        pag.setPage(paginacionProductos.getPage());
        return pag;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<ProductoListDTO> getDetalleProducto(FilterProductoDTO productoFilter, String fieldOrder,
                                                    boolean reverse, String type) throws BussinessException {
        Pagination<ProductoListDTO> pagination = this.getDetalleProductoByPage(productoFilter, 0, 0, fieldOrder, reverse, type);
        return pagination.getData();
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public void enabledOrdisabled(Set<ProductoDTO> productosDTO, boolean value) throws BussinessException {
        for (ProductoDTO productoDTO : productosDTO) {
            Producto producto = productosDAO.get(productoDTO.getId());
            producto.setActivo(value);
            productosDAO.update(producto);
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Map<String, Object> isExistCodigoBarra(String code) throws BussinessException {
        return productosDAO.isExistCodigoBarra(code);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public void deleteProducto(ProductoMinDTO productoDTO) throws BussinessException {
        Producto producto = modelMappero.map(productoDTO, Producto.class);
        productosDAO.delete(producto);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public int updatePrecioByFamilia(PrecioFamiliasProductoDTO precioFamiliasProductoDTO) {
        PrecioFamiliasProducto precioFamiliasProducto = modelMappero.map(precioFamiliasProductoDTO, PrecioFamiliasProducto.class);
        int result = productosDAO.updatePrecioByFamilia(precioFamiliasProducto);
        if (result > 0) {
            return result;
        } else {
            throw new HibernateException("Hubo un error");
        }
    }

    /**
     * Devuelve los stocks de un producto, de todas las sucursales existentes
     *
     * @param idProducto
     * @return
     */
    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<StockSucursalMinDTO> getStocksByProducto(Integer idProducto) {
        return modelMappero.mapAsList(productosDAO.getStockSucursal(idProducto), StockSucursalMinDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public int updatePrecio(Integer idProducto, PrecioProductoDTO precioProductoDTO) {
        PrecioProducto precioProducto = modelMappero.map(precioProductoDTO, PrecioProducto.class);
        int result = productosDAO.updatePrecio(idProducto, precioProducto, this.getUser());
        if (result > 0) {
            return result;
        } else {
            throw new HibernateException("Hubo un error");
        }

    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public Map getParentsAndChildsFromId(Integer idProducto) {
        Producto producto = productosDAO.get(idProducto);
        Map<String, ProductoRecursiveDTO> map = new HashMap<>();
        map.put("parents", this.getParents(producto, BigDecimal.ZERO));
        map.put("childs", this.getChilds(producto, BigDecimal.ZERO));
        return map;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public ProductoRecursiveDTO getParentsFromId(Integer idProducto) {
        Producto producto = productosDAO.get(idProducto);
        return this.getParents(producto, BigDecimal.ZERO);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public ProductoRecursiveDTO getChildsFromId(Integer idProducto) {
        Producto producto = productosDAO.get(idProducto);
        return this.getChilds(producto, BigDecimal.ZERO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoRecursiveDTO getParents(Producto producto, BigDecimal cantidad) {
        ProductoRecursiveDTO productoRecursive = new ProductoRecursiveDTO();
        productoRecursive.setId(producto.getId());
        productoRecursive.setNombreFinal(producto.getNombreFinal());
        productoRecursive.setCantidad(cantidad);
        Set<ProductosCompuestos> productosCompuestos = productosCompuestoDAO.getProdCompuestosFromProducto(producto);
        if (!Objects.isNull(productosCompuestos)) {
            productoRecursive.setProductos(new HashSet<>());
            productosCompuestos.forEach((productosCompuesto) -> {
                ProductoRecursiveDTO pr = this.getParents(productosCompuesto.getProductoPrincipal(), productosCompuesto.getCantidad());
                productoRecursive.getProductos().add(pr);
            });
        }
        return productoRecursive;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoRecursiveDTO getChilds(Producto producto, BigDecimal cantidad) {
        if (producto.getTipo() == 2) {// si es producto compuesto nada mas verificamos
            ProductoRecursiveDTO productoRecursive = new ProductoRecursiveDTO();
            productoRecursive.setId(producto.getId());
            productoRecursive.setNombreFinal(producto.getNombreFinal());
            productoRecursive.setCantidad(cantidad);
            Set<ProductosCompuestos> productosCompuestos = productosCompuestoDAO.getProdCompuestosFromProductoPrincipal(producto);
            if (!Objects.isNull(productosCompuestos)) {
                productoRecursive.setProductos(new HashSet<>());
                productosCompuestos.forEach((productosCompuesto) -> {
                    ProductoRecursiveDTO pr = this.getChilds(productosCompuesto.getProducto(), productosCompuesto.getCantidad());
                    productoRecursive.getProductos().add(pr);
                });
            }
            return productoRecursive;
        } else {
            ProductoRecursiveDTO productoRecursive = new ProductoRecursiveDTO();
            productoRecursive.setId(producto.getId());
            productoRecursive.setNombreFinal(producto.getNombreFinal());
            productoRecursive.setCantidad(cantidad);
            return productoRecursive;
        }
    }

    @Override
    @Transactional()
    public void buildAllCodigosEspeciales() {
        productosDAO.buildAllCodigosEspeciales();
    }

    @Override
    @Transactional(readOnly = true)
    public String getCodigoEspecialFromIdFamilia(Integer idFamilia) {
        return productosDAO.getCodigoEspecialFromIdFamilia(idFamilia);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialDTO> getHistorialProducto(Integer idProducto, Integer page, Integer size) {
        return modelMappero.mapAsList(productosDAO.getHistorialProducto(idProducto, page, size), HistorialDTO.class);
    }

    @Override
    @Transactional
    public void resetCompujuy() {
        productosDAO.resetCompujuy();
    }
}
