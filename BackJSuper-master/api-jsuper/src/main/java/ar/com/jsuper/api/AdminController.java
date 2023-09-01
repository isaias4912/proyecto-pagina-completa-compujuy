/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.services.VentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rafa22
 */
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    private VentasService ventasService;

    @RequestMapping(value = "/ventas/deletemayorque/{idVenta}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> get(@PathVariable Integer idVenta) throws BussinessException {
        return new ResponseEntity<>(Response.ok(ventasService.deleteVentaAdminMayor(idVenta)), HttpStatus.OK);
    }
}
