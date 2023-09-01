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
import ar.com.jsuper.domain.utils.FilterFacturas;
import ar.com.jsuper.dto.CbteCompEncListDTO;
import ar.com.jsuper.dto.CbteCompEncDTO;
import ar.com.jsuper.dto.ConfiguracionDTO;
import ar.com.jsuper.dto.FilterFacturasDTO;
import ar.com.jsuper.dto.MovimientosCtaCteProvDTO;
import ar.com.jsuper.services.ContabilidadGenService;
import ar.com.jsuper.services.ParametricasService;
import ar.com.jsuper.services.UtilService;
import ar.com.jsuper.utils.ConstanstAfipTest;
import ar.com.jsuper.utils.Constants;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.HashMap;
import java.util.Map;
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
 *
 * @author rafa22
 */
@RestController
@RequestMapping("api/v1/cbteComp")
public class CbteCompController {

    @Autowired
    ContabilidadGenService contabilidadGenService;
    @Autowired
    ParametricasService parametricasService;
    @Autowired
    private UtilService utilService;

    /**
     * devuelve todos los impuestos activos
     *
     * @return
     * @throws ar.com.jsuper.dao.exception.BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "/impuestos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllImpuestosActive() throws BussinessException {
        return new ResponseEntity<>(Response.ok(contabilidadGenService.getAllImpuestosActive()), HttpStatus.OK);
    }

    /**
     * Guardar una factura de compra
     *
     * @return
     */
    @CheckPermission
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveFacturaCompra(@RequestBody CbteCompEncDTO contFacturasEncDTO) {
        return new ResponseEntity<>(Response.insert(contabilidadGenService.saveFacturaCompra(contFacturasEncDTO)), HttpStatus.CREATED);
    }

    /**
     * Devuelve paginacion de las facturas de compra
     *
     * @param filterFacturasDTO
     * @param pagina
     * @param paginaTamanio
     * @param orden
     * @param reverse
     * @return
     */
    @CheckPermission
    @RequestMapping(value = "/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getFacturasComprasByPage(@RequestBody FilterFacturasDTO filterFacturasDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
        return new ResponseEntity<>(contabilidadGenService.getFacturasByPage(filterFacturasDTO, pagina, paginaTamanio, orden, reverse, 1), HttpStatus.OK);
    }

    /**
     * devuelve una factura por id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getFacturasCompras(@PathVariable Integer id) {
        CbteCompEncDTO contFacturasEncDTO = contabilidadGenService.get(id);
        return new ResponseEntity<>(Response.ok(contFacturasEncDTO), HttpStatus.OK);
    }

    /**
     * devuelve una factura por el id del pago
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/pago/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getFacturaByPago(@PathVariable Integer id) {
        CbteCompEncDTO contFacturasEncDTO = contabilidadGenService.getFacturaByPago(id);
        return new ResponseEntity<>(Response.ok(contFacturasEncDTO), HttpStatus.OK);
    }

    /**
     * devuelve una factura por id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/movimiento", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getMovimientoFromFactura(@PathVariable Integer id) {
        MovimientosCtaCteProvDTO movimientosCtaCteProvDTO = contabilidadGenService.getMovimientoFromFactura(id);
        return new ResponseEntity<>(Response.ok(movimientosCtaCteProvDTO), HttpStatus.OK);
    }

    /**
     * devuelve una factura por numero, comp ueden seer muchas devuelve solo la ultima cargada
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/numero", params = {"q"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getFacturasCompraByNum(@RequestParam(value = "q") String q) {
        CbteCompEncListDTO contFacturasEncDTO = contabilidadGenService.getFacturaByNum(q);
        return new ResponseEntity<>(Response.ok(contFacturasEncDTO), HttpStatus.OK);
    }

    /**
     * Devuelve los datos necesarios para realizar un pago aun proveedor
     *
     * @return
     */
    @RequestMapping(value = "/proveedores", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response> dataForPagos() {
        Map<String, Object> data = new HashMap<>();
        data.put("formaPagos", parametricasService.getFormaPagosForProveedores());
        return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
    }

    /**
     * Devuelve los datos necesarios para realizar un pago de una factura electronica
     *
     * @return
     */
    @RequestMapping(value = "/dataPagos/fe", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response> dataForPagosFE() {
        Map<String, Object> data = new HashMap<>();
        data.put("formaPagos", parametricasService.getFormaPagosForFacElec());
        return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
    }

    /**
     * Devuelve todo lo necesario para el alta o modificacion de una factura de compras
     *
     * @param id
     * @return
     * @throws BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "/dataCompras", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response> dataProductos() throws BussinessException {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> empresa = new HashMap<>();
        ConfiguracionDTO config = utilService.getConfiguracionFromApp();
        data.put("impuestos", contabilidadGenService.getAllImpuestosActive());
        ArrayNode tiposComprobantes = Constants.getTpoCbtesCompra();
        data.put("comprobantes", tiposComprobantes);
        data.put("conceptos", ConstanstAfipTest.getTpoConceptos());
        data.put("facturas", ConstanstAfipTest.getTpoFacturas(2));
        data.put("puntosventa", ConstanstAfipTest.getPtoVentas());
        data.put("ivas", ConstanstAfipTest.getTpoIvas());
        data.put("docs", ConstanstAfipTest.getTpoDocs());
        data.put("tributos", ConstanstAfipTest.getTpoTributos());
        data.put("formasPago", this.parametricasService.getFormaPagosForProveedores());
        // datos empresa
        empresa.put("tipo", config.getTipoEmpresa());
        empresa.put("razonSocial", config.getRazonSocial());
        empresa.put("cuit", config.getCuitEmpresa());
        data.put("empresa", empresa);
        return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
    }

    /**
     * Devuelve libro de iva de compras
     *
     * @param filterCompra
     * @param pagina
     * @param paginaTamanio
     * @param orden
     * @param reverse
     * @return
     */
    @RequestMapping(value = "/libro/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pagination> getLibroIvaByPage(@RequestBody FilterFacturas filterCompra, @RequestParam(value = "page") Integer pagina, @RequestParam(value = "pageSize") Integer paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) Boolean reverse) {
        return new ResponseEntity<>(contabilidadGenService.getLibroIvaByPage(filterCompra, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
    }
}
