package ar.com.jsuper.domain.utils;

import java.util.Set;

import ar.com.jsuper.domain.Familias;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.Sucursales;

public class FilterProducto {

    private Producto producto;
    private Boolean showStock;
    private Integer activo; //0= inactivos, 1: activos, 2 : todos
    private Integer filterStock; //0= todos, 1: stockMinimo, 2 : puntoReposicion
    private Integer tipo; //1= simples, 2: compuestos, 0 : todos
    private Set<Familias> familias;
    private Set<Sucursales> sucursales;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Set<Familias> getFamilias() {
        return familias;
    }

    public void setFamilias(Set<Familias> familias) {
        this.familias = familias;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Set<Sucursales> getSucursales() {
        return sucursales;
    }

    public void setSucursales(Set<Sucursales> sucursales) {
        this.sucursales = sucursales;
    }

    public Boolean getShowStock() {
        return showStock;
    }

    public void setShowStock(Boolean showStock) {
        this.showStock = showStock;
    }

    public Integer getFilterStock() {
        return filterStock;
    }

    public void setFilterStock(Integer filterStock) {
        this.filterStock = filterStock;
    }

}
