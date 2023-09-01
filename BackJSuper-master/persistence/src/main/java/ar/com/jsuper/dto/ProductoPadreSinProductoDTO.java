package ar.com.jsuper.dto;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author RAFAEL
 */
public class ProductoPadreSinProductoDTO  implements Serializable {

    private int id;
    private String nombre;
    private String nombreCorto;
    private BigDecimal contenidoNeto;
    private String detalle;
    private boolean activo;
    private FamDTO familia;

    public ProductoPadreSinProductoDTO() {
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

    public FamDTO getFamilia() {
        return familia;
    }

    public void setFamilia(FamDTO familia) {
        this.familia = familia;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
