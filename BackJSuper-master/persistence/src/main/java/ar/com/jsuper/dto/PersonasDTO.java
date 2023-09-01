/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author rafa22
 */
public class PersonasDTO {

    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private String cuil;
    private String direccion;
    private String ciudad;
    private Boolean isCliente;
    private Integer sexo;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date fechAlta;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date fechaNac;
    private Set<DomiciliosDTO> domicilios;
    private Set<ContactosDTO> contactos;
    private Set<TelefonosDTO> telefonos;
    private ClienteSimpleDTO cliente;

    public PersonasDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public Date getFechAlta() {
        return fechAlta;
    }

    public void setFechAlta(Date fechAlta) {
        this.fechAlta = fechAlta;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public Set<DomiciliosDTO> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(Set<DomiciliosDTO> domicilios) {
        this.domicilios = domicilios;
    }

    public Set<ContactosDTO> getContactos() {
        return contactos;
    }

    public void setContactos(Set<ContactosDTO> contactos) {
        this.contactos = contactos;
    }

    public Set<TelefonosDTO> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(Set<TelefonosDTO> telefonos) {
        this.telefonos = telefonos;
    }

    public Boolean getIsCliente() {
        return isCliente;
    }

    public void setIsCliente(Boolean isCliente) {
        this.isCliente = isCliente;
    }

    public ClienteSimpleDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteSimpleDTO cliente) {
        this.cliente = cliente;
    }
    
}
