package ar.com.jsuper.dto;

public class FilterOfertaDTO {

    private String nombre;
    private Integer activo;
    private Integer vigente;

    public FilterOfertaDTO() {
    }

    public Integer getVigente() {
        return vigente;
    }

    public void setVigente(Integer vigente) {
        this.vigente = vigente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

}
