package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.FileMeta;
import ar.com.jsuper.api.utils.Response;
import ar.com.jsuper.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ar.com.jsuper.aspect.CheckPermission;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.domain.utils.FilterUsuarios;
import ar.com.jsuper.dto.UsuariosDTO;
import ar.com.jsuper.dto.UsuariosMinDTO;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.services.FileService2;
import ar.com.jsuper.services.RolesService;
import ar.com.jsuper.services.SecurityService;
import ar.com.jsuper.services.UsuariosService;

import java.util.LinkedList;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {

    @Autowired
    UsuariosService usuariosService;
    @Autowired
    RolesService rolesService;
    @Autowired
    SecurityService<Usuarios> securityService;
    private Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    FileService2 fileService;
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    @Autowired
    private FileUtils fileUtil;

    @CheckPermission
    @RequestMapping(value = "/all/pagination", params = {"page", "pageSize", "order", "reverse"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUsuarioBypage(@RequestBody FilterUsuarios filterUsuariosDTO, @RequestParam(value = "page") int pagina, @RequestParam(value = "pageSize") int paginaTamanio, @RequestParam(value = "order", required = false) String orden, @RequestParam(value = "reverse", required = false) boolean reverse) throws BussinessException {
        return new ResponseEntity<>(usuariosService.getUsuarioBypage(filterUsuariosDTO, pagina, paginaTamanio, orden, reverse), HttpStatus.OK);
    }

    /**
     * devuelve los usuarios, pero segun el nombre de usuario , nombre o apelido de persona, lo hace con like
     *
     * @param name
     * @return
     * @throws BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "/all", params = {"user"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getByUserMatch(@RequestParam(value = "user", required = false) String name)
            throws BussinessException {
        return new ResponseEntity<>(Response.ok(usuariosService.getByUserMatch(name)), HttpStatus.OK);
    }

    @CheckPermission
    @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getRoles() throws BussinessException {
        return new ResponseEntity<>(Response.ok(usuariosService.getRoles()), HttpStatus.OK);
    }

    @CheckPermission
    @RequestMapping(value = "/all/active", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getAllActive() throws BussinessException {
        return new ResponseEntity<>(Response.ok(usuariosService.getRoles()), HttpStatus.OK);
    }

    @CheckPermission
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> insertUsuario(@RequestBody UsuariosDTO usuarioDTO) throws BussinessException {
        return new ResponseEntity<>(Response.insert(usuariosService.insertFromApp(usuarioDTO)), HttpStatus.CREATED);
    }

    @CheckPermission
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> get(@PathVariable Integer id) throws BussinessException {
        UsuariosDTO usuariosDTO = usuariosService.get(id);
        usuariosDTO.setPassword(""); // quitamso la contraseña hash por seguridad
        return new ResponseEntity<>(Response.ok(usuariosDTO), HttpStatus.OK);
    }

    @CheckPermission
    @RequestMapping(value = "/min/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getMin(@PathVariable Integer id) throws BussinessException {
        UsuariosMinDTO usuariosDTO = usuariosService.getMin(id);
        return new ResponseEntity<>(Response.ok(usuariosDTO), HttpStatus.OK);
    }

    /**
     * devueve datos del usuario loeguado
     *
     * @param id
     * @return
     * @throws BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "/my", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getMy() throws BussinessException {
        UsuariosDTO usuariosDTO = usuariosService.get(TenantContext.getCurrentIdUser());
        usuariosDTO.setPassword(""); // quitamso la contraseña hash por seguridad
        return new ResponseEntity<>(Response.ok(usuariosDTO), HttpStatus.OK);
    }

    /**
     * devuelve el menu del usuario segun el token
     *
     * @param id
     * @return
     * @throws BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getMenu() throws BussinessException {
        return new ResponseEntity<>(Response.ok(rolesService.getMenuesFromUserAuth()), HttpStatus.OK);
    }

    @CheckPermission
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> update(@RequestBody UsuariosDTO usuarioDTO, @PathVariable Integer id) throws BussinessException {
        securityService.checkIfIdBelognsApp(id, Usuarios.class);
        usuarioDTO.setId(id);
        return new ResponseEntity<>(new Response(usuariosService.updateFromApp(id, usuarioDTO), HttpStatus.OK.value(), ""), HttpStatus.OK);
    }

    /**
     * Deshabilita a un usuario,pone el estado en false
     *
     * @param usuariosDTO
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity disable(@RequestBody Set<UsuariosDTO> usuariosDTO) throws BussinessException {
        usuariosService.enabledOrdisabled(usuariosDTO, false);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * Habilita un usuario, pone un estado en true
     *
     * @param usuariosDTO
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity enable(@RequestBody Set<UsuariosDTO> usuariosDTO) throws BussinessException {
        usuariosService.enabledOrdisabled(usuariosDTO, true);
        return new ResponseEntity<>(Response.ok(), HttpStatus.OK);
    }

    /**
     * elimino un usuario
     *
     * @param id
     * @return
     * @throws BussinessException
     */
    @CheckPermission
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Response> delete(@PathVariable Integer id) throws BussinessException {
        UsuariosMinDTO usuariosMinDTO = usuariosService.getUsuarioMin(id);
        try {
            usuariosService.deleteUsuario(usuariosMinDTO);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Error al eliminar el usuario", ex);
            throw new DataIntegrityViolationException("No se puedo eliminar el usuario " + usuariosMinDTO.getUsuario() + ", porque esta asociado a otros registros.");
        } catch (Exception ex) {
            logger.error("Error al eliminar el usuario", ex);
            throw new DataIntegrityViolationException("No se puedo eliminar el usuario " + usuariosMinDTO.getUsuario());
        }
        return new ResponseEntity<>(new Response(null, HttpStatus.OK.value(), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/upload/image", headers = "content-type=multipart/*", method = RequestMethod.POST, produces = "application/json")
    public LinkedList<FileMeta> uploadImage(MultipartHttpServletRequest request, HttpServletResponse response) {
        this.files = fileService.generateFileImage(request, this.fileUtil.getDirImagenesUsers(), false);
        return files;
    }

    @RequestMapping(value = "/my/upload/image", headers = "content-type=multipart/*", method = RequestMethod.POST, produces = "application/json")
    public LinkedList<FileMeta> uploadImageMy(MultipartHttpServletRequest request, HttpServletResponse response) {
        this.files = fileService.generateFileImage(request, this.fileUtil.getDirImagenesMy(), false);
        return files;
    }
}
