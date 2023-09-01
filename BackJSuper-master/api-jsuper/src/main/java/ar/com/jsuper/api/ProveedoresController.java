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
import ar.com.jsuper.domain.Proveedores;
import ar.com.jsuper.domain.utils.FilterProveedores;
import ar.com.jsuper.dto.FilterMovCtaCteProvDTO;
import ar.com.jsuper.dto.PagarCtaCteProvDTO;
import ar.com.jsuper.dto.ProveedoresDTO;
import ar.com.jsuper.services.ParametricasService;
import ar.com.jsuper.services.ProveedoresService;
import ar.com.jsuper.services.SecurityService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedoresController {

	@Autowired
	ProveedoresService proveedoresService;

	@Autowired
	SecurityService<Proveedores> securityService;

	@Autowired
	ParametricasService parametricasService;

	@CheckPermission
	@RequestMapping(value = "/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProveedoresBypage(@RequestBody FilterProveedores filterProveedor, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
		return new ResponseEntity<Object>(proveedoresService.getProveedoresBypage(filterProveedor, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/pagination/", params = {"q", "page", "pageSize", "order", "reverse"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProveedoresByMultipleFilter(@RequestParam(value = "q") String q, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
		return new ResponseEntity<Object>(proveedoresService.getProveedoresByMultipleFilter(q, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> insert(@RequestBody ProveedoresDTO proveedorDTO) throws BussinessException {
		return new ResponseEntity<>(new Response(proveedoresService.insertProveedor(proveedorDTO), HttpStatus.CREATED.value(), ""),
				HttpStatus.CREATED);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> update(@RequestBody ProveedoresDTO proveedorDTO, @PathVariable Integer id) throws BussinessException {
		securityService.checkIfIdBelognsApp(id, Proveedores.class);
		proveedorDTO.setId(id);
		return new ResponseEntity<>(new Response(proveedoresService.updateProveedor(id, proveedorDTO), HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> get(@PathVariable Integer id, @RequestParam(value = "min", required = false, defaultValue = "0") Optional<Integer> min) {
		if (min.isPresent()) {
			if (min.get() == 1) {
				return new ResponseEntity<>(Response.ok(proveedoresService.getProveedorMin(id)),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(Response.ok(proveedoresService.get(id)),
				HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}/min", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getProveedorMin(@PathVariable Integer id) {
		return new ResponseEntity<>(Response.ok(proveedoresService.getProveedorMin(id)),
				HttpStatus.OK);
	}

	/**
	 * Deshabilita a un proveedor,pone el estado en false
	 *
	 * @param proveedorDTO
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity disable(@RequestBody Set<ProveedoresDTO> proveedorDTO) throws BussinessException {
		proveedoresService.enabledOrdisabled(proveedorDTO, false);
		return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
	}

	/**
	 * Habilita un proveedor, pone un estado en true
	 *
	 * @param proveedorDTO
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity enable(@RequestBody Set<ProveedoresDTO> proveedorDTO) throws BussinessException {
		proveedoresService.enabledOrdisabled(proveedorDTO, true);
		return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
	}

	/**
	 * Eliminamos un proveedor siempre y cuando no exita la relacion en otras tablas
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> delete(@PathVariable Integer id) throws BussinessException {
		proveedoresService.delete(id);
		return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), "Proveedor  correctamente eliminado"), HttpStatus.OK);
	}

	/**
	 * devuelve los proveedorse, pero segun el cuit o razon social , mediante like
	 *
	 * @param q
	 * @return
	 */
	@CheckPermission
	@RequestMapping(value = "/all", params = {"q"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getByProveedorMatch(@RequestParam(value = "q", required = false) String q
	) {
		return new ResponseEntity<>(Response.ok(proveedoresService.getByProveedorMatch(q)), HttpStatus.OK);
	}

//	/**
//	 * Pagamos cuenta corriente de proveedores
//	 */
//	@CheckPermission
//	@RequestMapping(value = "/ctacte/pagar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Response> pagar(@RequestBody MovimientosCtaCteProvDTO movimientosCtaCteProvDTO
//	) {
//		return new ResponseEntity<>(Response.ok(proveedoresService.pagarCuentaCteProv(movimientosCtaCteProvDTO)), HttpStatus.OK);
//	}
	/**
	 * devuelve resultados sin paginar de los momvimientos de un cliente con cta cte
	 *
	 */
	@RequestMapping(value = "/ctacte/mto/all", params = {"order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMovimientosCtaCte(@RequestBody FilterMovCtaCteProvDTO filterMovCtaCteDTO, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
		return new ResponseEntity<>(proveedoresService.getMovimientosCtaCte(filterMovCtaCteDTO, 0, 0, orden, reverse), HttpStatus.OK);
	}

	/**
	 * Devuelve todo lo necesario para el pago de una cta cte
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/ctacte/dataPagos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> dataProductos() throws BussinessException {
		Map<String, Object> data = new HashMap<>();
		data.put("formasPago", this.parametricasService.getListAllActiveForCtaCteProv());
		return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
	}

	/**
	 * Hacemos una previa de lo que se pagara para que se genere en el back y no en el fron t por seguridad de datos y calculos
	 */
	@RequestMapping(value = "/ctacte/pagarPreview", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> pagarPreview(@RequestBody PagarCtaCteProvDTO pagarCtaCteProvDTO) {
		return new ResponseEntity<>(Response.ok(proveedoresService.pagarPreview(pagarCtaCteProvDTO)), HttpStatus.OK);
	}

	/**
	 * Pagamos cuenta corriente de proveedores, la cuenta q tiene la empresa con el proveedor
	 */
	@RequestMapping(value = "/ctacte/pagar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> pagar(@RequestBody PagarCtaCteProvDTO pagarCtaCteProvDTO) {
		return new ResponseEntity<>(Response.ok(proveedoresService.pagar(pagarCtaCteProvDTO)), HttpStatus.OK);
	}

	/**
	 * Devuelve todosz los detalles del mto, incluye saldos y pagos realizados
	 */
	@CheckPermission
	@RequestMapping(value = "/ctacte/mto/{id}/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getDetailPagosFromMtoCtaCte(@PathVariable Integer id) {
		return new ResponseEntity<>(Response.ok(proveedoresService.getDetailPagosFromMtoCtaCte(id)), HttpStatus.OK);
	}

	/**
	 * Devuelve todos los pagos que se realizaron para un mto de cta cte segun su id
	 */
	@CheckPermission
	@RequestMapping(value = "/ctacte/pagos/{idPagoCtaCte}/pagos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getPagosFromIdPagoCtaCte(@PathVariable Integer idPagoCtaCte) {
		return new ResponseEntity<>(Response.ok(proveedoresService.getPagosFromIdPagoCtaCte(idPagoCtaCte)), HttpStatus.OK);
	}
}
