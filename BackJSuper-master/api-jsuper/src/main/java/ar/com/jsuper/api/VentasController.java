/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.aspect.CheckPermission;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.dto.ConfiguracionDTO;
import ar.com.jsuper.dto.CbteVenEncDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.FilterTransaccionDTO;
import ar.com.jsuper.dto.FilterVentasDTO;
import ar.com.jsuper.services.CajasService;
import ar.com.jsuper.services.UtilService;
import ar.com.jsuper.services.VentasService;
import ar.com.jsuper.services.async.VentasAsyncService;
import ar.com.jsuper.services.factElec.FacturaElectronicaService;
import ar.com.jsuper.utils.Factura;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rafa22
 */
@RestController
@RequestMapping("/api/v1/ventas")
public class VentasController extends BaseController {

    @Autowired
    private VentasService ventasService;
    @Autowired
    private CajasService cajasService;
    @Autowired
    private FacturaElectronicaService facturaElectronicaService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private VentasAsyncService ventasAsyncService;

    /**
     * @param encabezadoVentasDTO
     * @param checkUUID           verifica si es que hay que hacer un check del campo uuid, es usado para los punto de venta desktop cuando falla el envio y trata de enviar de nuevo, para validar que no se haya ingresado ya
     * @return
     */
    @CheckPermission
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> postVenta(@RequestBody CbteVenEncDTO encabezadoVentasDTO, @RequestParam(value = "checkUUID", required = false, defaultValue = "0") Optional<Integer> checkUUID, @RequestParam(value = "checkStock", required = false, defaultValue = "1") Optional<Integer> checkStock) {
        // traemos una sola vez la configuracion que se nesecita en las tareas
        if (Objects.nonNull(checkUUID) && checkUUID.get().equals(1)) {
            if (this.ventasService.existByUUID(encabezadoVentasDTO.getUuid())) {
                return new ResponseEntity<>(new Response(this.ventasService.getByUUID(encabezadoVentasDTO.getUuid()), HttpStatus.OK.value(), ""), HttpStatus.OK);
            }
        }
        Factura tipoFactura = encabezadoVentasDTO.getTipoFactura();
        ConfiguracionDTO configuracionDTO = this.utilService.getConfiguracionFromApp();
        CbteVenEncDTO cbteVenEncDTO = ventasService.insertVenta(encabezadoVentasDTO, configuracionDTO, checkStock.isPresent() ? (checkStock.get().equals(1)) : true);
        if (tipoFactura.equals(Factura.FACTURA_ELECTRONICA)) {
            cbteVenEncDTO = ventasService.insertVentaFE(cbteVenEncDTO.getId(), configuracionDTO);
        }
//        ventasService.extraTasksVenta(cbteVenEncDTO);
        this.ventasAsyncService.extraTasksVenta(cbteVenEncDTO, this.getAuthentication());
        return new ResponseEntity<>(new Response(cbteVenEncDTO, HttpStatus.OK.value(), ""), HttpStatus.OK);
    }

