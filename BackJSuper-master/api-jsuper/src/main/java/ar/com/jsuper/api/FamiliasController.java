package ar.com.jsuper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.aspect.CheckPermission;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Familias;
import ar.com.jsuper.dto.FamiliaMinDTO;
import ar.com.jsuper.dto.FamiliaPadreDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.SimpleObjectDTO;
import ar.com.jsuper.services.FamiliasService;
import ar.com.jsuper.services.SecurityService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/familias")
public class FamiliasController {

	@Autowired
	FamiliasService familiasService;

	@Autowired
	SecurityService<Familias> securityService;
	private Logger logger = Logger.getLogger(FamiliasController.class);

	@CheckPermission
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public ResponseEntity<Object> insert(@RequestBody FamiliaPadreDTO familiaPadreDTO) throws BussinessException {
		return new ResponseEntity<Object>(new Response(familiasService.insertFamilia(familiaPadreDTO), HttpStatus.CREATED.value(), ""),
				HttpStatus.CREATED);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public ResponseEntity<Response> update(@RequestBody FamiliaPadreDTO familiaPadreDTO, @PathVariable Integer id) throws BussinessException {
		securityService.checkIfIdBelognsApp(id, Familias.class);
		familiaPadreDTO.setId(id);
		return new ResponseEntity<>(new Response(familiasService.updateFamilia(id, familiaPadreDTO), HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Response> delete(@PathVariable Integer id) throws BussinessException {
		FamiliaMinDTO familiaDTO = familiasService.getFamiliaMin(id);
		try {
			familiasService.deleteFamilia(familiaDTO);
		} catch (DataIntegrityViolationException ex) {
			logger.error("Error al eliminar una familia", ex);
			throw new DataIntegrityViolationException("No se puedo eliminar la familia " + familiaDTO.getNombre() + ", porque esta asociado a otros registros.");
		} catch (Exception ex) {
			logger.error("Error al eliminar una familia", ex);
			throw new DataIntegrityViolationException("No se puedo eliminar la familia " + familiaDTO.getNombre() + ", hubo un error intente mas tarde.");
		}
		return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/shortname/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response> getAllByShortName(@PathVariable Integer id) throws BussinessException {
		return new ResponseEntity<>(new Response(familiasService.getAllByShortName(), HttpStatus.OK.value(), ""),
				HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response> get(@PathVariable Integer id) throws BussinessException {
		return new ResponseEntity<>(new Response(familiasService.get(id), HttpStatus.OK.value(), ""),
				HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAll() throws BussinessException {
		return new ResponseEntity<Object>(new Response(familiasService.getAllByShortName(), HttpStatus.OK.value(), ""),
				HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/all", params = {"name"}, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response> getByName(@RequestParam(value = "name", required = false) String name) throws BussinessException {
		return new ResponseEntity<>(Response.ok(familiasService.getByName(name)), HttpStatus.OK);
	}

	/**
	 *
	 * @param notId este paremetro si es 0 trae todos, si es desigual a cero traera todos menos ese valor (id)
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/all/arbol", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllMayorNivel() throws BussinessException {
		return new ResponseEntity<Object>(new Response(familiasService.getAllMayorLevel(), HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public ResponseEntity<Object> getFamiliasBypage(@RequestBody FilterGenericDTO filterFamilia, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
		return new ResponseEntity<Object>(familiasService.getFamiliasBypage(filterFamilia, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	/**
	 * Verifica si existe ya el nombre corto de una familia
	 *
	 * @param nombreCorto
	 * @return
	 */
	@RequestMapping(value = "/nombreCorto/exist/{nombreCorto}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response> isExistNombreCorto(@PathVariable String nombreCorto) {
		Map<String, Boolean> data = new HashMap<>();
		data.put("existe", familiasService.isExistNombreCorto(nombreCorto));
		return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
	}

	/**
	 * Devuelve familias desde los ids que se pasan
	 *
	 * @param
	 */
	@RequestMapping(value = "/min/ids", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getProductosMinFromIds(@RequestBody Set<Integer> familias) {
		return new ResponseEntity<>(Response.ok(familiasService.getFamiliasMin(familias)), HttpStatus.OK);
	}
	/**
	 * Devuelve familias desde los ids que se pasan, estos ids estan en un objeto {id:<>}
	 *
	 * @param
	 */
	@RequestMapping(value = "/min/objs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getProductosMinFromObjs(@RequestBody Set<SimpleObjectDTO> familias) {
		Set<Integer> lista= new HashSet<>();
		for (SimpleObjectDTO familia : familias) {
			lista.add(familia.getId());
		}
		return new ResponseEntity<>(Response.ok(familiasService.getFamiliasMin(lista)), HttpStatus.OK);
	}
}
