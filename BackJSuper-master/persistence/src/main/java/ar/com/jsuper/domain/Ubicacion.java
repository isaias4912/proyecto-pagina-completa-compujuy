/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.domain;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rafa22
 */
@Entity
@Table(name = "ubicaciones")
public class Ubicacion {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "detalle")
    private String detalle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursales_id")
    private Sucursales sucursal;
    @OneToMany(mappedBy = "ubicacion", fetch = FetchType.LAZY)
    private Set<UbicacionStock> ubicacionStocks;

    public Ubicacion() {
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Sucursales getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursales sucursal) {
        this.sucursal = sucursal;
    }

    public Set<UbicacionStock> getUbicacionStocks() {
        return ubicacionStocks;
    }

    public void setUbicacionStocks(Set<UbicacionStock> ubicacionStocks) {
        this.ubicacionStocks = ubicacionStocks;
    }

}
