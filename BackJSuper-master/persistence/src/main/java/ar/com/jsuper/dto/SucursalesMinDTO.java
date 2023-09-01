package ar.com.jsuper.dto;

import java.io.Serializable;

public class SucursalesMinDTO implements Serializable {

    private int id;
    private String keyid;
    private String nombre;
    private boolean principal;

    public SucursalesMinDTO() {
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

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

}
