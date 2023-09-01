/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.dto;

import java.util.Set;

/**
 *
 * @author rafa22
 */
public class ProductoUpdateDTO {

    private ProductoDTO producto;
    private Set<ImagenProductoDTO> dataImagenes;

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public Set<ImagenProductoDTO> getDataImagenes() {
        return dataImagenes;
    }

    public void setDataImagenes(Set<ImagenProductoDTO> dataImagenes) {
        this.dataImagenes = dataImagenes;
    }

}
