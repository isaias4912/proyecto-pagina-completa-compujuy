package ar.com.jsuper.dto;

import java.io.Serializable;

public class MenuItemDataDTO implements Serializable {

    private int id;
    private String nombre;
    private Integer tipoHref;
    private String href;
    private String classCss;
    private String title;
    private Integer orden;

    public MenuItemDataDTO() {
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

    public Integer getTipoHref() {
        return tipoHref;
    }

    public void setTipoHref(Integer tipoHref) {
        this.tipoHref = tipoHref;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getClassCss() {
        return classCss;
    }

    public void setClassCss(String classCss) {
        this.classCss = classCss;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

}
