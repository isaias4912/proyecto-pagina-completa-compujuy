/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author rafa22
 */
public class CustomUser extends User {

    private Integer id;
    private String idApp;
    private String idUser;
    private String keyAvatar;
    private List<SucursalesAuthDTO> sucursales;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer id, String idApp, String idUser, String keyAvatar) {
        super(username, password, authorities);
        this.id = id;
        this.idApp = idApp;
        this.idUser = idUser;
        this.keyAvatar = keyAvatar;
    }

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer id, String idApp, String idUser, String keyAvatar, List<SucursalesAuthDTO> sucursales) {
        super(username, password, authorities);
        this.id = id;
        this.idApp = idApp;
        this.idUser = idUser;
        this.keyAvatar = keyAvatar;
        this.sucursales = sucursales;
    }

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getKeyAvatar() {
        return keyAvatar;
    }

    public void setKeyAvatar(String keyAvatar) {
        this.keyAvatar = keyAvatar;
    }

    public List<SucursalesAuthDTO> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<SucursalesAuthDTO> sucursales) {
        this.sucursales = sucursales;
    }

}
