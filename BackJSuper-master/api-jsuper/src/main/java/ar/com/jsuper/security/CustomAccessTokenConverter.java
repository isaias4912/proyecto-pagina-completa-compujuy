/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.security;

import java.util.Map;

import ar.com.jsuper.domain.UserLogged;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

/**
 * @author rafa
 */
@Component
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    @Value("${pass.key.app}")
    private String passKeyApp;
    @Value("${pass.key.vector}")
    private String passKeyVector;
    private final Logger logger = Logger.getLogger(CustomAccessTokenConverter.class);

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        UserLogged userLogged = new UserLogged();
        userLogged.setNameUser(map.get(CustomOAuth2Authentication.JWT_USER_NAME_KEY).toString());
        logger.info("##############################Interceptando API#############################");
        try {
            logger.info("Api id:" + map.get("app_id").toString());
//            logger.info("passKeyApp:" + passKeyApp);
//            logger.info("randomInitVector:" + passKeyVector);
//            logger.info("passKeyVector:" + passKeyVector);
            String appStr = SimpleEncryption.decrypt(passKeyApp, passKeyVector, map.get(CustomOAuth2Authentication.JWT_APP_ID_KEY).toString());
            userLogged.setIdApp(Integer.parseInt(appStr));
            TenantContext.setCurrentTenant(appStr);
        } catch (NullPointerException e) {
            logger.error("Existio un error con sus credenciales, falta el app.", e);
//            throw new BadCredentialsException("Existio un error con sus credenciales, falta el app.");
//            throw new AccessDeniedException("Existio un error con sus credenciales, falta el app.");
            return null;
        }
        try {
            logger.info("User id:" + map.get("id_user").toString());
            String userStr = SimpleEncryption.decrypt(passKeyApp, passKeyVector, map.get(CustomOAuth2Authentication.JWT_USER_ID_KEY).toString());
            userLogged.setIdUser(Integer.parseInt(userStr));
            TenantContext.setCurrentIdUser(SimpleEncryption.decrypt(passKeyApp, passKeyVector, map.get(CustomOAuth2Authentication.JWT_USER_ID_KEY).toString()));
        } catch (NullPointerException e) {
            logger.error("Existio un error con sus credenciales, falta el id de usuario.", e);
//            throw new BadCredentialsException("Existio un error con sus credenciales, falta el el id de usuario.");
//            throw new AccessDeniedException("Existio un error con sus credenciales, falta el el id de usuario.");
            return null;

        }
        return new CustomOAuth2Authentication(authentication.getOAuth2Request(), authentication.getUserAuthentication(), userLogged); //To change body of generated methods, choose Tools | Templates.
    }
}
