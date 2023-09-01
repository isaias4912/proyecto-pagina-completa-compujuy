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
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.utils.FilterClientes;
import ar.com.jsuper.domain.utils.FilterMovCtaCte;
import ar.com.jsuper.dto.ClienteDTO;
import ar.com.jsuper.dto.CtaCteMinDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.dto.PagarCtaCteDTO;
import ar.com.jsuper.services.ClientesService;
import ar.com.jsuper.services.ParametricasService;
import ar.com.jsuper.services.SecurityService;
import ar.com.jsuper.utils.ConstanstAfipTest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClientesController {

	@Autowired
	ClientesService clientesService;
	@Autowired
	ParametricasService parametricasService;
	@Autowired
	SecurityService<Cliente> securityService;

	@CheckPermission
	@RequestMapping(value = "/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getClientesBypage(@RequestBody FilterClientes filterCliente, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
		return new ResponseEntity<>(clientesService.getClientesBypage(filterCliente, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	/**
	 * devuelve lista de clientes sin paginacion,filtra por dni o nombre
	 *
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/all/multiple", params = {"q", "active"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getClientesByNameOrDni(@RequestParam(value = "q") String q, @RequestParam(value = "active", required = false, defaultValue = "1") Integer active) {
		return new ResponseEntity<>(Response.ok(clientesService.getClientesByNameOrDni(q, active)), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/pagination/multiple", params = {"q", "active", "page", "pageSize", "order", "reverse"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getClientesByMultipleFilter(@RequestParam(value = "q") String q, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse, @RequestParam(value = "active", required = false, defaultValue = "2") Integer active) throws BussinessException {
		return new ResponseEntity<>(clientesService.getClientesByMultipleFilter(q, active, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	/**
	 * Se inserta un cliente, pero la entidad debe existir, y no se la modifica
	 *
	 * @param clienteDTO
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> insert(@RequestBody ClienteDTO clienteDTO) throws BussinessException {
		return new ResponseEntity<>(new Response(clientesService.insertCliente(clienteDTO), HttpStatus.CREATED.value(), ""),
				HttpStatus.CREATED);
	}

	/**
	 * Se inserta un cliente pero la entidad puede ser nueva o se la puede modificar
	 *
	 * @param clienteDTO
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/entidad", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> insertFromEntidad(@RequestBody ClienteDTO clienteDTO) throws BussinessException {
		return new ResponseEntity<>(new Response(clientesService.insertClienteFromEntidad(clienteDTO), HttpStatus.CREATED.value(), ""),
				HttpStatus.CREATED);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> update(@RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) throws BussinessException {
		clienteDTO.setId(id);
		return new ResponseEntity<>(new Response(clientesService.updateCliente(id, clienteDTO), HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/existclienteorpersona/{dni}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getAllByShortName(@PathVariable String dni) throws BussinessException {
		return new ResponseEntity<>(Response.ok(clientesService.getIsExistClienteOrPersona(dni)), HttpStatus.OK);
	}

	/**
	 * devuelve lista de clientes (si no es cliente incluye si esite una persona con los filtros) sin paginacion,filtra por dni o nombre
	 *
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/all/clienteandpersona", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getListByDniOrName(@RequestBody FilterGenericDTO filterGenericDTO) throws BussinessException {
		return new ResponseEntity<>(Response.ok(clientesService.getListByDniOrName(filterGenericDTO)), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> get(@PathVariable Integer id) throws BussinessException {
		return new ResponseEntity<>(Response.ok(clientesService.get(id)), HttpStatus.OK);
	}

	/**
	 * devuelve un cliente pero minimizado por id
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/{id}/min", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getMin(@PathVariable Integer id) throws BussinessException {
		return new ResponseEntity<>(Response.ok(clientesService.getClienteMinById(id)), HttpStatus.OK);
	}

	/**
	 * devuelve un cliente pero minimizado por id, para el punto de venta
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/{id}/mic", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getMic(@PathVariable Integer id) throws BussinessException {
		return new ResponseEntity<>(Response.ok(clientesService.getClienteMicById(id)), HttpStatus.OK);
	}

	/**
	 * Deshabilita a un cliente,pone el estado en false
	 *
	 * @param clientesDTO
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity disable(@RequestBody Set<ClienteDTO> clientesDTO) throws BussinessException {
		clientesService.enabledOrdisabled(clientesDTO, false);
		return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
	}

	/**
	 * Habilita un cliente, pone un estado en true
	 *
	 * @param clientesDTO
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity enable(@RequestBody Set<ClienteDTO> clientesDTO) throws BussinessException {
		clientesService.enabledOrdisabled(clientesDTO, true);
		return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
	}

	/**
	 * Eliminamos un cliente siempre y cuando no exita la relacion en otras tablas
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> delete(@PathVariable Integer id) throws BussinessException {
		try {
			clientesService.delete(id);
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException("No se puedo eliminar el cliente,  porque esta asociado a otros registros.");
		} catch (Exception ex) {
			throw new DataIntegrityViolationException("No se puedo eliminar el cliente , hubo un error intente mas tarde.");
		}
		return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), "Cliente correctamente eliminado"), HttpStatus.OK);
	}

	/**
	 * Devuelve un cliente por dni
	 *
	 * @param dni
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/dni/{dni}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getByDni(@PathVariable String dni) throws BussinessException {
		return new ResponseEntity<>(Response.ok(clientesService.getByDni(dni)), HttpStatus.OK);
	}

	/**
	 * Crea una cuenta corriente para un cliente
	 *
	 * @param cteSinCliente
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/{id}/ctacte", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> createCtaCte(@RequestBody CtaCteMinDTO cteSinCliente, @PathVariable Integer id) throws BussinessException {
		return new ResponseEntity<>(Response.insert(clientesService.createCtaCte(id, cteSinCliente)), HttpStatus.CREATED);
	}

	/**
	 * modifica una cuenta corriente
	 *
	 * @param cteSinCliente
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/{id}/ctacte", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateCtaCte(@RequestBody CtaCteMinDTO cteSinCliente, @PathVariable Integer id) {
		return new ResponseEntity<>(Response.ok(clientesService.updateCtaCte(id, cteSinCliente)), HttpStatus.OK);
	}

	/**
	 * devuelve una cuenta corriente, si no tiene nada es null
	 *
	 */
	@CheckPermission
	@RequestMapping(value = "/ctacte/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getCtaCte(@PathVariable Integer id) {
		return new ResponseEntity<>(Response.ok(clientesService.getCtaCte(id)), HttpStatus.OK);
	}

	/**
	 * devuelve resultados paginados de los momvimientos de un cliente con cta cte
	 *
	 */
	@CheckPermission
	@RequestMapping(value = "/ctacte/mto/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMovimientosCtaCte(@RequestBody FilterMovCtaCte filterMovCtaCteDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
		return new ResponseEntity<>(clientesService.getMovimientosCtaCte(filterMovCtaCteDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	/**
	 * devuelve resultados sin paginar de los momvimientos de un cliente con cta cte
	 *
	 */
	@RequestMapping(value = "/ctacte/mto/all", params = {"order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMovimientosCtaCte(@RequestBody FilterMovCtaCte filterMovCtaCteDTO, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
		return new ResponseEntity<>(clientesService.getMovimientosCtaCte(filterMovCtaCteDTO, 0, 0, orden, reverse), HttpStatus.OK);
	}

	/**
	 * Pagamos cuenta corriente de clientes
	 */
	@RequestMapping(value = "/ctacte/pagar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> pagar(@RequestBody PagarCtaCteDTO pagarCtaCteDTO) {
		return new ResponseEntity<>(Response.ok(clientesService.pagar(pagarCtaCteDTO)), HttpStatus.OK);
	}

	/**
	 * Hacemos una previa de lo que se pagara para que se genere en el back y no en el fron t por seguridad de datos y calculos
	 */
	@RequestMapping(value = "/ctacte/pagarPreview", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> pagarPreview(@RequestBody PagarCtaCteDTO pagarCtaCteDTO) {
		return new ResponseEntity<>(Response.ok(clientesService.pagarPreview(pagarCtaCteDTO)), HttpStatus.OK);
	}

	/**
	 * Devuelve todos los pagos que se realizaron para un mto de cta cte segun su id
	 */
	@CheckPermission
	@RequestMapping(value = "/ctacte/mto/{id}/pagos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getPagosFromMtoCtaCte(@PathVariable Integer id) {
		return new ResponseEntity<>(Response.ok(clientesService.getPagosFromMtoCtaCte(id)), HttpStatus.OK);
	}

	/**
	 * Devuelve todosz los detalles del mto, incluye saldos y pagos realizados
	 */
	@CheckPermission
	@RequestMapping(value = "/ctacte/mto/{id}/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getDetailPagosFromMtoCtaCte(@PathVariable Integer id) {
		return new ResponseEntity<>(Response.ok(clientesService.getDetailPagosFromMtoCtaCte(id)), HttpStatus.OK);
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
		data.put("formasPago", this.parametricasService.getListAllActiveForCtaCteCli());
		return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
	}

	/**
	 * Devuelve todos los pagos que se realizaron para un mto de cta cte segun su id
	 */
	@CheckPermission
	@RequestMapping(value = "/ctacte/pagos/{idPagoCtaCte}/pagos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getPagosFromIdPagoCtaCte(@PathVariable Integer idPagoCtaCte) {
		return new ResponseEntity<>(Response.ok(clientesService.getPagosFromIdPagoCtaCte(idPagoCtaCte)), HttpStatus.OK);
	}

	/**
	 * Devuelve todo lo necesario para el alta o modificacion de una factura de venta
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/dataClientes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response> dataClientes() {
		Map<String, Object> data = new HashMap<>();
		data.put("docs", ConstanstAfipTest.getTpoDocs());
		return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
	}

	/**
	 * Crea una cuenta corriente para un cliente
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/mails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getMails(@PathVariable Integer id) {
		return new ResponseEntity<>(Response.ok(clientesService.getMails(id)), HttpStatus.OK);
	}
}
