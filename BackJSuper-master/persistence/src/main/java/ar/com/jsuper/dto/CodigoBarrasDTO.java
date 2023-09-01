package ar.com.jsuper.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CodigoBarrasDTO extends ObjectDTO implements Serializable {

    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private int tipo;
    private BigDecimal cantidadBulto;

    public CodigoBarrasDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getCantidadBulto() {
        return cantidadBulto;
    }

    public void setCantidadBulto(BigDecimal cantidadBulto) {
        this.cantidadBulto = cantidadBulto;
    }

}
