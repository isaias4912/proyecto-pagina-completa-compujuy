package ar.com.jsuper.services;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.domain.Roles;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.domain.utils.FilterUsuarios;
import ar.com.jsuper.dto.UsuariosDTO;
import ar.com.jsuper.dto.UsuariosListDTO;
import ar.com.jsuper.dto.UsuariosMinDTO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

public interface UsuariosService {

    Usuarios getByUser(String user) throws BussinessException;

    UsuariosListDTO getUserById(Integer idUser);

    List<UsuariosMinDTO> getByUserMatch(String user);

    List<UsuariosMinDTO> getAllActive() throws BussinessException;

    Usuarios getByUserOrMail(String userOrMail) throws BussinessException;

    public Usuarios getByIdFacebook(String idUserFaceboook);

    Set<Roles> getRolesByUser(Usuarios user) throws BussinessException;

    List<Roles> getRoles() throws BussinessException;

    public boolean isExistUser(String user);

    boolean isExistMailUser(String mail);

    boolean isExistUserFromPersona(Personas persona) throws BussinessException;

    Usuarios saveUser(UsuariosDTO usuariosDTO) throws BussinessException;

    Usuarios saveUserRedSocial(UsuariosDTO usuariosDTO) throws BussinessException;

    void saveLogUser(Usuarios usuariosDTO) throws BussinessException;

    Pagination<UsuariosListDTO> getUsuarioBypage(FilterUsuarios filterUsuariosDTO, int numeroPagina, int pagina, String fieldOrder, boolean reverse) throws BussinessException;

    UsuariosDTO insertFromApp(UsuariosDTO usuarioDTO) throws BussinessException;

    UsuariosDTO updateFromApp(Integer id, UsuariosDTO UsuariosDTO) throws BussinessException;

    void enabledOrdisabled(Set<UsuariosDTO> usuariosDTO, boolean value) throws BussinessException;

    UsuariosDTO get(Integer id) throws BussinessException;

    UsuariosMinDTO getMin(Integer id);

    public UsuariosMinDTO getUsuarioMin(Integer id) throws BussinessException;

    public Collection<GrantedAuthority> getAuthoritiesNotNull(Set<Roles> roles);

    public void deleteUsuario(UsuariosMinDTO usuariosMinDTO) throws BussinessException;

    public void recuperarPass(String email);

    public Map getRecuperacionPasswordFromToken(String token, Boolean dataRec);

    public String savePasswordFromRec(String token, String password);

}
