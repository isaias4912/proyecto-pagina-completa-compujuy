/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.controllers;

import ar.com.jsuper.services.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rafa
 */
@RestController
public class HelloController {

    @Autowired
    ProductosService productosService;
    
//    @RequestMapping("/")
//    public String index() throws BussinessException{
//        productosService.getall();
//        return "Greetings from Spring Boot!";
//    }
}
