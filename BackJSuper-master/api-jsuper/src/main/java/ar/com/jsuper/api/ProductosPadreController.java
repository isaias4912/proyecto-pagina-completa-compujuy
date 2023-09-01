package ar.com.jsuper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.api.utils.MessageError;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.aspect.CheckPermission;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.FilterProductoPadreDTO;
import ar.com.jsuper.dto.ProductoPadreDTO;
import ar.com.jsuper.dto.ProductoPadreSinProductoDTO;
import ar.com.jsuper.services.ProductoPadreService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/productosPadre")
public class ProductosPadreController {

	@Autowired
	ProductoPadreService productoPadreService;

	@RequestMapping(value = "/padre/", params = {"name"}, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getProductoPadreByName(@RequestParam(value = "name", required = false) String name)
			throws BussinessException {
		return new ResponseEntity<Object>(new Response(productoPadreService.getByName(name), HttpStatus.OK.value(), ""),
				HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public ResponseEntity<Object> getProductosBypage(@RequestBody FilterProductoPadreDTO filterProductoPadreDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
		return new ResponseEntity<>(productoPadreService.getProductoPadreByPage(filterProductoPadreDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> get(@PathVariable Integer id) throws BussinessException {
		ProductoPadreDTO productoPadreDTO = productoPadreService.get(id);
		if (productoPadreDTO == null) {
			return new ResponseEntity<Object>(new MessageError(HttpStatus.NOT_FOUND, "No se encontro el Producto con el id:" + id, null), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new Response(productoPadreDTO, HttpStatus.OK.value(), ""), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/min/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getMin(@PathVariable Integer id) {
		ProductoPadreSinProductoDTO productoPadreDTO = productoPadreService.getMin(id);
		if (productoPadreDTO == null) {
			return new ResponseEntity<>(new MessageError(HttpStatus.NOT_FOUND, "No se encontro el Producto con el id:" + id, null), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(productoPadreDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Response> insert(@RequestBody ProductoPadreDTO productoPadreDTO) throws BussinessException {
		return new ResponseEntity<>(Response.insert(productoPadreService.insert(productoPadreDTO)), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Response> update(@RequestBody ProductoPadreDTO productoPadreDTO) throws BussinessException {
		return new ResponseEntity<>(Response.update(productoPadreService.update(productoPadreDTO)), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Response> delete(@PathVariable Integer id) throws BussinessException {
		ProductoPadreDTO productoPadreDTO = productoPadreService.get(id);
		try {
			productoPadreService.delete(productoPadreDTO);
		} catch (Exception ex) {
			throw new DataIntegrityViolationException("No se puedo eliminar el producto padre " + productoPadreDTO.getNombre() + ", porque esta asociado a otros registros.");
		}
		return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), "Producto Padre correctamente eliminado"), HttpStatus.OK);
	}

}
