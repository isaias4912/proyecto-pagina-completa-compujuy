/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.domain.Cliente;

/**
 *
 * @author rafael
 */
public class FilterMovCtaCte {

    private Cliente cliente;
    private Integer estado;
    private Integer idFacturaVenta;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getIdFacturaVenta() {
        return idFacturaVenta;
    }

    public void setIdFacturaVenta(Integer idFacturaVenta) {
        this.idFacturaVenta = idFacturaVenta;
    }

}
