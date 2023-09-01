/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.websockets.impl;

import ar.com.jsuper.services.impl.BaseService;
import ar.com.jsuper.services.websockets.Message;
import ar.com.jsuper.services.websockets.VentaWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author rafa
 */
@Service
public class VentaWSServiceImpl extends BaseService implements VentaWSService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendMsgNuevaVenta(Message message) {
        messagingTemplate.convertAndSendToUser(this.getUserName().trim(), "/topic/messages", message);
    }

    @Override
    public void sendMsgNuevaVenta(Message message, String  userName) {
        messagingTemplate.convertAndSendToUser(userName, "/topic/messages", message);
    }

}
