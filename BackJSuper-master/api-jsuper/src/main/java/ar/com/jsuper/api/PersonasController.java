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
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.domain.utils.FilterEntidad;
import ar.com.jsuper.dto.PersonasDTO;
import ar.com.jsuper.dto.PersonasMinDTO;
import ar.com.jsuper.services.PersonasService;
import ar.com.jsuper.services.SecurityService;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/personas")
public class PersonasController {

    @Autowired
    PersonasService personasService;

    @Autowired
    SecurityService<Personas> securityService;

    @CheckPermission
    @RequestMapping(value = "/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPersonasBypage(@RequestBody FilterEntidad filterPersona, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
        return new ResponseEntity<>(personasService.getPersonasBypage(filterPersona, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
    }

    @CheckPermission
    @RequestMapping(value = "/pagination/multiple", params = {"q", "page", "pageSize", "order", "reverse"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getProductoByMultipleFilter(@RequestParam(value = "q") String q, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
        return new ResponseEntity<>(personasService.getPersonasByMultipleFilter(q, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
    }

    @CheckPermission
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> insert(@RequestBody PersonasDTO personaDTO) throws BussinessException {
        return new ResponseEntity<>(new Response(personasService.insertPersona(personaDTO), HttpStatus.CREATED.value(), ""),
                HttpStatus.CREATED);
    }

    @CheckPermission
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> update(@RequestBody PersonasDTO personaDTO, @PathVariable Integer id) throws BussinessException {
        personaDTO.setId(id);
        return new ResponseEntity<>(new Response(personasService.updatePersona(id, personaDTO), HttpStatus.OK.value(), ""), HttpStatus.OK);
    }

    @CheckPermission
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> get(@PathVariable Integer id) throws BussinessException {
        return new ResponseEntity<>(new Response(personasService.get(id), HttpStatus.OK.value(), ""),
                HttpStatus.OK);
    }

    /**
     * Eliminamos una persona siempre y cuando no exita la relacion en otras
     * tablas
     *
     * @param id
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> delete(@PathVariable Integer id) throws BussinessException {
        personasService.delete(id);
        return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), "Persona correctamente eliminada"), HttpStatus.OK);
    }

    /**
     * Devolvemos una persona por dni en caso de que exista
     *
     * @param dni
     * @return
     */
    @RequestMapping(value = "/dni/{dni}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getByDni(@PathVariable String dni) {
        PersonasMinDTO persona = personasService.getByDni(dni);
        Map<String, Object> data = new HashMap<>();
        data.put("existe", !Objects.isNull(persona));
        data.put("persona", persona);
        return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
    }

}
