package ar.com.jsuper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.api.utils.SimpleRequest;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dto.EmailDTO;
import ar.com.jsuper.dto.UsuariosDTO;
import ar.com.jsuper.services.UsuariosService;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/public/user")
public class UserPublicController {

    @Autowired
    UsuariosService usuariosService;

    /**
     * Verifica si existe un nombre de usuario
     *
     * @param user
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/exist/{user}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response> isExistUser(@PathVariable String user) throws BussinessException {
        Map<String, Boolean> data = new HashMap<>();
        data.put("existe", usuariosService.isExistUser(user));
        return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
    }

    /**
     * Verifica si existe un mail de usuariom en la tabla usuario
     *
     * @param request
     * @param mail
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/existmail", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> isExistMailUser(@RequestBody SimpleRequest request) throws BussinessException {
        Map<String, Boolean> data = new HashMap<>();
        data.put("existe", usuariosService.isExistMailUser(request.getData()));
        return new ResponseEntity<>(Response.ok(data), HttpStatus.OK);
    }

    /**
     * Guarda un usuario
     *
     * @param usuarioDTO
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> saveUser(@RequestBody UsuariosDTO usuarioDTO) throws BussinessException {
        return new ResponseEntity<>(Response.insert(usuariosService.saveUser(usuarioDTO)), HttpStatus.OK);
    }

    /**
     * Recuperar un password
     *
     * @return
     */
    @RequestMapping(value = "/recuperarpass", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> recuperarPass(@RequestBody EmailDTO emailDTO) {
        usuariosService.recuperarPass(emailDTO.getEmail());
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * Guarda la contraseña desde la recuperacion
     *
     * @return
     */
    @RequestMapping(value = "/recuperarpass/save", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> recuperarPass(@RequestBody Map<String, String> data) {
        if (Objects.nonNull(data.get("token")) && Objects.nonNull(data.get("password"))) {
            return new ResponseEntity<>(Response.ok(usuariosService.savePasswordFromRec(data.get("token"), data.get("password"))), HttpStatus.OK);
        } else {
            throw new DataIntegrityViolationException("Datos de consulta invalidos.");
        }
    }

    /**
     * Devuelve un json que dice si es valido o no el token
     *
     * @return
     */
    @RequestMapping(value = "/validate/{token}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response> recuperarPass(@PathVariable String token) {
        return new ResponseEntity<>(Response.ok(usuariosService.getRecuperacionPasswordFromToken(token, false)), HttpStatus.OK);
    }

}
