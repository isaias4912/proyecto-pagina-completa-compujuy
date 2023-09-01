package ar.com.jsuper.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author RAFAEL
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoMinDTO implements Serializable {

    private int id;
    private String codigoEspecial;
    private String nombre;
    private String nombreFinal;

    public ProductoMinDTO() {
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

    public String getNombreFinal() {
        return nombreFinal;
    }

    public void setNombreFinal(String nombreFinal) {
        this.nombreFinal = nombreFinal;
    }

    public String getCodigoEspecial() {
        return codigoEspecial;
    }

    public void setCodigoEspecial(String codigoEspecial) {
        this.codigoEspecial = codigoEspecial;
    }

}
