/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.security;

import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;

/**
 *
 * @author rafa22
 */
public class SecurityApp {
    public static void checkIsEqualAPPParamAndTenant(int idApp){
        if (idApp!=TenantContext.getCurrentTenant()){
            throw  new UnauthorizedUserException("No eta autorizado a realizar esta tarea.");
        }
        
    }
}
