package ar.com.jsuper.dto;

import java.util.Set;

public class FilterCajaDTO {

    private String nombre;
    private String nombreMaquina;
    private Integer activo;
    private Set<Integer> sucursales;

    public FilterCajaDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreMaquina() {
        return nombreMaquina;
    }

    public void setNombreMaquina(String nombreMaquina) {
        this.nombreMaquina = nombreMaquina;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Set<Integer> getSucursales() {
        return sucursales;
    }

    public void setSucursales(Set<Integer> sucursales) {
        this.sucursales = sucursales;
    }

}
