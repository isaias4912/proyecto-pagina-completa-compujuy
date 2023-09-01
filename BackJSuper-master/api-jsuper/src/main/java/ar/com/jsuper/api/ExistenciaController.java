package ar.com.jsuper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.aspect.CheckPermission;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.EncMovimientosDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.PaseMovimientosDTO;
import ar.com.jsuper.dto.StockFromFacturaEncDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import ar.com.jsuper.services.EncMovimientosService;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/v1/existencias")
public class ExistenciaController {

	@Autowired
	EncMovimientosService facturaService;
    private Logger logger = Logger.getLogger(ExistenciaController.class);

	/**
	 * agrega existencia de prod a traves de movimientos
	 *
	 * @param facturaDTO
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> insertMovimientos(@RequestBody EncMovimientosDTO facturaDTO) throws BussinessException {
		return new ResponseEntity<>(new Response(facturaService.insertMovimiento(facturaDTO), HttpStatus.OK.value(), ""),
				HttpStatus.OK);
	}

	/**
	 * devuealve los datos para agregar existencia atravez de una factura, es un preview
	 *
	 * @return
	 */
	@RequestMapping(value = "/add/factura/preview/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> previewInsertMovimientos(@PathVariable Integer id) {
		return new ResponseEntity<>(Response.ok(facturaService.addStockFromFacturaPreview(id)), HttpStatus.OK);
	}

	/**
	 * devuelve los pases genericos
	 *
	 * @param filterGenericDTO
	 * @param pagina
	 * @param paginaTamanio
	 * @param orden
	 * @param reverse
	 * @return
	 */
	@CheckPermission
	@RequestMapping(value = "/pases/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPasesBypage(@RequestBody FilterGenericDTO filterGenericDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
		return new ResponseEntity<>(facturaService.getPasesBypage(filterGenericDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	/**
	 * agrega existencia a traves de una factura
	 *
	 * @return
	 */
	@RequestMapping(value = "/add/factura", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> insertMovimientos(@RequestBody StockFromFacturaEncDTO stockFromFacturaEncDTO) {
		return new ResponseEntity<>(Response.insert(facturaService.addStockFromFactura(stockFromFacturaEncDTO)), HttpStatus.CREATED);
	}
	/**
	 * Confmira un pase  y es cuando se concreta que se aumente el stock de un lado y se disminuya en el otra sucursal
	 * @param movimientosDTO
	 * @return 
	 */
	@RequestMapping(value = "/pases/confirm", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> confirmPase(@RequestBody PaseMovimientosDTO movimientosDTO) {
		return new ResponseEntity<>(Response.insert(facturaService.confirmPase(movimientosDTO)), HttpStatus.CREATED);
	}

	/**
	 * agrega existencia a traves de un pase
	 *
	 * @return
	 */
	@RequestMapping(value = "/add/pase", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> insertMovimientoPase(@RequestBody PaseMovimientosDTO movimientosDTO) {
		return new ResponseEntity<>(Response.insert(facturaService.insertMovimientoPase(movimientosDTO)), HttpStatus.CREATED);
	}

	/**
	 * Eliminamos un pase siempre y cuando no exita la relacion en otras tablas
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/remove/pase/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> deletePase(@PathVariable Integer id){
		PaseMovimientosDTO paseMovimientoDTO = facturaService.getPase(id);
		try {
			facturaService.removePase(paseMovimientoDTO);
		} catch (Exception ex) {
			logger.error("Error al eliminar el pase", ex);
			throw new DataIntegrityViolationException("No se puedo eliminar el pase  " + paseMovimientoDTO.getId() + ", porque esta asociado a otros registros.");
		}
		return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), "Pase correctamente eliminado"), HttpStatus.OK);
	}

	/**
	 * quita existencia de prod a traves de movimientos
	 *
	 * @param facturaDTO
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> removeMovimientos(@RequestBody EncMovimientosDTO facturaDTO) throws BussinessException {
		return new ResponseEntity<>(new Response(facturaService.removeMovimiento(facturaDTO), HttpStatus.OK.value(), ""),
				HttpStatus.OK);
	}

}
