package ar.com.jsuper.domain;

import java.io.Serializable;

public class SucursalesAuthDTO implements Serializable {

    private String nombre;
    private String id;

    public SucursalesAuthDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
