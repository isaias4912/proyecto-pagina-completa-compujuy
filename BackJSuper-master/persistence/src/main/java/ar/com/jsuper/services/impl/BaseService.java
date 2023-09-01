/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.impl;

import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.CustomUser;
import ar.com.jsuper.domain.UserLogged;
import ar.com.jsuper.dto.CbteVenEncMinDTO;
import ar.com.jsuper.dto.maps.OrikaBeanMapper;
import ar.com.jsuper.security.SimpleEncryption;
import ar.com.jsuper.security.TenantContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author rafa22
 */
public class BaseService {

    UserLogged user;
    @Value("${path.general}")
    private String pathGeneral;
    @Autowired
    protected OrikaBeanMapper modelMapper;

    //    @Autowired
//    @Qualifier("tokenJS")
//    private TokenStore tokenStore;
    public BaseService() {
    }

    public UserLogged getUser() {
//        HttpServletRequest curRequest
//                = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                .getRequest();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        this.user = (UserLogged) auth.getPrincipal();


        return user;
    }

    public ArrayList getRoles() {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return new ArrayList(authorities);
    }

    public void setUser(UserLogged user) {
        this.user = user;
    }

    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }

    public String getDirFromToken() {
        return this.getDirFromToken(null);
    }

    public static String getDirFromToken(Integer id) {
        String dir = null;
        if (Objects.isNull(id)) {
            dir = SimpleEncryption.encrypt(String.valueOf(TenantContext.getCurrentTenant()));
        } else {
            dir = SimpleEncryption.encrypt(id.toString());
        }
        // reemplazamos los caracteres especiales para poder crear un directorio porque no permite el so caracteres especiales
        return dir.replaceAll("[^a-zA-Z0-9]", "");
    }

    public String getFullPathApp() {
        return this.pathGeneral + this.getDirFromToken() + File.separator;
    }

    protected <T, G> Pagination<G> getPagination(Pagination<T> pagination, Class<G> objectClass) {
        List<G> lista = new ArrayList<>();
        if (pagination.getData() != null) {
            lista = modelMapper.mapAsList(pagination.getData(), objectClass);
        }
        Pagination<G> pag = new Pagination<>();
        pag.setData(lista);
        pag.setTotal(pagination.getTotal());
        pag.setPageSize(pagination.getPageSize());
        pag.setPage(pagination.getPage());
        return pag;
    }

}
