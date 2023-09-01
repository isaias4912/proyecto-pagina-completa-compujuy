package ar.com.jsuper.utils;


public class AccionEntity<T> {

    private T data;
    private String accion;

    public AccionEntity() {
    }

    public AccionEntity(T data, String accion) {
        this.data = data;
        this.accion = accion;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
