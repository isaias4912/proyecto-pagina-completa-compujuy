package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.LogUser;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.domain.RecuperacionPass;
import ar.com.jsuper.domain.Roles;
import java.util.Set;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.domain.utils.FilterUsuarios;
import java.util.List;

public interface UsuariosDAO extends GenericDAO<Usuarios, Integer> {

    Usuarios getByUser(String user);

    List<Usuarios> getByUserMatch(String name);

    Usuarios getUserById(Integer idUser);

    Usuarios getUserByEmail(String email);

    Usuarios getByUserOrMail(String userOrMail);

    Set<Roles> getRolesByUser(Usuarios user) throws BussinessException;

    List<Roles> getRoles();

    public Usuarios getByIdFacebook(String idUserFaceboook);

    Roles getRoleByName(String role) throws BussinessException;

    public Usuarios insertUserRedSocial(Usuarios usuario) throws BussinessException;

    public RecuperacionPass saveRecuperarPass(RecuperacionPass recuperacionPass);

    public RecuperacionPass updateRecuperarPass(RecuperacionPass recuperacionPass);

    boolean isExistUser(String user);

    boolean isExistMailUser(String mail);

    void saveLogUser(Usuarios usuario) throws BussinessException;

    LogUser getBeforesLastLogUser(Integer usuario) throws BussinessException;

    Pagination<Usuarios> getUsuariosBypage(FilterUsuarios filterUsuarios, int page, int pageSize, String fieldOrder, boolean reverse);

    Usuarios insertFromApp(Usuarios usuario) throws BussinessException;

    Usuarios updateFromApp(Usuarios usuarioOld, Usuarios usuarioNew) throws BussinessException;

    boolean isExistUserFromPersona(Personas persona) throws BussinessException;

    Usuarios getObject(Usuarios usuarioOld, Usuarios usuarioNew);

    public Usuarios getByIdLogged();

    public Usuarios getMin(Integer id);

    public RecuperacionPass getRecuperacionPasswordFromToken(String token);

}
