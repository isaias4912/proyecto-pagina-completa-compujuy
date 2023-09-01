package ar.com.jsuper.dto;

import java.util.Set;

public class FamiliasDTO {

    private int id;
    private String path;
    private String nombreCorto;
    private String nombre;
    private int nivel;
    private Set<FamiliasDTO> familias;

    public FamiliasDTO() {
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

    public Set<FamiliasDTO> getFamilias() {
        return familias;
    }

    public void setFamilias(Set<FamiliasDTO> familias) {
        this.familias = familias;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

}
