/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.AppDAO;
import ar.com.jsuper.dao.CajaDAO;
import ar.com.jsuper.dao.FormasPagoDAO;
import ar.com.jsuper.dao.ListaPreciosDAO;
import ar.com.jsuper.dao.OfertasDAO;
import ar.com.jsuper.dao.ProductoDAO;
import ar.com.jsuper.dao.SucursalDAO;
import ar.com.jsuper.dao.UsuariosDAO;
import ar.com.jsuper.dao.VencimientosProductoDAO;
import ar.com.jsuper.dao.VentasDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.param.ParamEntradasCajasDAO;
import ar.com.jsuper.dao.param.ParamSalidasCajasDAO;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Caja;
import ar.com.jsuper.domain.Configuracion;
import ar.com.jsuper.domain.FormaPagos;
import ar.com.jsuper.domain.LogUser;
import ar.com.jsuper.domain.Ofertas;
import ar.com.jsuper.domain.ListaPrecios;
import ar.com.jsuper.domain.PuntoVenta;
import ar.com.jsuper.domain.Roles;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.domain.TransaccionCaja;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.domain.VencimientosProductos;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.dto.AfipDTO;
import ar.com.jsuper.dto.AppDTO;
import ar.com.jsuper.dto.CajaDTO;
import ar.com.jsuper.dto.ClienteAppDesktopDTO;
import ar.com.jsuper.dto.ConfiguracionDTO;
import ar.com.jsuper.dto.ConfiguracionMinDTO;
import ar.com.jsuper.dto.DashBoardDTO;
import ar.com.jsuper.dto.DataClientDesktop;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.FormaPagosDTO;
import ar.com.jsuper.dto.ListaPreciosDTO;
import ar.com.jsuper.dto.NotificacionesDTO;
import ar.com.jsuper.dto.OfertasProdDTO;
import ar.com.jsuper.dto.ParamGenericDTO;
import ar.com.jsuper.dto.SucursalesDTO;
import ar.com.jsuper.dto.SucursalesMinDTO;
import ar.com.jsuper.dto.TransaccionCajaDTO;
import ar.com.jsuper.dto.VencimientosProductosDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.services.Backup;
import ar.com.jsuper.services.UtilService;
import ar.com.jsuper.services.VentasService;
import ar.com.jsuper.services.factElec.FacturaElectronicaService;
import ar.com.jsuper.utils.Constants;
import ar.com.jsuper.utils.FileUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rafa22
 */
@Service
public class UtilServiceImpl extends BaseService implements UtilService {

    @Autowired
    ProductoDAO productoDAO;
    @Autowired
    AppDAO appDAO;
    @Autowired
    VentasDAO ventasDAO;
    @Autowired
    UsuariosDAO usuariosDAO;
    @Autowired
    private SucursalDAO sucursalDAO;
    @Autowired
    private ListaPreciosDAO listaPreciosDAO;
    @Autowired
    private CajaDAO cajaDAO;
    @Autowired
    private OfertasDAO ofertasDAO;
    @Autowired
    private ParamEntradasCajasDAO paramEntradasCajasDAO;
    @Autowired
    private ParamSalidasCajasDAO paramSalidasCajasDAO;
    @Autowired
    private VencimientosProductoDAO vencimientosProductoDAO;
    @Autowired
    private Backup backup;
    @Autowired
    private VentasService ventasService;
    @Autowired
    private OrikaBeanMapper modelMapper;
    private Logger logger = Logger.getLogger(UtilServiceImpl.class);

    @Value("${afip.modo}")
    protected String afipModo;

    @Value("${jsuper.tipo}")
    protected String jsuperTipo;

