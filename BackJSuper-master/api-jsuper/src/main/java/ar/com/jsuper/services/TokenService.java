/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services;

import ar.com.jsuper.domain.CustomUser;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 *
 * @author rafael
 */
public interface TokenService {

    OAuth2AccessToken createToken(CustomUser userDetail);
}
