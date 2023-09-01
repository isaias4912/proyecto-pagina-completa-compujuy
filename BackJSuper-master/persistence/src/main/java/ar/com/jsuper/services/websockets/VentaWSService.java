/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.websockets;

/**
 *
 * @author rafa
 */
public interface VentaWSService {

    void sendMsgNuevaVenta(Message message);
    void sendMsgNuevaVenta(Message message, String  userName);
}
