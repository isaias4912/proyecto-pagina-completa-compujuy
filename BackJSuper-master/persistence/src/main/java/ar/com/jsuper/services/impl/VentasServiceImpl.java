package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.AppDAO;
import ar.com.jsuper.dao.CuentasCorrientesDAO;
import ar.com.jsuper.dao.ListaPreciosDAO;
import ar.com.jsuper.dao.VentasDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.ComparatorDetalleVentas;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.CbteDet;
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.Configuracion;
import ar.com.jsuper.domain.CbteEncVenta;
import ar.com.jsuper.domain.IvasAfip;
import ar.com.jsuper.domain.PagoCbteVen;
import ar.com.jsuper.domain.TributosAfip;
import ar.com.jsuper.domain.utils.DataSummaryVentas;
import ar.com.jsuper.domain.utils.FilterCbte;
import ar.com.jsuper.domain.utils.FilterGeneric;
import ar.com.jsuper.dto.*;
import ar.com.jsuper.dto.reportes.ReportesVentaProductoDTO;
import ar.com.jsuper.exceptions.JSuperException;
import ar.com.jsuper.services.ParametricasService;
import ar.com.jsuper.services.PresupuestoService;
import ar.com.jsuper.services.UtilService;
import ar.com.jsuper.services.VentasService;
import ar.com.jsuper.services.async.VentasAsyncService;
import ar.com.jsuper.services.factElec.FacturaElectronicaService;
import ar.com.jsuper.services.factElec.ws.FECAECabResponse;
import ar.com.jsuper.services.factElec.ws.FECAEDetRequest;
import ar.com.jsuper.services.factElec.ws.FECAEDetResponse;
import ar.com.jsuper.services.factElec.ws.FECAEResponse;
import ar.com.jsuper.services.websockets.Message;
import ar.com.jsuper.services.websockets.VentaWSService;
import ar.com.jsuper.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentasServiceImpl extends BaseService implements VentasService {

    @Autowired
    private VentasDAO ventasDAO;
    @Autowired
    private CuentasCorrientesDAO cuentasCorrientesDAO;
    //    @Autowired
//    private OrikaBeanMapper modelMapper;
    @Autowired
    private VentaWSService ventaWSService;
//    @Autowired
//    private VentasAsyncService asyncService;
    @Autowired
    private FacturaElectronicaService facturaElectronicaService;
    @Autowired
    private AppDAO appDAO;
    @Autowired
    private UtilService utilService;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(VentasServiceImpl.class);
    @Autowired
    private ParametricasService parametricasService;
    @Autowired
    private ListaPreciosDAO listaPreciosDAO;
    @Autowired
    private PresupuestoService presupuestoService;
    @Autowired
    private ProductosServiceImpl productosService;

    /**
     * @param cbteVenEncDTO
     * @param configuracionDTO
     * @return
     */
    @Override
    @Transactional()
    public CbteVenEncDTO insertVenta(CbteVenEncDTO cbteVenEncDTO, ConfiguracionDTO configuracionDTO, Boolean checkStock) {
        CbteEncVenta cbteEncVenta = modelMapper.map(cbteVenEncDTO, CbteEncVenta.class);
        try {
            if (this.isExistCtaCte(cbteEncVenta.getPagosCbte())) {
                Cliente clienteValido = this.verifiedClienteAndCtaCte(cbteEncVenta.getPagosCbte(), cbteEncVenta.getCliente());
                cbteEncVenta.setCliente(clienteValido);// cli
            }
            this.setVenta(cbteEncVenta, configuracionDTO);
            cbteEncVenta = ventasDAO.insertVenta(cbteEncVenta, checkStock);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DataIntegrityViolationException("No se pudo registrar la venta");
        }
        cbteVenEncDTO = modelMapper.map(cbteEncVenta, CbteVenEncDTO.class);
        return cbteVenEncDTO;
    }

    @Override
    @Transactional(noRollbackFor = {DataIntegrityViolationException.class, JSuperException.class})
    public CbteVenEncDTO insertVentaFE(Integer idCbteVen, ConfiguracionDTO configuracionDTO) {
        Configuracion configuracion = modelMapper.map(configuracionDTO, Configuracion.class);
        this.validateFE(configuracion);
        CbteEncVenta cbteEncVenta = this.ventasDAO.get(idCbteVen);
        this.validateCbteVen(cbteEncVenta);
        try {
            ResponseSolCae response = facturaElectronicaService.solicitarCAEFromEncVentas(cbteEncVenta, configuracion);
            Boolean valido = response.getResultado();
            FECAEResponse fECAEResponse = response.getResponse();
            FECAEDetRequest request = response.getRequest();
            if (valido && Objects.nonNull(fECAEResponse)) {
                this.setVentaAfip(cbteEncVenta, fECAEResponse, request, configuracion);
                cbteEncVenta.setAfipValida(true);
                cbteEncVenta.setTipoFactura(Factura.FACTURA_ELECTRONICA);
                this.ventasDAO.update(cbteEncVenta);
            } else {
                JSuperException ex = new JSuperException("No se pudo generar la factura electrónica corrija los errores, o intente mas tarde.");
                if (Objects.nonNull(fECAEResponse)) {
                    ex.setError(AfipUtil.getErrorFromErrorAfip(fECAEResponse.getErrors()));
                }
                throw ex;
            }
        } catch (JSuperException ex) {
            this.setVentaFENolida(cbteEncVenta, ex.getMessage(), ex.getError());
            this.ventasDAO.update(cbteEncVenta);
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            this.setVentaFENolida(cbteEncVenta, ex.getMessage(), null);
            this.ventasDAO.update(cbteEncVenta);
            throw ex;
        } catch (BussinessException ex) {
            this.setVentaFENolida(cbteEncVenta, ex.getMessage(), null);
            this.ventasDAO.update(cbteEncVenta);
            throw new DataIntegrityViolationException(ex.getMessage());
        } catch (Exception ex) {
            this.setVentaFENolida(cbteEncVenta, "No se pudo realizar la factura electrónica, intente mas tarde, verifique su conexion - 500", null);
            this.ventasDAO.update(cbteEncVenta);
            throw new DataIntegrityViolationException("No se pudo realizar la factura electrónica, intente mas tarde, verifique su conexion - 500");
        }
        // enviamos s mensage a los suupcriptores de nueva venta
        CbteVenEncDTO cbteVenEncDTO = modelMapper.map(cbteEncVenta, CbteVenEncDTO.class);
        return cbteVenEncDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existByUUID(String uuid) {
        return this.ventasDAO.existByUUID(uuid);
    }

    private void validateFE(Configuracion configuracion) {
        // validamos que la configuracion sea valida
        if (Objects.isNull(configuracion.getEnabledVenta()) || !configuracion.getEnabledVenta()) {
            throw new DataIntegrityViolationException("Para realizar ventas debe completar los datos de la empresa.");
        }
    }

    private void validateCbteVen(CbteEncVenta cbteEncVenta) {
        // validamos que la configuracion sea valida
//        if (!cbteEncVenta.getTipo().equals(TipoCbte.VENTA)) {
//            throw new DataIntegrityViolationException("El cbte. no es del tipo venta.");
//        }
    }

//    @Override
//    @Async("taskExecutor")
//    public void extraTasksVenta(CbteVenEncDTO cbteVenEncDTO) {
//        // tareas asyncronas a realizar
//        System.out.println("Esperando");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("termino espera");
//        asyncService.extraTasksVenta(cbteVenEncDTO);
//        ventaWSService.sendMsgNuevaVenta(new Message(cbteVenEncDTO.getId(), "Nueva venta"));
//    }

    @Override
    @Transactional()
    public CbteVenEncDTO refacturarAFIP(Integer id, ConfiguracionDTO configuracionDTO) {
        return this.insertVentaFE(id, configuracionDTO);
    }

    private void setVentaFENolida(CbteEncVenta cbteEncVenta, String error, Set<ar.com.jsuper.utils.Error> errosAfip) {
        cbteEncVenta.setAfipValida(false);
        cbteEncVenta.setTipoFactura(Factura.TIKE_FACTURA_NO_VALIDA); // se transforma en factura no valida
        // hacemos el error un json 
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            cbteEncVenta.setAfipError(objectMapper.writeValueAsString(errosAfip));
        } catch (JsonProcessingException ex) {
            cbteEncVenta.setAfipError("Se produjo un error en AFIP, no se pudo captar los errores.");

        }
    }

    /**
     * cargamos los ivas y demas como dcorresponde para la entidades Para los ivas lo agrupamos por id, segun lo requiere la afip y cargamnos otors datos necesarios
     *
     * @param cbteEncVenta
     */
    private void setVenta(CbteEncVenta cbteEncVenta, ConfiguracionDTO configuracionDTO) {
        //Toda venta se considera como tipo de factura no valida, hasta que se la modifique mediante fe o ticket
        cbteEncVenta.setTipoFactura(Factura.TIKE_FACTURA_NO_VALIDA);
        cbteEncVenta.setAfipValida(Boolean.FALSE); // es false  por lo mismo de la linea anterior
        // cargamos los datos de la empresa en el momento de relizar la venta
        cbteEncVenta.setCuitEmpresa(configuracionDTO.getCuitEmpresa());
        cbteEncVenta.setRazonSocialEmpresa(configuracionDTO.getRazonSocial());
        cbteEncVenta.setDomComercialEmpresa(configuracionDTO.getDomicilioComercial());
        cbteEncVenta.setFechaIniActEmpresa(DateUtils.getStringFromDate(configuracionDTO.getFechaIniAct()));
        cbteEncVenta.setIngBrutosEmpresa(configuracionDTO.getIngresosBrutos());
        cbteEncVenta.setCondicionEmpresa(configuracionDTO.getTipoEmpresa().toString());

        Set<CbteDet> detalleVentas = cbteEncVenta.getDetalleVentas();
        if (cbteEncVenta.getTipoCbte().equals(Comprobante.C) || cbteEncVenta.getTipoCbte().equals(Comprobante.X)) { // para comprobantes tipo C no se informa ni se cargan los ivas
            // ponemos los ivas del detalle en 0 y seteamos los ivas en 0 por si 
            cbteEncVenta.setIvas(null);
        } else {
            if (Objects.isNull(detalleVentas)) {
                throw new DataIntegrityViolationException("La factura debe tener detalles");
            } else {
                Set<IvasAfip> ivas = new HashSet<>();
                detalleVentas.stream().map(x -> x.getIvaId()).distinct().forEach(x -> {
                    IvasAfip i = new IvasAfip();
                    i.setIdAfipIva(x);
                    BigDecimal ivaTotal = detalleVentas.stream().filter(y -> y.getIvaId() == x).map(z -> z.getIvaTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal baseImpTotal = detalleVentas.stream().filter(y -> y.getIvaId() == x).map(z -> z.getBaseImponibleTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    i.setBaseImponible(baseImpTotal.doubleValue());
                    i.setImporte(ivaTotal.doubleValue());
                    i.setAlicuota(AfipUtil.getAlicuotaFromId(x));
                    i.setDescripcion(AfipUtil.getDescripcionFromId(x));
                    ivas.add(i);
                });
                cbteEncVenta.setIvas(ivas);
            }
            // agregamos los index a los tributos, es parte de la clave, para que no falle si se menten dos tipos de tributos iguales
            if (Objects.nonNull(cbteEncVenta.getTributos())) {
                Integer index = 0;
                for (TributosAfip tributo : cbteEncVenta.getTributos()) {
                    tributo.setIndex(index);
                    index++;
                }
            }

        }
    }

    private CbteEncVenta setVentaAfip(CbteEncVenta cbteEncVenta, FECAEResponse response, FECAEDetRequest request, Configuracion configuracion) {
        FECAEDetResponse detFE = response.getFeDetResp().getFECAEDetResponse().get(0);
        FECAECabResponse cabFE = response.getFeCabResp();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date fechaVtoCae = null;
        try {
            fechaVtoCae = dateFormat.parse(detFE.getCAEFchVto());
        } catch (ParseException ex) {
            logger.error("error en la fecha del cbte de venc", ex);
        }
        Date fechaCbte = null;
        try {
            fechaCbte = dateFormat.parse(detFE.getCbteFch());
        } catch (ParseException ex) {
            logger.error("error en la fecha del cbte", ex);
        }
        cbteEncVenta.setCae(detFE.getCAE());
        cbteEncVenta.setCaeVenc(fechaVtoCae);
        cbteEncVenta.setCae(detFE.getCAE());
        cbteEncVenta.setAfipModo(utilService.getRealAfipModo(configuracion));
        cbteEncVenta.setFechaCbte(fechaCbte);
        cbteEncVenta.setCbteNro(Long.toString(detFE.getCbteDesde()));
        // aqui seteamos los ivas si esque el request los tuvo
        if (Objects.nonNull(request.getIva())) {
            if (Objects.nonNull(request.getIva().getAlicIva())) {
                List<IvasAfip> ivas = modelMapper.mapAsList(request.getIva().getAlicIva(), IvasAfip.class);

                cbteEncVenta.setIvas(new HashSet<>(ivas));
            }
        }
        return cbteEncVenta;
    }

    private Boolean isExistCtaCte(Set<PagoCbteVen> pagoVentas) {
        Boolean res = false;
        if (!Objects.isNull(pagoVentas)) {
            for (PagoCbteVen pagoVenta : pagoVentas) {
                // para cta cte validamos que el saldo sea valido y no se metan datos erroneos
                if (pagoVenta.getFormaPago().getId() == 4) {// controlamos para cta corrientes
                    res = true;
                }
            }
        } else {
            throw new DataIntegrityViolationException("Las ventas tiene que tener pagos.");
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public Cliente verifiedClienteAndCtaCte(Set<PagoCbteVen> pagoVentas, Cliente cliente) {
        Cliente res = null;
        for (PagoCbteVen pagoVenta : pagoVentas) {
            // para cta cte validamos que el saldo sea valido y no se metan datos erroneos
            if (pagoVenta.getFormaPago().getId() == 4) {// controlamos para cta corrientes
                res = cuentasCorrientesDAO.verifiedClienteAndCtaCte(cliente, pagoVenta.getMonto());
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Set<CbteVenEncDTO> getall() throws BussinessException {
        Set<CbteEncVenta> cbteEncVentas = ventasDAO.getAll();
        return modelMapper.mapAsSet(cbteEncVentas, CbteVenEncDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public DataSummaryVentasDTO getSummaryVentasFromFilter(FilterVentasDTO filterVentasDTO) {
        FilterCbte filterventas = modelMapper.map(filterVentasDTO, FilterCbte.class);
        DataSummaryVentas dataSummaryVentas = ventasDAO.getSummaryVentasFromFilter(filterventas);
        return modelMapper.map(dataSummaryVentas, DataSummaryVentasDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Map getGraficosVentas(FilterVentasDTO filterVentasDTO) {
        Map<String, Object> data = new HashMap<>();
//        if (filterVentasDTO.getFechaInicial().trim().equals(filterVentasDTO.getFechaFinal().trim())) {
//            List lista = this.getTotalPesosVentasByDateTime(filterVentasDTO.getFechaInicial().trim(), filterVentasDTO.getFechaInicial().trim());
        List lista = this.getTotalPesosVentasTodayBydateTime();
        data.put("graficoDiario", lista);
//        }
        return data;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List getTotalPesosVentasTodayBydateTime() {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String fecha = dt.format(date);
        return this.getTotalPesosVentasByDateTime(fecha, fecha);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List getTotalPesosVentasByDateTime(String dateTimeInicial, String dateTimeFinal) {
        List<LinkedHashMap<String, Object>> lista = new ArrayList<>();
        String hourInicial = "";
        String hourFinal = "";
        String dateTimeInicialF = "";
        String dateTimeFinalF = "";
        for (int i = 6; i < 24; i++) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            hourInicial = i + "";
            hourFinal = i + "";
            dateTimeInicialF = "";
            dateTimeFinalF = "";
            if (hourInicial.trim().length() == 1) {
                hourInicial = "0" + hourInicial.trim();
                hourFinal = "0" + hourFinal.trim();
            }
            hourInicial = hourInicial + ":" + "00";
            hourFinal = hourFinal + ":" + "59";
            dateTimeInicialF = dateTimeInicial + " " + hourInicial;
            dateTimeFinalF = dateTimeFinal + " " + hourFinal;
            BigDecimal res = ventasDAO.getTotalPesosVentasByDateTime(dateTimeInicialF, dateTimeFinalF);
            map.put("hora", hourInicial + "-" + hourFinal);
            map.put("value", res);
            lista.add(map);
        }
        return lista;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Pagination<CbteVenEncMinDTO> getVentasByPage(FilterVentasDTO filterVentasDTO, int page, int pageSize, String fieldOrder, boolean reverse) {
        FilterCbte filterventas = modelMapper.map(filterVentasDTO, FilterCbte.class);
        // armamos los filtros extras para los daos
        Pagination<CbteEncVenta> paginacionVentas = ventasDAO.getVentasByPage(filterventas, page, pageSize,
                fieldOrder, reverse);
        return this.getPagination(paginacionVentas, CbteVenEncMinDTO.class);

//        List<CbteVenEncMinDTO> lista = new ArrayList<>();
//        if (paginacionVentas.getData() != null) {
//            lista = modelMapper.mapAsList(paginacionVentas.getData(), CbteVenEncMinDTO.class);
//        }
//        Pagination<CbteVenEncMinDTO> pag = new Pagination<>();
//        pag.setData(lista);
//        pag.setTotal(paginacionVentas.getTotal());
//        pag.setPageSize(paginacionVentas.getPageSize());
//        pag.setPage(paginacionVentas.getPage());
//        return pag;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<CbteVenEncMinDTO> getVentas(FilterVentasDTO filterVentasDTO, String fieldOrder, boolean reverse) throws BussinessException {
        Pagination<CbteVenEncMinDTO> pagination = this.getVentasByPage(filterVentasDTO, 0, 0, fieldOrder, reverse);
        return pagination.getData();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CbteVenEncMinDTO> getAllVentasFromTransaccionCaja(Integer idTransaccionCaja) throws BussinessException {
        List<CbteEncVenta> lista = ventasDAO.getAllVentasFromTransaccionCaja(idTransaccionCaja);
        return modelMapper.mapAsList(lista, CbteVenEncMinDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CbteVenEncDTO get(Integer id) throws BussinessException {
        return modelMapper.map(ventasDAO.get(id), CbteVenEncDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CbteVenEncDTO get(Integer id, Boolean order) {
        CbteVenEncDTO cbteVenEncDTO = modelMapper.map(ventasDAO.get(id), CbteVenEncDTO.class);
        if (order) {
            Collections.sort(cbteVenEncDTO.getDetalleVentas(), new ComparatorDetalleVentas());
        }
        return cbteVenEncDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public CbteVenEncDTO getByUUID(String uuid) {
        CbteVenEncDTO cbteVenEncDTO = modelMapper.map(ventasDAO.getByUUID(uuid), CbteVenEncDTO.class);
        return cbteVenEncDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public DineroDTO getTotalVentasFromTransaccion(Integer idTransaccion) throws BussinessException {
        return modelMapper.map(ventasDAO.getTotalVentasFromTransaccion(idTransaccion), DineroDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public DataSummaryVentasDTO getSummaryVentasFromTransaccion(Integer idTransaccion) throws BussinessException {
        return modelMapper.map(ventasDAO.getSummaryVentasFromTransaccion(idTransaccion), DataSummaryVentasDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportesVentaProductoDTO> getProductosMasVendidos(FilterGenericDTO filterGenericDTO) {
        FilterGeneric filterGeneric = modelMapper.map(filterGenericDTO, FilterGeneric.class);
        return modelMapper.mapAsList(ventasDAO.getProductosMasVendidos(filterGeneric), ReportesVentaProductoDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportesVentaProductoDTO> getProductosDeMasEntradaMonetaria(FilterGenericDTO filterGenericDTO) {
        FilterGeneric filterGeneric = modelMapper.map(filterGenericDTO, FilterGeneric.class);
        return modelMapper.mapAsList(ventasDAO.getProductosDeMasEntradaMonetaria(filterGeneric), ReportesVentaProductoDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public GananciasDTO getGanancias(FilterGenericDTO filterGenericDTO) {
        FilterGeneric filterGeneric = modelMapper.map(filterGenericDTO, FilterGeneric.class);
        return modelMapper.map(ventasDAO.getGanancias(filterGeneric), GananciasDTO.class);
    }

    /**
     * *****************************UTILS PARA USO DE ADMIN SOLAMENTE*********************************************
     */
    @Override
    @Transactional()
    public int deleteVentaAdminMayor(Integer idVenta) {
        return ventasDAO.deleteVentaAdminMayor(idVenta);
    }

    @Override
    @Transactional()
    public ArrayNode getTpoCbtesVenta() {
        Configuracion configuracion = appDAO.getConfiguracion();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode temp = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        // verificamos que tipo de empresa es para ver que comprobante le corresponde
        if (configuracion.getTipoEmpresa() == TipoEmpresa.MONOTRIBUTO) {//monotributista, en teoria solo puede solictar facturas c
            temp = mapper.createObjectNode();
            temp.put("id", 11);
            temp.put("descripcion", "Factura C");
            temp.put("nombre", Comprobante.C.toString());
            arrayNode.add(temp);
        }
        // para responsables inscriptos
        if (configuracion.getTipoEmpresa() == TipoEmpresa.RESPONSABLE_INSCRIPTO) {
            temp = mapper.createObjectNode();
            temp.put("id", 6);
            temp.put("descripcion", "Factura B");
            temp.put("nombre", Comprobante.B.toString());
            arrayNode.add(temp);
            temp = mapper.createObjectNode();
            temp.put("id", 1);
            temp.put("descripcion", "Factura A");
            temp.put("nombre", Comprobante.A.toString());
            arrayNode.add(temp);
        }
        // para excentos
        if (configuracion.getTipoEmpresa() == TipoEmpresa.EXCENTO) {
            temp = mapper.createObjectNode();
            temp.put("id", 11);
            temp.put("descripcion", "Factura C");
            temp.put("nombre", Comprobante.C.toString());
            arrayNode.add(temp);
        }
        temp = mapper.createObjectNode();
        temp.put("id", 501);
        temp.put("descripcion", "FACTURA X");
        temp.put("nombre", Comprobante.X.toString());
        arrayNode.add(temp);
        return arrayNode;
    }

    @Override
    @Transactional()
    public Map getDataVentas(Integer presupuesto) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> empresa = new HashMap<>();
        ConfiguracionDTO config = utilService.getConfiguracionFromApp();
        ArrayNode tiposComprobantes = this.getTpoCbtesVenta();
        List<ListaPreciosDTO> listaPreciosDTO = modelMapper.mapAsList(listaPreciosDAO.getAllListActiveId(), ListaPreciosDTO.class);
        data.put("formasPago", this.parametricasService.getListAllActiveForcliente());
        data.put("conceptos", ConstanstAfipTest.getTpoConceptos());
        data.put("docs", ConstanstAfipTest.getTpoDocs());
        data.put("tributos", ConstanstAfipTest.getTpoTributos());
        data.put("ivas", ConstanstAfipTest.getTpoIvas());
        data.put("facturas", ConstanstAfipTest.getTpoFacturas(1));
        data.put("enabled", config.getEnabledVenta());
        data.put("comprobantes", tiposComprobantes);
        data.put("listaPrecios", listaPreciosDTO);
        if (utilService.getAfipModo().equals("prod")) {
            if (config.getAfipProduccion()) {
                data.put("puntosventa", config.getPuntosVenta());
            } else {
                data.put("puntosventa", ConstanstAfipTest.getPtoVentas());
            }
        }
        if (utilService.getAfipModo().equals("homo")) {
            data.put("puntosventa", ConstanstAfipTest.getPtoVentas());
        }
        if (utilService.getAfipModo().equals("test")) {
            data.put("puntosventa", ConstanstAfipTest.getPtoVentas());
        }
        // datos empresa
        empresa.put("tipo", config.getTipoEmpresa());
        empresa.put("razonSocial", config.getRazonSocial());
        empresa.put("cuit", config.getCuitEmpresa());
        data.put("empresa", empresa);
        // extra data
        if (Objects.nonNull(presupuesto)) {

            CbtePresupuestoEncVenDTO cbtePresupuestoEncVenDTO =this.presupuestoService.getToVen(presupuesto);
            for (CbtePresupuestoDetVenSinEncabDTO detalleVenta : cbtePresupuestoEncVenDTO.getDetalleVentas()) {
                this.productosService.setPrecios(detalleVenta.getProducto());
            }
            data.put("presupuesto", cbtePresupuestoEncVenDTO);
        }
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<CbteVenEncLibDTO> getLibroIvaByPage(FilterVentasDTO filterVentasDTO, Integer page, Integer pageSize, String fieldOrder, Boolean reverse) {
        FilterCbte filterventas = modelMapper.map(filterVentasDTO, FilterCbte.class);
        // armamos los filtros extras para los daos
        Pagination<CbteEncVenta> paginacionVentas = ventasDAO.getLibroIvaByPage(filterventas, page, pageSize,
                fieldOrder, reverse);
        Pagination<CbteVenEncLibDTO> pagination=this.getPagination(paginacionVentas, CbteVenEncLibDTO.class);
        pagination.setExtraData(ventasDAO.getTotalesLibroIva(filterventas));
        return pagination;

    }
}
