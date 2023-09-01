/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Set;

/**
 *
 * @author rafa22
 */
public class UbicacionDTO {

    private int id;
    private String nombre;
    private String detalle;
//    @JsonManagedReference
    @JsonIgnoreProperties("ubicacion")
    private Set<UbicacionStockDTO> ubicacionStocks;

    public UbicacionDTO() {
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Set<UbicacionStockDTO> getUbicacionStocks() {
        return ubicacionStocks;
    }

    public void setUbicacionStocks(Set<UbicacionStockDTO> ubicacionStocks) {
        this.ubicacionStocks = ubicacionStocks;
    }

   
}