    @Override
    @Transactional(readOnly = true)
    public DashBoardDTO getDataDashBoard() throws BussinessException {
        DashBoardDTO dashBoardDTO = new DashBoardDTO();
        dashBoardDTO.setCantidadProductos(productoDAO.getTotalRows());
        dashBoardDTO.setCantidadVentasHoy(ventasDAO.getTotalVentasToday());
        dashBoardDTO.setCantidadVentasAyer(ventasDAO.getTotalVentasYesterday());
        //cantidad por meses
        List<LinkedHashMap<String, Object>> lista = new ArrayList<>();
        LocalDate futureDate = null;
        for (int i = 0; i < 6; i++) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            Long l = ventasDAO.getTotalVentasByMonthLater(i);
            futureDate = LocalDate.now().minusMonths(i);
            Date date = Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            map.put("month", (new SimpleDateFormat("MMM-yyyy").format(date)).toUpperCase());
            map.put("value", l);
            lista.add(map);
        }
        Collections.reverse(lista);
        dashBoardDTO.setCantidadPorMes(lista);
        // pesos por mes
        lista = new ArrayList<>();
        futureDate = null;
        for (int i = 0; i < 6; i++) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            BigDecimal valor = ventasDAO.getTotalPesosVentasByMonthLater(i);
            futureDate = LocalDate.now().minusMonths(i);
            Date date = Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            map.put("month", (new SimpleDateFormat("MMM-yyyy").format(date)).toUpperCase());
            map.put("value", valor);
            lista.add(map);
        }
        Collections.reverse(lista);
        dashBoardDTO.setPesosPorMes(lista);
        //cantidad por Dia
        lista = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            Long l = ventasDAO.getTotalVentasByDayLater(i);
            futureDate = LocalDate.now().minusDays(i);
            Date date = Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            map.put("day", (new SimpleDateFormat("EEE d").format(date)).toUpperCase());
            map.put("value", l);
            lista.add(map);
        }
        Collections.reverse(lista);
        dashBoardDTO.setCantidadPorDia(lista);
        //pesos por Dia
        lista = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            BigDecimal value = ventasDAO.getTotalPesosVentasByDayLater(i);
            futureDate = LocalDate.now().minusDays(i);
            Date date = Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            map.put("day", (new SimpleDateFormat("EEE d").format(date)).toUpperCase());
            map.put("value", value);
            lista.add(map);
        }
        Collections.reverse(lista);
        dashBoardDTO.setPesosPorDia(lista);
        // cantidad de prod por debajo del punto de reposicion
        dashBoardDTO.setCantidadProdBajoPuntoRepo(productoDAO.getCantProdBajoPuntoRepo());
        // cantidad de prod por debajo del stock minimo
        dashBoardDTO.setCantidadProdBajoStockMin(productoDAO.getCantProdBajoStockMin());
        // ultimo acceso del usuario (penultimo)
        LogUser logUser = usuariosDAO.getBeforesLastLogUser(TenantContext.getCurrentIdUser());
        if (Objects.isNull(logUser)) {
            dashBoardDTO.setUltimoAcceso(null);
        } else {
            dashBoardDTO.setUltimoAcceso(logUser.getFechaIngreso());
        }
        return dashBoardDTO;
    }

    /**
     * devuelve todos los datos necesarios para la app swing desktop
     *
     * @param clienteAppDesktopDTO
     * @return
     * @throws BussinessException
     */
    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public DataClientDesktop getDataAppDesktop(ClienteAppDesktopDTO clienteAppDesktopDTO, String option) throws BussinessException {
        List<ListaPrecios> listaPrecios = listaPreciosDAO.getAllListActiveId();
        List<Ofertas> ofertas = ofertasDAO.getAllVigentes();
        List<OfertasProdDTO> ofertasDTO = modelMapper.mapAsList(ofertas, OfertasProdDTO.class);
        List<ListaPreciosDTO> listaPreciosDTO = modelMapper.mapAsList(this.getListaPrecios(listaPrecios), ListaPreciosDTO.class);
        DataClientDesktop dataClientDesktop = new DataClientDesktop();
        dataClientDesktop.setOfertas(ofertasDTO);
        dataClientDesktop.setListaPrecios(listaPreciosDTO);
        if (option.equals("1")) {
            App app = appDAO.get();
            dataClientDesktop.setNombreApp(app.getNombre());
            dataClientDesktop.setLogo(app.getLogo());
            ConfiguracionMinDTO configuracionMinDTO = modelMapper.map(app.getConfiguracion(), ConfiguracionMinDTO.class);
            dataClientDesktop.setConfiguracion(configuracionMinDTO);
        } else {
            dataClientDesktop.setNombreApp(null);
            dataClientDesktop.setLogo(null);
            dataClientDesktop.setConfiguracion(null);
        }
        Caja caja = cajaDAO.getByNombrePC(clienteAppDesktopDTO.getNombreMaquina());
        dataClientDesktop.setCaja(modelMapper.map(caja, CajaDTO.class));
        if (Objects.isNull(caja)) {
            dataClientDesktop.setTransaccionCaja(null);
        } else {
            TransaccionCaja transaccionCaja = cajaDAO.getLastTransaccionCaja(caja);
            dataClientDesktop.setTransaccionCaja(modelMapper.map(transaccionCaja, TransaccionCajaDTO.class));
        }
        //opciones entradas para caja
        List<ParamGenericDTO> paramGenericDTOE = modelMapper.mapAsList(paramEntradasCajasDAO.getAllParamListActive(), ParamGenericDTO.class);
        dataClientDesktop.setEntradasCaja(paramGenericDTOE);
        List<ParamGenericDTO> paramGenericDTOS = modelMapper.mapAsList(paramSalidasCajasDAO.getAllParamListActive(), ParamGenericDTO.class);
        dataClientDesktop.setSalidasCaja(paramGenericDTOS);
        dataClientDesktop.setDataVentas(this.ventasService.getDataVentas(null));
        // sucursales del usuario logueado
        dataClientDesktop.setSucursales(modelMapper.mapAsList(sucursalDAO.getSucursalesByUserLogged(), SucursalesDTO.class));
        return dataClientDesktop;
    }

    private List<ListaPrecios> getListaPrecios(List<ListaPrecios> lista) {
        List<ListaPrecios> temp = new ArrayList<>();
        ListaPrecios listaPrecios = new ListaPrecios();
        listaPrecios.setActivo(true);
        listaPrecios.setDetalle("");
        listaPrecios.setId(0);
        listaPrecios.setNombre("Normal");
        listaPrecios.setTipo(0);
        listaPrecios.setValor(BigDecimal.ZERO);
        temp.add(listaPrecios);
        lista.forEach((lp) -> {
            temp.add(lp);
        });
        return temp;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public AppDTO getDataApp() {
        App app = appDAO.get();
        Sucursales sucursalPrincipal = sucursalDAO.getPrincipal();
        SucursalesDTO sucursalPrincipalDTO = modelMapper.map(sucursalPrincipal, SucursalesDTO.class);
        AppDTO appDTO = new AppDTO();
        appDTO.setNombre(app.getNombre());
        appDTO.setLogo(app.getLogo());
        appDTO.setAltLogo(app.getAltLogo());
        appDTO.setDescripcion(app.getDescripcion());
        appDTO.setSucursalPrincipal(sucursalPrincipalDTO);
        return appDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public AfipDTO getDataAppAfip() {
        App app = appDAO.get();
        AfipDTO afipDTO = modelMapper.map(app, AfipDTO.class);
        afipDTO.setCuitEmpresa(app.getConfiguracion().getCuitEmpresa()); // traemos cuit de configuracion
        return afipDTO;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public AppDTO saveDataApp(AppDTO appDTO) throws BussinessException {
        App app = modelMapper.map(appDTO, App.class);
        app = appDAO.update(app);
        Sucursales sucursalPrincipal = modelMapper.map(appDTO.getSucursalPrincipal(), Sucursales.class);
        sucursalPrincipal = sucursalDAO.savePrincipal(sucursalPrincipal);
        SucursalesDTO sucursalPrincipalDTO = modelMapper.map(sucursalPrincipal, SucursalesDTO.class);
        appDTO.setNombre(app.getNombre());
        appDTO.setLogo(app.getLogo());
        appDTO.setDescripcion(app.getDescripcion());
        appDTO.setSucursalPrincipal(sucursalPrincipalDTO);
        return appDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW) // se usa require new porque si ocurre un error no vuelva a pedir un token
    public void setDataAppAfip(Integer idApp, String token, String sign, Date init, Date end) {
        App app = appDAO.load(idApp);
        app.setAfipSign(sign);
        app.setAfipToken(token);
        app.setAfipFechaInitToken(init);
        app.setAfipFechaEndToken(end);
        appDAO.update(app);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW) // se usa require new porque si ocurre un error no vuelva a pedir un token
    public void setDataAppAfipTest(Integer idApp, String token, String sign, Date init, Date end) {
        App app = appDAO.load(idApp);
        app.setAfipSignTest(sign);
        app.setAfipTokenTest(token);
        app.setAfipFechaInitTokenTest(init);
        app.setAfipFechaEndTokenTest(end);
        appDAO.update(app);
    }

    @Override
    @Transactional(readOnly = true)
    public ConfiguracionDTO getConfiguracionFromApp() {
        return modelMapper.map(appDAO.getConfiguracion(), ConfiguracionDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ConfiguracionDTO getConfiguracionFromId(Integer idApp) {
        return modelMapper.map(appDAO.getConfiguracionFromAppId(idApp), ConfiguracionDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public ConfiguracionDTO getConfiguracion() {
        ConfiguracionDTO configuracionDTO = modelMapper.map(appDAO.getConfiguracion(), ConfiguracionDTO.class);
        // sacamos la clave privada para enviar
        configuracionDTO.setPrivateKey(this.readStringPrivateKey());
        configuracionDTO.setFileCertificateCSR(this.existFileInAppDir("afip/certificateCSR"));
        configuracionDTO.setFileCertificateCRT(this.existFileInAppDir("afip/" + configuracionDTO.getCertNameCRT()));
        configuracionDTO.setFilePrivateKey(this.existFileInAppDir("afip/private"));

        return configuracionDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Map getConfiguracionGral() {
        Map<String, Object> response = new HashMap<>();
        ConfiguracionMinDTO cmdto = modelMapper.map(appDAO.getConfiguracion(), ConfiguracionMinDTO.class);
        if (afipModo.equals("dev")) {
            cmdto.setExisteCertAfip(false); // para dev siempre mandamos false
        } else {
            cmdto.setExisteCertAfip(this.existFileInAppDir("afip/certificatePFX.pfx")); // si existe el cert estaria para prod
        }
        response.put("configuracion", cmdto);
        // verificamos si el usuario es admin entregamos todas las sucursales
        Usuarios u = usuariosDAO.getByIdLogged();
        Boolean isAdmin = false;
        for (Roles rol : u.getRoles()) {
            if (Objects.equals(rol.getNombre().trim(), Constants.ROLE_ADMIN)) {
                isAdmin = true;
            }
        }
        if (isAdmin) {
            response.put("sucursales", modelMapper.mapAsList(sucursalDAO.getListAllActive(), SucursalesMinDTO.class));
        } else {
            response.put("sucursales", modelMapper.mapAsList(sucursalDAO.getSucursalesByUserLogged(), SucursalesMinDTO.class));
        }
        response.put("jsuperTipo", jsuperTipo);
        return response;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public ConfiguracionDTO updateConfiguracion(ConfiguracionDTO configuracionDTO) {
        Configuracion configuracion = modelMapper.map(configuracionDTO, Configuracion.class);
        Configuracion configuracionDB = appDAO.getConfiguracion();
        configuracion.setId(configuracionDB.getId());// seteamo spor seguridad
        this.setEnabledVenta(configuracion);
        appDAO.saveUpdateOrDelete(configuracion, configuracion.getPuntosVenta(), configuracionDB.getPuntosVenta());
        return modelMapper.map(appDAO.updateConfiguracion(configuracion), ConfiguracionDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public ConfiguracionDTO updateConfiguracionBasica(ConfiguracionDTO configuracionDTO) {
        Configuracion configuracion = modelMapper.map(configuracionDTO, Configuracion.class);
        Configuracion configuracionDB = appDAO.getConfiguracion();
        configuracion.setId(configuracionDB.getId());
        this.setEnabledVenta(configuracion);
        if (Objects.isNull(configuracion.getPuntosVenta()) || (Objects.nonNull(configuracion.getPuntosVenta()) && configuracion.getPuntosVenta().size() <= 0)) {
            PuntoVenta puntoVenta = new PuntoVenta();
            puntoVenta.setDescripcion("Punto venta 1");
            puntoVenta.setNro(1);
            configuracion.setPuntosVenta(new HashSet<>());
            configuracion.getPuntosVenta().add(puntoVenta);
        }
        appDAO.saveUpdateOrDelete(configuracion, configuracion.getPuntosVenta(), configuracionDB.getPuntosVenta());
        return modelMapper.map(appDAO.updateConfiguracionBasica(configuracion), ConfiguracionDTO.class);
    }

    private void setEnabledVenta(Configuracion configuracion) {
        if ((Objects.nonNull(configuracion.getCuitEmpresa()) && !configuracion.getCuitEmpresa().trim().equals(""))
                && (Objects.nonNull(configuracion.getDomicilioComercial()) && !configuracion.getDomicilioComercial().trim().equals(""))
                && (Objects.nonNull(configuracion.getTipoEmpresa()))
                && (Objects.nonNull(configuracion.getFechaIniAct()))
                && (Objects.nonNull(configuracion.getRazonSocial()) && !configuracion.getRazonSocial().trim().equals(""))) {
            configuracion.setEnabledVenta(Boolean.TRUE);
        } else {
            configuracion.setEnabledVenta(Boolean.FALSE);
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public void updateNameCRTConfiguracion(String name) {
        Configuracion configuracion = appDAO.getConfiguracion();
        configuracion.setCertNameCRT(name);
        appDAO.updateConfiguracion(configuracion);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getEncabezadoReporte() {
        return appDAO.getEncabezadoReporte();
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public VencimientosProductosDTO saveVencimientoProducto(VencimientosProductosDTO vencimientosProductoDTO) {
        VencimientosProductos vencimientosProducto = modelMapper.map(vencimientosProductoDTO, VencimientosProductos.class);
        return modelMapper.map(vencimientosProductoDAO.saveVencimientoProducto(vencimientosProducto), VencimientosProductosDTO.class);
    }

    /*
    *Metodo que nos devuelve un object Pagination de los vencimientos
     */
    @Override
    @Transactional(readOnly = true)
    public Pagination<VencimientosProductosDTO> getVencimientosByPage(FilterGenericDTO filterGenericDTO, int page, int pageSize, String fieldOrder,
            boolean reverse) {
        // armamos los filtros extras para los daos
        FilterGeneric filterGeneric = modelMapper.map(filterGenericDTO, FilterGeneric.class);
        Pagination<VencimientosProductos> paginacionProductos = vencimientosProductoDAO.getVencimientosByPage(filterGeneric, page, pageSize,
                fieldOrder, reverse);
        List<VencimientosProductos> detalleProductos = null;
        if (paginacionProductos.getData() != null) {
            detalleProductos = paginacionProductos.getData();
        } else {
            detalleProductos = new ArrayList<>();
        }
        List<VencimientosProductosDTO> detalleProductosDTO = new ArrayList<>();
        if (detalleProductos != null) {
            detalleProductosDTO = modelMapper.mapAsList(detalleProductos, VencimientosProductosDTO.class);
        }
        // nos fijamos si la fecha de vencimiento es valida o no, segun la fecha de hoy
        // controlamos el alerta

        Date hoy = ar.com.jsuper.utils.DateUtils.getNowOnlyDate();
        Date fechaCompara = null;
        for (VencimientosProductosDTO vencimientosProductosDTO : detalleProductosDTO) {
            fechaCompara = new Date(vencimientosProductosDTO.getFechaVencimiento().getTime());
            if (fechaCompara.equals(hoy) || fechaCompara.after(hoy)) {
                vencimientosProductosDTO.setValido(true);
            } else {
                vencimientosProductosDTO.setValido(false);
            }
            if (vencimientosProductosDTO.getValido()) {
                Date fecha = DateUtils.addDays(fechaCompara, Math.negateExact(vencimientosProductosDTO.getDiasAlerta()));
                if (hoy.equals(fecha) || hoy.after(fecha)) {
                    vencimientosProductosDTO.setAlerta(1); // se activa el alerta con 1
                } else {
                    vencimientosProductosDTO.setAlerta(0);
                }
            } else {
                vencimientosProductosDTO.setAlerta(-1);
            }
        }

        Pagination<VencimientosProductosDTO> pag = new Pagination<>();
        pag.setData(detalleProductosDTO);
        pag.setTotal(paginacionProductos.getTotal());
        pag.setPageSize(paginacionProductos.getPageSize());
        pag.setPage(paginacionProductos.getPage());
        return pag;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public void enabledOrdisabledVencimientos(Set<VencimientosProductosDTO> vencimientosProductosDTO, boolean value) {
        Set<VencimientosProductos> vencimientos = modelMapper.mapAsSet(vencimientosProductosDTO, VencimientosProductos.class);
        vencimientosProductoDAO.enabledOrdisabledVencimientos(vencimientos, value);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public void enabledOrdisabledAlertaVencimientos(VencimientosProductosDTO vencimientosProductosDTO, boolean value) {
        VencimientosProductos vencimientos = modelMapper.map(vencimientosProductosDTO, VencimientosProductos.class);
        vencimientosProductoDAO.enabledOrdisabledAlertaVencimientos(vencimientos, value);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public void deleteVencimientos(Integer id) {
        vencimientosProductoDAO.deleteByApp(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionesDTO> notificaciones() {
        List<NotificacionesDTO> data = new ArrayList<>();
        NotificacionesDTO notificacionesDTO = new NotificacionesDTO();
        Integer venci = vencimientosProductoDAO.getCountAlerta();
        if (!Objects.isNull(venci)) {
            if (venci > 0) {
                notificacionesDTO.setNombre("Vencimientos");
                notificacionesDTO.setCantidad(venci);
                data.add(notificacionesDTO);
            }
        }
        return data;
    }

    /**
     * Calve privada por app, le sirve a afip por ejemplo
     *
     * @return
     */
    @Override
    public String readStringPrivateKey() {
        InputStream is = null;
        try {
            System.out.println(this.getFullPathApp() + "afip" + File.separator + "private");
            is = new FileInputStream(this.getFullPathApp() + "afip" + File.separator + "private");
        } catch (FileNotFoundException ex) {
            logger.error("No se encotro la clave privada");
            return null;
        }
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            line = buf.readLine();
        } catch (IOException ex) {
            logger.error("Error al leer las lineas de la clave privada - 1");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        while (line != null) {
            sb.append(line).append("\n");
            try {
                line = buf.readLine();
            } catch (IOException ex) {
                logger.error("Error al leer las lineas de la clave privada - 2");
                return null;
            }
        }
        String fileAsString = sb.toString();
        return fileAsString;
    }

    @Override
    public Boolean existFileInAppDir(String file) {
        return FileUtils.existFile(this.getFullPathApp() + file);
    }

    /**
     *
     * @param CN Common Name, is X.509 speak for the name that distinguishes the Certificate best, and ties it to your Organization
     * @param O Organization NAME
     */
    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public void generateCSR(String CN, String O, String cuit, String password) {
        Configuracion configuracion = appDAO.getConfiguracion();
        configuracion.setCertCN(CN);
        configuracion.setCertO(O);
        configuracion.setCertSerialNumber(cuit);
        configuracion.setCertPassword(password);
        appDAO.updateConfiguracion(configuracion);
        try {
            String path = this.getFullPathApp() + "afip" + File.separator;
            StringBuilder sb = new StringBuilder();
            sb.append("/C=AR/O=");
            sb.append(configuracion.getCertO());
            sb.append("/CN=");
            sb.append(configuracion.getCertCN());
            sb.append("/serialNumber=CUIT ");
            sb.append(configuracion.getCertSerialNumber());
            Process p = Runtime.getRuntime().exec(new String[]{"openssl", "req", "-new", "-key", path + "private",
                "-subj", sb.toString(), "-out", path + "certificateCSRtemp"});
//                    , "-subj", "/C=AR/O=RAMOS RAFAEL ALDO/CN=COMPUJUY/serialNumber=CUIT 23314428889", "-out", path + "certificateCSRtemp"});
            p.waitFor();
            if (p.exitValue() == 0) {
                p = Runtime.getRuntime().exec(new String[]{"rm", "-rdf", path + "certificateCSR"});
                p.waitFor();
                p = Runtime.getRuntime().exec(new String[]{"mv", path + "certificateCSRtemp", path + "certificateCSR"});
                p.waitFor();
            } else {
                throw new DataIntegrityViolationException("Error - No se puedo crear el certificado CSR - error en el comando, verifique que exista la clave privada");
            }
        } catch (IOException ex) {
            throw new DataIntegrityViolationException("Error - No se puedo crear el certificado CSR - 1");
        } catch (InterruptedException ex) {
            throw new DataIntegrityViolationException("Error - No se puedo crear el certificado CSR - 2");
        }
    }

    @Override
    @Transactional()
    public void generatePkcs12() {
        Configuracion configuracion = appDAO.getConfiguracion();
        if (!Objects.isNull(configuracion.getCertNameCRT()) && !configuracion.getCertNameCRT().trim().equals("")) {
            try {
                String path = this.getFullPathApp() + "afip" + File.separator;
                if (FileUtils.existFile(path + configuracion.getCertNameCRT())) {
                    Process p = Runtime.getRuntime().exec(new String[]{"openssl", "pkcs12", "-export", "-out", path + "certificatePFX.pfx", "-inkey", path + "private", "-password", "pass:" + configuracion.getCertPassword(), "-in", path + configuracion.getCertNameCRT()});
                    p.waitFor();
                    if (p.exitValue() != 0) {
                        throw new DataIntegrityViolationException("Error - No se puedo crear el certificado certificatePFX - error en el comando");
                    }
                } else {
                    throw new DataIntegrityViolationException("Error - No existe el certificado CRT");
                }
            } catch (IOException ex) {
                throw new DataIntegrityViolationException("Error - No se puedo crear el certificado pxcs12 - 1");
            } catch (InterruptedException ex) {
                throw new DataIntegrityViolationException("Error - No se puedo crear el certificado pxcs12 - 2");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public void generatePrivateKey() {
        try {
            String path = this.getFullPathApp() + "afip" + File.separator;
            Process p = Runtime.getRuntime().exec(new String[]{"openssl", "genrsa", "-out", path + "private", "2048"});
            p.waitFor();
            if (p.exitValue() != 0) {
                throw new DataIntegrityViolationException("Error - No se puedo crear la clave privada - 0");
            }
        } catch (IOException ex) {
            throw new DataIntegrityViolationException("Error - No se puedo crear la clave privada - 1");
        } catch (InterruptedException ex) {
            throw new DataIntegrityViolationException("Error - No se puedo crear la clave privada - 2");
        }
    }

    @Override
    public Boolean isDataProd() {
        if (afipModo.equals("dev")) {
            return false;
        } else {
            return this.existFileInAppDir("afip/certificatePFX.pfx");
        }
    }

    @Override
    public String getAfipModo() {
        return afipModo;
    }

    @Override
    public String getRealAfipModo(Configuracion configuracion) {
        if (afipModo.equals("prod")) {
            if (!configuracion.getAfipProduccion()) {
                return "test";
            }
        }
        return afipModo;
    }

    /**
     * setea la parte del backup y de paso inicia si esque corresponde
     *
     * @param config
     * @return
     */
    @Override
    @Transactional
    public ConfiguracionDTO setBackup(ConfiguracionDTO config) {
        Configuracion configuracion = modelMapper.map(config, Configuracion.class);
        appDAO.setBackup(configuracion);
        if (config.getBackupHab()) {
            backup.start(config.getBackupCron());
        } else {
            backup.stop();
        }
        return modelMapper.map(configuracion, ConfiguracionDTO.class);
    }

}
