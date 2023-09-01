/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

/**
 *
 * @author rafa22
 */
public class ExistClienteOrPersonaDTO {

    private Boolean isExistCliente;
    private Boolean isExistPersona;
    private ClienteDTO cliente;
    private PersonasDTO personas;

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

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public PersonasDTO getPersonas() {
        return personas;
    }

    public void setPersonas(PersonasDTO personas) {
        this.personas = personas;
    }

}
