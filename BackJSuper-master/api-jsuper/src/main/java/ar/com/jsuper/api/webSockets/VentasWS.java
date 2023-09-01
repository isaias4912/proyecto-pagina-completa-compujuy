/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api.webSockets;

import ar.com.jsuper.domain.CustomUser;
import ar.com.jsuper.services.VentasService;
import ar.com.jsuper.services.websockets.Message;
import ar.com.jsuper.services.websockets.VentaWSService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rafa
 */
@RestController
@RequestMapping("/api/v1/message")
public class VentasWS {

//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//    @Autowired
//    private VentaWSService ventaWSService;
    @Autowired
    private VentasService ventasService;
//    @SendTo("/topic/messages")

//    @MessageMapping("/chat")
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void send(Principal principal) throws Exception {
//        System.out.println("Enviando mensaje--------------------------------------------");
//        ventaWSService.sendMsgNuevaVenta(new Message(9, "Nueva venta"));
//        ventasService.test();
//        int i = 0;
//        while (true) {
//            i++;
//            Thread.sleep(5000); // simulated delay
//            System.out.println("Name:" + principal.getName());
//            messagingTemplate.convertAndSendToUser(principal.getName(), "/topic/messages", new VentaMessage("Test 88ws " + i));
//        }
    }

}
