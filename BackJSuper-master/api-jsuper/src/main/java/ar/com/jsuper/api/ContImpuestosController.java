package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.aspect.CheckPermission;
import ar.com.jsuper.dto.ImpuestoDTO;
import ar.com.jsuper.dto.FilterGenericDTO;
import ar.com.jsuper.services.ParametricasService;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/impuestos")
public class ContImpuestosController {

    @Autowired
    ParametricasService parametricaService;
    private Logger logger = Logger.getLogger(ContImpuestosController.class);

    /**
     * Deuelve la paginacion de impuestos cont_impuestos
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
        return new ResponseEntity<>(parametricaService.getContImpuestosByPage(filterGenericDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
    }

    /**
     * Insertamos un nuevo impuesto cont_impuestos
     *
     * @param contImpuestosDTO
     * @return
     */
    @CheckPermission
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> insertContImpuesto(@RequestBody ImpuestoDTO contImpuestosDTO) {
        return new ResponseEntity<>(Response.insert(parametricaService.insertContImpuesto(contImpuestosDTO)), HttpStatus.CREATED);
    }

    /**
     * Modifica un impuesto
     *
     * @param contImpuestosDTO
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateContImpuesto(@RequestBody ImpuestoDTO contImpuestosDTO, @PathVariable Integer id){
        return new ResponseEntity<>(new Response(parametricaService.updateContImpuesto(id, contImpuestosDTO), HttpStatus.OK.value(), ""), HttpStatus.OK);
    }

    /**
     * Deshabilita a un impuesto,pone el estado en false
     *
     * @param contImpuestosDTO
     * @return 
     */
    @RequestMapping(value = "/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity disable(@RequestBody Set<ImpuestoDTO> contImpuestosDTO) {
        parametricaService.enabledOrdisabledContImpuestos(contImpuestosDTO, false);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * Habilita un impuesto, pone un estado en true
     *
     * @param contImpuestosDTO
     * @return
     */
    @RequestMapping(value = "/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity enable(@RequestBody Set<ImpuestoDTO> contImpuestosDTO) {
        parametricaService.enabledOrdisabledContImpuestos(contImpuestosDTO, true);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * elimino un impuesto
     *
     * @param id
     * @return 
     */
    @CheckPermission
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> delete(@PathVariable Integer id) {
        parametricaService.deleteContImpuesto(id);
        return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), ""), HttpStatus.OK);
    }
  
}
