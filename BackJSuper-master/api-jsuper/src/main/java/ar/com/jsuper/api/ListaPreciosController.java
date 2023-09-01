package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.aspect.CheckPermission;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.ListaPreciosDTO;
import ar.com.jsuper.services.ListaPreciosService;
import java.util.Set;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/listaprecios")
public class ListaPreciosController {

	@Autowired
	ListaPreciosService listaPreciosService;

	/**
	 * Deuelve la paginacion de lista de precios
	 *
	 * @param filterGenericDTO
	 * @param pagina
	 * @param paginaTamanio
	 * @param orden
	 * @param reverse
	 * @return
	 */
	@RequestMapping(value = "/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getContImpuestosByPage(@RequestBody FilterGenericDTO filterGenericDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
		return new ResponseEntity<>(listaPreciosService.getListaPreciosByPage(filterGenericDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	/**
	 * devuelve todos las listas de precios activas
	 *
	 * @return
	 * @throws ar.com.jsuper.dao.exception.BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/activos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllImpuestosActive() throws BussinessException {
		return new ResponseEntity<>(Response.ok(listaPreciosService.getAllListasPreciosActive()), HttpStatus.OK);
	}

	/**
	 * Insertamos una nueva lista de precios
	 *
	 * @param listaPreciosDTO
	 * @return
	 */
	@CheckPermission
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> insertContImpuesto(@RequestBody ListaPreciosDTO listaPreciosDTO) {
		return new ResponseEntity<>(Response.insert(listaPreciosService.insertListaPrecios(listaPreciosDTO)), HttpStatus.CREATED);
	}

	/**
	 * Devuelve una lista de precio segun el id
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getListaPrecios(@PathVariable Integer id) {
		return new ResponseEntity<>(new Response(listaPreciosService.getListaPrecios(id), HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

	/**
	 * Modifica una lista de precios
	 *
	 * @param listaPreciosDTO
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateContImpuesto(@RequestBody ListaPreciosDTO listaPreciosDTO, @PathVariable Integer id) {
		return new ResponseEntity<>(new Response(listaPreciosService.updateListaPrecios(id, listaPreciosDTO), HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

	/**
	 * Deshabilita a una lista de precios ,pone el estado en false
	 *
	 * @param listaPreciosDTOs
	 * @return
	 */
	@RequestMapping(value = "/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity disable(@RequestBody Set<ListaPreciosDTO> listaPreciosDTOs) {
		listaPreciosService.enabledOrdisabledListaPrecios(listaPreciosDTOs, false);
		return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
	}

	/**
	 * Habilita una lista de precios , pone un estado en true
	 *
	 * @param listaPreciosDTOs
	 * @return
	 */
	@RequestMapping(value = "/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity enable(@RequestBody Set<ListaPreciosDTO> listaPreciosDTOs) {
		listaPreciosService.enabledOrdisabledListaPrecios(listaPreciosDTOs, true);
		return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
	}

	/**
	 * elimino una lista de precios
	 *
	 * @param id
	 * @return
	 */
	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> delete(@PathVariable Integer id) {
		listaPreciosService.deleteListaPrecios(id);
		return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

}
