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
import ar.com.jsuper.domain.utils.FilterCbte;
import ar.com.jsuper.domain.utils.PaginationRequestDTO;
import ar.com.jsuper.dto.*;
import ar.com.jsuper.services.CajasService;
import ar.com.jsuper.services.PresupuestoService;
import ar.com.jsuper.services.UtilService;
import ar.com.jsuper.services.VentasService;
import ar.com.jsuper.services.factElec.FacturaElectronicaService;
import ar.com.jsuper.utils.EstadoCbte;
import ar.com.jsuper.utils.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author rafa22
 */
@RestController
@Validated
@RequestMapping("/api/v1/presupuesto")
public class PresupuestoController {

    @Autowired
    PresupuestoService presupuestoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> get(@PathVariable Integer id) throws BussinessException {
        return new ResponseEntity<>(Response.ok(presupuestoService.get(id)), HttpStatus.OK);
    }

    @CheckPermission
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> postPresupueso(@RequestBody CbtePresupuestoEncDTO cbtePresupuestoEncDTO, @RequestParam(value = "checkUUID", required = false, defaultValue = "0") Optional<Integer> checkUUID) {
        cbtePresupuestoEncDTO = this.presupuestoService.insertPresupuesto(cbtePresupuestoEncDTO);
        return new ResponseEntity<>(new Response(cbtePresupuestoEncDTO, HttpStatus.OK.value(), ""), HttpStatus.OK);
    }

    @CheckPermission
    @PostMapping(value = "/{id}/estado-cbte", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> putEstadoCbtePresupuesto(@RequestBody SingleObjectIntDTO singleObjectIntDTO,
                                                             @PathVariable Integer id) {
        this.presupuestoService.updateEstadoCbte(id, singleObjectIntDTO.getValue());
        return new ResponseEntity<>(Response.ok(
                new SingleObject<EstadoCbte>(EstadoCbte.value(singleObjectIntDTO.getValue()))
        ), HttpStatus.OK);
    }

    @CheckPermission
    @PostMapping(value = "/all/pagination", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getBypage(@RequestBody FilterCbte filterventa, @Valid PaginationRequestDTO paginationRequestDTO) {
        return new ResponseEntity<>(Response.ok(this.presupuestoService.getByPage(filterventa, paginationRequestDTO)), HttpStatus.OK);
    }

    /**
     * Devuelve todo lo necesario para el alta o modificacion de un cbte
     *
     * @return
     */
    @GetMapping(value = "/dataFilter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> dataFilter() {
        Map<String, Object> data = new HashMap<>();
        return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
    }

    /**
     * Devuelve todo lo necesario para el alta o modificacion de una factura de venta
     *
     * @return
     */
    @GetMapping(value = "/dataPresupuestos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> dataPresupuestos() {
        return new ResponseEntity<>(Response.ok(this.presupuestoService.getDataPresupuestos()), HttpStatus.OK);
    }

}
