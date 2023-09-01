package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.aspect.CheckPermission;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.utils.FilterEntidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.services.EntidadService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/entidad")
public class EntidadController {

	@Autowired
	EntidadService entidadService;

	@CheckPermission
	@RequestMapping(value = "/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEntidadesBypage(@RequestBody FilterEntidad filterEmpresa, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
		return new ResponseEntity<>(entidadService.getEntidadesBypage(filterEmpresa, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	@RequestMapping(value = "/pagination/multiple", params = {"q", "page", "pageSize", "order", "reverse"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEntidadByMultipleFilter(@RequestParam(value = "q") String q, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
		return new ResponseEntity<>(entidadService.getEntidadesByMultipleFilter(q, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	@RequestMapping(value = "/all/emails", params = {"q"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEntidadesAndEmail(@RequestParam(value = "q") String q) {
		return new ResponseEntity<>(Response.ok(entidadService.getEntidadesAndEmail(q)), HttpStatus.OK);
	}

	@RequestMapping(value = "/clientes/all/pagination/multiple", params = {"q", "page", "pageSize", "order", "reverse"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEntidadesAndClienteByMultipleFilter(@RequestParam(value = "q") String q, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
		return new ResponseEntity<>(Response.ok(entidadService.getAllEntidadesAndClienteByMultipleFilter(q, pagina, paginaTamanio, orden, reverse)), HttpStatus.OK);
	}

	@RequestMapping(value = "/clientes/all/pagination/multiple/full", params = {"q", "page", "pageSize", "order", "reverse"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEntidadesAndClienteByMultipleFilterFull(@RequestParam(value = "q") String q, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
		return new ResponseEntity<>(Response.ok(entidadService.getAllEntidadesAndClienteByMultipleFilterFull(q, pagina, paginaTamanio, orden, reverse)), HttpStatus.OK);
	}

}
