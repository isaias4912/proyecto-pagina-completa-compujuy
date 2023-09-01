package ar.com.jsuper.security;

import ar.com.jsuper.domain.CustomUser;
import ar.com.jsuper.domain.UserLogged;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.Map;

public class CustomOAuth2Authentication extends OAuth2Authentication {
    private UserLogged userLogged;

    public static final String JWT_USER_NAME_KEY = "user_name";
    public static final String JWT_USER_ID_KEY = "id_user";
    public static final String JWT_APP_ID_KEY = "app_id";

    public CustomOAuth2Authentication(OAuth2Request storedRequest, Authentication userAuthentication) {
        super(storedRequest, userAuthentication);
    }

    public CustomOAuth2Authentication(OAuth2Request storedRequest, Authentication userAuthentication, UserLogged userLogged) {
        super(storedRequest, userAuthentication);
        this.userLogged = userLogged;
    }

    @Override
    public Object getPrincipal() {
        return this.userLogged;
    }
}
