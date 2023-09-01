package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.services.SucursalService;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalesController {

    @Autowired
    SucursalService sucursalService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response> getAll() throws BussinessException {
        return new ResponseEntity<>(Response.ok(sucursalService.getAllActive()), HttpStatus.OK);
    }

}
