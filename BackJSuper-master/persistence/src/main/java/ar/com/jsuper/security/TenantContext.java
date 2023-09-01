package ar.com.jsuper.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Alon Segal on 16/03/2017.
 */
public class TenantContext {

    private static Logger logger = LoggerFactory.getLogger(TenantContext.class.getName());

    private static ThreadLocal<Integer> currentTenant = new ThreadLocal<>();
    private static ThreadLocal<Integer> currentIdUser = new ThreadLocal<>();

    public static void setCurrentTenant(Integer tenant) {
        logger.debug("Setting tenant to " + tenant);
        currentTenant.set(tenant);
    }

    public static void setCurrentTenant(String tenant) {
        logger.debug("Setting tenant to " + tenant);
        Integer t = -1;
        try {
            t = Integer.parseInt(tenant);
        } catch (NumberFormatException e) {
            logger.debug("Excepcion en la conversion del tenant: " + tenant);
        }
        currentTenant.set(t);
    }

    public static Integer getCurrentTenant() {
        return currentTenant.get();
    }

    public static Integer getCurrentIdUser() {
        return currentIdUser.get();
    }

    public static void setCurrentIdUser(String idUser) {
        logger.debug("Setting Iduser to " + idUser);
        Integer t = -1;
        try {
            t = Integer.parseInt(idUser);
        } catch (NumberFormatException e) {
            logger.debug("Excepcion en la conversion del id de usuario: " + idUser);
        }
        currentIdUser.set(t);
    }

    public static void clearTenant() {
        currentTenant.set(null);
    }
}
