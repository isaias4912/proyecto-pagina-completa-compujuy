/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.security;

import ar.com.jsuper.domain.CustomUser;
import ar.com.jsuper.domain.SucursalesAuthDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 *
 * @author rafa
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    HttpServletRequest request;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("app_id", user.getIdApp());
        additionalInfo.put("id_user", user.getIdUser());
        additionalInfo.put("id_suc", this.getSucursalSelected(user.getSucursales()));
//        additionalInfo.put("suc", user.getSucursales());
        additionalInfo.put("key_avatar", user.getKeyAvatar());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(
                additionalInfo);
        return accessToken;
    }

    private String getSucursalSelected(List<SucursalesAuthDTO> sucursales) {
        if (request.getParameterMap().containsKey("sucursal")) {
            String response = null;
            String id = request.getParameter("sucursal").trim();
            for (SucursalesAuthDTO sucursal : sucursales) {
                if (Objects.equals(id, sucursal.getId())) {
                    response = sucursal.getId();
                }
            }
            if (Objects.isNull(response)) {
                throw new UnauthorizedUserException("No habilitado para la sucursal seleccionada");
            } else {
                return response;
            }
        } else {
            // si no existe el parametro mandamos la primer sucursal
            return sucursales.get(0).getId();
        }
    }
}
