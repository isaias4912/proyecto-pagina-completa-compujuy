package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 *
 * @author RAFAEL
 */
public class ProductoPadreDTO implements Serializable {

    private int id;
    private String nombre;
    private String nombreCorto;
    private BigDecimal contenidoNeto;
    private String detalle;
    private boolean activo;
    private FamiliaDTO familia;
    @JsonIgnoreProperties("productoPadre")
    private Set<ProductoDTO> productosDTO;

    public ProductoPadreDTO() {
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

    public BigDecimal getContenidoNeto() {
        return contenidoNeto;
    }

    public void setContenidoNeto(BigDecimal contenidoNeto) {
        this.contenidoNeto = contenidoNeto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

//    public RubrosDTO getRubro() {
//        return rubro;
//    }
//
//    public void setRubro(RubrosDTO rubro) {
//        this.rubro = rubro;
//    }
    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public FamiliaDTO getFamilia() {
        return familia;
    }

    public void setFamilia(FamiliaDTO familia) {
        this.familia = familia;
    }

    public Set<ProductoDTO> getProductosDTO() {
        return productosDTO;
    }

    public void setProductosDTO(Set<ProductoDTO> productosDTO) {
        this.productosDTO = productosDTO;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

}
