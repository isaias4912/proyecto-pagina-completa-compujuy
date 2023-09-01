package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FamiliasArbolDTO {

    private int id;
    private String path;
    private String nombre;
    private int nivel;

    private Set<FamiliasArbolDTO> familias;

    public FamiliasArbolDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("text")
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
    
    @JsonProperty("nodes")
    public Set<FamiliasArbolDTO> getFamilias() {
        return familias;
    }

    public void setFamilias(Set<FamiliasArbolDTO> familias) {
        this.familias = familias;
    }

}
