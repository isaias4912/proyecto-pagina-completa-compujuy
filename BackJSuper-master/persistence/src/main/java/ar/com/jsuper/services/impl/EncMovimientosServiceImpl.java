package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.utils.PaginationExtraData;
import ar.com.jsuper.domain.*;
import ar.com.jsuper.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.EncMovimientosService;
import ar.com.jsuper.dao.EncMovimientosDAO;
import ar.com.jsuper.dao.MovimientosDAO;
import ar.com.jsuper.dao.ProductoDAO;
import ar.com.jsuper.dao.ProveedoresDAO;
import ar.com.jsuper.dao.SucursalDAO;
import ar.com.jsuper.dao.VentasDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.security.TenantContext;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import ar.com.jsuper.dao.CbteCompDAO;

@Service
public class EncMovimientosServiceImpl implements EncMovimientosService {

    @Autowired
    private EncMovimientosDAO facturasDAO;
    @Autowired
    private CbteCompDAO contFacturasEncDAO;
    @Autowired
    private MovimientosDAO movimientosDAO;
    @Autowired
    private OrikaBeanMapper modelMapper;
    @Autowired
    private ProductoDAO productoDAO;
    @Autowired
    private ProveedoresDAO proveedorDAO;
    @Autowired
    private SucursalDAO sucursalDAO;
    @Autowired
    private VentasDAO ventasDAO;

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public EncMovimientosDTO insertMovimiento(EncMovimientosDTO facturaDTO) {
        // verificamos que los que se quiere ingresar es de la sucursal habilitada primero
        Boolean res = sucursalDAO.isSucursalOfApp(facturaDTO.getSucursal().getId());
        if (!res) {
            throw new DataIntegrityViolationException("La sucursal seleccionada no esta habilitada, por favor inicie sesión nuevamente, o refresque el sistema.");
        }
        EncMovimientos factura = modelMapper.map(facturaDTO, EncMovimientos.class);
        factura = facturasDAO.insert(factura);
        return modelMapper.map(factura, EncMovimientosDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public PaseMovimientosDTO insertMovimientoPase(PaseMovimientosDTO movimientoPaseDTO) {
        PaseMovimientos paseMovimiento = modelMapper.map(movimientoPaseDTO, PaseMovimientos.class);
        paseMovimiento.setEstado(Boolean.FALSE);
        paseMovimiento.setFecha(new Date());
        paseMovimiento.setApp(new App(TenantContext.getCurrentTenant()));
        facturasDAO.insertPase(paseMovimiento);
        return modelMapper.map(paseMovimiento, PaseMovimientosDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PaseMovimientosDTO getPase(Integer idPase) {
        PaseMovimientos paseMovimiento = facturasDAO.getPase(idPase);
        return modelMapper.map(paseMovimiento, PaseMovimientosDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public void removePase(PaseMovimientosDTO paseDTO) {
        PaseMovimientos pase = modelMapper.map(paseDTO, PaseMovimientos.class);
        facturasDAO.removePase(pase);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public PaseMovimientosDTO confirmPase(PaseMovimientosDTO movimientoPaseDTO) {
        // insertamos primero desde la sucursal origen a la destino
        PaseMovimientos paseMovimiento = facturasDAO.getPase(movimientoPaseDTO.getId());
        paseMovimiento.setEstado(true);
        facturasDAO.updatePase(paseMovimiento);
//		Producto producto = modelMapper.map(paseMovimiento.getProducto(), Producto.class);
        Producto producto = paseMovimiento.getProducto();
        EncMovimientos encMovimientosIng = new EncMovimientos();
        if (!Objects.isNull(movimientoPaseDTO.getDescripcion()) && !movimientoPaseDTO.getDescripcion().trim().equals("")) {
            encMovimientosIng.setDescripcion(movimientoPaseDTO.getDescripcion());
        } else {
            encMovimientosIng.setDescripcion("Pase entre sucursales, ingreso desde sucursal" + paseMovimiento.getSucursalOrigen().getNombre());
        }
        encMovimientosIng.setFacturaCompra(null);
        encMovimientosIng.setFechaCarga(new Date());
        encMovimientosIng.setFechaIngreso(new Date());
        encMovimientosIng.setNumero("");
        encMovimientosIng.setPaseMovimiento(paseMovimiento);
        encMovimientosIng.setProveedor(null);
        encMovimientosIng.setTipo(4);//pase
        encMovimientosIng.setSubtipo(1); //ingreso
        encMovimientosIng.setSucursal(paseMovimiento.getSucursalDestino());
        DetMovimientos detMovimientoIng = new DetMovimientos();
        detMovimientoIng.setCantidad(movimientoPaseDTO.getCantidad());
        detMovimientoIng.setFactura(null);
        detMovimientoIng.setIdProducto(movimientoPaseDTO.getProducto().getId());
        detMovimientoIng.setProducto(producto);
        detMovimientoIng.setReferencia("");
        Set<DetMovimientos> detMovimientos = new HashSet<>();
        detMovimientos.add(detMovimientoIng);
        encMovimientosIng.setMovimientos(detMovimientos);
        facturasDAO.insert(encMovimientosIng);
        // ahora quitamos de la sucursal origen lo que se pasa
        EncMovimientos encMovimientosEgr = new EncMovimientos();
        encMovimientosEgr.setDescripcion("Pase entre sucursales, egreso desde sucursal" + paseMovimiento.getSucursalDestino().getNombre());
        encMovimientosEgr.setFacturaCompra(null);
        encMovimientosEgr.setFechaCarga(new Date());
        encMovimientosEgr.setFechaIngreso(new Date());
        encMovimientosEgr.setNumero("");
        encMovimientosEgr.setPaseMovimiento(paseMovimiento);
        encMovimientosEgr.setProveedor(null);
        encMovimientosEgr.setTipo(4);//pase
        encMovimientosEgr.setSubtipo(2);// egreso
        encMovimientosEgr.setSucursal(paseMovimiento.getSucursalOrigen());
        DetMovimientos detMovimientoEgr = new DetMovimientos();
        detMovimientoEgr.setCantidad(movimientoPaseDTO.getCantidad().negate());
        detMovimientoEgr.setFactura(null);
        detMovimientoEgr.setIdProducto(movimientoPaseDTO.getProducto().getId());
        detMovimientoEgr.setProducto(producto);
        detMovimientoEgr.setReferencia("");
        Set<DetMovimientos> detMovimientosEgr = new HashSet<>();
        detMovimientosEgr.add(detMovimientoEgr);
        encMovimientosEgr.setMovimientos(detMovimientosEgr);
        facturasDAO.insert(encMovimientosEgr, true);
        return modelMapper.map(paseMovimiento, PaseMovimientosDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<PaseMovimientosDTO> getPasesBypage(FilterGenericDTO filterGenericDTO, int page, int pageSize, String fieldOrder, boolean reverse) {
        FilterGeneric filterCaja = modelMapper.map(filterGenericDTO, FilterGeneric.class);
        Pagination<PaseMovimientos> paginacionCajas = facturasDAO.getPasesBypage(filterCaja, page, pageSize, fieldOrder, reverse);
        List<PaseMovimientosDTO> lista = new ArrayList<>();
        if (paginacionCajas.getData() != null) {
            lista = modelMapper.mapAsList(paginacionCajas.getData(), PaseMovimientosDTO.class);
        }
        Pagination<PaseMovimientosDTO> pag = new Pagination<>();
        pag.setData(lista);
        pag.setTotal(paginacionCajas.getTotal());
        pag.setPageSize(paginacionCajas.getPageSize());
        pag.setPage(paginacionCajas.getPage());
        return pag;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public EncMovimientosDTO removeMovimiento(EncMovimientosDTO facturaDTO) {
        EncMovimientos factura = modelMapper.map(facturaDTO, EncMovimientos.class);
        Set<DetMovimientos> detMovimientos = factura.getMovimientos();
        // transformamos al valoer a negativo asi resta en vez de sumar
        for (DetMovimientos detMovimiento : detMovimientos) {
            if (detMovimiento.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
                detMovimiento.setCantidad(detMovimiento.getCantidad().negate());
            }
        }
        factura = facturasDAO.insert(factura);
        return modelMapper.map(factura, EncMovimientosDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Pagination<DetMovimientosMinDTO> getMovimientosByProducto(Integer idProducto, int page, int pageSize, String fieldOrder, boolean reverse, boolean ventas, boolean asociados) {
        // armamos los filtros extras para los daos
        Producto producto = productoDAO.get(idProducto);
        List<Producto> productos = new ArrayList<>();
        // traemos los asociados primero - productos compuestos
        if (asociados) {
            List<Producto> productosParents = productoDAO.getAllParents(producto);
            if (Objects.equals(producto.getTipo(), 2)) {
                List<Producto> productosChilds = productoDAO.getAllChilds(producto);
                if (!Objects.isNull(productosChilds)) {
                    productos.addAll(productosChilds);
                }
            }
            if (!Objects.isNull(productosParents)) {
                productos.addAll(productosParents);
            }
        }
        productos.add(producto);
        Pagination<DetMovimientos> paginacion = movimientosDAO.getMovimientosByProducto(productos, page, pageSize, fieldOrder, reverse);
        List<DetMovimientosMinDTO> lista = new ArrayList<>();
        if (paginacion.getData() != null) {
            lista = modelMapper.mapAsList(paginacion.getData(), DetMovimientosMinDTO.class);
        }
        for (ar.com.jsuper.dto.DetMovimientosMinDTO det : lista) {
            for (Producto pro : productos) {
                if (Objects.equals(det.getIdProducto(), pro.getId())) {
                    det.setNombreProducto(pro.getNombreFinal());
                }
            }
        }
        Pagination<DetMovimientosMinDTO> pag = new Pagination<>();
        pag.setData(lista);
        pag.setTotal(paginacion.getTotal());
        pag.setPageSize(paginacion.getPageSize());
        pag.setPage(paginacion.getPage());
        return pag;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public PaginationExtraData<MovimientoProductoDTO, List<StockSucursalMinDTO>> getMovimientosProductoByProducto(Integer idProducto, int page, int pageSize, String fieldOrder, boolean reverse, FilterGenericDTO filterGenericDTO) {
        // armamos los filtros extras para los daos
        Producto producto = productoDAO.get(idProducto);
        List<Producto> productos = new ArrayList<>();
        FilterGeneric filterGeneric = modelMapper.map(filterGenericDTO, FilterGeneric.class);
        // traemos los asociados primero - productos compuestos
        if (filterGeneric.getAsociados()) {
            List<Producto> productosParents = productoDAO.getAllParents(producto);
            if (Objects.equals(producto.getTipo(), 2)) {
                List<Producto> productosChilds = productoDAO.getAllChilds(producto);
                if (!Objects.isNull(productosChilds)) {
                    productos.addAll(productosChilds);
                }
            }
            if (!Objects.isNull(productosParents)) {
                productos.addAll(productosParents);
            }
        }
        productos.add(producto);
        Pagination<MovimientosProducto> paginacion = movimientosDAO.getMovimientosProductoByProducto(productos, page, pageSize, fieldOrder, reverse, filterGeneric);
        List<MovimientoProductoDTO> lista = new ArrayList<>();
        if (paginacion.getData() != null) {
            lista = modelMapper.mapAsList(paginacion.getData(), MovimientoProductoDTO.class);
        }
        for (MovimientoProductoDTO det : lista) {
            for (Producto pro : productos) {
                if (Objects.equals(det.getTipo(), 1)) {
                    if (Objects.equals(det.getMovimiento().getProducto().getId(), pro.getId())) {
                        det.setNombreProducto(pro.getNombreFinal());
                    }
                }
                if (Objects.equals(det.getTipo(), 2)) {
                    if (Objects.equals(det.getVenta().getProducto().getId(), pro.getId())) {
                        det.setNombreProducto(pro.getNombreFinal());
                    }
                }
            }
        }
		PaginationExtraData<MovimientoProductoDTO, List<StockSucursalMinDTO>> pagination= new PaginationExtraData<>();
        pagination.setData(lista);
        pagination.setTotal(paginacion.getTotal());
        pagination.setPageSize(paginacion.getPageSize());
        pagination.setPage(paginacion.getPage());
        return pagination;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Pagination<HistPreciosDTO> getMovimientosPrecioByProducto(Integer idProducto, int page, int pageSize, String fieldOrder, boolean reverse) {
        // armamos los filtros extras para los daos
        Producto producto = new Producto();
        producto.setId(idProducto);
        Pagination<HistPrecios> paginacion = movimientosDAO.getMovimientosPrecioByProducto(producto, page, pageSize, fieldOrder, reverse);
        List<HistPreciosDTO> lista = new ArrayList<>();
        if (paginacion.getData() != null) {
            lista = modelMapper.mapAsList(paginacion.getData(), HistPreciosDTO.class);
        }
        // calculamos la diferencia de precios y el porcentaje que aumento
        for (HistPreciosDTO histPreciosDTO : lista) {
            histPreciosDTO.setDiferencia(histPreciosDTO.getValorFinal().subtract(histPreciosDTO.getValorInicial(), MathContext.DECIMAL128));
            if (histPreciosDTO.getValorInicial().compareTo(BigDecimal.ZERO) > 0) {
                histPreciosDTO.setPorcentaje(
                        (histPreciosDTO.getDiferencia().multiply(new BigDecimal(100), MathContext.DECIMAL128)).divide(histPreciosDTO.getValorInicial(), MathContext.DECIMAL128)
                );
            } else {
                histPreciosDTO.setPorcentaje(BigDecimal.ZERO);
            }
            histPreciosDTO.setPorcentaje(histPreciosDTO.getPorcentaje().setScale(2, RoundingMode.DOWN));
        }
        Pagination<HistPreciosDTO> pag = new Pagination<>();
        pag.setData(lista);
        pag.setTotal(paginacion.getTotal());
        pag.setPageSize(paginacion.getPageSize());
        pag.setPage(paginacion.getPage());
        return pag;
    }

    @Override
    @Transactional(readOnly = true)
    public StockFromFacturaEncDTO addStockFromFacturaPreview(Integer idFactura) {
        CbteCompEnc contFacturasEnc = contFacturasEncDAO.getFactura(idFactura);
        BigDecimal cantidadAgregada = BigDecimal.ZERO;
        Boolean isEmpty = true;
        List<StockFromFacturaDetDTO> itemsStock = new ArrayList<>();
        Set<CbteCompDet> items = contFacturasEnc.getItems();
        List<EncMovimientos> movimientos = movimientosDAO.getFromFactura(idFactura);
        if (!Objects.isNull(movimientos)) {
            if (movimientos.size() > 0) {
                isEmpty = false;
                for (CbteCompDet item : items) {
                    if (!item.getCantAgregadaStock()) {
                        cantidadAgregada = BigDecimal.ZERO;
                        for (EncMovimientos movimiento : movimientos) {
                            Set<DetMovimientos> detMovimientos = movimiento.getMovimientos();
                            for (DetMovimientos detMovimiento : detMovimientos) {
                                if (Objects.equals(detMovimiento.getIdProducto(), item.getIdProducto())) {
                                    cantidadAgregada = detMovimiento.getCantidad().add(cantidadAgregada);
                                }
                            }
                        }
                        StockFromFacturaDetDTO saffd = new StockFromFacturaDetDTO();
                        saffd.setId(item.getId());
                        saffd.setCantidadComprada(item.getCantidad());
                        saffd.setCantidadAgregada(cantidadAgregada);
                        saffd.setMinimo(BigDecimal.ZERO);
                        saffd.setCompleta(false);
                        saffd.setMaximo(item.getCantidad().subtract(cantidadAgregada));
                        saffd.setIdProducto(item.getIdProducto());
                        saffd.setNombreProducto(item.getNombreProducto());
                        itemsStock.add(saffd);
                    } else {
                        StockFromFacturaDetDTO saffd = new StockFromFacturaDetDTO();
                        saffd.setId(item.getId());
                        saffd.setCantidadComprada(item.getCantidad());
                        saffd.setCantidadAgregada(item.getCantidad());
                        saffd.setIdProducto(item.getIdProducto());
                        saffd.setNombreProducto(item.getNombreProducto());
                        saffd.setCompleta(true);
                        saffd.setMaximo(BigDecimal.ZERO);
                        saffd.setMinimo(BigDecimal.ZERO);
                        itemsStock.add(saffd);
                    }
                }
            }
        }
        if (isEmpty) {
            for (CbteCompDet item : items) {
                StockFromFacturaDetDTO saffd = new StockFromFacturaDetDTO();
                saffd.setId(item.getId());
                saffd.setCompleta(false);
                saffd.setCantidadComprada(item.getCantidad());
                saffd.setCantidadAgregada(BigDecimal.ZERO);
                saffd.setMinimo(BigDecimal.ZERO);
                saffd.setMaximo(item.getCantidad());
                saffd.setIdProducto(item.getIdProducto());
                saffd.setNombreProducto(item.getNombreProducto());
                itemsStock.add(saffd);
            }
        }
        // armamos el response
        StockFromFacturaEncDTO stockFromFacturaEncDTO = new StockFromFacturaEncDTO();
        stockFromFacturaEncDTO.setId(idFactura);
        stockFromFacturaEncDTO.setItems(itemsStock);
        stockFromFacturaEncDTO.setIdProveedor(contFacturasEnc.getIdProveedor());
        stockFromFacturaEncDTO.setCompleta(contFacturasEnc.getCargada());
        stockFromFacturaEncDTO.setIdSucursal(contFacturasEnc.getIdSucursal());
        stockFromFacturaEncDTO.setNumero(contFacturasEnc.getNumero());
//		stockFromFacturaEncDTO.setSubtotal(contFacturasEnc.getSubtotal());
        stockFromFacturaEncDTO.setTotal(contFacturasEnc.getTotal());
//		stockFromFacturaEncDTO.setTotalAdicionales(contFacturasEnc.getTotalAdicionales());
//		stockFromFacturaEncDTO.setTotalDescuentos(contFacturasEnc.getTotalDescuentos());
//		stockFromFacturaEncDTO.setTotalImpuestos(contFacturasEnc.getTotalImpuestos());
        stockFromFacturaEncDTO.setTipoCbte(contFacturasEnc.getTipoCbte());
        return stockFromFacturaEncDTO;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public EncMovimientosDTO addStockFromFactura(StockFromFacturaEncDTO stockFromFacturaEncDTO) {
        StockFromFacturaEncDTO preview = this.addStockFromFacturaPreview(stockFromFacturaEncDTO.getId());
        List<StockFromFacturaDetDTO> itemsStockDB = preview.getItems();
        List<StockFromFacturaDetDTO> itemsStock = stockFromFacturaEncDTO.getItems();
        Set<DetMovimientos> detMovimientos = new HashSet<>();
        Set<Integer> idsUpdateCantidadAgregada = new HashSet<>();
        EncMovimientos encMovimiento = new EncMovimientos();
        encMovimiento.setFacturaCompra(contFacturasEncDAO.load(stockFromFacturaEncDTO.getId()));
        encMovimiento.setFechaIngreso(new Date());
        encMovimiento.setFechaCarga(stockFromFacturaEncDTO.getFechaFactura());
        encMovimiento.setNumero(stockFromFacturaEncDTO.getNumero());
        encMovimiento.setProveedor(proveedorDAO.load(stockFromFacturaEncDTO.getIdProveedor()));
        encMovimiento.setSucursal(sucursalDAO.load(stockFromFacturaEncDTO.getIdSucursal()));
        encMovimiento.setTipo(2);
        encMovimiento.setSubtipo(2);
        Integer amedias = 0;
        Integer completas = 0;
        Integer incompletas = 0;
        for (StockFromFacturaDetDTO item : itemsStock) {
            for (StockFromFacturaDetDTO itemDB : itemsStockDB) {
                if (Objects.equals(item.getId(), itemDB.getId())) {
                    if (!itemDB.getCompleta()) {
                        if (item.getCantidad().compareTo(itemDB.getMaximo()) <= 0) {
                            DetMovimientos detMovimiento = new DetMovimientos();
                            detMovimiento.setCantidad(item.getCantidad());
                            detMovimiento.setIdProducto(item.getIdProducto());
                            detMovimiento.setProducto(productoDAO.load(item.getIdProducto()));
//                            detMovimiento.setTipo(1); // una factura siempre sera ingreso
                            detMovimientos.add(detMovimiento);
                            if (item.getCantidad().compareTo(itemDB.getMaximo()) == 0) {
//                                item.setCompleta(Boolean.TRUE);
                                idsUpdateCantidadAgregada.add(item.getId());
                                completas++;
                            } else {
//                                item.setCompleta(Boolean.FALSE);
                                if (item.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
                                    amedias++;
                                } else {
                                    incompletas++;
                                }
                            }
                        }
                    } else {
//                        item.setCompleta(Boolean.TRUE);
                        completas++;
                    }
                }
            }
        }
        if (amedias > 0) {
            contFacturasEncDAO.updateFacturaCompleta(stockFromFacturaEncDTO.getId(), 1);
        } else {
            if (completas > 0 && incompletas == 0) {
                contFacturasEncDAO.updateFacturaCompleta(stockFromFacturaEncDTO.getId(), 2);
            }
            if (completas > 0 && incompletas > 0) {
                contFacturasEncDAO.updateFacturaCompleta(stockFromFacturaEncDTO.getId(), 1);
            }
        }
        encMovimiento.setMovimientos(detMovimientos);

        if (!idsUpdateCantidadAgregada.isEmpty()) {
            contFacturasEncDAO.updateCantidadAgregada(idsUpdateCantidadAgregada, true);
        }

        return modelMapper.map(facturasDAO.insert(encMovimiento), EncMovimientosDTO.class
        );
    }
}
