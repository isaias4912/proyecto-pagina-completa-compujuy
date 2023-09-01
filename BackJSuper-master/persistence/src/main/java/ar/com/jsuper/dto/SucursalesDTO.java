package ar.com.jsuper.dto;

import java.io.Serializable;

public class SucursalesDTO implements Serializable {

    private int id;
    private String nombre;
    private String direccion;
    private String prefijoTelFijo;
    private String numeroTelFijo;
    private String prefijoTelCel;
    private String numeroTelCel;
    private String detalle;
    private boolean principal;
    private boolean activo;

    public SucursalesDTO() {
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public String getPrefijoTelFijo() {
        return prefijoTelFijo;
    }

    public void setPrefijoTelFijo(String prefijoTelFijo) {
        this.prefijoTelFijo = prefijoTelFijo;
    }

    public String getNumeroTelFijo() {
        return numeroTelFijo;
    }

    public void setNumeroTelFijo(String numeroTelFijo) {
        this.numeroTelFijo = numeroTelFijo;
    }

    public String getPrefijoTelCel() {
        return prefijoTelCel;
    }

    public void setPrefijoTelCel(String prefijoTelCel) {
        this.prefijoTelCel = prefijoTelCel;
    }

    public String getNumeroTelCel() {
        return numeroTelCel;
    }

    public void setNumeroTelCel(String numeroTelCel) {
        this.numeroTelCel = numeroTelCel;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
