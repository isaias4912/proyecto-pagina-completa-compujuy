/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.aspect;

import ar.com.jsuper.security.TenantContext;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.BadCredentialsException;

/**
 *
 * @author rafa22
 */
@Aspect
public class LoggingAspect {

    private Logger logger = Logger.getLogger(LoggingAspect.class);

    @Before("@annotation(CheckPermission)")
    public void checkPermission(JoinPoint joinPoint) throws Throwable {
        logger.info("----Checkeando el permiso de la applicacion-----");
        try {
            if ("".equals(TenantContext.getCurrentTenant().toString().trim())) {
                throw new BadCredentialsException("Falta un dato, o usted no esta autorizado.");
            } else if (TenantContext.getCurrentTenant() < 0) {
                throw new BadCredentialsException("Falta un dato, o usted no esta autorizado.");
            }
        } catch (NullPointerException npe) {
                throw new BadCredentialsException("Error en la autenticacion, por favor vuelva a loguearse.");
        }
//        if ("0".equals(TenantContext.getCurrentTenant().toString().trim())){
//            throw  new BadCredentialsException("Falta un dato, o usted no esta autorizado.");
//        }
    }
}
