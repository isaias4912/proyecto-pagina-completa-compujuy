/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author rafael
 */
@RequestMapping(value = "/500")
public class ErrorController {

    public @ResponseBody
    String handleException(HttpServletRequest req) {
        Throwable exception = (Throwable) req.getAttribute("javax.servlet.error.exception");
        return "Internal server error.";
    }
}
