package ar.com.jsuper.api;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.services.UnidadService;

@RestController
@RequestMapping("public/v1/unidad")
public class UnidadPublicController {

    @Autowired
    UnidadService unidadService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAll() throws BussinessException {
        return new ResponseEntity<Object>(new Response(unidadService.getAll(), HttpStatus.OK.value(), ""),
                HttpStatus.OK);
    }

}
