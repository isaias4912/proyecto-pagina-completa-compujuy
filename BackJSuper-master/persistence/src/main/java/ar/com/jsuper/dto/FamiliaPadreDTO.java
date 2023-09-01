package ar.com.jsuper.dto;

import java.io.Serializable;

public class FamiliaPadreDTO implements Serializable {

    private Integer id;
    private String nombre;
    private String nombreCorto;
    private int nivel;
    private FamiliaPadreDTO familia;

    public FamiliaPadreDTO() {
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public FamiliaPadreDTO getFamilia() {
        return familia;
    }

    public void setFamilia(FamiliaPadreDTO familia) {
        this.familia = familia;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

}
