/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author rafael
 */
public class CajaListDTO implements Serializable {

    private int id;
    private String nombre;
    private String ipMaquina;
    private String nombreMaquina;
    private String observacion;
    private Boolean activo;
    private Set<SucursalesMinDTO> sucursales;

    public CajaListDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIpMaquina() {
        return ipMaquina;
    }

    public void setIpMaquina(String ipMaquina) {
        this.ipMaquina = ipMaquina;
    }

    public String getNombreMaquina() {
        return nombreMaquina;
    }

    public void setNombreMaquina(String nombreMaquina) {
        this.nombreMaquina = nombreMaquina;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<SucursalesMinDTO> getSucursales() {
        return sucursales;
    }

    public void setSucursales(Set<SucursalesMinDTO> sucursales) {
        this.sucursales = sucursales;
    }

}
