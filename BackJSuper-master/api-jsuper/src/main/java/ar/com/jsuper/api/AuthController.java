package ar.com.jsuper.api;

import ar.com.jsuper.api.utils.Auth;
import ar.com.jsuper.api.utils.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

//    @Resource(name = "tokenServices")
//    ConsumerTokenServices tokenServices;
//
//    @Autowired
//    @Qualifier("oauthTokenStore")
//    TokenStore oauthTokenStore;
//
//    @Autowired
//    @Qualifier("tokenServices")
//    ConsumerTokenServices tokenServices;
//
//    @RequestMapping(value = "/tokens", method = RequestMethod.GET, produces = "application/json")
//    public List<String> getTokens() {
//        List<String> tokenValues = new ArrayList<String>();
//        Collection<OAuth2AccessToken> tokens = oauthTokenStore.findTokensByClientId("clienteweb");
//        if (tokens != null) {
//            for (OAuth2AccessToken token : tokens) {
//                tokenValues.add(token.getValue());
//            }
//        }
//        return tokenValues;
//    }
//

    @RequestMapping(value = "/token/revoke", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> revokeToken(@RequestBody Auth token) {
        /*
        en relidad no  revoka el token jwt porque no seria posible, habira que investigar un poco mas
         */
//        OAuth2AccessToken accessToken = oauthTokenStore.readAccessToken(token.getToken());
//        oauthTokenStore.removeAccessToken(accessToken);
//        tokenServices.revokeToken(accessToken.getValue());
        return new ResponseEntity<>(new Response(token, HttpStatus.OK.value(), "Se revoko el token exitosamente"), HttpStatus.OK);
    }
}
