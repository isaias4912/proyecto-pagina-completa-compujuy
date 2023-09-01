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
import ar.com.jsuper.dto.FechaDTO;
import ar.com.jsuper.dto.FilterOfertaDTO;
import ar.com.jsuper.dto.FilterOfertaProductosDTO;
import ar.com.jsuper.dto.OfertasDTO;
import ar.com.jsuper.dto.OfertasProdDTO;
import ar.com.jsuper.dto.OfertasProductoSinOfertaDTO;
import ar.com.jsuper.services.OfertasService;
import ar.com.jsuper.services.SecurityService;
import java.util.Map;
import java.util.Set;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/ofertas")
public class OfertasController {

    @Autowired
    OfertasService ofertasService;
    @Autowired
    SecurityService<Caja> securityService;

    /**
     * Devuelve una oferta
     *
     * @param id
     * @return
     * @throws BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable Integer id) throws BussinessException {
        OfertasDTO ofertaDTO = ofertasService.get(id);
        return new ResponseEntity<>(Response.ok(ofertaDTO), HttpStatus.OK);
    }

    /**
     * Guarda ofertasProductos, unn araray de productos que se corrresponderan
     * con la oferta
     *
     * @param ofertaDTO
     * @return
     * @throws BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "/ofertasproductos", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertOfertaAndProductos(@RequestBody OfertasProdDTO ofertaDTO) throws BussinessException {
        Set<OfertasProductoSinOfertaDTO> response = ofertasService.insertOfertaProductos(ofertaDTO);
        return new ResponseEntity<>(Response.ok(response), HttpStatus.OK);
    }

    /**
     * Devuelve una ofertta junto a sus productos que tiene asociados
     *
     * @param id
     * @return
     * @throws BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "/ofertasproductos/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOfertaAndProductos(@PathVariable Integer id) throws BussinessException {
        OfertasProdDTO ofertaDTO = ofertasService.getOfertaAndProductos(id);
        return new ResponseEntity<>(Response.ok(ofertaDTO), HttpStatus.OK);
    }

    /**
     * Guarda solamente una oferta
     *
     * @param ofertaDTO
     * @return
     * @throws BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> insertferta(@RequestBody OfertasDTO ofertaDTO) throws BussinessException {
        return new ResponseEntity<>(Response.insert(ofertasService.insert(ofertaDTO)), HttpStatus.CREATED);
    }

    /**
     * Paginacion de ofertas
     *
     * @param filterOfertaDTO
     * @param pagina
     * @param paginaTamanio
     * @param orden
     * @param reverse
     * @return
     */
    @CheckPermission
    @RequestMapping(value = "/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOfertasBypage(@RequestBody FilterOfertaDTO filterOfertaDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
        return new ResponseEntity<>(ofertasService.getOfertasBypage(filterOfertaDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
    }

    /**
     * Paginacion de productos de una oferta, o de todas las ofertas segun el id
     * del filtro
     *
     * @param filterOfertaProductosDTO
     * @param pagina
     * @param paginaTamanio
     * @param orden
     * @param reverse
     * @return
     */
    @CheckPermission
    @RequestMapping(value = "/productos/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOfertasProductosBypage(@RequestBody FilterOfertaProductosDTO filterOfertaProductosDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) {
        return new ResponseEntity<>(ofertasService.getOfertasProductosBypage(filterOfertaProductosDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
    }

    /**
     * Deshabilita una oferta o varias,pone el estado en false
     *
     * @param ofertasDTO
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity disable(@RequestBody Set<OfertasDTO> ofertasDTO) throws BussinessException {
        ofertasService.enabledOrdisabled(ofertasDTO, false);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * Habilita una oferta o varias, pone un estado en true
     *
     * @param ofertasDTO
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity enable(@RequestBody Set<OfertasDTO> ofertasDTO) throws BussinessException {
        ofertasService.enabledOrdisabled(ofertasDTO, true);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * Deshabilita una producto por oferta o varias,pone el estado en false
     *
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/productos/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity disableProductosOfertas(@RequestBody Set<OfertasProductoSinOfertaDTO> ofertasProductosDTO) throws BussinessException {
        ofertasService.enabledOrdisabledOfertasProductos(ofertasProductosDTO, false);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * Habilita una producto por oferta o varias, pone un estado en true
     *
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/productos/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity enableProductosOfertas(@RequestBody Set<OfertasProductoSinOfertaDTO> ofertasProductosDTO) throws BussinessException {
        ofertasService.enabledOrdisabledOfertasProductos(ofertasProductosDTO, true);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * cambia la prioridad a una oferta
     *
     * @param idOferta
     * @param prioridad
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/{idOferta}/prioridad", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity putPrioridad(@PathVariable Integer idOferta, @RequestBody Map<String, Integer> prioridad) throws BussinessException {
        Boolean res = ofertasService.updatePriridad(idOferta, prioridad);
        if (res) {
            return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Response.notModified(), HttpStatus.NOT_MODIFIED);
        }

    }

    /**
     * cambia la fecha de una oferta a una oferta
     *
     * @param idOferta
     * @param fechaHasta
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/{idOferta}/fechaHasta", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity putFechaHasta(@PathVariable Integer idOferta, @RequestBody FechaDTO fechaDTO) throws BussinessException {
        FechaDTO fechaDTO1 = fechaDTO;
        Boolean res = ofertasService.updateFechaHasta(idOferta, fechaDTO);
        if (res) {
            return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Response.notModified(), HttpStatus.NOT_MODIFIED);
        }

    }
}
