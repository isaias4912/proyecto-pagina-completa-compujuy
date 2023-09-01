/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api.webSockets;

/**
 *
 * @author rafa
 */
public class VentaMessage {

    public String producto;

    public VentaMessage(String producto) {
        this.producto = producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

}
