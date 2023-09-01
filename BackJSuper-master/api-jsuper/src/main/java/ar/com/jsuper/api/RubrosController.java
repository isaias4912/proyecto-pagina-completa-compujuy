package ar.com.jsuper.api;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.services.RubrosService;

@RestController
@RequestMapping("/api/v1/rubros")
public class RubrosController {

	@Autowired
	RubrosService rubrosService;

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAll() throws BussinessException{
			return new ResponseEntity<Object>(new Response(rubrosService.getall(), HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

}
