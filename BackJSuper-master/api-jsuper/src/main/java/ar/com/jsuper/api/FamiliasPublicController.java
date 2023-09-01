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
import ar.com.jsuper.services.FamiliasService;

@RestController
@RequestMapping("public/v1/familias")
public class FamiliasPublicController {

    @Autowired
    FamiliasService familiasService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAll() throws BussinessException {
        return new ResponseEntity<Object>(new Response(familiasService.getAllByShortName(), HttpStatus.OK.value(), ""),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/all", params = {"name"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getByName(@RequestParam(value = "name", required = false) String name)
            throws BussinessException {
        return new ResponseEntity<Object>(new Response(familiasService.getByName(name), HttpStatus.OK.value(), ""),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    public String test() {
        return "rafael";
    }

}
