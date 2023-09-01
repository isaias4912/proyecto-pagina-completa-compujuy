package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.RolesDAO;
import ar.com.jsuper.dao.SucursalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.com.jsuper.dao.UsuariosDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.domain.RecuperacionPass;
import ar.com.jsuper.domain.Roles;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.domain.utils.FilterUsuarios;
import ar.com.jsuper.dto.UsuariosDTO;
import ar.com.jsuper.dto.UsuariosListDTO;
import ar.com.jsuper.dto.UsuariosMinDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.UsuariosService;
import ar.com.jsuper.services.mails.MailAdminService;
import ar.com.jsuper.utils.Constants;
import ar.com.jsuper.utils.FileUtils;
import ar.com.jsuper.utils.ValidatorsUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuariosServiceImpl implements UsuariosService {

    @Autowired
    MailAdminService mailAdminService;
    @Autowired
    private UsuariosDAO usuariosDAO;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private OrikaBeanMapper beanMapper;
    @Autowired
    private RolesDAO rolesDAO;
    @Autowired
    private SucursalDAO sucursalDAO;
    @Autowired
    private FileUtils fileUtils;

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Usuarios getByUser(String user) throws BussinessException {
        try {
            return usuariosDAO.getByUser(user);
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UsuariosListDTO getUserById(Integer idUser) {
        Usuarios usuario = usuariosDAO.getUserById(idUser);
        return beanMapper.map(usuario, UsuariosListDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<UsuariosMinDTO> getByUserMatch(String user) {
        List<Usuarios> usuarios = usuariosDAO.getByUserMatch(user);
        return beanMapper.mapAsList(usuarios, UsuariosMinDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public List<UsuariosMinDTO> getAllActive() throws BussinessException {
        List<Usuarios> lista = usuariosDAO.getAllListActive();
        return beanMapper.mapAsList(lista, UsuariosMinDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Usuarios getByUserOrMail(String userOrMail) throws BussinessException {
        try {
            return usuariosDAO.getByUserOrMail(userOrMail);
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Usuarios getByIdFacebook(String idUserFaceboook) {
        return usuariosDAO.getByIdFacebook(idUserFaceboook);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Set<Roles> getRolesByUser(Usuarios user) throws BussinessException {
        try {
            return usuariosDAO.getRolesByUser(user);
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public List<Roles> getRoles() throws BussinessException {
        try {
            return usuariosDAO.getRoles();
        } catch (Exception e) {
            throw new BussinessException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public boolean isExistUser(String user) {
        return usuariosDAO.isExistUser(user);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public boolean isExistMailUser(String mail) {
        return usuariosDAO.isExistMailUser(mail);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public boolean isExistUserFromPersona(Personas persona) throws BussinessException {
        return usuariosDAO.isExistUserFromPersona(persona);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public Usuarios saveUser(UsuariosDTO usuariosDTO) throws BussinessException {
        // comprobamos  nuevamente desde el lado del server que  no exista el usuario y el mail
        if (this.isExistUser(usuariosDTO.getUsuario())) {
            throw new HttpMessageNotWritableException("Ya existe el usuario :" + usuariosDTO.getUsuario());
        }
        if (this.isExistMailUser(usuariosDTO.getMail())) {
            throw new HttpMessageNotWritableException("Ya existe el mail :" + usuariosDTO.getMail());
        }
        Usuarios usuario = beanMapper.map(usuariosDTO, Usuarios.class);
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuariosDAO.insert(usuario);
        // creamos directorios de usuario necesarios
        fileUtils.createDirectorysUser(usuario.getApp().getId());
        mailAdminService.sendAdminNewUser(usuario);
        mailAdminService.sendNewUser(usuario);
        return usuario;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public Usuarios saveUserRedSocial(UsuariosDTO usuariosDTO) throws BussinessException {
        // comprobamos  nuevamente desde el lado del server que  no exista el usuario y el mail
        Usuarios usuario = beanMapper.map(usuariosDTO, Usuarios.class);
        return usuariosDAO.insertUserRedSocial(usuario);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public void saveLogUser(Usuarios usuario) throws BussinessException {
        usuariosDAO.saveLogUser(usuario);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Pagination<UsuariosListDTO> getUsuarioBypage(FilterUsuarios filterUsuarios, int page, int pageSize, String fieldOrder, boolean reverse) throws BussinessException {
//		FilterUsuarios filterUsuarios = beanMapper.map(clienteFilter, FilterUsuarios.class);
        // armamos los filtros extras para los daos
        Pagination<Usuarios> paginacionUsuarios = usuariosDAO.getUsuariosBypage(filterUsuarios, page, pageSize, fieldOrder, reverse);
        List<UsuariosListDTO> lista = new ArrayList<>();
        if (paginacionUsuarios.getData() != null) {
            lista = beanMapper.mapAsList(paginacionUsuarios.getData(), UsuariosListDTO.class);
        }
        Pagination<UsuariosListDTO> pag = new Pagination<>();
        pag.setData(lista);
        pag.setTotal(paginacionUsuarios.getTotal());
        pag.setPageSize(paginacionUsuarios.getPageSize());
        pag.setPage(paginacionUsuarios.getPage());
        return pag;
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public UsuariosDTO insertFromApp(UsuariosDTO usuarioDTO) throws BussinessException {
//		// comprobamos  nuevamente desde el lado del server que  no exista el usuario y el mail
//		if (this.isExistUser(usuarioDTO.getUsuario())) {
//			throw new HttpMessageNotWritableException("Ya existe el usuario :" + usuarioDTO.getUsuario());
//		}
//		if (this.isExistMailUser(usuarioDTO.getMail())) {
//			throw new HttpMessageNotWritableException("Ya existe el mail :" + usuarioDTO.getMail());
//		}
//		//comprobamos que la sucursal que se quiera asociar al usuario sea valida
//		Set<SucursalesMinDTO> sucursales = usuarioDTO.getSucursales();
//		for (SucursalesMinDTO sucursal : sucursales) {
//			Boolean res = sucursalDAO.isSucursalOfApp(sucursal.getId());
//			if (!res) {
//				throw new DataIntegrityViolationException("La sucursal seleccionada no esta habilitada, por favor inicie sesión nuevamente, o refresque el sistema.");
//			}
//		}
        Usuarios usuario = beanMapper.map(usuarioDTO, Usuarios.class);
        this.validateUsuario(usuario, null);
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario.setTipoUsuario(1);// usuario normal
        usuario.setFechaCreacion(new Date());
        usuario.setRoot(Boolean.FALSE); // root por ahora es cuando se crea desde el primer registro nomas
        usuario = usuariosDAO.insertFromApp(usuario);
        return beanMapper.map(usuario, UsuariosDTO.class);
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public UsuariosDTO updateFromApp(Integer id, UsuariosDTO UsuariosDTO) throws BussinessException {
        UsuariosDTO.setId(id);
        Usuarios usuario = beanMapper.map(UsuariosDTO, Usuarios.class);
        Usuarios usuarioBD = usuariosDAO.get(id);
        this.validateUsuario(usuario, usuarioBD);
        if (usuario.getPassword() == null) {
            usuario.setPassword(usuarioBD.getPassword());
        } else {
            if (usuario.getPassword().trim().equals("")) {
                usuario.setPassword(usuarioBD.getPassword());
            } else {
                usuario.setPassword(encoder.encode(usuario.getPassword()));
            }
        }
        rolesDAO.saveUpdateOrDelete(usuarioBD, usuario.getRoles(), new HashSet<>(usuarioBD.getRoles()));
        sucursalDAO.saveUpdateOrDelete(usuarioBD, usuario.getSucursales(), new HashSet<>(usuarioBD.getSucursales()));
        usuario = usuariosDAO.updateFromApp(usuarioBD, usuario);
        return beanMapper.map(usuario, UsuariosDTO.class);
    }

    private void validateUsuario(Usuarios usuario, Usuarios usuarioBD) throws BussinessException {
        // comprobamos  nuevamente desde el lado del server que  no exista el usuario y el mail
        if (Objects.isNull(usuarioBD)) {
            if (this.isExistUser(usuario.getUsuario())) {
                throw new HttpMessageNotWritableException("Ya existe el usuario :" + usuario.getUsuario());
            }
            if (this.isExistMailUser(usuario.getMail())) {
                throw new HttpMessageNotWritableException("Ya existe el mail :" + usuario.getMail());
            }
            if (this.isExistUserFromPersona(usuario.getPersona())) {
                throw new HttpMessageNotWritableException("Ya existe un usuario asociado a la persona :" + usuario.getPersona().getApellido() + " " + usuario.getPersona().getNombre());
            }
            if (usuario.getUsuario().trim().equals("admin") // nombre invalidos
                    || usuario.getUsuario().trim().equals("root")
                    || usuario.getUsuario().trim().equals("rafa")
                    || usuario.getUsuario().trim().equals("rafael")
                    || usuario.getUsuario().trim().equals("aldo")) {
                throw new HttpMessageNotWritableException("Ya existe el usuario :" + usuario.getUsuario());
            }
        } else {
            if (!usuario.getUsuario().trim().equals(usuarioBD.getUsuario())) {
                if (this.isExistUser(usuario.getUsuario())) {
                    throw new HttpMessageNotWritableException("Ya existe el usuario :" + usuario.getUsuario());
                }
            }
            if (!usuario.getMail().trim().equals(usuarioBD.getMail())) {
                if (this.isExistMailUser(usuario.getMail())) {
                    throw new HttpMessageNotWritableException("Ya existe el mail :" + usuario.getMail());
                }
            }
            // validamos que esa persona no tenga ya un usuario
            if (!(usuario.getPersona().getId().equals(usuarioBD.getPersona().getId()))) {
                if (this.isExistUserFromPersona(usuario.getPersona())) {
                    throw new HttpMessageNotWritableException("Ya existe un usuario asociado a la persona :" + usuarioBD.getPersona().getApellido() + " " + usuarioBD.getPersona().getNombre());
                }
            }
        }

        if (Objects.isNull(usuario.getRoles()) || usuario.getRoles().size() <= 0) {
            throw new HttpMessageNotWritableException("Debe tener roles el usuario");
        }
        List<Roles> roles = new ArrayList(usuario.getRoles());
        if (Objects.isNull(usuario.getSucursales()) || usuario.getSucursales().size() <= 0) {
            if (roles.get(0).getNombre().equals(Constants.ROLE_ADMIN)) {
                List<Sucursales> sucursales = sucursalDAO.getListAll();
                usuario.setSucursales(new HashSet<>(sucursales));
            } else {
                throw new HttpMessageNotWritableException("Debe tener sucursales el usuario");
            }
        } else {
            if (roles.get(0).getNombre().equals(Constants.ROLE_ADMIN)) {
                List<Sucursales> sucursales = sucursalDAO.getListAll();
                usuario.setSucursales(new HashSet<>(sucursales));
            }
            Set<Sucursales> sucursales = usuario.getSucursales();
            for (Sucursales sucursal : sucursales) {
                Boolean res = sucursalDAO.isSucursalOfApp(sucursal.getId());
                if (!res) {
                    throw new DataIntegrityViolationException("La sucursal seleccionada no esta habilitada, por favor inicie sesión nuevamente, o refresque el sistema.");
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = BussinessException.class)
    public void enabledOrdisabled(Set<UsuariosDTO> usuariosDTO, boolean value) throws BussinessException {
        for (UsuariosDTO usuarioDTO : usuariosDTO) {
            Usuarios usuario = usuariosDAO.get(usuarioDTO.getId());
            usuario.setEstado(value);
            usuariosDAO.update(usuario);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UsuariosDTO get(Integer id) {
        Usuarios usuario = usuariosDAO.get(id);
        return beanMapper.map(usuario, UsuariosDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuariosMinDTO getMin(Integer id) {
        Usuarios usuario = usuariosDAO.getMin(id);
        return beanMapper.map(usuario, UsuariosMinDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuariosMinDTO getUsuarioMin(Integer id) throws BussinessException {
        Usuarios usuario = usuariosDAO.get(id);
        return beanMapper.map(usuario, UsuariosMinDTO.class);
    }

    @Override
    public Collection<GrantedAuthority> getAuthoritiesNotNull(Set<Roles> roles) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (!Objects.isNull(roles)) {
            for (Roles role : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getNombre()));
            }
        } else {
            throw new DataIntegrityViolationException("Falto  datos del usuario (roles)");
        }
        return grantedAuthorities;
    }

    @Override
    public void deleteUsuario(UsuariosMinDTO usuariosMinDTO) throws BussinessException {
        Usuarios usuario = beanMapper.map(usuariosMinDTO, Usuarios.class);
        usuariosDAO.delete(usuario);
    }

    @Override
    @Transactional()
    public void recuperarPass(String email) {
        if (Objects.isNull(email) || !ValidatorsUtils.isValidEmail(email) || email.equals("")) {
            throw new DataIntegrityViolationException("Email invalido.");
        }
        Usuarios usuario = usuariosDAO.getUserByEmail(email);
        if (Objects.nonNull(usuario)) {
            String token = UUID.randomUUID().toString();
            RecuperacionPass recuperacionPass = new RecuperacionPass();
            recuperacionPass.setActivo(Boolean.TRUE);
            recuperacionPass.setEmail(email);
            recuperacionPass.setFechaCreacion(new Date());
            recuperacionPass.setToken(token);
            recuperacionPass.setTomado(Boolean.FALSE);
            recuperacionPass.setIdUser(usuario.getId());
            usuariosDAO.saveRecuperarPass(recuperacionPass);
            mailAdminService.sendMailRecuperarPass(usuario, recuperacionPass);
        } else {
            throw new DataIntegrityViolationException("No existe el email ingresado en el sistema.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Map getRecuperacionPasswordFromToken(String token, Boolean dataRec) {
        RecuperacionPass recuperacionPass = this.usuariosDAO.getRecuperacionPasswordFromToken(token);
        Map<String, Object> resp = new HashMap<>();
        resp.put("valido", false);
        if (Objects.nonNull(recuperacionPass)) {
            if (recuperacionPass.getActivo() && (Objects.isNull(recuperacionPass.getTomado()) || !recuperacionPass.getTomado())) {
                // controlamos la fecha ahora 15 dias de validez
                Date fecha = recuperacionPass.getFechaCreacion();
                Calendar c = Calendar.getInstance();
                c.setTime(fecha);
                c.add(Calendar.DATE, 15); //same with c.add(Calendar.DAY_OF_MONTH, 1);
                Date fecha15DiasDespues = c.getTime();
                System.out.println("fecha15DiasDespues:" + fecha15DiasDespues);
                if (fecha15DiasDespues.after(new Date())) {
                    resp.put("valido", true);
                }
            }
            resp.put("token", recuperacionPass.getToken());
            resp.put("idUser", recuperacionPass.getIdUser());
        }
        if (dataRec) {
            resp.put("data", recuperacionPass);
        }
        return resp;
    }

    @Override
    @Transactional()
    public String savePasswordFromRec(String token, String password) {
        Map<String, Object> resp = this.getRecuperacionPasswordFromToken(token, true);
        RecuperacionPass recuperacionPass = (RecuperacionPass) resp.get("data");
        Boolean valido = (Boolean) resp.get("valido");
        if (valido) {
            Usuarios usuario = usuariosDAO.getUserById((Integer) resp.get("idUser"));
            if (Objects.isNull(usuario)) {
                throw new DataIntegrityViolationException("Usuario invalido");
            }
            usuario.setPassword(encoder.encode(password.trim()));
            usuariosDAO.update(usuario);
            recuperacionPass.setTomado(Boolean.TRUE);
            recuperacionPass.setFechaTomado(new Date());
            usuariosDAO.updateRecuperarPass(recuperacionPass);
            return "Se modifico correctamente la contraseña del usuario :" + usuario.getUsuario();
        } else {
            throw new DataIntegrityViolationException("Token invalido o caducado.");
        }
    }
}
