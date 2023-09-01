package ar.com.jsuper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.services.ProductosService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/public/util")
public class UtilPublicController {

	@Autowired
	ProductosService productosService;

	/**
	 * Verifica si existe un nombre de usuario
	 *
	 * @return
	 */
	@RequestMapping(value = "/resetCompujuy", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response> resetCompujuy() {
//       productosService.resetCompujuy(); 
		return new ResponseEntity<>(Response.ok(""), HttpStatus.OK);
	}

	/**
	 * testing
	 *
	 * @return
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response> testCompujuy() {
		return new ResponseEntity<>(Response.ok("OK"), HttpStatus.OK);
	}

}
