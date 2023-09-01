package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.RolesDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.jsuper.dto.MenuDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.services.RolesService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolesServiceImpl extends BaseService implements RolesService {

    @Autowired
    private RolesDAO rolesDAO;
    @Autowired
    private OrikaBeanMapper modelMapper;

    /**
     * Devuelve los menues de un rol, segun el usuario que figura en el token
     *
     * @param rolDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = BussinessException.class, readOnly = true)
    public Map getMenuesFromUserAuth() {
//        Roles rol = beanMapper.map(rolDTO, Roles.class);
//        return beanMapper.mapAsList(rolesDAO.getMenuesFromRol(rol), MenuDTO.class);
//        CustomUser customUser= this.getRoles();
        Map<String, MenuDTO> response = new HashMap<>();
        ArrayList<SimpleGrantedAuthority> roles = this.getRoles();
//        List<Menu> lista = new ArrayList<>();
        if (roles.isEmpty()) {
            throw new UnauthorizedUserException("Usuario sin roles");
        } else {
            SimpleGrantedAuthority role = roles.get(0);
            MenuDTO menuDTO= modelMapper.map(rolesDAO.getMenuesPrincipalFromNameRol(role.getAuthority()), MenuDTO.class);
            response.put("menuPrincipal", menuDTO);
        }
//        for (SimpleGrantedAuthority role : roles) {
//            System.out.println("role:" + role.getAuthority());
//            response.put("menuPrincipal", rolesDAO.getMenuesPrincipalFromNameRol(role.getAuthority()));
////            lista.addAll();
//        }
//        return modelMapper.mapAsList(lista, MenuDTO.class);
        return response;
    }

}
