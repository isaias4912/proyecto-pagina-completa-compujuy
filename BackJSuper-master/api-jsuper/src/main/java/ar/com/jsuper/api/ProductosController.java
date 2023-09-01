package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.FileMeta;
import java.util.Set;

import ar.com.jsuper.dto.*;
import ar.com.jsuper.services.*;
import ar.com.jsuper.utils.FileUtils;
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
import ar.com.jsuper.domain.Producto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartHttpServletRequest;
//import ar.com.jsuper.services.FileService;
import java.util.HashSet;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductosController {

	@Autowired
	ProductosService productosService;
	@Autowired
	EncMovimientosService encMovimientosService;
	@Autowired
	ProductoPadreService productoPadreService;
	@Autowired
	private FileUtils fileUtil;
	@Autowired
	FileService fileService;
	@Autowired
	UnidadService unidadService;
	@Autowired
	SucursalService sucursalService;
	LinkedList<FileMetaDTO> files = new LinkedList<>();
	FileMeta fileMeta = null;
	@Autowired
	SecurityService<Producto> securityService;

	private Logger logger = Logger.getLogger(ProductosController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<ProductoDTO> getAll() throws BussinessException {
		return productosService.getall();
	}

	@RequestMapping(value = "/", params = {"name"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getProductoByName(@RequestParam(value = "name", required = false) String name)
			throws BussinessException {
		return new ResponseEntity<>(new Response(productoPadreService.getByName(name), HttpStatus.OK.value(), ""),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/padre", params = {"name"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getProductoPadreByName(@RequestParam(value = "name", required = false) String name)
			throws BussinessException {
		return new ResponseEntity<>(new Response(productoPadreService.getByName(name), HttpStatus.OK.value(), ""),
				HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/detalleProductos", params = {"page", "pageSize", "order", "reverse", "type"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProductosBypage(@RequestBody FilterProductoDTO productoFilter, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse, @RequestParam(value = "type", required = false) String type) throws BussinessException {
		return new ResponseEntity<>(productosService.getDetalleProductoByPage(productoFilter, pagina, paginaTamanio, orden, reverse, type), HttpStatus.OK);
	}
    
	@CheckPermission
	@RequestMapping(value = "/withoufilter/pagination", params = {"page", "pagespageSizeize"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getWithouFilterPagination(@RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio) {
		return new ResponseEntity<>(productosService.getWithouFilterPagination(pagina, paginaTamanio), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/pagination/", params = {"q", "page", "pageSize", "order", "reverse"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProductoByMultipleFilter(@RequestParam(value = "q") String q, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse, @RequestParam(value = "precios", required = false, defaultValue = "0") Optional<Integer> precios) throws BussinessException {
		return new ResponseEntity<>(productosService.getProductoByMultipleFilter(q, pagina, paginaTamanio, orden, reverse, precios.get() == 1), HttpStatus.OK);
	}

	/**
	 * Devuelve un producto mas los stock del mismo
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProducto(@PathVariable Integer id, @RequestParam(value = "stock", required = false, defaultValue = "0") Optional<Integer> stock, @RequestParam(value = "precios", required = false, defaultValue = "0") Optional<Integer> precios) throws BussinessException {
		ProductoDTO detalleProducto = null;
		if (stock.get() == 1) {
			detalleProducto = productosService.getProductoAndStock(id, precios.get() == 1);
		} else {
			detalleProducto = productosService.getProducto(id, precios.get() == 1);
		}
		if (detalleProducto == null) {
			return new ResponseEntity<>(
					new MessageError(HttpStatus.NOT_FOUND, "No se encontro el producto con el id:" + id, null),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new Response(detalleProducto, HttpStatus.OK.value(), ""), HttpStatus.OK);
		}
	}

	/**
	 * Devuelve un producto pero solo los minimos campos id nombre y nombre final
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/min/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProductoMin(@PathVariable Integer id) throws BussinessException {
		ProductoMinDTO detalleProducto = productosService.getProductoMin(id);
		if (detalleProducto == null) {
			return new ResponseEntity<>(
					new MessageError(HttpStatus.NOT_FOUND, "No se encontro el Producto con el id:" + id, null),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new Response(detalleProducto, HttpStatus.OK.value(), ""), HttpStatus.OK);
		}
	}

	/**
	 * Devuelve un priducto con todos los campos excepto los relacionados
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/submin/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProductoSubMin(@PathVariable Integer id) throws BussinessException {
		ProductoSubMinDTO detalleProducto = productosService.getProductoSubMin(id);
		if (detalleProducto == null) {
			return new ResponseEntity<>(
					new MessageError(HttpStatus.NOT_FOUND, "No se encontro el Producto con el id:" + id, null),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new Response(detalleProducto, HttpStatus.OK.value(), ""), HttpStatus.OK);
		}
	}

	/**
	 * Devuelve un priducto con todos los campos excepto los relacionados, pero si con la imagen que tiene mayor orden
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/preview/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProductoPreview(@PathVariable Integer id) throws BussinessException {
		ProductoPreviewDTO detalleProducto = productosService.getProductoPreview(id);
		if (detalleProducto == null) {
			return new ResponseEntity<>(
					new MessageError(HttpStatus.NOT_FOUND, "No se encontro el Producto con el id:" + id, null),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new Response(detalleProducto, HttpStatus.OK.value(), ""), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/existebarcode/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> isExistCodigoBarra(@PathVariable String code) throws BussinessException {
		Map<String, Boolean> temp = new HashMap<>();
		return new ResponseEntity<>(new Response(productosService.isExistCodigoBarra(code), HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

//    @RequestMapping(value = "/rafa", method = RequestMethod.GET, produces = "application/pdf")
//    public byte[] prueba() {
////        ArrayList<String> lista = new ArrayList<>();
////        lista.add("rafael");
////        return lista;
//        return productosService.prueba();
//    }
	/**
	 * Creamos un nuevo producto
	 *
	 * @param productoDTO
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> insert(@RequestBody ProductoDTO productoDTO) throws BussinessException {
		return new ResponseEntity<>(new Response(productosService.insert(productoDTO), HttpStatus.CREATED.value(), ""), HttpStatus.CREATED);
	}

	/**
	 * Modifica un producto segun su id
	 *
	 * @param productoUpdateDTO
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> update(@RequestBody ProductoDTO productoUpdateDTO, @PathVariable Integer id) throws BussinessException {
		securityService.checkIfIdBelognsAppForProducto(id, Producto.class);
		return new ResponseEntity<>(Response.update(productosService.update(id, productoUpdateDTO)), HttpStatus.OK);
	}

	/**
	 * Elimina un producto, siempre y cuando no tenga relaciones creaadas, como ser una venta, caso contrario tira un 409
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(@PathVariable Integer id) throws BussinessException {
		securityService.checkIfIdBelognsAppForProducto(id, Producto.class);
		ProductoMinDTO productoDTO = productosService.getProductoMin(id);
		try {
			productosService.deleteProducto(productoDTO);
		} catch (Exception ex) {
			logger.error("Error al eliminar un producto", ex);
			throw new DataIntegrityViolationException("No se puedo eliminar el producto " + productoDTO.getNombreFinal() + ", porque esta asociado a otros registros.");
		}
		return new ResponseEntity<Object>(new Response(null, HttpStatus.OK.value(), ""), HttpStatus.OK);
	}

	/**
	 * Devuelve todo lo necesario para el alta o modificacion de un producto
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/dataNewUpdate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> dataProductos() throws BussinessException {
		Map<String, Object> data = new HashMap<>();
		data.put("unidades", unidadService.getAll());
		data.put("sucursales", sucursalService.getAllActive());
		return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
	}

	/**
	 * Modificar solo el precio de un producto
	 *
	 * @param id
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/{id}/updateprecio", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateprecio(@RequestBody PrecioProductoDTO precioProductoDTO, @PathVariable Integer id) throws BussinessException {
		productosService.updatePrecio(id, precioProductoDTO);
		return new ResponseEntity<>(Response.ok(null), HttpStatus.OK);
	}

	/**
	 * Modificar solo el precio de una o varias familias de productos
	 *
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/updatepreciobyfamilia", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateprecio(@RequestBody PrecioFamiliasProductoDTO precioFamiliasProductoDTO) throws BussinessException {
		productosService.updatePrecioByFamilia(precioFamiliasProductoDTO);
		return new ResponseEntity<>(Response.ok(null), HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/upload/image", headers = "content-type=multipart/*", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public LinkedList<FileMetaDTO> uploadImage(MultipartHttpServletRequest request, HttpServletResponse response) {
		this.files = fileService.generateFileImage(request, this.fileUtil.getDirImagenesProd(), true);
		return files;
	}

	@CheckPermission
	@RequestMapping(value = "/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity disable(@RequestBody Set<ProductoDTO> productosDTO) throws BussinessException {
		productosService.enabledOrdisabled(productosDTO, false);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@CheckPermission
	@RequestMapping(value = "/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity enable(@RequestBody Set<ProductoDTO> productosDTO) throws BussinessException {
		productosService.enabledOrdisabled(productosDTO, true);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	/**
	 * Devuelve un o varios productos segun cod barra o descripcion
	 *
	 * @param request
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/codigobarraorname", params = {"active", "like", "tipoBarCode"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getProductoByBarCodeOrName(@RequestParam(value = "active", required = false, defaultValue = "1") Integer active, @RequestParam(value = "like", required = false, defaultValue = "0") Integer like, @RequestParam(value = "tipoBarCode", required = false, defaultValue = "1") Integer tipoBarCode, @RequestBody SearchProdPVDTO request, @RequestParam(value = "precios", required = false, defaultValue = "0") Optional<Integer> precios) throws BussinessException {
		return new ResponseEntity<>(Response.ok(productosService.getProductoByBarCodeOrName(request.getQuery(), request.getIdSucursal(), active, like, tipoBarCode, precios.get() == 1)), HttpStatus.OK);
	}

	/**
	 * Devuelve un o varios productos segun cod barra o descripcion
	 *
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/query/search", params = {"active", "like", "tipoBarCode", "q"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getProductoByBarCodeOrNameWithoutStock(@RequestParam(value = "q", required = false, defaultValue = "1") String q, @RequestParam(value = "active", required = false, defaultValue = "1") Integer active, @RequestParam(value = "like", required = false, defaultValue = "0") Integer like, @RequestParam(value = "tipoBarCode", required = false, defaultValue = "1") Integer tipoBarCode) throws BussinessException {
		return new ResponseEntity<>(Response.ok(productosService.getProductoByBarCodeOrNameWithOutStock(q.trim(), active, like, tipoBarCode)), HttpStatus.OK);
	}

	/**
	 * Devuelve lso estocks de un producto por id
	 *
	 * @param idProducto
	 * @return
	 * @throws BussinessException
	 */
	@CheckPermission
	@RequestMapping(value = "/{idProducto}/stocks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getStocksByProducto(@PathVariable Integer idProducto) throws BussinessException {
		return new ResponseEntity<>(Response.ok(productosService.getStocksByProducto(idProducto)), HttpStatus.OK);
	}

	/**
	 * Devuelve los movimientos de existencia que tuvo un producto
	 *
	 * @param idProducto
	 * @return
	 */
	@CheckPermission
	@RequestMapping(value = "/{idProducto}/movimientos/stock", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMovimientosStockByProducto(@PathVariable Integer idProducto,
			@RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio,
			@RequestParam(value = "order", required = false) String orden,
			@RequestParam(value = "reverse", required = false) boolean reverse,
			@RequestBody FilterGenericDTO filterGenericDTO) {
		return new ResponseEntity<>(encMovimientosService.getMovimientosProductoByProducto(idProducto, pagina, paginaTamanio, orden, reverse, filterGenericDTO), HttpStatus.OK);
	}

	/**
	 * Devuelve los movimientos de precios que tuvo un producto
	 *
	 * @param idProducto
	 * @return
	 */
	@CheckPermission
	@RequestMapping(value = "/{idProducto}/movimientos/precio", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMovimientosPrecioByProducto(@PathVariable Integer idProducto, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
		return new ResponseEntity<>(encMovimientosService.getMovimientosPrecioByProducto(idProducto, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
	}

	/**
	 * Devuelve los pdres o el arblo de padres de un producto
	 *
	 * @param idProducto
	 * @return
	 */
	@CheckPermission
	@RequestMapping(value = "/{idProducto}/parents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getParents(@PathVariable Integer idProducto) {
		return new ResponseEntity<>(Response.ok(productosService.getParentsFromId(idProducto)), HttpStatus.OK);
	}

	/**
	 * Devuelve los hijos o el arbol de hijos de un producto
	 *
	 * @param idProducto
	 * @return
	 */
	@CheckPermission
	@RequestMapping(value = "/{idProducto}/childs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getChilds(@PathVariable Integer idProducto) {
		return new ResponseEntity<>(Response.ok(productosService.getChildsFromId(idProducto)), HttpStatus.OK);
	}

	/**
	 * Devuelve los hijos y padres de un producto
	 *
	 * @param idProducto
	 * @return
	 */
	@CheckPermission
	@RequestMapping(value = "/{idProducto}/parentsandchilds", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getParentsAndChilds(@PathVariable Integer idProducto) {
		return new ResponseEntity<>(Response.ok(productosService.getParentsAndChildsFromId(idProducto)), HttpStatus.OK);
	}

	/**
	 * Reconstruye todos los codigos especiales
	 *
	 * @param
	 */
	@CheckPermission
	@RequestMapping(value = "/buildAllCodigosEspeciales", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> buildAllCodigosEspeciales() {
		productosService.buildAllCodigosEspeciales();
		return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
	}

	/**
	 * Reconstruye todos los codigos especiales
	 *
	 * @param
	 */
	@CheckPermission
	@RequestMapping(value = "/familia/{idFamilia}/codigoespecial", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getCodigoEspecialFromIdFamilia(@PathVariable Integer idFamilia) {
		return new ResponseEntity<>(Response.ok(productosService.getCodigoEspecialFromIdFamilia(idFamilia)), HttpStatus.OK);
	}

	/**
	 * Devuelve el historial de stock de un producto por paginas
	 *
	 * @param
	 */
	@CheckPermission
	@RequestMapping(value = "/{idProducto}/historial/stock", params = {"page", "pageSize"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getHistorialProducto(@PathVariable Integer idProducto, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio) {
		return new ResponseEntity<>(Response.ok(productosService.getHistorialProducto(idProducto, pagina, paginaTamanio)), HttpStatus.OK);
	}

	/**
	 * devuelve productos desde los ids que se pasan
	 *
	 * @param
	 */
	@RequestMapping(value = "/ids", params = {"sucursal"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getProductosFromIds(@RequestParam(value = "sucursal", required = true) Integer sucursal, @RequestBody Set<SimpleObjectDTO> productos, @RequestParam(value = "precios", required = false, defaultValue = "0") Optional<Integer> precios) {
		return new ResponseEntity<>(Response.ok(productosService.getProductosFromIds(sucursal, productos, precios.get() == 1)), HttpStatus.OK);
	}

	/**
	 * devuelve productos desde los ids que se pasan
	 *
	 * @param
	 */
	@RequestMapping(value = "/min/ids", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getProductosMinFromIds(@RequestBody Set<Integer> productos) {
		return new ResponseEntity<>(Response.ok(productosService.getProductosMin(productos)), HttpStatus.OK);
	}

	/**
	 * devuelve productos desde los ids que se pasan, pero objecots [{id:<>}]
	 *
	 * @param
	 */
	@RequestMapping(value = "/min/objs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getProductosMinFromObjs(@RequestBody Set<SimpleObjectDTO> productos) {
		Set<Integer> lista= new HashSet<>();
		for (SimpleObjectDTO producto : productos) {
			lista.add(producto.getId());
		}
		return new ResponseEntity<>(Response.ok(productosService.getProductosMin(lista)), HttpStatus.OK);
	}

}
