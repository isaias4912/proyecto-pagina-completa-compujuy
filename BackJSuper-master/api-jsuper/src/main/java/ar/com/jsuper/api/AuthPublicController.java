package ar.com.jsuper.api;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.services.FacebookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
public class AuthPublicController {

//    @Autowired
//    FacebookService facebookService;
//
//    @RequestMapping(value = "/facebook", params = {"access_token"}, method = RequestMethod.GET, produces = "application/json")
//    public OAuth2AccessToken getToken(@RequestParam(value = "access_token", required = true) String accessToken)  throws BussinessException {
//        System.out.println("roken:"+accessToken);
//        return facebookService.getToken(accessToken);
//    }

}
