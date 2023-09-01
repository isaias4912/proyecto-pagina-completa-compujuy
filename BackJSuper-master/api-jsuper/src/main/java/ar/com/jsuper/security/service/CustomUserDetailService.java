/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.security.service;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.CustomUser;
import ar.com.jsuper.domain.Roles;
import ar.com.jsuper.domain.SucursalesAuthDTO;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ar.com.jsuper.services.UsuariosService;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author rafa22
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final Logger logger = Logger.getLogger(CustomUserDetailService.class);

    @Autowired
    private UsuariosService usuariosService;
    @Autowired
    private OrikaBeanMapper modelMapper;

    public CustomUserDetailService(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    public CustomUserDetailService() {
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrMail) throws UsernameNotFoundException {
        Usuarios usuario = null;
        Set<Roles> roles = null;
        try {
            usuario = usuariosService.getByUserOrMail(usernameOrMail);
            if (!Objects.isNull(usuario)) {
                roles = usuario.getRoles();
            }
        } catch (BussinessException ex) {
            logger.error("Problemas de conexion 0", ex);
            throw new RuntimeException("Problemas de conexion.");
        }
        if (usuario != null) {
            // validamos que el key user y app no sean nulos
            if (Objects.isNull(usuario.getApp()) || Objects.isNull(usuario.getKeyUser())) {
                logger.error("Falto  datos del usuario:" + usernameOrMail);
                throw new DataIntegrityViolationException("Falto  datos del usuario (App o Key User)");
            }
            // validamos que el usuario tenga sucursal
            if (Objects.isNull(usuario.getSucursales())) {
                logger.error("Usuario no tiene sucursales (null):" + usernameOrMail);
                throw new DataIntegrityViolationException("El usuario no tiene sucursales asignadas(null)");
            }
            if (usuario.getSucursales().isEmpty()) {
                logger.error("Usuario no tiene sucursales (vacio):" + usernameOrMail);
                throw new DataIntegrityViolationException("El usuario no tiene sucursales asignadas(vacio)");
            }
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            if (!Objects.isNull(roles)) {
                for (Roles role : roles) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(role.getNombre()));
                }
            } else {
                logger.error("Usuario sin roles:" + usernameOrMail);
                throw new DataIntegrityViolationException("Falto  datos del usuario (roles)");
            }
            try {
                usuariosService.saveLogUser(usuario);
            } catch (BussinessException ex) {
                logger.error("Problemas de conexion 1", ex);
            }
            logger.info("Usuario logueado:" + usernameOrMail);
            List<SucursalesAuthDTO> sucursales = modelMapper.mapAsList(usuario.getSucursales(), SucursalesAuthDTO.class);
            CustomUser userDetail = new CustomUser(usuario.getUsuario(),
                    usuario.getPassword(),
                    grantedAuthorities,
                    usuario.getId(),
                    usuario.getApp().getKeyid(),
                    usuario.getKeyUser(),
                    usuario.getKeyGravatar(), sucursales);
            return userDetail;
        } else {
            logger.warn("Logueo fallido:" + usernameOrMail);
            throw new UsernameNotFoundException("User " + usernameOrMail + " not found.");
        }
    }

}
