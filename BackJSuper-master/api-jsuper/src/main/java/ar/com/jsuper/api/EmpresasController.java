package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.aspect.CheckPermission;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.utils.FilterEntidad;
import ar.com.jsuper.dto.EmpresasDTO;
import ar.com.jsuper.dto.EmpresasMinDTO;
import ar.com.jsuper.services.ParametricasService;
import ar.com.jsuper.services.EmpresasService;
import ar.com.jsuper.services.SecurityService;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresasController {

	@Autowired
	EmpresasService empresasService;

	@Autowired
	SecurityService<Empresas> securityService;

	@Autowired
	ParametricasService parametricasService;

	@CheckPermission
	@RequestMapping(value = "/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEmpresasBypage(@RequestBody FilterEntidad filterEmpresa, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
		return new ResponseEntity<>(empresasService.getEmpresasBypage(filterEmpresa, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/pagination/", params = {"q", "page", "pageSize", "order", "reverse"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEmpresasByMultipleFilter(@RequestParam(value = "q") String q, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
		return new ResponseEntity<>(empresasService.getEmpresasByMultipleFilter(q, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> insert(@RequestBody EmpresasDTO empresaDTO) throws BussinessException {
		return new ResponseEntity<>(new Response(empresasService.insertEmpresa(empresaDTO), HttpStatus.CREATED.value(), ""),
				HttpStatus.CREATED);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> update(@RequestBody EmpresasDTO empresaDTO, @PathVariable Integer id) throws BussinessException {
		empresaDTO.setId(id);
		return new ResponseEntity<>(new Response(empresasService.updateEmpresa(id, empresaDTO), HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> get(@PathVariable Integer id, @RequestParam(value = "min", required = false, defaultValue = "0") Optional<Integer> min) {
		if (min.isPresent()) {
			if (min.get() == 1) {
				return new ResponseEntity<>(Response.ok(empresasService.getEmpresaMin(id)),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(Response.ok(empresasService.get(id)),
				HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}/min", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getEmpresaMin(@PathVariable Integer id) {
		return new ResponseEntity<>(Response.ok(empresasService.getEmpresaMin(id)),
				HttpStatus.OK);
	}

	/**
	 * Eliminamos un empresa siempre y cuando no exita la relacion en otras tablas
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> delete(@PathVariable Integer id) throws BussinessException {
		empresasService.delete(id);
		return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), "Empresa  correctamente eliminada"), HttpStatus.OK);
	}

	/**
	 * devuelve los empresase, pero segun el cuit o razon social , mediante like
	 *
	 * @param q
	 * @return
	 */
	@CheckPermission
	@RequestMapping(value = "/all", params = {"q"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getByEmpresaMatch(@RequestParam(value = "q", required = false) String q
	) {
		return new ResponseEntity<>(Response.ok(empresasService.getByEmpresaMatch(q)), HttpStatus.OK);
	}

	/**
	 * Devolvemos una empresa por cuit en caso de que exista
	 *
	 * @param cuit
	 * @return
	 */
	@RequestMapping(value = "/cuit/{cuit}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getByCuit(@PathVariable String cuit) {
		EmpresasMinDTO empresa = empresasService.getByCuit(cuit);
		Map<String, Object> data = new HashMap<>();
		data.put("existe", !Objects.isNull(empresa));
		data.put("empresa", empresa);
		return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
	}
}
