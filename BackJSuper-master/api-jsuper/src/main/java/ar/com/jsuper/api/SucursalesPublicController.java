package ar.com.jsuper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.services.SucursalService;

@RestController
@RequestMapping("public/v1/sucursales")
public class SucursalesPublicController {

    @Autowired
    SucursalService sucursalService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAll() throws BussinessException {
        return new ResponseEntity<Object>(sucursalService.getAllActive(), HttpStatus.OK);
    }

}
