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
import ar.com.jsuper.domain.Caja;
import ar.com.jsuper.dto.CajaDTO;
import ar.com.jsuper.dto.DataTransaccionCajaDTO;
import ar.com.jsuper.dto.FilterCajaDTO;
import ar.com.jsuper.services.CajasService;
import ar.com.jsuper.services.ClavesService;
import ar.com.jsuper.services.SecurityService;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/cajas")
public class CajaController {

	@Autowired
	CajasService cajasService;
	@Autowired
	ClavesService clavesService;
	@Autowired
	SecurityService<Caja> securityService;
	private Logger logger = Logger.getLogger(CajaController.class);

	@CheckPermission
	@RequestMapping(value = "/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getCajaBypage(@RequestBody FilterCajaDTO filterCajaDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
		return new ResponseEntity<>(cajasService.getCajasBypage(filterCajaDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/all/active", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllActive() throws BussinessException {
		return new ResponseEntity<>(Response.ok(cajasService.getAllActive()), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity get(@PathVariable Integer id) throws BussinessException {
		CajaDTO cajaDTO = cajasService.get(id);
		return new ResponseEntity<>(Response.ok(cajaDTO), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> insertCaja(@RequestBody CajaDTO cajaDTO) throws BussinessException {
		return new ResponseEntity<>(Response.insert(cajasService.saveCaja(cajaDTO)), HttpStatus.CREATED);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> update(@RequestBody CajaDTO cajaDTO, @PathVariable Integer id) throws BussinessException {
		securityService.checkIfIdBelognsApp(id, Caja.class);
		cajaDTO.setId(id);
		return new ResponseEntity<>(new Response(cajasService.updateCaja(id, cajaDTO), HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/data/caja", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getDataCaja() {
		return new ResponseEntity<>(new Response(cajasService.getDataCaja(), HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

	/**
	 * Eliminamos una caja siempre y cuando no exita la relacion en otras tablas
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> delete(@PathVariable Integer id) throws BussinessException {
		CajaDTO cajaDTO = cajasService.get(id);
		try {
			cajasService.delete(cajaDTO);
		} catch (Exception ex) {
			logger.error("Error al eliminar la caja", ex);
			throw new DataIntegrityViolationException("No se puedo eliminar la caja " + cajaDTO.getNombre() + ", porque esta asociado a otros registros.");
		}
		return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), "Caja correctamente eliminada"), HttpStatus.OK);
	}

	/**
	 * Deshabilita una caja,pone el estado en false
	 *
	 * @param cajasDTO
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity disable(@RequestBody Set<CajaDTO> cajasDTO) throws BussinessException {
		cajasService.enabledOrdisabled(cajasDTO, false);
		return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
	}

	/**
	 * Habilita una caja, pone un estado en true
	 *
	 * @param cajasDTO
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity enable(@RequestBody Set<CajaDTO> cajasDTO) throws BussinessException {
		cajasService.enabledOrdisabled(cajasDTO, true);
		return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
	}

	/**
	 * Abrir una caja
	 *
	 * @param dataTransaccionCajaDTO
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/apertura", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> abrirCaja(@RequestBody DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException {
		return new ResponseEntity<>(Response.ok(cajasService.abrirCaja(dataTransaccionCajaDTO)), HttpStatus.OK);
	}

	/**
	 * Cerrar una caja
	 *
	 * @param dataTransaccionCajaDTO
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/cierre", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> cerrarCaja(@RequestBody DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException {
		return new ResponseEntity<>(Response.ok(cajasService.cerrarrCaja(dataTransaccionCajaDTO)), HttpStatus.OK);
	}

	/**
	 *
	 * @param dataTransaccionCajaDTO
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/movimiento", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> movimientoCaja(@RequestBody DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException {
		return new ResponseEntity<>(Response.ok(cajasService.movimientoCaja(dataTransaccionCajaDTO)), HttpStatus.OK);
	}

	/**
	 * anula una venta
	 *
	 * @param dataTransaccionCajaDTO
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/anulaventa", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> anulaVenta(@RequestBody DataTransaccionCajaDTO dataTransaccionCajaDTO) throws BussinessException {
		return new ResponseEntity<>(Response.ok(cajasService.anulaVenta(dataTransaccionCajaDTO)), HttpStatus.OK);
	}

	/**
	 * Devuelve un obj clave si es que encuentra al usuario sin la clave....
	 *
	 * @param clave
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/clave/{clave}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getUsuarioClave(@PathVariable String clave) throws BussinessException {
		return new ResponseEntity<>(Response.ok(clavesService.getByClave(clave)), HttpStatus.OK);
	}

	/**
	 * Devuelve todo el resumen de una caja
	 *
	 * @param clave
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/resumencaja/{idTransaccion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getUsuarioClave(@PathVariable Integer idTransaccion) throws BussinessException {
		return new ResponseEntity<>(Response.ok(cajasService.getSummaryVentasFromTransaccion(idTransaccion)), HttpStatus.OK);
	}

	@RequestMapping(value = "/transaccion/{idTransaccion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getTransaccion(@PathVariable Integer idTransaccion) throws BussinessException {
		return new ResponseEntity<>(Response.ok(cajasService.getTransaccionCaja(idTransaccion)), HttpStatus.OK);
	}
}
