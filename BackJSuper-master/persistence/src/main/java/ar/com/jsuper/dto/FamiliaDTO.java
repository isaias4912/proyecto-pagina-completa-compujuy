package ar.com.jsuper.dto;

import java.io.Serializable;

public class FamiliaDTO implements Serializable {

    private int id;
    private String path;
    private FamDTO familia;
    private String nombre;
    private String nombreCorto;
    private int nivel;

    public FamiliaDTO() {
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public FamDTO getFamilia() {
        return familia;
    }

    public void setFamilia(FamDTO familia) {
        this.familia = familia;
    }

}