    @CheckPermission
    @RequestMapping(value = "/refacturar/afip/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> postRefacturarAFIP(@PathVariable Integer id) {
        ConfiguracionDTO configuracionDTO = this.utilService.getConfiguracionFromApp();
        return new ResponseEntity<>(new Response(ventasService.refacturarAFIP(id, configuracionDTO), HttpStatus.OK.value(), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getAll() throws BussinessException {
        return new ResponseEntity<>(new Response(ventasService.getall(), HttpStatus.OK.value(), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> get(@PathVariable Integer id) throws BussinessException {
        return new ResponseEntity<>(Response.ok(ventasService.get(id, true)), HttpStatus.OK);
    }

    /**
     * Devuelve los datos para poder realizar un filtrado
     *
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/dataFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getData() throws BussinessException {
        Map<String, Object> data = new HashMap<>();
        data.put("cajas", cajasService.getAllActive());
        return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
    }

    /**
     * Devuelve el dinero que se recuado para una transaccion
     *
     * @param idTransaccionCaja
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/dinero/transaccioncaja/{idTransaccionCaja}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getDineroFromTransaccion(@PathVariable Integer idTransaccionCaja) throws BussinessException {
        return new ResponseEntity<>(Response.ok(ventasService.getTotalVentasFromTransaccion(idTransaccionCaja)), HttpStatus.OK);
    }

    @CheckPermission
    @RequestMapping(value = "/all/pagination", params = {"page", "pageSize", "order", "reverse", "summary", "graficoDiario"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getVentasBypage(@RequestBody FilterVentasDTO filterventa, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse, @RequestParam(value = "summary", required = false) boolean summary,
                                                  @RequestParam(value = "graficoDiario", required = false) boolean graficoDiario) {
        Map<String, Object> data = new HashMap<>();
        if (summary) {
            data.put("summary", ventasService.getSummaryVentasFromFilter(filterventa));
        }
        // graficos
        if (graficoDiario) {
            data.put("graficos", ventasService.getGraficosVentas(filterventa));
        }
        data.put("data", ventasService.getVentasByPage(filterventa, pagina, paginaTamanio, orden, reverse));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping(value = "/transacciones/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getTransaccionBypage(@RequestBody FilterTransaccionDTO filterTransaccionDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
        return new ResponseEntity<>(cajasService.getTransaccionBypage(filterTransaccionDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
    }

    @RequestMapping(value = "/detalletransacciones/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDetalleTransaccionBypage(@RequestBody FilterTransaccionDTO filterTransaccionDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse, @RequestParam(value = "summary", required = false) boolean summary) {
        return new ResponseEntity<>(cajasService.getDetalleTransaccionBypage(filterTransaccionDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
    }

    @RequestMapping(value = "/all/transaccioncaja/{idTransaccionCaja}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getAllByTransaccionCaja(@PathVariable Integer idTransaccionCaja) throws BussinessException {
        return new ResponseEntity<>(Response.ok(ventasService.getAllVentasFromTransaccionCaja(idTransaccionCaja)), HttpStatus.OK);
    }

    @RequestMapping(value = "/detalletransaccioncaja/all", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getAllByTransaccionCaja(@RequestBody Map<String, Integer> data) {
        Integer idTransaccion = data.get("idTransaccion");
        Integer tipo = data.get("tipo");
        return new ResponseEntity<>(Response.ok(cajasService.getAllDetalleTransacciones(idTransaccion, tipo)), HttpStatus.OK);
    }

    /**
     * devuelve un detall de transacccion de caja
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detalletransaccioncaja/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getDetalleTransaccionCaja(@PathVariable Integer id) {
        return new ResponseEntity<>(Response.ok(cajasService.getDetalleTransaccionCaja(id)), HttpStatus.OK);
    }

    /**
     * Devuelve los productos mas vendiddos segun filtro
     *
     * @return
     */
    @CheckPermission
    @RequestMapping(value = "/productosmasvendidos", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getProductosMasVendidos(@RequestBody FilterGenericDTO filterGenericDTO) {
        return new ResponseEntity<>(Response.ok(ventasService.getProductosMasVendidos(filterGenericDTO)), HttpStatus.OK);
    }

    /**
     * Devuelve los productos que mas dinero dejaron
     *
     * @return
     */
    @CheckPermission
    @RequestMapping(value = "/productosconmasentrada", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProductosDeMasEntradaMonetaria(@RequestBody FilterGenericDTO filterGenericDTO) {
        return new ResponseEntity<>(Response.ok(ventasService.getProductosDeMasEntradaMonetaria(filterGenericDTO)), HttpStatus.OK);
    }

    /**
     * Devuelve datos de la venta para la web
     *
     * @return
     */
    @CheckPermission
    @RequestMapping(value = "/dataventasweb", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDataVentasWeb() {
        return new ResponseEntity<>(Response.ok(facturaElectronicaService.getDataVentasWeb()), HttpStatus.OK);
    }

    /**
     * Devuelve todo lo necesario para el alta o modificacion de una factura de venta
     *
     * @return
     * @throws BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "/data/dataVentas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> dataVentas(@RequestParam(value = "presupuesto", required = false) Optional<Integer> presupuesto) {
        return new ResponseEntity<>(Response.ok(this.ventasService.getDataVentas(presupuesto.orElseGet(() -> null))), HttpStatus.OK);
    }

    /**
     * Devolvemos libro ventas iva
     *
     * @param filterventa
     * @param pagina
     * @param paginaTamanio
     * @param orden
     * @param reverse
     * @return
     */
    @RequestMapping(value = "/libro/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pagination> getLibroIvaByPage(@RequestBody FilterVentasDTO filterventa, @RequestParam(value = "page") Integer pagina, @RequestParam(value = "pageSize") Integer paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) Boolean reverse) {
        return new ResponseEntity<>(ventasService.getLibroIvaByPage(filterventa, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
    }

}
