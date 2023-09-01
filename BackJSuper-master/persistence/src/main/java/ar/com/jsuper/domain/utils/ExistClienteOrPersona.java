/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain.utils;

import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.Personas;

/**
 *
 * @author rafa22
 */
public class ExistClienteOrPersona {
    
    private Boolean isExistCliente;
    private Boolean isExistPersona;
    private Cliente cliente;
    private Personas personas;

    public Boolean getIsExistCliente() {
        return isExistCliente;
    }

    public void setIsExistCliente(Boolean isExistCliente) {
        this.isExistCliente = isExistCliente;
    }

    public Boolean getIsExistPersona() {
        return isExistPersona;
    }

    public void setIsExistPersona(Boolean isExistPersona) {
        this.isExistPersona = isExistPersona;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Personas getPersonas() {
        return personas;
    }

    public void setPersonas(Personas personas) {
        this.personas = personas;
    }
    
    
}
