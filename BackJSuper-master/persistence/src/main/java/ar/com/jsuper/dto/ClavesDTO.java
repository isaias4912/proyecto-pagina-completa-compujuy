/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.io.Serializable;

/**
 *
 * @author rafael
 */
public class ClavesDTO implements Serializable {

    private int id;
    private String clave;
    private String detalle;
    private UsuariosMinDTO usuario;

    public ClavesDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public UsuariosMinDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosMinDTO usuario) {
        this.usuario = usuario;
    }

}
