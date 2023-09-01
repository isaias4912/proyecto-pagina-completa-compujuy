/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.services.VentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rafa22
 */
@RestController
@RequestMapping("/api/v1/empresa")
public class EmpresaController {

    @Autowired
    private VentasService ventasService;

    /**
     * Devuelve las ganancias de
     *
     * @param filterGenericDTO
     * @return
     */
    @RequestMapping(value = "/ganancias", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getGanancias(@RequestBody FilterGenericDTO filterGenericDTO) {
        return new ResponseEntity<>(Response.ok(ventasService.getGanancias(filterGenericDTO)), HttpStatus.OK);
    }
}
