package ar.com.jsuper.dto;

import ar.com.jsuper.domain.Sucursales;
import java.util.Set;
import java.io.Serializable;

public class FilterProductoDTO implements Serializable {

    private Set<FamiliaDTO> familias;
    private ProductoDTO producto;
    private Integer activo; //0= inactivos, 1: activos, 2 : todos
    private Integer tipo; //1= smiples, 2: compuestos, 0 : todos
    private Integer filterStock; //0= todos, 1: stockMinimo, 2 : puntoReposicion
    private Boolean showStock;
    private Set<Sucursales> sucursales;

    public FilterProductoDTO() {
    }

    public Set<FamiliaDTO> getFamilias() {
        return familias;
    }

    public void setFamilias(Set<FamiliaDTO> familias) {
        this.familias = familias;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
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
